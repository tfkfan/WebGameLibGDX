package com.webgame.client.server.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.webgame.client.world.common.IFramed;

public abstract class AnimatedEntity extends Entity implements IFramed {

    public AnimatedEntity() {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(getFrame(), position.x - getXOffset(), position.y - getYOffset(), getWidth(), getHeight());
    }

}
