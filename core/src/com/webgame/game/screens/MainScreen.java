package com.webgame.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.webgame.game.stages.GameStage;

public class MainScreen implements Screen {

	private String title = "WebGame";


	private GameStage stage;

	@Override
	public void show() {
		Gdx.app.log(title, "Starting...");
		stage = new GameStage();
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
