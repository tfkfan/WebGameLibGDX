package com.webgame.common.client.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.webgame.common.client.Configs;

public class CustomLabel extends Label{
    public CustomLabel(String text, Label.LabelStyle style){
        super(text, style);
    }

    public static CustomLabel createLabel(String title, Boolean isScaled){
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont();

        labelStyle.fontColor = Color.WHITE;

       // labelStyle.downFontColor = Color.BLACK;
        //labelStyle.checkedFontColor = Color.GREEN;

        CustomLabel label = new CustomLabel(title, labelStyle);

       // label.setSize(0,0);
        if(isScaled) {
           // label.setTransform(true);

            label.setScale(1 / Configs.PPM);
        }

        label.setDebug(true);

        return label;
    }

    public static CustomLabel createLabel(String title){
        return createLabel(title, false);
    }
}
