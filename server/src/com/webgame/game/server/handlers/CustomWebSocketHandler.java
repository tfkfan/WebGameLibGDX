package com.webgame.game.server.handlers;

import com.badlogic.gdx.math.Vector2;
import com.webgame.game.server.serialization.dto.event.impl.LoginDTOEvent;
import com.webgame.game.server.serialization.dto.event.listeners.impl.LoginDTOEventListener;
import com.webgame.game.server.serialization.dto.player.EnemyDTO;
import com.webgame.game.server.serialization.dto.player.LoginDTO;
import com.webgame.game.server.serialization.dto.player.PlayerConnectedDTO;
import com.webgame.game.server.serialization.dto.player.PlayerDTO;
import com.webgame.game.server.sessions.SessionContainer;
import io.vertx.core.http.ServerWebSocket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CustomWebSocketHandler extends AbstractWebSocketHandler {

    private final ConcurrentHashMap<Long, SessionContainer> sessions = new ConcurrentHashMap<>();

    private final List<LoginDTOEventListener> loginDTOEventList = Collections.synchronizedList(new ArrayList<LoginDTOEventListener>());

    public CustomWebSocketHandler() {
        addLoginDTOListener(new LoginDTOEventListener() {
            @Override
            public void handle(LoginDTOEvent event) {
                LoginDTO loginDTO = event.getLoginDTO();
                if (!sessions.containsKey(loginDTO)) {

                    PlayerDTO playerDTO = new PlayerDTO();
                    playerDTO.setName(loginDTO.getName());
                    playerDTO.setId(sessions.size());
                    playerDTO.setPosition(new Vector2(2, 2));


                    EnemyDTO enemyDTO = new EnemyDTO(playerDTO);
                    LoginDTO succesLoginDTO = new LoginDTO(playerDTO);
                    sessions.put(playerDTO.getId(), new SessionContainer(playerDTO, event.getWebSocket()));

                    writeResponse(event.getWebSocket(), succesLoginDTO);
                    writeResponseToAllExcept(event.getWebSocket(), enemyDTO);
                }
            }
        });
    }

    @Override
    public void afterDeserializationHandler(final ServerWebSocket webSocket, final Object obj) {
        if (obj == null)
            return;

        if (obj instanceof LoginDTO) {
            LoginDTO loginDTO = (LoginDTO) obj;
            LoginDTOEvent loginDTOEvent = new LoginDTOEvent(webSocket,loginDTO);
            for(LoginDTOEventListener loginDTOEventListener : loginDTOEventList)
                loginDTOEventListener.handle(loginDTOEvent);
        } else if (obj instanceof PlayerDTO) {
            PlayerDTO playerDTO = (PlayerDTO) obj;
            writeResponseToAll(playerDTO);
        }

    }

    public void addLoginDTOListener(LoginDTOEventListener listener) {
        loginDTOEventList.add(listener);
    }

    protected ConcurrentHashMap<Long, SessionContainer> getSessions() {
        return sessions;
    }

    public void writeResponseToAll(final Object obj) {
        for (SessionContainer sessionContainer : sessions.values()) {
            this.writeResponse(sessionContainer.getSession(), obj);
        }
    }

    public void writeResponseToAllExcept(final ServerWebSocket webSocket, final Object obj) {
        for (SessionContainer sessionContainer : sessions.values()) {
            if (sessionContainer.getSession().equals(webSocket))
                continue;

            this.writeResponse(sessionContainer.getSession(), obj);
        }
    }
}
