package com.webgame.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.webgame.game.objects.Mage;
import com.webgame.game.objects.Player;

import static com.webgame.game.Configs.VIEW_WIDTH;
import static com.webgame.game.Configs.VIEW_HEIGHT;

public class MainScreen implements Screen {
	SpriteBatch batch;
	String title = "WebGame";

	private World world;
	private Box2DDebugRenderer debugRenderer;

	private OrthographicCamera cam;
	private Viewport viewport;
	private TmxMapLoader loader;
	private OrthogonalTiledMapRenderer renderer;
	private TiledMap map;

	Player player;

	float dx = 0;

	@Override
	public void show() {
		batch = new SpriteBatch();
		// player = new Player();
		
		cam = new OrthographicCamera();
		viewport = new StretchViewport(VIEW_WIDTH, VIEW_HEIGHT, cam);
		cam.position.set(0, 0, 0);

		loader = new TmxMapLoader();
		map = loader.load("map.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		
		player = new Mage("mage.png");

		Gdx.app.log(title, "Hi1!");
	}

	private void handleInput() {
		float d = 5f;
		if (Gdx.input.isKeyPressed(Input.Keys.A)) 
			cam.zoom += 0.1;
		if (Gdx.input.isKeyPressed(Input.Keys.Q)) 
			cam.zoom -= 0.1;
		
		
		Vector2 vec = new Vector2(0,0);
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) 
			vec.x = -d;
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) 
			vec.x = d;
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) 
			vec.y = -d;
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) 
			vec.y = d;

		cam.translate(vec.x, vec.y, 0);
		
		player.setVelocity(vec);
	//	cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 100 / cam.viewportWidth);
	}

	@Override
	public void render(float dt) {
		handleInput();
		cam.update();
		player.move();
		renderer.setView(cam);

		batch.setProjectionMatrix(cam.combined);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// player.setPosition(new Vector2(dx, dx));
		renderer.render();
		batch.begin();
		
		// DRAWING GAME OBJECTS
		player.draw(batch, dt);

		batch.end();

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
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
	public void dispose() {
		batch.dispose();
	}

}
