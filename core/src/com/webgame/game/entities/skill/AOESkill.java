package com.webgame.game.entities.skill;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.entities.player.Player;
import com.webgame.game.enums.SkillAnimationState;


import static com.webgame.game.Configs.PPM;

public abstract class AOESkill extends Skill{
    protected Rectangle area;

    public AOESkill(AOESkill skill){
        super(skill);
    }

    public AOESkill(Player player) {
        super(player);
    }

    public AOESkill(Player player, TextureRegion standTexture, GameAnimation gameAnimation) {
        super(player, standTexture, gameAnimation);
    }

    public AOESkill(Player player, TextureRegion standTexture, GameAnimation gameAnimation, Boolean looping) {
        super(player, standTexture, gameAnimation, looping);
    }

    public AOESkill(Player player, TextureRegion standTexture, GameAnimation gameAnimation, SkillAnimationState animationState) {
        super(player, standTexture, gameAnimation, animationState);
    }

    public AOESkill(Player player, TextureRegion standTexture, GameAnimation gameAnimation, SkillAnimationState animationState, Boolean looping) {
        super(player, standTexture, gameAnimation, animationState, looping);
    }

    public AOESkill(Player player, TextureRegion standTexture, GameAnimation gameAnimation, Integer numFrames, SkillAnimationState animationState, Boolean looping) {
        super(player, standTexture, gameAnimation, numFrames, animationState, looping);
    }


    @Override
    protected void init(Player player){
        super.init(player);
        setArea(new Rectangle(0, 0, 100 / PPM, 100 / PPM));
    }

    @Override
    public void cast(Vector2 targetPosition){
        super.cast(targetPosition);
        Vector2 newPos = new Vector2();
        newPos.x = targetPosition.x - getArea().getWidth()/2;
        newPos.y = targetPosition.y - getArea().getHeight()/2;
        getArea().setPosition(newPos);
    }

    public Rectangle getArea() {
        return area;
    }

    public void setArea(Rectangle area) {
        this.area = area;
    }

}
