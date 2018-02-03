package com.webgame.game.world.common.factory.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.animation.impl.BuffAnimation;
import com.webgame.game.animation.impl.MagicSourceAnimation;
import com.webgame.game.world.common.factory.ISkillSpriteFactory;
import com.webgame.game.world.skills.skillsprites.SkillSprite;

import java.lang.reflect.InvocationTargetException;

import static com.webgame.game.Configs.PPM;

public class SkillSpriteFactory implements ISkillSpriteFactory {
    @Override
    public <A extends GameAnimation, T extends SkillSprite> T createSingleSkillSprite(final Class<A> animationClass) {
        return (T) new SkillSprite() {

            @Override
            public void initSkillSprite(SpriteBatch batch, Texture spriteTexture) {
                this.setSpriteBatch(batch);
                this.setSpriteTexture(spriteTexture);


                setAnimationDuration(0.1f);
                //new BuffAnimation(getSpriteTexture(), getAnimationDuration())
                try {
                    setGameAnimation((GameAnimation)animationClass.getDeclaredConstructor(Texture.class, Float.class).newInstance(getSpriteTexture(), getAnimationDuration()));
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }

                setAnimationMaxDuration(getAnimationDuration() * getGameAnimation().getAnimation().getKeyFrames().length);
                int w2 = 100;
                int h2 = 80;
                this.setBounds(0, 0, w2 / PPM, h2 / PPM);
                setRegion(getGameAnimation().getAnimation().getKeyFrame(0));
            }

            @Override
            public TextureRegion getFrame() {
                TextureRegion region = null;

                if (isActive())
                    region = getGameAnimation().getAnimation().getKeyFrame(getAnimateTimer(), false);

                return region;
            }
        };
    }
}
