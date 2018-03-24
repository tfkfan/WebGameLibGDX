package com.webgame.game.events.ws;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.github.czyzby.websocket.WebSocket;

public abstract class AbstractWSEvent extends Event {
    protected WebSocket webSocket;

    public AbstractWSEvent(WebSocket webSocket) {
        setWebSocket(webSocket);
    }

    public WebSocket getWebSocket() {
        return webSocket;
    }

    public void setWebSocket(WebSocket webSocket) {
        this.webSocket = webSocket;
    }
}
