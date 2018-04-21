package com.webgame.client.enums;

import com.webgame.client.Configs;

public enum FrameSizes {
    BLIZZARD(20 / Configs.PPM, 30 / Configs.PPM),
    LITTLE_SPHERE(10 / Configs.PPM, 10 / Configs.PPM),
    ANIMATION(50 / Configs.PPM, 50 / Configs.PPM),
    BIG_ANIMATION(100/Configs.PPM, 100/Configs.PPM);

    private float w;
    private float h;

    FrameSizes(float w, float h) {
        setW(w);
        setH(h);
    }

    public float getW() {
        return w;
    }

    public void setW(float w) {
        this.w = w;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }
}
