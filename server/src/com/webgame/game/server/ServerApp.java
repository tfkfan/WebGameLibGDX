package com.webgame.game.server;

import com.webgame.game.server.handlers.CustomWebSocketHandler;
import com.webgame.game.server.utils.ServerUtils;
import io.vertx.core.*;
import io.vertx.core.http.*;

public final class ServerApp extends AbstractVerticle {
    private CustomWebSocketHandler webSocketHandler;
    private HttpServer server;
    private final HttpServerOptions hso;

    public ServerApp() {
        hso = new HttpServerOptions();
        hso.setMaxWebsocketMessageSize(100000000);
        hso.setMaxWebsocketFrameSize(100000000);

        webSocketHandler = new CustomWebSocketHandler();
    }

    public static void main(final String... args) {
        ServerApp appVerticle = new ServerApp();
        ServerUtils.vertx.deployVerticle(appVerticle);
    }

    @Override
    public void start(Future<Void> future) {
        System.out.println("Launching web socket server...");

        server = getVertx().createHttpServer(hso);
        server.websocketStream().handler(webSocketHandler);
        server.websocketStream().endHandler(event -> {
            webSocketHandler.closeDispatcher();
        });
        server.websocketStream().exceptionHandler(event -> {
            System.out.println(event.getMessage());
            event.fillInStackTrace();
            webSocketHandler.closeDispatcher();
        });
        server.websocketHandler(webSocketHandler).listen(8000);

        future.complete();
    }
}
