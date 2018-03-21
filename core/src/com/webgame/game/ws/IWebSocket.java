package com.webgame.game.ws;

import com.badlogic.gdx.utils.Disposable;
import com.github.czyzby.websocket.data.WebSocketCloseCode;

public interface IWebSocket extends Disposable {
    void connect();

    /**
     * @param message will be wrapped with a packet object (in necessary) and sent to the server.
     */
    void send(String message);

    /**
     * @param message will be wrapped with a packet object (in necessary) and sent to the server.
     */
    void send(Object message);

    /**
     * @param message will be wrapped with a packet object (in necessary) and sent to the server.
     */
    void send(String[] message);
}