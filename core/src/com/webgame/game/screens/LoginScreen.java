package com.webgame.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.webgame.game.Configs;
import com.webgame.game.stages.GameStage;
import com.webgame.game.ui.CustomLabel;
import com.webgame.game.ui.CustomTextButton;
import com.webgame.game.ui.CustomTextField;

public class LoginScreen implements Screen {

    private String title = "WebGame";


    private Stage stage;

    @Override
    public void show() {
        Gdx.app.log(title, "Starting login...");
        stage = new Stage(){
            @Override
            public void draw(){
                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                super.draw();
            }
        };
        Gdx.input.setInputProcessor(stage);

        stage.addActor(buildLoginPage());

    }

    public Table buildLoginPage(){
        Table table = new Table();

        table.setDebug(true);
        table.setFillParent(true);



        final CustomTextField usernameField = CustomTextField.createField();
        final CustomTextField passwordField = CustomTextField.createField();

        final CustomTextButton btn2 = CustomTextButton.createButton("dsfdsdgfsdgsdg",  new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("ok2 pressed");
                // player.setCurrentSkill(skill);
            }
        });

        table.add(CustomLabel.createLabel("Password"));
        table. add(usernameField).width(100);
        table.row();
        table.add(CustomLabel.createLabel("Password"));
        table. add(passwordField).width(100);
        table.row();
        table. add();
        table. add(btn2).width(100);

        table.row();


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
