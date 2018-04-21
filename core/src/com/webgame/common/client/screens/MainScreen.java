package com.webgame.common.client.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.webgame.common.client.controllers.GameController;
import com.webgame.common.client.stages.GameStage;

public class MainScreen implements Screen {
	private String title = "WebGame";
	private GameStage stage;

	public MainScreen(){
		stage = new GameStage();
	}

	@Override
	public void show() {
		Gdx.app.log(title, "Starting...");
	}

	public void login(String username, String password){
		stage.login(username, password);
	}

	@Override
	public void render(float dt) {
		stage.act(dt);
		stage.draw();
	}

	public GameController getGameController(){
		return stage.getGc();
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
