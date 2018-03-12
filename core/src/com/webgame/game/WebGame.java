package com.webgame.game;

import com.badlogic.gdx.Game;
import com.webgame.game.screens.LoginScreen;
import com.webgame.game.screens.MainScreen;

public class WebGame extends Game {
	private MainScreen mainScreen = new MainScreen();
	private LoginScreen loginScreen = new LoginScreen();

	@Override
	public void create() {
		this.setScreen(loginScreen);

	}
}
