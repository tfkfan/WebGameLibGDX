package com.webgame.game.server.handlers;

import com.github.czyzby.websocket.serialization.impl.JsonSerializer;
import com.webgame.game.server.serialization.dto.event.impl.AttackDTOEvent;
import com.webgame.game.server.serialization.dto.event.impl.LoginDTOEvent;
import com.webgame.game.server.serialization.dto.event.impl.PlayerDTOEvent;
import com.webgame.game.server.serialization.dto.event.listeners.AttackDTOEventListener;
import com.webgame.game.server.serialization.dto.event.listeners.DTOEventListener;
import com.webgame.game.server.serialization.dto.event.listeners.LoginDTOEventListener;
import com.webgame.game.server.serialization.dto.event.listeners.PlayerDTOEventListener;
import com.webgame.game.server.serialization.dto.Attack;
import com.webgame.game.server.serialization.dto.Login;
import com.webgame.game.server.entities.Player;
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

            if (obj instanceof Login) {
                Login loginDTO = (Login) obj;
                LoginDTOEvent loginEvent = new LoginDTOEvent(webSocket, loginDTO);
                for (DTOEventListener listener : loginEventList)
                    listener.handle(loginEvent);
            } else if (obj instanceof Player) {
                Player playerDTO = (Player) obj;
                PlayerDTOEvent playerDTOEvent = new PlayerDTOEvent(webSocket, playerDTO);
                for (DTOEventListener listener : playerEventList)
                    listener.handle(playerDTOEvent);
            } else if (obj instanceof Attack) {
                Attack attackDTO = (Attack) obj;
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
