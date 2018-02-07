package com.webgame.game.controllers;

import com.webgame.game.entities.Enemy;
import com.webgame.game.entities.Player;

import java.util.List;

public class SkillController extends AbstractController {
    private Player player;
    private List<Enemy> enemies;

    public SkillController(){

    }

    public void init(Player player, List<Enemy> enemies) {
        this.player = player;
        this.enemies = enemies;
    }


    @Override
    public void act(float dt){
        super.act(dt);
        
    }
}
