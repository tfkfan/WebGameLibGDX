package com.webgame.game.world.common.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.world.skills.Skill;
import com.webgame.game.world.skills.skillsprites.SkillSprite;

public interface ISkillSpriteFactory {
    public abstract <A extends GameAnimation, T extends SkillSprite> T createStaticSkillSprite(final Class<A> animationClass);
    public abstract <A extends GameAnimation, T extends SkillSprite> T createStaticTimedSkillSprite(final Class<A> animationClass);
    public abstract <A extends GameAnimation, T extends SkillSprite> T createSingleSkillSprite(final Class<A> animationClass, final TextureRegion standTexture);
    public abstract <A extends GameAnimation, T extends SkillSprite> T createFallingSkillSprite(final Class<A> animationClass, final TextureRegion standTexture);
}
