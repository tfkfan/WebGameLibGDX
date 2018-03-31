package com.webgame.game.events.ws;

import com.badlogic.gdx.utils.Array;
import com.github.czyzby.websocket.WebSocket;
import com.webgame.game.server.serialization.dto.player.AttackDTO;
import com.webgame.game.server.serialization.dto.player.PlayerDTO;


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
