package com.webgame.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.webgame.game.Configs;

public class SkillButton extends TextButton{
    public SkillButton(String text, TextButtonStyle style){
        super(text, style);
    }

    public static SkillButton createButton(String title, ClickListener listener){
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont();

        textButtonStyle.fontColor = Color.WHITE;

        textButtonStyle.downFontColor = Color.BLACK;
        textButtonStyle.checkedFontColor = Color.GREEN;

        SkillButton btn = new SkillButton(title, textButtonStyle);

        //btn.setSize(1 / Configs.PPM, 1/ Configs.PPM);
        btn.setTransform(true);

        btn.setScale(1/Configs.PPM);

        btn.setDebug(true);

        btn.addListener(listener);
        btn.setPosition(0,0);
        return btn;
    }
}
