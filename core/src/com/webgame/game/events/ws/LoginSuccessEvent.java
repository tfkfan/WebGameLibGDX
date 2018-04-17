package com.webgame.game.events.ws;

import com.github.czyzby.websocket.WebSocket;
import com.webgame.game.server.serialization.dto.Login;

public class LoginSuccessEvent extends AbstractWSEvent {
    Login loginDTO;


    public LoginSuccessEvent(WebSocket webSocket, Login loginDTO) {
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
