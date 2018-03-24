package com.webgame.game.server.serialization.dto.event.impl;

import com.webgame.game.server.serialization.dto.DTO;
import com.webgame.game.server.serialization.dto.event.AbstractDTOEvent;
import com.webgame.game.server.serialization.dto.event.DTOEvent;
import com.webgame.game.server.serialization.dto.player.LoginDTO;
import io.vertx.core.http.ServerWebSocket;

public class LoginDTOEvent extends AbstractDTOEvent {
    private LoginDTO loginDTO;

    public LoginDTOEvent(ServerWebSocket webSocket, LoginDTO loginDTO) {
        super(webSocket);
        setLoginDTO(loginDTO);
    }

    public LoginDTO getLoginDTO() {
        return loginDTO;
    }

    public void setLoginDTO(LoginDTO loginDTO) {
        this.loginDTO = loginDTO;
    }

}
