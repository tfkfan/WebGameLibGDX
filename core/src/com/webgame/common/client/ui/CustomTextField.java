package com.webgame.common.client.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.webgame.common.client.Configs;

public class CustomTextField extends TextField{
    public CustomTextField(String text, TextFieldStyle style){
        super(text, style);
    }

    public static CustomTextField createField(String title, Boolean isScaled){
        TextFieldStyle fieldStyle = new TextFieldStyle();
        fieldStyle.font = new BitmapFont();

        fieldStyle.fontColor = Color.WHITE;

       // fieldStyle.downFontColor = Color.BLACK;
        //fieldStyle.checkedFontColor = Color.GREEN;

        CustomTextField field = new CustomTextField(title, fieldStyle);

       // field.setSize(0,0);
        if(isScaled) {
           // field.setTransform(true);

            field.setScale(1 / Configs.PPM);
        }

        field.setDebug(true);

        return field;
    }

    public static CustomTextField createField(String title){
        return createField(title, false);
    }

    public static CustomTextField createField(){
        return createField("", false);
    }
}
