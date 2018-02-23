package com.webgame.game.entities.skill;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.entities.player.Player;
import com.webgame.game.enums.EntityState;
import com.webgame.game.enums.SkillAnimationState;

public class BuffSkill extends StaticSkill {
    public BuffSkill(Player player) {
        super(player);
    }

    public BuffSkill(Player player, TextureRegion standTexture, GameAnimation gameAnimation) {
        super(player, standTexture, gameAnimation);
    }

    public BuffSkill(Player player, TextureRegion standTexture, GameAnimation gameAnimation, Boolean looping) {
        super(player, standTexture, gameAnimation, looping);
    }

    public BuffSkill(Player player, TextureRegion standTexture, GameAnimation gameAnimation, SkillAnimationState animationState, Boolean looping) {
        super(player, standTexture, gameAnimation, animationState, looping);
    }

    @Override
    public void cast(Vector2 target) {
        super.cast(target);
        setTarget(getPlayer().getPosition());
        for (SkillSprite animation : getAnimations()) {
            animation.setEntityState(EntityState.ACTIVE);
        }
    }
}
