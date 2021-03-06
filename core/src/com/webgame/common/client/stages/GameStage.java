package com.webgame.common.client.stages;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.webgame.common.client.controllers.GameController;

import static com.webgame.common.client.Configs.VIEW_WIDTH;

import static com.webgame.common.client.Configs.VIEW_HEIGHT;
import static com.webgame.common.client.Configs.PPM;

public class GameStage extends Stage {
    public GameController gc;
    public OrthographicCamera camera;
    public Viewport viewport;

    public GameStage() {
        camera = new OrthographicCamera();

        viewport = new StretchViewport(VIEW_WIDTH / PPM, VIEW_HEIGHT / PPM, camera);
        setViewport(viewport);

        gc = new GameController(camera, viewport);
        //wc = new WidgetsController(camera, viewport, gc.getClientPlayer());
        this.addActor(gc);
        // this.addActor(wc);

    }

    public GameController getGc() {
        return gc;
    }

    public void login(String username, String password) {
        try {
            gc.playerLogin(username, password);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
