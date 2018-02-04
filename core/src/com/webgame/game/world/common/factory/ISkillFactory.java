package com.webgame.game.world.common.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.world.skills.Skill;
import com.webgame.game.world.skills.skillsprites.SkillSprite;

public interface ISkillFactory {
    public abstract <A extends GameAnimation, T extends SkillSprite> Skill<T>
    createStaticSkill(final Class<A> animationClass, final SpriteBatch batch, final Texture spriteTexture, final Integer numFrames) throws Exception;

    public abstract <A extends GameAnimation, T extends SkillSprite> Skill<T>
    createStaticAOESkill(final Class<A> animationClass, final SpriteBatch batch, final Texture spriteTexture, final Integer numFrames) throws Exception;

    public abstract <A extends GameAnimation, T extends SkillSprite> Skill<T>
    createFallingAOESkill(final Class<A> animationClass, final SpriteBatch batch, final Texture spriteTexture, final Integer numFrames) throws Exception;

    public abstract <A extends GameAnimation, T extends SkillSprite> Skill<T>
    createSingleSkill(final Class<A> animationClass, final SpriteBatch batch, final Texture spriteTexture) throws Exception;

    public abstract <A extends GameAnimation, T extends SkillSprite> Skill<T>
    createStaticSingleAOESkill(final Class<A> animationClass, final SpriteBatch batch, final Texture spriteTexture) throws Exception;

    public abstract <A extends GameAnimation, T extends SkillSprite> Skill<T>
    createBuffSkill(final Class<A> animationClass, final SpriteBatch batch, final Texture spriteTexture) throws Exception;

    public abstract <A extends GameAnimation, T extends SkillSprite> Skill<T>
    createStaticTimedAOESkill(final Class<A> animationClass, final SpriteBatch batch, final Texture spriteTexture) throws Exception;

    public abstract <A extends GameAnimation, T extends SkillSprite> Skill<T>
    createStaticSingleSkill(final Class<A> animationClass, final SpriteBatch batch, final Texture spriteTexture) throws Exception;
}
