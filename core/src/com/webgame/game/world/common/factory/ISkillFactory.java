package com.webgame.game.world.common.factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.webgame.game.world.skills.Skill;
import com.webgame.game.world.skills.SkillSprite;

public interface ISkillFactory {
    public abstract <T extends SkillSprite> Skill<T> createStaticSkill(final Class<T> clazz, final SpriteBatch batch, final Texture spriteTexture, final Integer numFrames) throws Exception;

    public abstract <T extends SkillSprite> Skill<T> createStaticAOESkill(final Class<T> clazz, final SpriteBatch batch, final Texture spriteTexture, final Integer numFrames) throws Exception;

    public abstract <T extends SkillSprite> Skill<T> createFallingAOESkill(final Class<T> clazz, final SpriteBatch batch, final Texture spriteTexture, final Integer numFrames) throws Exception;

    public abstract <T extends SkillSprite> Skill<T> createSingleSkill(final Class<T> clazz, final SpriteBatch batch, final Texture spriteTexture) throws Exception;

    public abstract <T extends SkillSprite> Skill<T> createStaticSingleAOESkill(final Class<T> clazz, final SpriteBatch batch, final Texture spriteTexture) throws Exception;

    public abstract <T extends SkillSprite> Skill<T> createStaticSingleSkill(final Class<T> clazz, final SpriteBatch batch, final Texture spriteTexture) throws Exception;
}