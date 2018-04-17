package com.webgame.game.server.serialization.dto.event.impl;

import com.webgame.game.server.serialization.dto.event.AbstractDTOEvent;
import com.webgame.game.server.serialization.dto.Attack;
import io.vertx.core.http.ServerWebSocket;

public class AttackDTOEvent extends AbstractDTOEvent {
    Attack dto;

    public AttackDTOEvent(ServerWebSocket webSocket, Attack dto) {
        super(webSocket);
        setDto(dto);
    }

    public Attack getDto() {
        return dto;
    }

    public void setDto(Attack dto) {
        this.dto = dto;
    }


}
