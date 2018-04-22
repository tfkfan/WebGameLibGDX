package com.webgame.common.client.controllers;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.webgame.common.client.entities.player.ClientPlayer;
import com.webgame.common.client.utils.GameUtils;
import com.webgame.common.client.ws.IWebSocket;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractController extends Group {
    protected IWebSocket socketService;
    protected final Map<String, ClientPlayer> players = GameUtils.createMap();
    protected ClientPlayer clientPlayer;

    public AbstractController() {

    }

    public void setSocketService(IWebSocket socketService) {
        this.socketService = socketService;
    }

    public synchronized IWebSocket getSocketService() {
        return socketService;
    }

    public Map<String, ClientPlayer> getPlayers() {
        return players;
    }

    public ClientPlayer getClientPlayer() {
        return clientPlayer;
    }

    public void setClientPlayer(ClientPlayer clientPlayer) {
        this.clientPlayer = clientPlayer;
    }

}
