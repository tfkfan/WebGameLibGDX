package com.webgame.game.server.handlers;

import com.badlogic.gdx.math.Vector2;
import com.webgame.game.server.serialization.dto.event.impl.AttackDTOEvent;
import com.webgame.game.server.serialization.dto.event.listeners.AttackDTOEventListener;
import com.webgame.game.server.serialization.dto.player.LoginDTO;
import com.webgame.game.server.serialization.dto.player.PlayerDTO;
import com.webgame.game.server.serialization.dto.skill.SkillDTO;
import io.vertx.core.TimeoutStream;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.webgame.game.server.utils.ServerUtils.*;

public class CustomWebSocketHandler extends AbstractWebSocketHandler {
    public static final int delay = 20;

    private TimeoutStream timeoutStream;

    public CustomWebSocketHandler() {
       timeoutStream =  vertx.periodicStream(delay);
       timeoutStream.handler(event -> {
           if(getSessions().isEmpty())
               return;

           writeResponseToAll(getSessions().values(), new ArrayList<>(getPlayers().values()), getJsonSerializer());
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
            if(currentDTO != null) {
                currentDTO.updateBy(playerDTO);
                if (currentDTO.getSkills() != null)
                    System.out.println("skills null");
            }else
                currentDTO = playerDTO;

            getPlayers().put(id, currentDTO);

        });

        addAttackDTOListener(event -> {
            Long plrId = event.getDto().getId();
            PlayerDTO playerDTO = getPlayers().get(plrId);
            if(playerDTO == null)
                return;

            final Vector2 target = event.getDto().getTarget();
            final Vector2 position = playerDTO.getPosition();

            Map<Long, SkillDTO> skills = playerDTO.getSkills();
            if(skills == null)
                skills = new ConcurrentHashMap<>();

            SkillDTO skillDTO = new SkillDTO();
            skillDTO.setTarget(target);
            skillDTO.setPosition(position);

            final Long skillId = Long.valueOf(skills.values().size());
            skillDTO.setId(skillId);

            skills.put(skillId , skillDTO);

            playerDTO.setSkills(skills);

            getPlayers().put(plrId, playerDTO);
        });
    }

    public void closeDispatcher(){
        timeoutStream.cancel();
    }

}
