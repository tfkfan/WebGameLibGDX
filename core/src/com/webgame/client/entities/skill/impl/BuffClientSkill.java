package com.webgame.client.entities.skill.impl;

import com.webgame.client.entities.skill.ClientSkill;

public class BuffClientSkill extends StaticClientSkill {

    public BuffClientSkill(){
        super();
    }

    public BuffClientSkill(ClientSkill clientSkill){
        super(clientSkill);
    }


    @Override
    public BuffClientSkill createCopy() {
        return new BuffClientSkill(this);
    }

    @Override
    public void updateAnimations(float dt) {
        setTarget(getPlayer().getPosition());
        super.updateAnimations(dt);

    }
}
