package com.webgame.game.controllers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.webgame.game.entities.player.Player;
import com.webgame.game.ui.PlayerPanel;

public class WidgetsController extends AbstractController {
    private SpriteBatch staticBatch;
    protected ShapeRenderer staticShapeRenderer;
    private boolean isInitStaticDrawing = false;

    private OrthographicCamera camera;
    private Viewport viewport;

    private PlayerPanel playerPanel;

    public WidgetsController(OrthographicCamera camera, Viewport viewport, Player player) {
        this.camera = camera;
        this.viewport = viewport;

        staticBatch = new SpriteBatch();
        staticShapeRenderer = new ShapeRenderer();
        playerPanel = new PlayerPanel(player);

       addActor(playerPanel);
    }


    @Override
    public void act(float dt) {
        super.act(dt);
        if (!isInitStaticDrawing) {
            staticBatch.setProjectionMatrix(this.camera.combined);
            staticShapeRenderer.setProjectionMatrix(this.camera.combined);
            isInitStaticDrawing = true;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        staticBatch.begin();
        playerPanel.draw(staticBatch, parentAlpha);
        staticBatch.end();


        staticShapeRenderer.setAutoShapeType(true);
        staticShapeRenderer.begin();
        playerPanel.drawDebug(staticShapeRenderer);
        staticShapeRenderer.end();
    }
}
