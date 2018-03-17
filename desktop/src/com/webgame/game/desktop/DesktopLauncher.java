package com.webgame.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.github.czyzby.websocket.CommonWebSockets;
import com.webgame.game.WebGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        System.setProperty("user.name", "seconduser");
        // Initiating web sockets module:
        CommonWebSockets.initiate();
        createApplication();
    }

    public static void createApplication() {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1400;
        config.height = 900;
        new LwjglApplication(new WebGame(), config);
    }
}
