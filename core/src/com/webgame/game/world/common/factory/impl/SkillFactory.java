package com.webgame.game.world.common.factory.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.world.common.factory.ISkillFactory;
import com.webgame.game.world.common.factory.ISkillSpriteFactory;
import com.webgame.game.world.skills.*;
import com.webgame.game.world.skills.skillsprites.SkillSprite;

public class SkillFactory implements ISkillFactory {
    ISkillSpriteFactory skillSpriteFactory = new SkillSpriteFactory();

    public SkillFactory() {

    }

    @Override
    public <A extends GameAnimation, T extends SkillSprite> Skill<T> createStaticSkill(final Class<A> animationClass, final SpriteBatch batch, final Texture spriteTexture, final Integer numFrames) throws Exception {
        return new StaticSkill<T>(batch, spriteTexture, numFrames) {
            @Override
            protected T createObject() {
                return skillSpriteFactory.createStaticSkillSprite(animationClass);
            }
        };
    }

    @Override
    public <A extends GameAnimation, T extends SkillSprite> Skill<T> createStaticAOESkill(final Class<A> animationClass, final SpriteBatch batch, final Texture spriteTexture, final Integer numFrames) throws Exception {
        return new StaticAOESkill<T>(batch, spriteTexture, numFrames) {
            @Override
            protected T createObject() {
                return skillSpriteFactory.createStaticSkillSprite(animationClass);
            }
        };
    }

    @Override
    public <A extends GameAnimation, T extends SkillSprite> Skill<T>
    createFallingAOESkill(final Class<A> animationClass, final SpriteBatch batch, final Texture spriteTexture, final TextureRegion standTexture, final Integer numFrames) throws Exception {
        return new FallingAOESkill<T>(batch, spriteTexture, numFrames) {
            @Override
            protected T createObject() {
                return skillSpriteFactory.createFallingSkillSprite(animationClass, standTexture);
            }
        };
    }

    @Override
    public <A extends GameAnimation, T extends SkillSprite> Skill<T> createSingleSkill(final Class<A> animationClass, final SpriteBatch batch,
                                                                                       final Texture spriteTexture,final TextureRegion standTexture) throws Exception {
        return new SingleSkill<T>(batch, spriteTexture) {
            @Override
            protected T createObject() {
                return skillSpriteFactory.createSingleSkillSprite(animationClass, standTexture);
            }
        };
    }

    @Override
    public <A extends GameAnimation, T extends SkillSprite> Skill<T> createStaticSingleAOESkill(final Class<A> animationClass, final SpriteBatch batch, final Texture spriteTexture) throws Exception {
        Skill<T> skill = new StaticAOESkill<T>(batch, spriteTexture, 1) {
            @Override
            protected T createObject() {
                T obj = skillSpriteFactory.createStaticSkillSprite(animationClass);
                return obj;
            }
        };
        skill.getSkillState().setStatic(true);
        skill.getSkillState().setAOE(true);
        return skill;
    }

    @Override
    public <A extends GameAnimation, T extends SkillSprite> Skill<T> createBuffSkill(Class<A> animationClass, SpriteBatch batch, Texture spriteTexture) throws Exception {
        Skill skill = new BuffSkill<T>(batch, spriteTexture, 1) {
            @Override
            protected T createObject() {
                return skillSpriteFactory.createStaticSkillSprite(animationClass);
            }
        };
        skill.getSkillState().setBuff(true);
        skill.getSkillState().setStatic(true);
        return skill;
    }

    @Override
    public <A extends GameAnimation, T extends SkillSprite> Skill<T> createStaticTimedAOESkill(Class<A> animationClass, SpriteBatch batch, Texture spriteTexture) throws Exception {
        Skill<T> skill = new StaticAOESkill<T>(batch, spriteTexture, 1) {
            @Override
            protected T createObject() {
                T obj = skillSpriteFactory.createStaticTimedSkillSprite(animationClass);
                return obj;
            }
        };
        skill.getSkillState().setTimed(true);
        skill.getSkillState().setStatic(true);
        return skill;
    }

    @Override
    public <A extends GameAnimation, T extends SkillSprite> Skill<T> createStaticSingleSkill(final Class<A> animationClass, final SpriteBatch batch, final Texture spriteTexture) throws Exception {
        Skill<T> skill = new StaticSkill<T>(batch, spriteTexture, 1) {
            @Override
            protected T createObject() {
                return skillSpriteFactory.createStaticSkillSprite(animationClass);
            }
        };
        skill.getSkillState().setStatic(true);
        return skill;
    }
}
