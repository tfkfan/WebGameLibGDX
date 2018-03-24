package com.webgame.game.controllers;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.webgame.game.entities.player.Player;
import com.webgame.game.ws.IWebSocket;

import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractController extends Group {
    protected IWebSocket socketService;
    protected final ConcurrentHashMap<Long, Player> players = new ConcurrentHashMap<Long, Player>();
    protected  Player player;

    public AbstractController() {

    }

    public void setSocketService(IWebSocket socketService) {
        this.socketService = socketService;
    }

    public synchronized IWebSocket getSocketService() {
        return socketService;
    }

    public ConcurrentHashMap<Long, Player> getPlayers() {
        return players;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
