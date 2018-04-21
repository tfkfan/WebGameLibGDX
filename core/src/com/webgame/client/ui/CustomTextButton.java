package com.webgame.client.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.webgame.client.Configs;

public class CustomTextButton extends TextButton{
    public CustomTextButton(String text, TextButtonStyle style){
        super(text, style);
    }

    public static CustomTextButton createButton(String title, Boolean isScaled, ClickListener listener){
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont();

        textButtonStyle.fontColor = Color.WHITE;

        textButtonStyle.downFontColor = Color.BLACK;
        textButtonStyle.checkedFontColor = Color.GREEN;

        CustomTextButton btn = new CustomTextButton(title, textButtonStyle);

       // btn.setSize(0,0);
        if(isScaled) {
            btn.setTransform(true);

            btn.setScale(1 / Configs.PPM);
        }

        btn.setDebug(true);

        btn.addListener(listener);
        return btn;
    }

    public static CustomTextButton createButton(String title, ClickListener listener){
        return createButton(title, false, listener);
    }
}
