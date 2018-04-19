package com.webgame.game.server.handlers;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.Configs;
import com.webgame.game.entities.player.ClientPlayer;
import com.webgame.game.entities.player.impl.Mage;
import com.webgame.game.entities.skill.impl.FallingClientSkill;
import com.webgame.game.entities.skill.impl.SingleClientSkill;
import com.webgame.game.enums.*;
import com.webgame.game.events.PlayerDamagedEvent;
import com.webgame.game.server.dto.LoginDTO;
import com.webgame.game.server.entities.Player;
import com.webgame.game.server.entities.Skill;
import com.webgame.game.utils.GameUtils;
import io.vertx.core.Handler;
import io.vertx.core.TimeoutStream;
import io.vertx.core.http.ServerWebSocket;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.webgame.game.Configs.PPM;
import static com.webgame.game.server.utils.ServerUtils.*;

public final class CustomWebSocketHandler extends AbstractWebSocketHandler {
    protected static final int delay = 50;
    protected static final float absVel = 10;
    protected static final float dl = 0.15f;

    private TimeoutStream timeoutStream;
    private final ConcurrentHashMap<String, ServerWebSocket> sessions;
    private final ConcurrentHashMap<String, Player> players;

    public CustomWebSocketHandler() {
        sessions = new ConcurrentHashMap<>();
        players = new ConcurrentHashMap<>();

        timeoutStream = vertx.periodicStream(delay);
        timeoutStream.handler((Long event) -> {
            if (getSessions().isEmpty())
                return;

            //Skills calculatings
            final Collection<Player> plrs = getPlayers().values();
            //Inactive skills to remove
            final Map<String, List<String>> skillsToRemove = new ConcurrentHashMap<>();
            for (Player currentPlayer : plrs) {
                final Map<String, Skill> skills = currentPlayer.getSkills();
                final List<String> skillIdsToRemove = Collections.synchronizedList(new ArrayList<>());
                if (skills != null) {
                    for (Iterator<Skill> it = skills.values().iterator(); it.hasNext(); ) {
                        final Skill currentSkill = it.next();
                        final SkillKind skillType = currentSkill.getSkillType();
                        if (currentSkill.getMoveState().equals(MoveState.MOVING))
                            currentSkill.getPosition().add(currentSkill.getVelocity());

                        for (Player anotherPlayer : getPlayers().values()) {
                            if (anotherPlayer == currentPlayer)
                                continue;

                            //handling collision
                            if (skillType.getSkillClass().equals(SkillClass.AOE)) {
                                if (Intersector.overlaps(anotherPlayer.getShape(), currentSkill.getArea())) {
                                    getPlayerDamaged(anotherPlayer, currentSkill);
                                }
                            } else if (skillType.getSkillClass().equals(SkillClass.SINGLE)) {
                                if (Intersector.overlaps(currentSkill.getShape(), anotherPlayer.getShape()) && currentSkill.getMarkState().equals(MarkState.UNMARKED)) {
                                    getPlayerDamaged(anotherPlayer, currentSkill);

                                    currentSkill.setMarkState(MarkState.MARKED);
                                    currentSkill.setMoveState(MoveState.STATIC);
                                    skillIdsToRemove.add(currentSkill.getId());
                                }
                            }
                        }

                        if (!skillType.getSkillClass().equals(SkillClass.AOE) && currentSkill.getEntityState().equals(EntityState.ACTIVE) && isCollided(currentSkill.getPosition(), currentSkill.getTarget())) {
                            currentSkill.setMoveState(MoveState.STATIC);
                            skillIdsToRemove.add(currentSkill.getId());
                        }
                        currentPlayer.getSkills().put(currentSkill.getId(), currentSkill);
                    }
                }
                skillsToRemove.put(currentPlayer.getId(), skillIdsToRemove);
            }

            writeResponseToAll(getSessions().values(), new ArrayList<>(plrs), getJsonSerializer());

            skillsToRemove.entrySet().stream().forEach(entry -> {

                entry.getValue().stream().forEach(skillId -> {
                    getPlayers().get(entry.getKey()).getSkills().remove(skillId);
                });

            });
        });

        addLoginDTOListener(event -> {
            LoginDTO loginDTO = event.getLoginDTO();

            final Player player = new Mage();
            player.init();
            player.setId(newUUID());
            player.setName(loginDTO.getName());
            player.setSpritePath(Configs.PLAYERSHEETS_FOLDER + "/mage.png");
            player.setPosition(new Vector2(2, 2));

            float[] animSizes1 = {FrameSizes.ANIMATION.getW(), FrameSizes.ANIMATION.getH()};
            float[] standSizes1 = {FrameSizes.LITTLE_SPHERE.getW(), FrameSizes.LITTLE_SPHERE.getH()};

            List<Skill> allClientSkills = new ArrayList<Skill>();

            Skill clientSkill1 = new SingleClientSkill();
            clientSkill1.setDamage(150);
            clientSkill1.setSkillType(SkillKind.FIRE_BALL);
            clientSkill1.setSpritePath(Configs.SKILLSHEETS_FOLDER + "/fire_002.png");
            clientSkill1.setAnimSpritePath(Configs.SKILLSHEETS_FOLDER + "/s001.png");
            clientSkill1.setCooldown(calcTime(3, 0));
            clientSkill1.setSizes(animSizes1, standSizes1);

            allClientSkills.add(clientSkill1);


           /* Skill clientSkill2 = new FallingClientSkill();
            clientSkill2.setCooldown(GameUtils.calcTime(10,0));
            clientSkill2.setDamage(1);
            clientSkill2.setSkillType(SkillKind.BLIZZARD);
            allClientSkills.add(clientSkill2);

            Texture skill3Texture =  GameUtils.loadSprite(Configs.SKILLSHEETS_FOLDER + "/cast_001.png");
            ClientSkill clientSkill3 = new BuffClientSkill(this, null, new BuffAnimation(skill3Texture));
            clientSkill3.setSkillType(SkillKind.MAGIC_DEFENCE);
            allClientSkills.add(clientSkill3);*/

            player.setAllSkills(allClientSkills);


            loginDTO.setPlayer(player);

            writeResponse(event.getWebSocket(), loginDTO, getJsonSerializer());
        });

        addPlayerDTOListener(event -> {
            final Player playerDTO = event.getPlayerDTO();
            final String id = playerDTO.getId();

            getSessions().put(id, event.getWebSocket());

            Player currentDTO = getPlayers().get(id);

            if (currentDTO != null)
                currentDTO.updateBy(playerDTO);
            else
                currentDTO = playerDTO;

            if (currentDTO != null)
                getPlayers().put(id, currentDTO);
        });

        addAttackDTOListener(event -> {
            final String plrId = event.getDto().getId();
            final Player playerDTO = getPlayers().get(plrId);

            if (playerDTO == null)
                return;

            final Vector2 target = event.getDto().getTarget();
            final Vector2 playerPos = playerDTO.getPosition();

            Vector2 vel = new Vector2(target.x - playerPos.x, target.y - playerPos.y);
            vel.nor();
            vel.scl(absVel / Configs.PPM);

            String skillId = newUUID();
            Skill skillDTO = playerDTO.castSkill(target, vel, skillId,
                    new Rectangle(0, 0, 100 / PPM, 100 / PPM));

            skillDTO.setDamage(100);
            getPlayers().put(plrId, playerDTO);
        });
    }

    public static void getPlayerDamaged(Player damagedPlayer, Skill skill) {
        Integer damage = skill.getDamage();
        if (damagedPlayer.getHealthPoints() > 0)
            damagedPlayer.setHealthPoints(damagedPlayer.getHealthPoints() - damage);
        else
            damagedPlayer.setHealthPoints(0);
    }

    protected ConcurrentHashMap<String, ServerWebSocket> getSessions() {
        return sessions;
    }

    protected ConcurrentHashMap<String, Player> getPlayers() {
        return players;
    }

    public void closeDispatcher() {
        timeoutStream.cancel();
    }

    protected static boolean isCollided(Vector2 skillPos, Vector2 target) {
        if (skillPos.x >= target.x - dl && skillPos.x <= target.x + dl &&
                skillPos.y >= target.y - dl && skillPos.y <= target.y + dl)
            return true;
        return false;
    }
}
