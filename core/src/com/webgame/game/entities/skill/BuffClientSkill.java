package com.webgame.game.entities.skill;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.animation.GameAnimation;
import com.webgame.game.entities.player.ClientPlayer;
import com.webgame.game.enums.EntityState;
import com.webgame.game.server.entities.Player;

public class BuffClientSkill extends StaticClientSkill {

    public BuffClientSkill(){

    }

    public BuffClientSkill(ClientSkill clientSkill){
        super(clientSkill);
    }

    public BuffClientSkill(Player clientPlayer) {
        super(clientPlayer);
    }

    public BuffClientSkill(Player clientPlayer, TextureRegion standTexture, GameAnimation gameAnimation) {
        super(clientPlayer, standTexture, gameAnimation);
    }

    @Override
    public void cast(Vector2 target) {
        super.cast(target);
        setTarget(getPlayer().getPosition());
        for (SkillSprite animation : getAnimations()) {
            animation.setEntityState(EntityState.ACTIVE);
        }
    }
}
