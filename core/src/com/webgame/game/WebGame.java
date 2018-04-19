package com.webgame.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.webgame.game.events.LoginEvent;
import com.webgame.game.events.listeners.LoginListener;
import com.webgame.game.screens.LoginScreen;
import com.webgame.game.screens.MainScreen;

public class WebGame extends Game {
    private MainScreen mainScreen;
    private LoginScreen loginScreen;

    @Override
    public void create() {
        mainScreen = new MainScreen();
        loginScreen = new LoginScreen();
        loginScreen.addLoginListener(event -> {
            try {
                LoginEvent loginEvent = (LoginEvent) event;
                mainScreen.login(loginEvent.getUsername(), loginEvent.getPassword());
                WebGame.this.setScreen(mainScreen);
            } catch (Exception e) {
                //loginScreen.setMessage(e.getMessage());
            } finally {
                return true;
            }
        });
        this.setScreen(loginScreen);
    }
}
