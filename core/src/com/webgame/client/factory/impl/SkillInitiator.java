package com.webgame.client.factory.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.client.animation.GameAnimation;
import com.webgame.client.entities.skill.ClientSkill;
import com.webgame.client.factory.ISkillInitiator;
import com.webgame.client.utils.GameUtils;
import com.webgame.server.dto.SpriteAttributesDTO;
import com.webgame.server.entities.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class SkillInitiator implements ISkillInitiator {
    private Map<String, Texture> cachedTextures;

    public SkillInitiator() {
        cachedTextures = new HashMap<>();
    }

    @Override
    public synchronized void initSkill(ClientSkill skill, Player player) {
        skill.init(player);
      //  skill.updateBy(skillDTO);

        final String spritePath = skill.getSpritePath();
        final String animSpritePath = skill.getAnimSpritePath();

        Texture standSkillTexture = null;
        Texture animSkillTexture = null;
        TextureRegion standRegion = null;
        SpriteAttributesDTO attributes = skill.getSpriteAttributes();

        if (spritePath != null) {
            if (cachedTextures.containsKey(spritePath))
                standSkillTexture = cachedTextures.get(spritePath);
            else {
                standSkillTexture = GameUtils.loadSprite(spritePath);
                cachedTextures.put(spritePath, standSkillTexture);
            }
        }

        if (animSpritePath != null) {
            if (cachedTextures.containsKey(animSpritePath))
                animSkillTexture = cachedTextures.get(animSpritePath);
            else {
                animSkillTexture = GameUtils.loadSprite(animSpritePath);
                cachedTextures.put(animSpritePath, animSkillTexture);
            }
        }

        if(standSkillTexture != null) {
            int x = attributes.getStandTextureRegionX() != null ? attributes.getStandTextureRegionX() : 0;
            int y = attributes.getStandTextureRegionY() != null ? attributes.getStandTextureRegionY() : 0;
            int w = attributes.getStandTextureRegionWidth() != null ? attributes.getStandTextureRegionWidth() : standSkillTexture.getWidth();
            int h = attributes.getStandTextureRegionHeight() != null ? attributes.getStandTextureRegionHeight() : standSkillTexture.getHeight();
            standRegion = new TextureRegion(standSkillTexture, x, y, w, h);
        }

        try {
            GameAnimation animation = (GameAnimation) Class.forName(attributes.getAnimationClazz()).getDeclaredConstructor(Texture.class).newInstance(animSkillTexture);
            skill.initAnimations(standRegion, animation, attributes.getNumFrames(), attributes.getAnimationState(), attributes.isLooping());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
