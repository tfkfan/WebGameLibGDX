package com.webgame.game.ws;

import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.WebSockets;
import com.github.czyzby.websocket.net.ExtendedNet;
import com.webgame.game.Configs;

public abstract class AbstractWebSocketAdapter implements IWebSocket {
    private WebSocket socket;

    @Override
    public void connect() {
        // Note: you can also use WebSockets.newSocket() and WebSocket.toWebSocketUrl() methods.
        socket = ExtendedNet.getNet().newWebSocket(Configs.SERVER_HOST, Configs.SERVER_PORT);
        socket.addListener(createListener());
        socket.connect();
    }

    @Override
    public WebSocket getSocket() {
        return socket;
    }

    protected abstract WebSocketListener createListener();

    @Override
    public void dispose() {
        WebSockets.closeGracefully(socket);
    }
}