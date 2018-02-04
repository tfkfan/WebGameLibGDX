package com.webgame.game.stages.actor;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class SkillButton extends TextButton{
    protected Integer skillIndex;
    public SkillButton(String text, TextButtonStyle style, Integer skillIndex){
        super(text, style);
        setSkillIndex(skillIndex);
    }

    public Integer getSkillIndex() {
        return skillIndex;
    }

    public void setSkillIndex(Integer skillIndex) {
        this.skillIndex = skillIndex;
    }
}
