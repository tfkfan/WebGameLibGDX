package com.webgame.game.world.common.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.skills.*;

public class SkillFactory implements ISkillFactory {
    public SkillFactory(){

    }

    @Override
    public <T extends SkillSprite> Skill<T> createStaticSkill(final Class<T> clazz, final SpriteBatch batch, final Texture spriteTexture, final Integer numFrames) throws Exception {
        return new StaticSkill<T>(batch, spriteTexture, numFrames) {
            @Override
            protected T createObject() {
                try {
                    return (T) clazz.newInstance();
                }catch(InstantiationException | IllegalAccessException e){
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public <T extends SkillSprite> Skill<T> createStaticAOESkill(final Class<T> clazz, final SpriteBatch batch, final Texture spriteTexture, final Integer numFrames) throws Exception {
        return new StaticAOESkill<T>(batch, spriteTexture, numFrames) {
            @Override
            protected T createObject() {
                try {
                    return (T) clazz.newInstance();
                }catch(InstantiationException | IllegalAccessException e){
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public <T extends SkillSprite> Skill<T> createFallingAOESkill(final Class<T> clazz, final SpriteBatch batch, final Texture spriteTexture, final Integer numFrames) throws Exception {
        return new FallingAOESkill<T>(batch, spriteTexture, numFrames) {
            @Override
            protected T createObject() {
                try {
                    return (T) clazz.newInstance();
                }catch(InstantiationException | IllegalAccessException e){
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public <T extends SkillSprite> Skill<T> createSingleSkill(final Class<T> clazz, final SpriteBatch batch, final Texture spriteTexture) throws Exception {
        return new SingleSkill<T>(batch, spriteTexture) {
            @Override
            protected T createObject() {
                try {
                    return (T) clazz.newInstance();
                }catch(InstantiationException | IllegalAccessException e){
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public <T extends SkillSprite> Skill<T> createStaticSingleAOESkill(final Class<T> clazz, final SpriteBatch batch, final Texture spriteTexture) throws Exception {
        return new StaticAOESkill<T>(batch, spriteTexture, 1) {
            @Override
            protected T createObject() {
                try {
                    return (T) clazz.newInstance();
                }catch(InstantiationException | IllegalAccessException e){
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public <T extends SkillSprite> Skill<T> createStaticSingleSkill(final Class<T> clazz, final SpriteBatch batch, final Texture spriteTexture) throws Exception{
        return new StaticSkill<T>(batch, spriteTexture, 1) {
            @Override
            protected T createObject() {
                try {
                    return (T) clazz.newInstance();
                }catch(InstantiationException | IllegalAccessException e){
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }
}
