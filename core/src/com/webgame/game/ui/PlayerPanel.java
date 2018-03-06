package com.webgame.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.webgame.game.Configs;
import com.webgame.game.entities.player.Player;
import com.webgame.game.entities.skill.Skill;
import sun.awt.ConstrainableGraphics;

import java.util.List;

public class PlayerPanel extends Table {
    protected Player player;


    protected BitmapFont font = new BitmapFont();
    protected TextButton[] buttons;


    public PlayerPanel() {
        init();
    }

    public PlayerPanel(Player player) {
        setPlayer(player);
        init();

    }

    @Override
    public void act(float dt){
        //this.setPosition(this.getStage().getCamera().position.x - getWidth()/2, this.getStage().getCamera().position.y - getHeight()/2);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    public void init() {
        List<Skill> allSkills = player.getAllSkills();
        buttons = new TextButton[allSkills.size()];


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

        SkillButton btn2 = SkillButton.createButton("dsfdsdgfsdgsdg", new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("ok2 pressed");
                // player.setCurrentSkill(skill);
            }
        });
        add(btn2).size(btn2.getWidth()/ Configs.PPM, btn2.getHeight()/ Configs.PPM).expand().left();
        SkillButton btn = SkillButton.createButton("dsfd", new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("ok pressed");
                // player.setCurrentSkill(skill);
            }
        });

        add(btn).size(btn.getWidth()/ Configs.PPM, btn.getHeight()/ Configs.PPM).expand().left();
        /*
        row();
        for (int i = 0; i < allSkills.size(); i++) {
            Skill skill = allSkills.get(i);
            TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
            textButtonStyle.font = new BitmapFont();

            textButtonStyle.fontColor = Color.WHITE;

            textButtonStyle.downFontColor = Color.BLACK;
            textButtonStyle.checkedFontColor = Color.GREEN;
            SkillButton btn = new SkillButton(skill.getTitle(), textButtonStyle, i);
            btn.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    player.setCurrentSkill(skill);
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
