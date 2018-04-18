package com.webgame.game.entities.skill;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.entities.player.ClientPlayer;
import com.webgame.game.enums.EntityState;
import com.webgame.game.enums.SkillAnimationState;
import com.webgame.game.server.entities.Player;

public class BuffClientSkill extends StaticClientSkill {

    public BuffClientSkill(){

    }

    public BuffClientSkill(ClientSkill clientSkill){
        super(clientSkill);
    }

    public BuffClientSkill(Player clientPlayer) {
        super(clientPlayer);
    }

    public BuffClientSkill(Player clientPlayer, TextureRegion standTexture, GameAnimation gameAnimation) {
        super(clientPlayer, standTexture, gameAnimation);
    }

    public BuffClientSkill(Player clientPlayer, TextureRegion standTexture, GameAnimation gameAnimation, Boolean looping) {
        super(clientPlayer, standTexture, gameAnimation, looping);
    }

    public BuffClientSkill(Player clientPlayer, TextureRegion standTexture, GameAnimation gameAnimation, SkillAnimationState animationState) {
        super(clientPlayer, standTexture, gameAnimation, animationState);
    }

    public BuffClientSkill(Player clientPlayer, TextureRegion standTexture, GameAnimation gameAnimation, SkillAnimationState animationState, Boolean looping) {
        super(clientPlayer, standTexture, gameAnimation, animationState, looping);
    }

    public BuffClientSkill(Player clientPlayer, TextureRegion standTexture, GameAnimation gameAnimation, Integer numFrames, SkillAnimationState animationState, Boolean looping) {
        super(clientPlayer, standTexture, gameAnimation, numFrames, animationState, looping);
    }

    @Override
    public ClientSkill createCopy() {
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
