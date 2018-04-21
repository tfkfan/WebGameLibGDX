package com.webgame.server_app.event.impl;

import com.webgame.common.server.dto.LoginDTO;
import com.webgame.server_app.event.AbstractDTOEvent;
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
