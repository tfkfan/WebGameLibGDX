package com.webgame.server_app.handlers;

import com.github.czyzby.websocket.serialization.impl.JsonSerializer;
import com.webgame.common.server.dto.AttackDTO;
import com.webgame.common.server.dto.LoginDTO;
import com.webgame.common.server.entities.Player;
import com.webgame.server_app.event.impl.AttackDTOEvent;
import com.webgame.server_app.event.impl.LoginDTOEvent;
import com.webgame.server_app.event.impl.PlayerDTOEvent;
import com.webgame.server_app.event.listeners.AttackDTOEventListener;
import com.webgame.server_app.event.listeners.DTOEventListener;
import com.webgame.server_app.event.listeners.LoginDTOEventListener;
import com.webgame.server_app.event.listeners.PlayerDTOEventListener;
import io.vertx.core.Handler;
import io.vertx.core.http.ServerWebSocket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractWebSocketHandler implements Handler<ServerWebSocket> {
    private final JsonSerializer jsonSerializer = new JsonSerializer();

    private final List<DTOEventListener> loginEventList = Collections.synchronizedList(new ArrayList<DTOEventListener>());
    private final List<DTOEventListener> playerEventList = Collections.synchronizedList(new ArrayList<DTOEventListener>());
    private final List<DTOEventListener> attackEventList = Collections.synchronizedList(new ArrayList<DTOEventListener>());

    public AbstractWebSocketHandler() {
    }

    @Override
    public void handle(final ServerWebSocket webSocket) {
        webSocket.binaryMessageHandler(event -> {
            Object obj = jsonSerializer.deserialize(event.getBytes());
            if (obj == null)
                return;

            if (obj instanceof LoginDTO) {
                LoginDTO loginDTO = (LoginDTO) obj;
                LoginDTOEvent loginEvent = new LoginDTOEvent(webSocket, loginDTO);
                for (DTOEventListener listener : loginEventList)
                    listener.handle(loginEvent);
            } else if (obj instanceof Player) {
                Player player = (Player) obj;
                PlayerDTOEvent playerDTOEvent = new PlayerDTOEvent(webSocket, player);
                for (DTOEventListener listener : playerEventList)
                    listener.handle(playerDTOEvent);
            } else if (obj instanceof AttackDTO) {
                AttackDTO attackDTO = (AttackDTO) obj;
                AttackDTOEvent attackDTOEvent = new AttackDTOEvent(webSocket, attackDTO);
                for (DTOEventListener listener : attackEventList)
                    listener.handle(attackDTOEvent);
            }
        });
    }

    public void addAttackDTOListener(AttackDTOEventListener listener) {
        attackEventList.add(listener);
    }

    public void addPlayerDTOListener(PlayerDTOEventListener listener) {
        playerEventList.add(listener);
    }

    public void addLoginDTOListener(LoginDTOEventListener listener) {
        loginEventList.add(listener);
    }

    protected JsonSerializer getJsonSerializer() {
        return jsonSerializer;
    }
}
