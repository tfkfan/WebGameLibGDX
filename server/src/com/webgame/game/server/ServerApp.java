package com.webgame.game.server;

import com.webgame.game.server.handlers.CustomWebSocketHandler;
import com.webgame.game.server.utils.ServerUtils;
import io.vertx.core.*;
import io.vertx.core.http.*;

public final class ServerApp extends AbstractVerticle {
    private CustomWebSocketHandler webSocketHandler;
    private HttpServer server;

    public ServerApp() {
        final HttpServerOptions hso = new HttpServerOptions();
        hso.setMaxWebsocketMessageSize(100000000);
        hso.setMaxWebsocketFrameSize(100000000);

        webSocketHandler = new CustomWebSocketHandler();
        server = vertx.createHttpServer(hso);
    }

    public static void main(final String... args) {
        ServerApp appVerticle = new ServerApp();
        ServerUtils.vertx.deployVerticle(appVerticle);
    }

    @Override
    public void start(Future<Void> future) {
        System.out.println("Launching web socket server...");

        server.websocketStream().handler(webSocketHandler);
        server.websocketStream().endHandler(event -> {
        });
        server.websocketStream().exceptionHandler(event -> {
            System.out.println(event.getMessage());
        });
        server.websocketHandler(webSocketHandler).listen(8000);

        future.complete();
    }
}
