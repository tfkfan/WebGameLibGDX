package com.webgame.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.webgame.game.world.WorldRenderer;
import com.webgame.game.world.objects.Player;
import com.webgame.game.world.objects.impl.Mage;
import static com.webgame.game.Configs.VIEW_WIDTH;
import static com.webgame.game.Configs.VIEW_HEIGHT;
import static com.webgame.game.Configs.PPM;

public class MainScreen implements Screen {
	private SpriteBatch batch;
	private String title = "WebGame";

	private OrthographicCamera cam;
	private Viewport viewport;

	private WorldRenderer worldRenderer;

	private Player player;

	@Override
	public void show() {
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		viewport = new StretchViewport(VIEW_WIDTH / PPM, VIEW_HEIGHT / PPM, cam);
		cam.position.set(0, 0, 0);

		worldRenderer = new WorldRenderer();
		worldRenderer.initWorld(cam);

		player = new Mage(batch, "mage.png");
		player.createObject(worldRenderer.world);

		Gdx.app.log(title, "Starting...");
	}

	private void handleInput() {
		float d = 5f;
		//if (Gdx.input.isKeyPressed(Input.Keys.A))
		//	cam.zoom += 0.1;
		//if (Gdx.input.isKeyPressed(Input.Keys.Q))
		//	cam.zoom -= 0.1;

		Vector2 vec = new Vector2(0, 0);
		if (Gdx.input.isKeyPressed(Input.Keys.A))
			vec.x = -d;
		if (Gdx.input.isKeyPressed(Input.Keys.D))
			vec.x = d;
		if (Gdx.input.isKeyPressed(Input.Keys.S))
			vec.y = -d;
		if (Gdx.input.isKeyPressed(Input.Keys.W))
			vec.y = d;
		
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
			player.attack();
		// player.b2body.applyLinearImpulse(vec, player.b2body.getWorldCenter(),
		// true);
		player.setVelocity(vec);
		cam.position.x = player.getX();
		cam.position.y = player.getY();

		// cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 100 / cam.viewportWidth);
	}

	@Override
	public void render(float dt) {
		handleInput();
		cam.update();
		player.move(dt);
		((Mage)player).moveSkills(dt);

		batch.setProjectionMatrix(cam.combined);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		worldRenderer.world.step(0.01f, 6, 2);
		worldRenderer.render();
		// DRAWING GAME OBJECTS

		batch.begin();

		player.draw(batch);
		((Mage)player).drawSkills();

		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

}
