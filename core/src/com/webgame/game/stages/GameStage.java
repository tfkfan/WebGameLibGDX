package com.webgame.game.stages;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.webgame.game.controllers.GameController;

import static com.webgame.game.Configs.VIEW_WIDTH;

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
