package com.webgame.game.world.common.factory.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.animation.impl.BlizzardFragmentAnimation;
import com.webgame.game.animation.impl.BuffAnimation;
import com.webgame.game.animation.impl.MagicSourceAnimation;
import com.webgame.game.world.common.factory.ISkillSpriteFactory;
import com.webgame.game.world.skills.skillsprites.SkillSprite;

import java.lang.reflect.InvocationTargetException;

import static com.webgame.game.Configs.PPM;

public class SkillSpriteFactory implements ISkillSpriteFactory {
    public SkillSpriteFactory() {

    }

    @SuppressWarnings("unchecked")
    @Override
    public synchronized <A extends GameAnimation, T extends SkillSprite> T createStaticSkillSprite(final Class<A> animationClass) {
        return (T) new SkillSprite() {

            @Override
            public void initSkillSprite(SpriteBatch batch, Texture spriteTexture) {
                setSpriteBatch(batch);
                setSpriteTexture(spriteTexture);

                setAnimationDuration(0.1f);
                try {
                    setGameAnimation((GameAnimation) animationClass.getDeclaredConstructor(Texture.class, Float.class).newInstance(getSpriteTexture(), getAnimationDuration()));
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

    @SuppressWarnings("unchecked")
    @Override
    public <A extends GameAnimation, T extends SkillSprite> T createFallingSkillSprite(Class<A> animationClass, final TextureRegion standTexture) {
        return (T) new SkillSprite(standTexture) {

            @Override
            public void initSkillSprite(SpriteBatch batch, Texture spriteTexture) {
                this.setSpriteBatch(batch);
                this.setSpriteTexture(spriteTexture);

                setAnimationDuration(0.1f);
                try {
                    setGameAnimation((GameAnimation) animationClass.getDeclaredConstructor(Texture.class, Float.class).newInstance(getSpriteTexture(), getAnimationDuration()));
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                setAnimationMaxDuration(getAnimationDuration() * getGameAnimation().getAnimation().getKeyFrames().length);
               // setStandTexture(new TextureRegion(getSpriteTexture(), 5, 245, 30, 30));

                int w2 = 20;
                int h2 = 25;
                this.setBounds(0, 0, w2 / PPM, h2 / PPM);
                setRegion(getStandTexture());
            }

            @Override
            public TextureRegion getFrame() {
                TextureRegion region = null;

                if (isActive())
                    region = isStatic() && !isFinalAnimated() ? this.getGameAnimation().getAnimation().getKeyFrame(getAnimateTimer(), false) : getStandTexture();

                return region;
            }
        };
    }

    //TODO доделать с standTexture
    @SuppressWarnings("unchecked")
    @Override
    public synchronized <A extends GameAnimation, T extends SkillSprite> T createSingleSkillSprite(final Class<A> animationClass,final TextureRegion standTexture) {
        return (T) new SkillSprite(standTexture) {

            @Override
            public void initSkillSprite(SpriteBatch batch, Texture spriteTexture) {
                setSpriteBatch(batch);
                setSpriteTexture(spriteTexture);

                setAnimationDuration(0.1f);
                try {
                    setGameAnimation((GameAnimation) animationClass.getDeclaredConstructor(Texture.class, Float.class).newInstance(getSpriteTexture(), getAnimationDuration()));
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                //setStandTexture(new TextureRegion(getSpriteTexture(), 810, 50, w, h));
                setAnimationMaxDuration(getAnimationDuration() * getGameAnimation().getAnimation().getKeyFrames().length);
                int w2 = 20;
                int h2 = 20;
                setBounds(0, 0, w2 / PPM, h2 / PPM);
                setRegion(getGameAnimation().getAnimation().getKeyFrame(0));
            }

            @Override
            public TextureRegion getFrame() {
                TextureRegion region = null;

                int w2 =100;
                int h2 = 80;

                int w1 = 30;
                int h1 = 30;
                if (isActive()){
                    if(isStatic() && !isFinalAnimated()){
                        setSize(w2 / PPM, h2 / PPM);
                        region = getGameAnimation().getAnimation().getKeyFrame(getAnimateTimer(), false);
                    }else {
                        setSize(w1 / PPM, h1 / PPM);
                        region = getStandTexture();
                    }
                }


                return region;
            }
        };
    }

    @SuppressWarnings("unchecked")
    @Override
    public synchronized <A extends GameAnimation, T extends SkillSprite> T createStaticTimedSkillSprite(final Class<A> animationClass) {
        return (T) new SkillSprite() {

            @Override
            public void initSkillSprite(SpriteBatch batch, Texture spriteTexture) {
                setSpriteBatch(batch);
                setSpriteTexture(spriteTexture);

                setAnimationDuration(0.1f);
                try {
                    setGameAnimation((GameAnimation) animationClass.getDeclaredConstructor(Texture.class, Float.class).newInstance(getSpriteTexture(), getAnimationDuration()));
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }

                setAnimationMaxDuration(getAnimationDuration() * getGameAnimation().getAnimation().getKeyFrames().length);
                int w2 = 100;
                int h2 = 100;
                this.setBounds(0, 0, w2 / PPM, h2 / PPM);
                setRegion(getGameAnimation().getAnimation().getKeyFrame(0));
            }

            @Override
            public TextureRegion getFrame() {
                TextureRegion region = null;

                if (isActive())
                    region = getGameAnimation().getAnimation().getKeyFrame(getAnimateTimer(), true);

                return region;
            }
        };
    }
}
