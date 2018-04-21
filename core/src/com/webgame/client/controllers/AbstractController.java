package com.webgame.client.controllers;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.webgame.client.entities.player.ClientPlayer;
import com.webgame.client.ws.IWebSocket;

import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractController extends Group {
    protected IWebSocket socketService;
    protected final ConcurrentHashMap<String, ClientPlayer> players = new ConcurrentHashMap<>();
    protected ClientPlayer clientPlayer;

    public AbstractController() {

    }

    public void setSocketService(IWebSocket socketService) {
        this.socketService = socketService;
    }

    public synchronized IWebSocket getSocketService() {
        return socketService;
    }

    public ConcurrentHashMap<String, ClientPlayer> getPlayers() {
        return players;
    }

    public ClientPlayer getClientPlayer() {
        return clientPlayer;
    }

    public void setClientPlayer(ClientPlayer clientPlayer) {
        this.clientPlayer = clientPlayer;
    }

}
