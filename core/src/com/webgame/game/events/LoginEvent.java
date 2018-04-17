package com.webgame.game.events;

import com.badlogic.gdx.scenes.scene2d.Event;

public class LoginEvent extends Event {
    String username;
    String password;

    public LoginEvent() {

    }

    public LoginEvent(String username, String password) {
       setUsername(username);
       setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
