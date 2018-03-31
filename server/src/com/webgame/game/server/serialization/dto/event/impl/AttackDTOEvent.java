package com.webgame.game.server.serialization.dto.event.impl;

import com.webgame.game.server.serialization.dto.event.AbstractDTOEvent;
import com.webgame.game.server.serialization.dto.player.AttackDTO;
import com.webgame.game.server.serialization.dto.player.PlayerDTO;
import io.vertx.core.http.ServerWebSocket;

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
