package com.webgame.game.server.handlers;

import com.badlogic.gdx.math.Vector2;
import com.webgame.game.Configs;
import com.webgame.game.enums.EntityState;
import com.webgame.game.enums.MoveState;
import com.webgame.game.enums.SkillTypeState;
import com.webgame.game.server.serialization.dto.player.LoginDTO;
import com.webgame.game.server.serialization.dto.player.PlayerDTO;
import com.webgame.game.server.serialization.dto.skill.SkillDTO;
import io.vertx.core.TimeoutStream;
import io.vertx.core.http.ServerWebSocket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import static com.webgame.game.server.utils.ServerUtils.*;

public final class CustomWebSocketHandler extends AbstractWebSocketHandler {
    protected static final int delay = 30;
    protected static final float absVel = 15;
    protected static final float dl = 0.1f;

    private TimeoutStream timeoutStream;
    private final ConcurrentHashMap<String, ServerWebSocket> sessions;
    private final ConcurrentHashMap<String, PlayerDTO> players;

    public CustomWebSocketHandler() {
        sessions = new ConcurrentHashMap<>();
        players = new ConcurrentHashMap<>();

        timeoutStream = vertx.periodicStream(delay);
        timeoutStream.handler((Long event) -> {
            if (getSessions().isEmpty())
                return;

            //Skills calculatings
            final Collection<PlayerDTO> plrs = getPlayers().values();
            //Inactive skills to remove
            final Map<String, List<String>> skillsToRemove = new ConcurrentHashMap<>();
            for (PlayerDTO currentDTO : plrs) {
                final Map<String, SkillDTO> skills = currentDTO.getSkills();
                final List<String> skillIdsToRemove = Collections.synchronizedList(new ArrayList<>());
                if (skills != null) {
                    for (Iterator<SkillDTO> it = skills.values().iterator(); it.hasNext(); ) {
                        final SkillDTO skillDTO = it.next();
                        final SkillTypeState skillType = skillDTO.getSkillType();
                        if (skillDTO.getMoveState().equals(MoveState.MOVING))
                            skillDTO.getPosition().add(skillDTO.getVelocity());

                        boolean finalAction = !(skillType.equals(SkillTypeState.FALLING_AOE) ||
                        skillType.equals(SkillTypeState.TIMED_AOE) || skillType.equals(SkillTypeState.TIMED_BUFF)
                        || skillType.equals(SkillTypeState.TIMED_SINGLE));

                        if (finalAction && skillDTO.getEntityState().equals(EntityState.ACTIVE) && isCollided(skillDTO.getPosition(), skillDTO.getTarget())) {
                            skillDTO.setMoveState(MoveState.STATIC);
                            skillIdsToRemove.add(skillDTO.getId());
                        }
                        currentDTO.getSkills().put(skillDTO.getId(), skillDTO);
                    }
                }
                skillsToRemove.put(currentDTO.getId(), skillIdsToRemove);
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
            LoginDTO succesLoginDTO = new LoginDTO(new PlayerDTO(newUUID(), loginDTO.getName(), new Vector2(2, 2)));

            writeResponse(event.getWebSocket(), succesLoginDTO, getJsonSerializer());
        });

        addPlayerDTOListener(event -> {
            final PlayerDTO playerDTO = event.getPlayerDTO();
            final String id = playerDTO.getId();

            getSessions().put(id, event.getWebSocket());

            PlayerDTO currentDTO = getPlayers().get(id);

            if (currentDTO != null)
                currentDTO.updateBy(playerDTO);
            else
                currentDTO = playerDTO;

            if (currentDTO != null)
                getPlayers().put(id, currentDTO);
        });

        addAttackDTOListener(event -> {
            final String plrId = event.getDto().getId();
            final PlayerDTO playerDTO = getPlayers().get(plrId);

            if (playerDTO == null)
                return;

            final Vector2 target = event.getDto().getTarget();
            final Vector2 playerPos = playerDTO.getPosition();

            Map<String, SkillDTO> skills = playerDTO.getSkills();

            Vector2 vel = new Vector2(target.x - playerPos.x, target.y - playerPos.y);
            vel.nor();
            vel.scl(absVel / Configs.PPM);

            SkillDTO skillDTO = new SkillDTO(newUUID(),
                    MoveState.MOVING, EntityState.ACTIVE, event.getDto().getSkillType(), target, playerPos, vel);

            skills.put(skillDTO.getId(), skillDTO);
            playerDTO.setSkills(skills);
            getPlayers().put(plrId, playerDTO);
        });
    }

    protected ConcurrentHashMap<String, ServerWebSocket> getSessions() {
        return sessions;
    }

    protected ConcurrentHashMap<String, PlayerDTO> getPlayers() {
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
