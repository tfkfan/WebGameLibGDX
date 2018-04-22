package com.webgame.common.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.github.czyzby.websocket.GwtWebSockets;
import com.webgame.common.client.WebGame;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(1200, 920);
                return cfg;
        }

        @Override
        public ApplicationListener createApplicationListener () {
              //  Gdx.app.setLogLevel(LOG_INFO);
                GwtWebSockets.initiate();
                return new WebGame();
        }
}