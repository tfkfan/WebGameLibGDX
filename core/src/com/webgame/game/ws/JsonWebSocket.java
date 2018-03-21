package com.webgame.game.ws;

import com.badlogic.gdx.Gdx;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketHandler;
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.data.WebSocketCloseCode;
import com.webgame.game.Configs;
import com.webgame.game.ws.json.JsonClientArrayMessage;
import com.webgame.game.ws.json.JsonClientMessage;
import com.webgame.game.ws.server.ServerResponse;

import java.lang.reflect.Array;

public abstract class JsonWebSocket extends AbstractWebSocketAdapter {
    @Override
    public void send(final String message) {
        sendPacket(message);
    }

    @Override
    public void send(Object message) {
        sendPacket(message);
    }

    @Override
    public void send(final String[] message) {
        sendPacket(message);
    }

    private void sendPacket(final Object packet) {
        final long start = System.currentTimeMillis();
        final byte[] serialized = getSocket().getSerializer().serialize(packet);
        final long time = System.currentTimeMillis() - start;
        Gdx.app.log("JSON", "Serialized packet in " + time + " millies.");
        getSocket().send(serialized);
    }
}
