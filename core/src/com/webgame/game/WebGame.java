package com.webgame.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.webgame.game.events.LoginEvent;
import com.webgame.game.events.listeners.LoginListener;
import com.webgame.game.screens.LoginScreen;
import com.webgame.game.screens.MainScreen;

public class WebGame extends Game {
    private MainScreen mainScreen = new MainScreen();
    private LoginScreen loginScreen = new LoginScreen();

    @Override
    public void create() {
        loginScreen.addLoginListener(new LoginListener() {
            @Override
            public void customHandle(LoginEvent event) {
                try {
                    mainScreen.login(event.getUsername(), event.getPassword());
                    WebGame.this.setScreen(mainScreen);
                } catch (Exception e) {
                    //loginScreen.setMessage(e.getMessage());
                }
            }
        });
        this.setScreen(loginScreen);
    }
}
