package com.webgame.server.dto.event.impl;

import com.webgame.server.dto.event.AbstractDTOEvent;
import io.vertx.core.http.ServerWebSocket;
import com.webgame.server.dto.AttackDTO;

public class AttackDTOEvent extends AbstractDTOEvent {
    AttackDTO dto;

    public AttackDTOEvent(ServerWebSocket webSocket, AttackDTO dto) {
        super(webSocket);
        setDto(dto);
    }

    public AttackDTO getDto() {
        return dto;
    }

    public void setDto(AttackDTO dto) {
        this.dto = dto;
    }


}
