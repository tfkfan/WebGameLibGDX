package com.webgame.game.server;

import com.webgame.game.server.handlers.AbstractWebSocketHandler;
import com.webgame.game.server.handlers.CustomWebSocketHandler;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.*;

public class ServerApp {
    private final Vertx vertx = Vertx.vertx();

    public ServerApp() {

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
        server.websocketHandler(new CustomWebSocketHandler()).listen(8000);
    }
}
