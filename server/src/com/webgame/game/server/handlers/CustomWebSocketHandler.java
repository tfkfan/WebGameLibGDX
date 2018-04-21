package com.webgame.client.server.handlers;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.webgame.client.Configs;
import com.webgame.client.entities.player.impl.Mage;
import com.webgame.client.entities.skill.impl.BuffClientSkill;
import com.webgame.client.entities.skill.impl.SingleClientSkill;
import com.webgame.client.enums.*;
import com.webgame.client.server.dto.LoginDTO;
import com.webgame.client.server.entities.Player;
import com.webgame.client.server.entities.Skill;
import com.webgame.client.server.factory.ISkillFactory;
import com.webgame.client.server.factory.impl.SkillFactory;
import io.vertx.core.TimeoutStream;
import io.vertx.core.http.ServerWebSocket;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.webgame.client.Configs.PPM;
import static com.webgame.client.server.utils.ServerUtils.*;

public final class CustomWebSocketHandler extends AbstractWebSocketHandler {
    protected static final int delay = 30;
    protected static final float absVel = 10;

    private TimeoutStream timeoutStream;
    private final ConcurrentHashMap<String, ServerWebSocket> sessions = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Player> players = new ConcurrentHashMap<>();
    private final ISkillFactory skillFactory = new SkillFactory();

    public CustomWebSocketHandler() {
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

                        if (!skillType.isFalling() && !skillType.isStatic() && currentSkill.getEntityState().equals(EntityState.ACTIVE)) {
                            if(isCollided(currentSkill.getPosition(), currentSkill.getTarget())) {
                                currentSkill.setMoveState(MoveState.STATIC);
                                skillIdsToRemove.add(currentSkill.getId());
                            }
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

            List<Skill> allClientSkills = new ArrayList<Skill>();

            allClientSkills.add(skillFactory.createSkill(SkillKind.FIRE_BALL));
            allClientSkills.add(skillFactory.createSkill(SkillKind.BLIZZARD));
            allClientSkills.add(skillFactory.createSkill(SkillKind.FIRE_EXPLOSION));
            allClientSkills.add(skillFactory.createSkill(SkillKind.ICE_BOLT));
            allClientSkills.add(skillFactory.createSkill(SkillKind.LIGHTNING));
            allClientSkills.add(skillFactory.createSkill(SkillKind.TORNADO));
            allClientSkills.add(skillFactory.createSkill(SkillKind.MAGIC_DEFENCE));

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

            final Vector2 vel = new Vector2(target.x - playerPos.x, target.y - playerPos.y);
            vel.nor();
            vel.scl(absVel / Configs.PPM);

            Skill skillDTO = playerDTO.castSkill(target, vel, newUUID(),
                    new Rectangle(0, 0, 100 / PPM, 100 / PPM));

            skillDTO.setDamage(100);

            getPlayers().put(plrId, playerDTO);
        });
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
}
