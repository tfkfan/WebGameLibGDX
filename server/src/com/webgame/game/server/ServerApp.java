package com.webgame.game.server;

import com.webgame.game.server.handlers.CustomWebSocketHandler;
import io.vertx.core.*;
import io.vertx.core.http.*;

public class ServerApp extends AbstractVerticle {
    public static final Vertx vertx = Vertx.vertx();

    public ServerApp() {

    }

    public static void main(final String... args) throws Exception {
       vertx.deployVerticle(new ServerApp());
    }

    @Override
    public void start(Future<Void> future){
        System.out.println("Launching web socket server...");
        CustomWebSocketHandler webSocketHandler = new CustomWebSocketHandler();

        HttpServerOptions hso = new HttpServerOptions();
        hso.setMaxWebsocketMessageSize(100000000);
        hso.setMaxWebsocketFrameSize(100000000);

        HttpServer server = vertx.createHttpServer(hso);
        server.websocketHandler(webSocketHandler).listen(8000);

        future.complete();
    }


}
