package com.webgame.game.server.handlers;

import com.github.czyzby.websocket.serialization.impl.JsonSerializer;
import com.webgame.game.entities.player.Player;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.http.WebSocketFrame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractWebSocketHandler implements Handler<ServerWebSocket> {
    private final JsonSerializer jsonSerializer = new JsonSerializer();


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

            final String response = "Packet had " + packet.length + " bytes. Class: " + deserialized.getClass().getSimpleName()
                    + ", took " + time + " nanos to deserialize.";
            System.out.println("(" + deserialized.toString() + ") " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void writeResponse(final ServerWebSocket webSocket, final Object obj) {
        if (obj == null || webSocket == null)
            return;

        webSocket.writeFinalBinaryFrame(Buffer.buffer(jsonSerializer.serialize(obj)));
    }

    public abstract void afterDeserializationHandler(final ServerWebSocket webSocket, final Object obj);

    private static void handleStringFrame(final ServerWebSocket webSocket, final WebSocketFrame frame) {
        final String response = "Packet had " + frame.binaryData().length()
                + " bytes. Cannot deserialize packet class.";
        System.out.println(response);
        webSocket.writeFinalTextFrame(response);
    }
}
