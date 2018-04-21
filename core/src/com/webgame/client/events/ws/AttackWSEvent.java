package com.webgame.client.events.ws;

import com.github.czyzby.websocket.WebSocket;
import com.webgame.server.dto.AttackDTO;


public class AttackWSEvent extends AbstractWSEvent {
    AttackDTO attackDTO;

    public AttackWSEvent(WebSocket webSocket, AttackDTO attackDTO) {
        super(webSocket);
        setAttackDTO(attackDTO);
    }

    public AttackDTO getAttackDTO() {
        return attackDTO;
    }

    public void setAttackDTO(AttackDTO attackDTO) {
        this.attackDTO = attackDTO;
    }


}
