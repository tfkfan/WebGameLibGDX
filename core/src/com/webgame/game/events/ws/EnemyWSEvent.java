package com.webgame.game.events.ws;

import com.github.czyzby.websocket.WebSocket;
import com.webgame.game.server.serialization.dto.player.EnemyDTO;
import com.webgame.game.server.serialization.dto.player.PlayerDTO;

public class EnemyWSEvent extends AbstractWSEvent {
    EnemyDTO enemyDTO;

    public EnemyWSEvent(WebSocket webSocket, EnemyDTO enemyDTO) {
        super(webSocket);
        setEnemyDTO(enemyDTO);
    }

    public PlayerDTO getEnemyDTO() {
        return enemyDTO;
    }

    public void setEnemyDTO(EnemyDTO enemyDTO) {
        this.enemyDTO = enemyDTO;
    }
}
