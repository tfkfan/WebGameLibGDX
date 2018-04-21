package com.webgame.client.server.dto.event.impl;

import com.webgame.client.server.dto.LoginDTO;
import com.webgame.client.server.dto.event.AbstractDTOEvent;
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
