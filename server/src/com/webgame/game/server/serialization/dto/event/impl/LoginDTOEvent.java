package com.webgame.game.server.serialization.dto.event.impl;

import com.webgame.game.server.serialization.dto.event.AbstractDTOEvent;
import com.webgame.game.server.serialization.dto.Login;
import io.vertx.core.http.ServerWebSocket;

public class LoginDTOEvent extends AbstractDTOEvent {
    private Login loginDTO;

    public LoginDTOEvent(ServerWebSocket webSocket, Login loginDTO) {
        super(webSocket);
        setLoginDTO(loginDTO);
    }

    public Login getLoginDTO() {
        return loginDTO;
    }

    public void setLoginDTO(Login loginDTO) {
        this.loginDTO = loginDTO;
    }

}
