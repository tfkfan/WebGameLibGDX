package com.webgame.game.server.handlers;

import com.github.czyzby.websocket.serialization.impl.JsonSerializer;
import com.webgame.game.entities.player.Player;
import com.webgame.game.server.serialization.dto.event.impl.LoginDTOEvent;
import com.webgame.game.server.serialization.dto.event.impl.PlayerDTOEvent;
import com.webgame.game.server.serialization.dto.event.listeners.DTOEventListener;
import com.webgame.game.server.serialization.dto.event.listeners.LoginDTOEventListener;
import com.webgame.game.server.serialization.dto.event.listeners.PlayerDTOEventListener;
import com.webgame.game.server.serialization.dto.player.LoginDTO;
import com.webgame.game.server.serialization.dto.player.PlayerDTO;
import com.webgame.game.server.sessions.SessionContainer;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.http.WebSocketFrame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractWebSocketHandler implements Handler<ServerWebSocket> {
    private final JsonSerializer jsonSerializer = new JsonSerializer();
    private final ConcurrentHashMap<Long, SessionContainer> sessions = new ConcurrentHashMap<>();

    private final List<DTOEventListener> loginEventList = Collections.synchronizedList(new ArrayList<DTOEventListener>());
    private final List<DTOEventListener> playerEventList = Collections.synchronizedList(new ArrayList<DTOEventListener>());

    public AbstractWebSocketHandler() {

    }

    @Override
    public void handle(ServerWebSocket webSocket) {
        webSocket.frameHandler(frame -> handleJsonFrame(webSocket, frame));
    }

    private void handleJsonFrame(final ServerWebSocket webSocket, final WebSocketFrame frame) {
        try {
            final byte[] packet = frame.binaryData().getBytes();
            final long start = System.nanoTime();
            final Object deserialized = jsonSerializer.deserialize(packet);

            afterDeserializationHandler(webSocket, deserialized);

            final long time = System.nanoTime() - start;

            //final String response = "Packet had " + packet.length + " bytes. Class: " + deserialized.getClass().getSimpleName()
            //       + ", took " + time + " nanos to deserialize.";
            // System.out.println("(" + deserialized.toString() + ") " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeResponse(final ServerWebSocket webSocket, final Object obj) {
        if (obj == null || webSocket == null)
            return;
        synchronized (webSocket) {
            webSocket.writeFinalBinaryFrame(Buffer.buffer(jsonSerializer.serialize(obj)));
        }
    }

    public void afterDeserializationHandler(final ServerWebSocket webSocket, final Object obj) {
        if (obj == null)
            return;

        if (obj instanceof LoginDTO) {
            LoginDTO loginDTO = (LoginDTO) obj;
            LoginDTOEvent event = new LoginDTOEvent(webSocket, loginDTO);
            for (DTOEventListener listener : loginEventList)
                listener.handle(event);
        } else if (obj instanceof PlayerDTO) {
            PlayerDTO playerDTO = (PlayerDTO) obj;
            PlayerDTOEvent event = new PlayerDTOEvent(webSocket, playerDTO);
            for (DTOEventListener listener : playerEventList)
                listener.handle(event);
        }
    }


    public void addPlayerDTOListener(PlayerDTOEventListener listener) {
        playerEventList.add(listener);
    }

    public void addLoginDTOListener(LoginDTOEventListener listener) {
        loginEventList.add(listener);
    }

    protected ConcurrentHashMap<Long, SessionContainer> getSessions() {
        return sessions;
    }

    public void writeResponseToAll(final Object obj) {
        for (SessionContainer sessionContainer : sessions.values()) {
            ServerWebSocket session = sessionContainer.getSession();
            synchronized (session) {
                this.writeResponse(session, obj);
            }
        }
    }

    public void writeResponseToAllExcept(final ServerWebSocket currentSession, final Object obj) {
        for (SessionContainer sessionContainer : sessions.values()) {
            ServerWebSocket session = sessionContainer.getSession();
            synchronized (session) {
                if (session.equals(currentSession))
                    continue;

                this.writeResponse(sessionContainer.getSession(), obj);
            }
        }
    }
}
