package com.webgame.game.server;

import com.github.czyzby.websocket.serialization.impl.JsonSerializer;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.*;

import java.util.Properties;

public class ServerApp {
    private final Vertx vertx = Vertx.vertx();

    private final JsonSerializer jsonSerializer;

    public ServerApp() {
        jsonSerializer = new JsonSerializer();
    }

    public static void main(final String... args) throws Exception {
        new ServerApp().launch();
    }

    private void launch() {
        System.out.println("Launching web socket server...");

        HttpServerOptions hso = new HttpServerOptions();
        hso.setMaxWebsocketFrameSize(211111);

        HttpServer server = vertx.createHttpServer(hso);

        server.connectionHandler(new Handler<HttpConnection>() {
            @Override
            public void handle(HttpConnection event) {
                System.out.println("Connected!");
            }
        });
        server.websocketHandler(webSocket -> {

            webSocket.frameHandler(frame -> handleJsonFrame(webSocket, frame));

        }).listen(8000);
    }

    private static void handleStringFrame(final ServerWebSocket webSocket, final WebSocketFrame frame) {
        final String response = "Packet had " + frame.binaryData().length()
                + " bytes. Cannot deserialize packet class.";
        System.out.println(response);
        webSocket.writeFinalTextFrame(response);
    }

    private void handleJsonFrame(final ServerWebSocket webSocket, final WebSocketFrame frame) {
        final byte[] packet = frame.binaryData().getBytes();
        final long start = System.nanoTime();
        final Object deserialized = jsonSerializer.deserialize(packet);
        final long time = System.nanoTime() - start;

        final String response =  "Packet had " + packet.length + " bytes. Class: " + deserialized.getClass().getSimpleName()
                + ", took " + time + " nanos to deserialize.";
        System.out.println("(" + deserialized.toString() +") "+ response);
        webSocket.writeFinalBinaryFrame(Buffer.buffer(jsonSerializer.serialize(response)));
    }

   /* private void handleSerializationFrame(final ServerWebSocket webSocket, final WebSocketFrame frame) {
        final byte[] packet = frame.binaryData().getBytes();
        final long start = System.nanoTime();
        final Object deserialized = serializer.deserialize(packet);
        final long time = System.nanoTime() - start;

        final ServerResponse response = new ServerResponse("Packet had " + packet.length + " bytes. Class: "
                + deserialized.getClass().getSimpleName() + ", took " + time + " nanos to deserialize.");
        System.out.println(response.getMessage());
        final byte[] serialized = serializer.serialize(response);
        webSocket.writeFinalBinaryFrame(Buffer.buffer(serialized));
    }*/
}
