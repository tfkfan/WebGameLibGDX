package com.webgame.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.webgame.game.Configs;
import com.webgame.game.events.LoginEvent;
import com.webgame.game.events.listeners.LoginListener;
import com.webgame.game.stages.GameStage;
import com.webgame.game.ui.CustomLabel;
import com.webgame.game.ui.CustomTextButton;
import com.webgame.game.ui.CustomTextField;

import java.util.ArrayList;
import java.util.List;

public class LoginScreen implements Screen {
    private String title = "WebGame";
    private Stage stage;

    private List<LoginListener> loginListeners = new ArrayList<LoginListener>();

    @Override
    public void show() {
        Gdx.app.log(title, "Starting login...");
        stage = new Stage() {
            @Override
            public void draw() {
                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                super.draw();
            }
        };
        Gdx.input.setInputProcessor(stage);

        stage.addActor(buildLoginPage());

    }

    public void addLoginListener(LoginListener listener) {
        loginListeners.add(listener);
    }

    private Table buildLoginPage() {
        final Table table = new Table();

        table.setDebug(true);
        table.setFillParent(true);
        table.add(CustomLabel.createLabel("Login form")).colspan(2).center().pad(50);
        table.row();

        final CustomTextField usernameField = CustomTextField.createField();
        final CustomTextField passwordField = CustomTextField.createField();
        final CustomTextButton btn2 = CustomTextButton.createButton("Sign in", new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (LoginListener listener : loginListeners) {
                    listener.customHandle(new LoginEvent(usernameField.getText(), passwordField.getText()));
                }
            }
        });

        table.add(CustomLabel.createLabel("Username")).pad(20);
        table.add(usernameField).width(100).pad(20);
        table.row();
        table.add(CustomLabel.createLabel("Password")).pad(20);
        table.add(passwordField).width(100).pad(20);
        table.row();
        table.add(btn2).colspan(2).width(50).pad(50);

        return table;
    }

    @Override
    public void render(float dt) {
        stage.act(dt);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
