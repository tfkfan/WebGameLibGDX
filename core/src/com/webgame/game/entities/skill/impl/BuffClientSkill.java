package com.webgame.game.entities.skill.impl;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.entities.skill.ClientSkill;
import com.webgame.game.entities.skill.SkillSprite;
import com.webgame.game.enums.EntityState;
import com.webgame.game.enums.SkillAnimationState;
import com.webgame.game.server.entities.Player;

public class BuffClientSkill extends StaticClientSkill {

    public BuffClientSkill(){
        super();
    }

    public BuffClientSkill(ClientSkill clientSkill){
        super(clientSkill);
    }


    @Override
    public BuffClientSkill createCopy() {
        return new BuffClientSkill(this);
    }

    @Override
    public void cast(Vector2 target) {
        super.cast(target);
        for (SkillSprite animation : getAnimations()) {
            animation.setEntityState(EntityState.ACTIVE);
        }
    }

    @Override
    public void updateAnimations(float dt) {
        setTarget(getPlayer().getPosition());
        super.updateAnimations(dt);

    }
}
