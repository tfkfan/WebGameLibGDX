package com.webgame.client.server.dto.event;

import io.vertx.core.http.ServerWebSocket;

public class AbstractDTOEvent implements DTOEvent {
    protected ServerWebSocket webSocket;
    public AbstractDTOEvent(ServerWebSocket webSocket){
        setWebSocket(webSocket);
    }

    public ServerWebSocket getWebSocket() {
        return webSocket;
    }

    public void setWebSocket(ServerWebSocket webSocket) {
        this.webSocket = webSocket;
    }
}
