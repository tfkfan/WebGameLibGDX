package com.webgame.game.server.handlers;

import com.badlogic.gdx.math.Vector2;
import com.webgame.game.Configs;
import com.webgame.game.enums.EntityState;
import com.webgame.game.enums.MoveState;
import com.webgame.game.server.serialization.dto.player.LoginDTO;
import com.webgame.game.server.serialization.dto.player.PlayerDTO;
import com.webgame.game.server.serialization.dto.skill.SkillDTO;
import io.vertx.core.TimeoutStream;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.webgame.game.server.utils.ServerUtils.*;

public final class CustomWebSocketHandler extends AbstractWebSocketHandler {
    public static final int delay = 30;

    protected static final float absVel = 15;
    protected static final float dl = 0.1f;

    private TimeoutStream timeoutStream;

    public CustomWebSocketHandler() {
        timeoutStream = vertx.periodicStream(delay);
        timeoutStream.handler(event -> {
            if (getSessions().isEmpty())
                return;


            final Collection<PlayerDTO> plrs = getPlayers().values();
            final Map<Long, List<Long>> skillsToRemove = new ConcurrentHashMap<>();
            for(PlayerDTO currentDTO : plrs) {
                final Map<Long, SkillDTO> skills = currentDTO.getSkills();
                final List<Long> skillIdsToRemove = Collections.synchronizedList(new ArrayList<>());
                if (skills != null) {
                    for (Iterator<SkillDTO> it = skills.values().iterator(); it.hasNext(); ) {
                        final SkillDTO skillDTO = it.next();

                        if (skillDTO.getMoveState().equals(MoveState.MOVING))
                            skillDTO.getPosition().add(skillDTO.getVelocity());

                        if (skillDTO.getEntityState().equals(EntityState.ACTIVE) && isCollided(skillDTO.getPosition(), skillDTO.getTarget())) {
                            skillDTO.setMoveState(MoveState.STATIC);
                            skillIdsToRemove.add(skillDTO.getId());
                           // skillDTO.setEntityState(EntityState.INACTIVE);
                        }

                        currentDTO.getSkills().put(skillDTO.getId(), skillDTO);
                    }
                }

                skillsToRemove.put(currentDTO.getId(), skillIdsToRemove);
            }

            writeResponseToAll(getSessions().values(), new ArrayList<>(plrs), getJsonSerializer());


            for(Map.Entry<Long, List<Long>> entry: skillsToRemove.entrySet()){
               for(Long skillId : entry.getValue()){
                   getPlayers().get(entry.getKey()).getSkills().remove(skillId);
               }
            }
        });

        addLoginDTOListener(event -> {
            LoginDTO loginDTO = event.getLoginDTO();

            PlayerDTO playerDTO = new PlayerDTO();
            playerDTO.setName(loginDTO.getName());
            playerDTO.setId(getSessions().size());
            playerDTO.setPosition(new Vector2(2, 2));

            LoginDTO succesLoginDTO = new LoginDTO(playerDTO);

            writeResponse(event.getWebSocket(), succesLoginDTO, getJsonSerializer());
        });

        addPlayerDTOListener(event -> {
            PlayerDTO playerDTO = event.getPlayerDTO();

            Long id = playerDTO.getId();
            getSessions().put(id, event.getWebSocket());

            PlayerDTO currentDTO = getPlayers().get(id);
            if (currentDTO != null) {
                currentDTO.updateBy(playerDTO);

            } else
                currentDTO = playerDTO;

            if (currentDTO != null)
                getPlayers().put(id, currentDTO);
        });

        addAttackDTOListener(event -> {
            Long plrId = event.getDto().getId();
            PlayerDTO playerDTO = getPlayers().get(plrId);
            if (playerDTO == null)
                return;

            final Vector2 target = event.getDto().getTarget();
            final Vector2 playerPos = playerDTO.getPosition();

            Map<Long, SkillDTO> skills = playerDTO.getSkills();

            Vector2 vel = new Vector2(target.x - playerPos.x, target.y - playerPos.y);
            vel.nor();
            vel.scl(absVel / Configs.PPM);

            SkillDTO skillDTO = new SkillDTO();
            skillDTO.setMoveState(MoveState.MOVING);
            skillDTO.setEntityState(EntityState.ACTIVE);
            skillDTO.setTarget(target);
            skillDTO.setVelocity(vel);
            skillDTO.setPosition(playerPos);
           // skillDTO.setFinishedState(EntityFinishedState.ANIMATED);

            final Long skillId = UUID.randomUUID().getMostSignificantBits();
            skillDTO.setId(skillId);

            skills.put(skillId, skillDTO);

            playerDTO.setSkills(skills);

            getPlayers().put(plrId, playerDTO);
        });
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
