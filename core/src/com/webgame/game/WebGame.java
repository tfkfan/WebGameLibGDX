package com.webgame.game;

import com.badlogic.gdx.Game;
import com.webgame.game.screens.MainScreen;

public class WebGame extends Game {
	public MainScreen mainScreen = new MainScreen();

	@Override
	public void create() {
		this.setScreen(mainScreen);

	}
}
