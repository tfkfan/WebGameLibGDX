package com.webgame.game.stages;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.webgame.game.Configs;
import com.webgame.game.controllers.GameController;
import com.webgame.game.events.MoveEvent;
import com.webgame.game.stages.actor.SkillPanel;
import com.webgame.game.world.WorldRenderer;
import com.webgame.game.entities.Player;
import com.webgame.game.world.player.impl.Knight;
import com.webgame.game.world.player.impl.Mage;
import com.webgame.game.world.skills.collision.CollisionHandler;

import static com.webgame.game.Configs.VIEW_WIDTH;

import java.util.ArrayList;
import java.util.List;

import static com.webgame.game.Configs.VIEW_HEIGHT;
import static com.webgame.game.Configs.PPM;

public class GameStage extends Stage {


    public GameController gc;
    public OrthographicCamera camera;
    public Viewport viewport;

    public GameStage() {

        camera = new OrthographicCamera();
        camera.position.set(0, 0, 0);
        camera.update();

        viewport = new StretchViewport(VIEW_WIDTH / PPM, VIEW_HEIGHT / PPM, camera);
        setViewport(viewport);

        gc = new GameController(camera, viewport);

        this.addActor(gc);


        //Player enemy = new Knight(batch, Configs.PLAYERSHEETS_FOLDER + "/knight.png");
        //enemy.setPosition(1.5f, 1.5f);
        //enemy.createObject(world);
        //enemy.getB2body().setTransform(1.5f, 1.5f, 0);

       // clsnHandler = new CollisionHandler();
        //skillPanel = new SkillPanel(player);

       // enemies = new ArrayList<Player>();
      //  enemies.add(enemy);

        //sr = new ShapeRenderer();

       // sr.setAutoShapeType(true);

        //player.setTouchable(Touchable.enabled);
        /*player.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
              //  if (keycode >= 8 && keycode <= 16)
                   // player.setCurrentSkillIndex(keycode - 8);
                return false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {


                return true;
            }
        });

        setKeyboardFocus(player);
*/
    }



}
