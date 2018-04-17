package com.webgame.game.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.webgame.game.Configs;
import com.webgame.game.entities.player.ClientPlayer;
import com.webgame.game.entities.skill.ClientSkill;

import java.util.List;

public class PlayerPanel extends Table {
    protected ClientPlayer clientPlayer;

    protected BitmapFont font = new BitmapFont();
    protected TextButton[] buttons;


    public PlayerPanel() {
        init();
    }

    public PlayerPanel(ClientPlayer clientPlayer) {
        setClientPlayer(clientPlayer);
        init();

    }

    @Override
    public void act(float dt){
        //this.setPosition(this.getStage().getCamera().position.x - getWidth()/2, this.getStage().getCamera().position.y - getHeight()/2);
    }

    public ClientPlayer getClientPlayer() {
        return clientPlayer;
    }

    public void setClientPlayer(ClientPlayer clientPlayer) {
        this.clientPlayer = clientPlayer;
    }

    public void init() {
        List<ClientSkill> allClientSkills = clientPlayer.getAllSkills();
        buttons = new TextButton[allClientSkills.size()];


        setPosition(-Configs.VIEW_WIDTH/(2*Configs.PPM), -Configs.VIEW_HEIGHT/(2*Configs.PPM));
        setSize(Configs.VIEW_WIDTH/Configs.PPM, Configs.VIEW_HEIGHT/Configs.PPM);

        //setFillParent(true);


        //bottom();



        add().expand();
        row();
        add().expand();
        row();
        add().expand();
        row();
        add().expand();
        row();

        CustomTextButton btn2 = CustomTextButton.createButton("dsfdsdgfsdgsdg", true, new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("ok2 pressed");
                // clientPlayer.setCurrentSkillIndex(skill);
            }
        });
        add(btn2).size(btn2.getWidth()/ Configs.PPM, btn2.getHeight()/ Configs.PPM).expand();
        CustomTextButton btn = CustomTextButton.createButton("dsfd", true, new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("ok pressed");
                // clientPlayer.setCurrentSkillIndex(skill);
            }
        });

        add(btn).center().size(btn.getWidth()/ Configs.PPM, btn.getHeight()/ Configs.PPM).expand();
        /*
        row();
        for (int i = 0; i < allClientSkills.size(); i++) {
            ClientSkill skill = allClientSkills.get(i);
            TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
            textButtonStyle.font = new BitmapFont();

            textButtonStyle.fontColor = Color.WHITE;

            textButtonStyle.downFontColor = Color.BLACK;
            textButtonStyle.checkedFontColor = Color.GREEN;
            CustomTextButton btn = new CustomTextButton(skill.getTitle(), textButtonStyle, i);
            btn.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    clientPlayer.setCurrentSkillIndex(skill);
                }
            });

            btn.setTransform(true);
            btn.setScale(1 / Configs.PPM);

            //  btn.setPosition(0,0);
            btn.setDebug(true);
            btn.setSize(10 / Configs.PPM, 15 / Configs.PPM);


            add(btn).expandX().pad(10/Configs.PPM);
            buttons[i] = btn;
        }

        */



        setDebug(true);


    }
}
