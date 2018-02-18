package com.webgame.game.entities.skill;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.webgame.game.entities.player.Player;


import static com.webgame.game.Configs.PPM;

public abstract class AOESkill extends Skill{
    protected Rectangle area;

    public AOESkill(Player player){
        super(player);

    }

    @Override
    protected void init(Player player){
        super.init(player);
        setArea(new Rectangle(0, 0, 100 / PPM, 100 / PPM));
    }

    @Override
    public void cast(Vector2 targetPosition){
        super.cast(targetPosition);
        Vector2 newPos = new Vector2();
        newPos.x = targetPosition.x - getArea().getWidth()/2;
        newPos.y = targetPosition.y - getArea().getHeight()/2;
        getArea().setPosition(newPos);
    }

    public Rectangle getArea() {
        return area;
    }

    public void setArea(Rectangle area) {
        this.area = area;
    }

}
