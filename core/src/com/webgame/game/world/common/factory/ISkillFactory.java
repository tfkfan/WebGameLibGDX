package com.webgame.game.world.common.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.entities.skill.SkillOrig;
import com.webgame.game.world.skills.skillsprites.SkillSprite;

public interface ISkillFactory {
    public abstract <A extends GameAnimation, T extends SkillSprite> SkillOrig<T>
    createStaticSkill(final Class<A> animationClass, final SpriteBatch batch, final Texture spriteTexture, final Integer numFrames) throws Exception;

    public abstract <A extends GameAnimation, T extends SkillSprite> SkillOrig<T>
    createStaticAOESkill(final Class<A> animationClass, final SpriteBatch batch, final Texture spriteTexture, final Integer numFrames) throws Exception;

    public abstract <A extends GameAnimation, T extends SkillSprite> SkillOrig<T>
    createFallingAOESkill(final Class<A> animationClass, final SpriteBatch batch, final Texture spriteTexture, final TextureRegion standTexture, final Integer numFrames) throws Exception;

    public abstract <A extends GameAnimation, T extends SkillSprite> SkillOrig<T>
    createSingleSkill(final Class<A> animationClass, final SpriteBatch batch, final Texture spriteTexture,final TextureRegion standTexture) throws Exception;

    public abstract <A extends GameAnimation, T extends SkillSprite> SkillOrig<T>
    createStaticSingleAOESkill(final Class<A> animationClass, final SpriteBatch batch, final Texture spriteTexture) throws Exception;

    public abstract <A extends GameAnimation, T extends SkillSprite> SkillOrig<T>
    createBuffSkill(final Class<A> animationClass, final SpriteBatch batch, final Texture spriteTexture) throws Exception;

    public abstract <A extends GameAnimation, T extends SkillSprite> SkillOrig<T>
    createStaticTimedAOESkill(final Class<A> animationClass, final SpriteBatch batch, final Texture spriteTexture) throws Exception;

    public abstract <A extends GameAnimation, T extends SkillSprite> SkillOrig<T>
    createStaticSingleSkill(final Class<A> animationClass, final SpriteBatch batch, final Texture spriteTexture) throws Exception;
}
