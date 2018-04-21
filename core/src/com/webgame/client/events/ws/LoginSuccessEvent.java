package com.webgame.client.events.ws;

import com.github.czyzby.websocket.WebSocket;
import com.webgame.server.dto.LoginDTO;

public class LoginSuccessEvent extends AbstractWSEvent {
    LoginDTO loginDTO;


    public LoginSuccessEvent(WebSocket webSocket, LoginDTO loginDTO) {
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
