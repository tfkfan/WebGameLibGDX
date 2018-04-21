package com.webgame.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.webgame.client.events.LoginEvent;
import com.webgame.client.screens.LoginScreen;
import com.webgame.client.screens.MainScreen;

public class WebGame extends Game {
    @Override
    public void create() {
        MainScreen mainScreen = new MainScreen();
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.addLoginListener(event -> {
            try {
                LoginEvent loginEvent = (LoginEvent) event;
                mainScreen.login(loginEvent.getUsername(), loginEvent.getPassword());
                Gdx.input.setInputProcessor(mainScreen.getGameController());
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
