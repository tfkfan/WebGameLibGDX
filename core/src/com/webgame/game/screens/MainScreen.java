package com.webgame.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.webgame.game.world.WorldRenderer;
import com.webgame.game.world.player.Player;
import com.webgame.game.world.player.impl.Knight;
import com.webgame.game.world.player.impl.Mage;
import com.webgame.game.world.skills.collision.CollisionHandler;

import static com.webgame.game.Configs.VIEW_WIDTH;

import java.util.ArrayList;
import java.util.List;

import static com.webgame.game.Configs.VIEW_HEIGHT;
import static com.webgame.game.Configs.PPM;

public class MainScreen implements Screen, InputProcessor {
	private SpriteBatch batch;
	private String title = "WebGame";

	private OrthographicCamera cam;
	private Viewport viewport;

	private WorldRenderer worldRenderer;
	protected ShapeRenderer sr;
	private Player player;

	private List<Player> enemies;
	
	private CollisionHandler clsnHandler;

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);

		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		viewport = new StretchViewport(VIEW_WIDTH / PPM, VIEW_HEIGHT / PPM, cam);
		cam.position.set(0, 0, 0);

		worldRenderer = new WorldRenderer();
		worldRenderer.initWorld(cam);

		player = new Mage(batch, "mage.png");
		player.createObject(worldRenderer.world, false);

		Player enemy = new Knight(batch, "knight.png");
		enemy.setPosition(1.5f, 1.5f);
		enemy.createObject(worldRenderer.world, false);
		enemy.getB2body().setTransform(1.5f, 1.5f, 0);
		
		clsnHandler = new CollisionHandler();
	
		enemies = new ArrayList<Player>();
		enemies.add(enemy);

		sr = new ShapeRenderer();
		sr.setAutoShapeType(true);

		Gdx.app.log(title, "Starting...");
	}

	@Override
	public void render(float dt) {
		handleInput();
		cam.update();
		player.update(dt);

		batch.setProjectionMatrix(cam.combined);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		worldRenderer.world.step(0.01f, 6, 2);
		worldRenderer.render();
		// DRAWING GAME OBJECTS
		batch.begin();
		player.draw(batch);
		for (Player enemy : enemies) {
			enemy.update(dt);
			enemy.draw(batch);
			enemy.animateSkills(dt);
			clsnHandler.collision(player, enemy);
		}
		player.animateSkills(dt);
		batch.end();

		sr.setProjectionMatrix(cam.combined);

		sr.begin();
		player.drawShape(sr);
		for (Player enemy : enemies)
			enemy.drawShape(sr);
		sr.end();
	}

	private void handleInput() {
		float d = 5f;
		// if (Gdx.input.isKeyPressed(Input.Keys.A))
		// cam.zoom += 0.1;
		// if (Gdx.input.isKeyPressed(Input.Keys.Q))
		// cam.zoom -= 0.1;

		Vector2 vec = new Vector2(0, 0);
		if (Gdx.input.isKeyPressed(Input.Keys.A))
			vec.x = -d;
		if (Gdx.input.isKeyPressed(Input.Keys.D))
			vec.x = d;
		if (Gdx.input.isKeyPressed(Input.Keys.S))
			vec.y = -d;
		if (Gdx.input.isKeyPressed(Input.Keys.W))
			vec.y = d;
		

		player.setVelocity(vec);
		cam.position.x = player.getX();
		cam.position.y = player.getY();
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

	@Override
	public boolean keyDown(int keycode) {
		if(keycode >= 8 && keycode <=16)
			player.setCurrentSkillIndex(keycode - 8);
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
	
		if (button == Input.Buttons.LEFT) {

			Vector3 target = cam.unproject(new Vector3(x, y, 0), viewport.getScreenX(), viewport.getScreenY(),
					viewport.getScreenWidth(), viewport.getScreenHeight());

			player.attack(target.x, target.y);

			return true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {

		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
