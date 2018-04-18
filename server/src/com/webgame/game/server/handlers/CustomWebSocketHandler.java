package com.webgame.game.server.handlers;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.Configs;
import com.webgame.game.entities.player.ClientPlayer;
import com.webgame.game.entities.player.impl.Mage;
import com.webgame.game.enums.EntityState;
import com.webgame.game.enums.MarkState;
import com.webgame.game.enums.MoveState;
import com.webgame.game.enums.SkillKind;
import com.webgame.game.events.PlayerDamagedEvent;
import com.webgame.game.server.dto.LoginDTO;
import com.webgame.game.server.entities.Player;
import com.webgame.game.server.entities.Skill;
import io.vertx.core.TimeoutStream;
import io.vertx.core.http.ServerWebSocket;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.webgame.game.server.utils.ServerUtils.*;

public final class CustomWebSocketHandler extends AbstractWebSocketHandler {
    protected static final int delay = 30;
    protected static final float absVel = 15;
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

                        //boolean finalAction = !(skillType.equals(SkillKind.FALLING_AOE) ||
                        //skillType.equals(SkillKind.TIMED_AOE) || skillType.equals(SkillKind.TIMED_BUFF)
                        //|| skillType.equals(SkillKind.TIMED_SINGLE));
                        //if (!(clientSkill instanceof AOEClientSkill) && clientSkill.isMarked())
                        //    continue;

                        for (Player anotherPlayer : getPlayers().values()) {
                            if (anotherPlayer == currentPlayer)
                                continue;

                            //handling collision
                            if (skillType.isAoe()) {
                                if (Intersector.overlaps(anotherPlayer.getShape(), currentSkill.getArea())) {
                                    Integer damage = currentSkill.getDamage();
                                    if (anotherPlayer.getHealthPoints() > 0)
                                        anotherPlayer.setHealthPoints(anotherPlayer.getHealthPoints() - damage);
                                    else
                                        anotherPlayer.setHealthPoints(0);

                                }
                            } else {
                                if (Intersector.overlaps(currentSkill.getShape(), anotherPlayer.getShape()) && currentSkill.getMarkState().equals(MarkState.UNMARKED)) {
                                    Integer damage = currentSkill.getDamage();
                                    if (anotherPlayer.getHealthPoints() > 0)
                                        anotherPlayer.setHealthPoints(anotherPlayer.getHealthPoints() - damage);
                                    else
                                        anotherPlayer.setHealthPoints(0);

                                    currentSkill.setMarkState(MarkState.MARKED);
                                    currentSkill.setMoveState(MoveState.STATIC);
                                    skillIdsToRemove.add(currentSkill.getId());
                                }
                            }
                        }

                        if (!skillType.isAoe() && currentSkill.getEntityState().equals(EntityState.ACTIVE) && isCollided(currentSkill.getPosition(), currentSkill.getTarget())) {
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

            Map<String, Skill> skills = playerDTO.getSkills();

            Vector2 vel = new Vector2(target.x - playerPos.x, target.y - playerPos.y);
            vel.nor();
            vel.scl(absVel / Configs.PPM);

            Skill skillDTO = Skill.createSkill(newUUID(),
                    MoveState.MOVING, EntityState.ACTIVE, event.getDto().getSkillType(), target, playerPos, vel);

            skillDTO.cast(event.getDto().getTarget());

            skills.put(skillDTO.getId(), skillDTO);
            playerDTO.setSkills(skills);
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

    protected static boolean isCollided(Vector2 skillPos, Vector2 target) {
        if (skillPos.x >= target.x - dl && skillPos.x <= target.x + dl &&
                skillPos.y >= target.y - dl && skillPos.y <= target.y + dl)
            return true;
        return false;
    }
}
