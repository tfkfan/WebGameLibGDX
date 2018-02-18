package com.webgame.game.entities.skill;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.entities.player.Player;
import com.webgame.game.enums.SkillState;

public abstract class StaticSkill extends Skill{

    public StaticSkill(Player player){
        super(player);
    }

    @Override
    public void update(float dt){

        super.update(dt);
    }



    @Override
    public TextureRegion getFrame(){
        TextureRegion region = null;

        //region = animation.getAnimation().getKeyFrame(getStateTimer(), false);

        return region;
    }
}
