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
import com.webgame.game.map.CustomMapRenderer;
import com.webgame.game.objects.Mage;
import com.webgame.game.objects.Player;

import static com.webgame.game.Configs.VIEW_WIDTH;
import static com.webgame.game.Configs.VIEW_HEIGHT;

public class MainScreen implements Screen {
	private SpriteBatch batch;
	private String title = "WebGame";

	private OrthographicCamera cam;
	private Viewport viewport;

	private CustomMapRenderer cMapRenderer;

	private Player player;

	@Override
	public void show() {
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		viewport = new StretchViewport(VIEW_WIDTH, VIEW_HEIGHT, cam);
		cam.position.set(0, 0, 0);

		player = new Mage("mage.png");

		cMapRenderer = new CustomMapRenderer();
		cMapRenderer.initWorld();

		Gdx.app.log(title, "Hi1!");
	}

	private void handleInput() {
		float d = 5f;
		if (Gdx.input.isKeyPressed(Input.Keys.A))
			cam.zoom += 0.1;
		if (Gdx.input.isKeyPressed(Input.Keys.Q))
			cam.zoom -= 0.1;

		Vector2 vec = new Vector2(0, 0);
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
			vec.x = -d;
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			vec.x = d;
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
			vec.y = -d;
		if (Gdx.input.isKeyPressed(Input.Keys.UP))
			vec.y = d;

		player.setVelocity(vec);
		cam.position.x = player.getPosition().x;
		cam.position.y = player.getPosition().y;
		// cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 100 / cam.viewportWidth);
	}

	@Override
	public void render(float dt) {
		handleInput();
		cam.update();
		player.move();

		batch.setProjectionMatrix(cam.combined);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		cMapRenderer.render(cam);
		// DRAWING GAME OBJECTS
		batch.begin();
		player.draw(batch, dt);
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
