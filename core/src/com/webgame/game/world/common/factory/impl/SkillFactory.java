package com.webgame.game.world.common.factory.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.entities.skill.SkillOrig;
import com.webgame.game.world.common.factory.ISkillFactory;
import com.webgame.game.world.common.factory.ISkillSpriteFactory;
import com.webgame.game.world.skills.*;
import com.webgame.game.world.skills.skillsprites.SkillSprite;

public class SkillFactory implements ISkillFactory {
    ISkillSpriteFactory skillSpriteFactory = new SkillSpriteFactory();

    public SkillFactory() {

    }

    @Override
    public <A extends GameAnimation, T extends SkillSprite> SkillOrig<T> createStaticSkill(final Class<A> animationClass, final SpriteBatch batch, final Texture spriteTexture, final Integer numFrames) throws Exception {
        return new StaticSkillOrig<T>(batch, spriteTexture, numFrames) {
            @Override
            protected T createObject() {
                return skillSpriteFactory.createStaticSkillSprite(animationClass);
            }
        };
    }

    @Override
    public <A extends GameAnimation, T extends SkillSprite> SkillOrig<T> createStaticAOESkill(final Class<A> animationClass, final SpriteBatch batch, final Texture spriteTexture, final Integer numFrames) throws Exception {
        return new StaticAOESkillOrig<T>(batch, spriteTexture, numFrames) {
            @Override
            protected T createObject() {
                return skillSpriteFactory.createStaticSkillSprite(animationClass);
            }
        };
    }

    @Override
    public <A extends GameAnimation, T extends SkillSprite> SkillOrig<T>
    createFallingAOESkill(final Class<A> animationClass, final SpriteBatch batch, final Texture spriteTexture, final TextureRegion standTexture, final Integer numFrames) throws Exception {
        return new FallingAOESkillOrig<T>(batch, spriteTexture, numFrames) {
            @Override
            protected T createObject() {
                return skillSpriteFactory.createFallingSkillSprite(animationClass, standTexture);
            }
        };
    }

    @Override
    public <A extends GameAnimation, T extends SkillSprite> SkillOrig<T> createSingleSkill(final Class<A> animationClass, final SpriteBatch batch,
                                                                                           final Texture spriteTexture, final TextureRegion standTexture) throws Exception {
        return new SingleSkillOrig<T>(batch, spriteTexture) {
            @Override
            protected T createObject() {
                return skillSpriteFactory.createSingleSkillSprite(animationClass, standTexture);
            }
        };
    }

    @Override
    public <A extends GameAnimation, T extends SkillSprite> SkillOrig<T> createStaticSingleAOESkill(final Class<A> animationClass, final SpriteBatch batch, final Texture spriteTexture) throws Exception {
        SkillOrig<T> skillOrig = new StaticAOESkillOrig<T>(batch, spriteTexture, 1) {
            @Override
            protected T createObject() {
                T obj = skillSpriteFactory.createStaticSkillSprite(animationClass);
                return obj;
            }
        };
        skillOrig.getSkillState().setStatic(true);
        skillOrig.getSkillState().setAOE(true);
        return skillOrig;
    }

    @Override
    public <A extends GameAnimation, T extends SkillSprite> SkillOrig<T> createBuffSkill(Class<A> animationClass, SpriteBatch batch, Texture spriteTexture) throws Exception {
        SkillOrig skillOrig = new BuffSkillOrig<T>(batch, spriteTexture, 1) {
            @Override
            protected T createObject() {
                return skillSpriteFactory.createStaticSkillSprite(animationClass);
            }
        };
        skillOrig.getSkillState().setBuff(true);
        skillOrig.getSkillState().setStatic(true);
        return skillOrig;
    }

    @Override
    public <A extends GameAnimation, T extends SkillSprite> SkillOrig<T> createStaticTimedAOESkill(Class<A> animationClass, SpriteBatch batch, Texture spriteTexture) throws Exception {
        SkillOrig<T> skillOrig = new StaticAOESkillOrig<T>(batch, spriteTexture, 1) {
            @Override
            protected T createObject() {
                T obj = skillSpriteFactory.createStaticTimedSkillSprite(animationClass);
                return obj;
            }
        };
        skillOrig.getSkillState().setTimed(true);
        skillOrig.getSkillState().setStatic(true);
        return skillOrig;
    }

    @Override
    public <A extends GameAnimation, T extends SkillSprite> SkillOrig<T> createStaticSingleSkill(final Class<A> animationClass, final SpriteBatch batch, final Texture spriteTexture) throws Exception {
        SkillOrig<T> skillOrig = new StaticSkillOrig<T>(batch, spriteTexture, 1) {
            @Override
            protected T createObject() {
                return skillSpriteFactory.createStaticSkillSprite(animationClass);
            }
        };
        skillOrig.getSkillState().setStatic(true);
        return skillOrig;
    }
}
