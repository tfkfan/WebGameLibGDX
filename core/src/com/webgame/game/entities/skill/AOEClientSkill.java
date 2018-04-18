package com.webgame.game.entities.skill;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.entities.player.ClientPlayer;
import com.webgame.game.enums.SkillAnimationState;
import com.webgame.game.server.entities.Player;


import static com.webgame.game.Configs.PPM;

public abstract class AOEClientSkill extends ClientSkill {
    public AOEClientSkill(){

    }
    public AOEClientSkill(ClientSkill clientSkill) {
        super(clientSkill);
    }

    public AOEClientSkill(Player clientPlayer) {
        super(clientPlayer);
    }

    public AOEClientSkill(Player clientPlayer, TextureRegion standTexture, GameAnimation gameAnimation) {
        super(clientPlayer, standTexture, gameAnimation);
    }

    public AOEClientSkill(Player clientPlayer, TextureRegion standTexture, GameAnimation gameAnimation, Boolean looping) {
        super(clientPlayer, standTexture, gameAnimation, looping);
    }

    public AOEClientSkill(Player clientPlayer, TextureRegion standTexture, GameAnimation gameAnimation, SkillAnimationState animationState) {
        super(clientPlayer, standTexture, gameAnimation, animationState);
    }

    public AOEClientSkill(Player clientPlayer, TextureRegion standTexture, GameAnimation gameAnimation, SkillAnimationState animationState, Boolean looping) {
        super(clientPlayer, standTexture, gameAnimation, animationState, looping);
    }

    public AOEClientSkill(Player clientPlayer, TextureRegion standTexture, GameAnimation gameAnimation,
                          Integer numFrames, SkillAnimationState animationState, Boolean looping, float[] standSizes, float[] animSizes) {
        super(clientPlayer, standTexture, gameAnimation, numFrames, SkillAnimationState.FULL_ANIMATION, false, standSizes, animSizes);
    }
}
