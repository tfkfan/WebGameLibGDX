package com.webgame.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.webgame.game.events.LoginEvent;
import com.webgame.game.screens.LoginScreen;
import com.webgame.game.screens.MainScreen;

public class WebGame extends Game {
    private MainScreen mainScreen = new MainScreen();
    private LoginScreen loginScreen = new LoginScreen();

    @Override
    public void create() {
        loginScreen.addLoginListener(event -> {
            LoginEvent loginEvent = (LoginEvent) event;
            try {
                mainScreen.login(loginEvent.getUsername(), loginEvent.getPassword());
            } catch (Exception e) {

                //loginScreen.setMessage(e.getMessage());
                return false;
            } finally {
                WebGame.this.setScreen(mainScreen);
                return true;
            }

        });
        this.setScreen(loginScreen);
    }
}
