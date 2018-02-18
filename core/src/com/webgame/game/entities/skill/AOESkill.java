package com.webgame.game.entities.skill;

import com.webgame.game.entities.player.Player;

public abstract class AOESkill extends StaticSkill{

    public AOESkill(Player player){
        super(player);
    }

    @Override
    public void update(float dt){

        super.update(dt);
    }
}
