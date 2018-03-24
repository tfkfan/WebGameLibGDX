package com.webgame.game.events.ws;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.github.czyzby.websocket.WebSocket;
import com.webgame.game.server.serialization.dto.player.LoginDTO;

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
