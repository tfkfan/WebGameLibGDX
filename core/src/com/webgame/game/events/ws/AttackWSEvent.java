package com.webgame.game.events.ws;

import com.github.czyzby.websocket.WebSocket;
import com.webgame.game.server.serialization.dto.Attack;


public class AttackWSEvent extends AbstractWSEvent {
    Attack attackDTO;

    public AttackWSEvent(WebSocket webSocket, Attack attackDTO) {
        super(webSocket);
        setAttackDTO(attackDTO);
    }

    public Attack getAttackDTO() {
        return attackDTO;
    }

    public void setAttackDTO(Attack attackDTO) {
        this.attackDTO = attackDTO;
    }


}
