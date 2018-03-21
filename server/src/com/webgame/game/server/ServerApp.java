package com.webgame.game.server;

import com.github.czyzby.websocket.serialization.impl.JsonSerializer;

import com.webgame.game.Configs;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.http.WebSocketFrame;

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
        HttpServer server = vertx.createHttpServer();
           /* server.websocketHandler(webSocket -> {
                // String test:
                webSocket.frameHandler(frame -> handleStringFrame(webSocket, frame));
            }).listen(8000);
            server = vertx.createHttpServer();*/
        server.websocketHandler(webSocket -> {
            // JSON test:
            webSocket.frameHandler(frame -> handleJsonFrame(webSocket, frame));
        }).listen(Configs.SERVER_PORT);
           /* server = vertx.createHttpServer();
            server.websocketHandler(webSocket -> {
                // Serialization test:
                webSocket.frameHandler(frame -> handleSerializationFrame(webSocket, frame));
            }).listen(8002);*/
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
        System.out.println(response);
        final byte[] serialized = jsonSerializer.serialize(response);
        webSocket.writeFinalBinaryFrame(Buffer.buffer(serialized));
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

   /* public static void main(final String... args) throws Exception {
        System.out.println("Launching web socket server...");
        final Vertx vertx = Vertx.vertx();
        final HttpServer server = vertx.createHttpServer();
        server.websocketHandler(webSocket -> {
            // Printing received packets to console:
            webSocket.frameHandler(frame -> System.out.println("Received packet: " + frame.textData()));
            // Sending a simple message:
            webSocket.writeFinalTextFrame("Hello from server!");
            // Closing the socket in 5 seconds:
            vertx.setTimer(5000L, id -> webSocket.close());
        }).listen(Configs.SERVER_PORT);
    }*/
}
