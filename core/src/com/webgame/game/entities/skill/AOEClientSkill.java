package com.webgame.game.entities.skill;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.entities.player.ClientPlayer;
import com.webgame.game.enums.SkillAnimationState;


import static com.webgame.game.Configs.PPM;

public abstract class AOEClientSkill extends ClientSkill {
    protected Rectangle area;

    public AOEClientSkill(){

    }
    public AOEClientSkill(ClientSkill clientSkill) {
        super(clientSkill);
    }

    public AOEClientSkill(ClientPlayer clientPlayer) {
        super(clientPlayer);
    }

    public AOEClientSkill(ClientPlayer clientPlayer, TextureRegion standTexture, GameAnimation gameAnimation) {
        super(clientPlayer, standTexture, gameAnimation);
    }

    public AOEClientSkill(ClientPlayer clientPlayer, TextureRegion standTexture, GameAnimation gameAnimation, Boolean looping) {
        super(clientPlayer, standTexture, gameAnimation, looping);
    }

    public AOEClientSkill(ClientPlayer clientPlayer, TextureRegion standTexture, GameAnimation gameAnimation, SkillAnimationState animationState) {
        super(clientPlayer, standTexture, gameAnimation, animationState);
    }

    public AOEClientSkill(ClientPlayer clientPlayer, TextureRegion standTexture, GameAnimation gameAnimation, SkillAnimationState animationState, Boolean looping) {
        super(clientPlayer, standTexture, gameAnimation, animationState, looping);
    }

    public AOEClientSkill(ClientPlayer clientPlayer, TextureRegion standTexture, GameAnimation gameAnimation,
                          Integer numFrames, SkillAnimationState animationState, Boolean looping, float[] standSizes, float[] animSizes) {
        super(clientPlayer, standTexture, gameAnimation, numFrames, SkillAnimationState.FULL_ANIMATION, false, standSizes, animSizes);
    }

    @Override
    protected void init(ClientPlayer clientPlayer) {
        super.init(clientPlayer);
        setArea(new Rectangle(0, 0, 100 / PPM, 100 / PPM));
    }

    @Override
    public void cast(Vector2 targetPosition) {
        super.cast(targetPosition);
        Vector2 newPos = new Vector2();
        newPos.x = targetPosition.x - getArea().getWidth() / 2;
        newPos.y = targetPosition.y - getArea().getHeight() / 2;
        getArea().setPosition(newPos);
    }

    public Rectangle getArea() {
        return area;
    }

    public void setArea(Rectangle area) {
        this.area = area;
    }

}
