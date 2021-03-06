package com.webgame.common.client.server.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.webgame.common.client.world.common.IFramed;

public abstract class AnimatedEntity extends Entity implements IFramed {

    public AnimatedEntity() {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(getFrame(), position.x - getXOffset(), position.y - getYOffset(), getWidth(), getHeight());
    }

}
