package com.webgame.game.server;

import com.webgame.game.server.handlers.AbstractWebSocketHandler;
import com.webgame.game.server.handlers.CustomWebSocketHandler;
import com.webgame.game.server.handlers.ServerTimerHandler;
import com.webgame.game.server.sessions.SessionContainer;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.*;

import java.util.concurrent.ConcurrentHashMap;

public class ServerApp {
    private final Vertx vertx = Vertx.vertx();

    public ServerApp() {

    }

    public static void main(final String... args) throws Exception {
        new ServerApp().launch();
    }

    private void launch() {
        System.out.println("Launching web socket server...");

       CustomWebSocketHandler webSocketHandler = new CustomWebSocketHandler();

        HttpServerOptions hso = new HttpServerOptions();
        hso.setMaxWebsocketMessageSize(10000000);
        hso.setMaxWebsocketFrameSize(10000000);

        HttpServer server = vertx.createHttpServer(hso);

        server.websocketHandler( webSocketHandler).listen(8000);

        vertx.setPeriodic(1000, webSocketHandler.getTimerHandler());
    }
}
