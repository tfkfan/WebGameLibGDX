package com.webgame.game.stages.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.webgame.game.Configs;
import com.webgame.game.stages.GameStage;
import com.webgame.game.world.player.Player;
import com.webgame.game.world.skills.cooldown.SkillContainer;

import java.util.List;

import static com.webgame.game.Configs.PPM;

public class SkillPanel extends Group {
    protected Player player;

    protected ShapeRenderer sr;
    protected BitmapFont font = new BitmapFont();
    protected TextButton[] buttons;

    public SkillPanel() {
        sr = new ShapeRenderer();
        init();
    }

    public SkillPanel(Player player) {
        sr = new ShapeRenderer();
        setPlayer(player);
        init();

    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void act(float dt) {
        int d = player.getCurrentSkillIndex();
        // Gdx.app.log("",d + "");
        for (int i = 0; i < buttons.length; i++) {
            if (d == i)
                buttons[i].getStyle().fontColor = Color.RED;
            else
                buttons[i].getStyle().fontColor = Color.WHITE;
        }
    }


    public void init() {
        List<SkillContainer> containers = player.getSkillContainers();
        buttons = new TextButton[containers.size()];

        float panelWidth = Configs.VIEW_WIDTH / Configs.PPM;
        float panelHeight = 70 / Configs.PPM;
        for (int i = 0; i < containers.size(); i++) {
            SkillContainer cont = containers.get(i);
            TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
            textButtonStyle.font = new BitmapFont();

            textButtonStyle.fontColor = Color.WHITE;

            textButtonStyle.downFontColor = Color.BLACK;
            //textButtonStyle.checkedFontColor = Color.GREEN;
            SkillButton btn = new SkillButton(cont.getSkill().getSkillState().getTitle(), textButtonStyle, i);
            btn.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    player.setCurrentSkillIndex(((SkillButton) event.getListenerActor()).getSkillIndex());
                }
            });

            btn.setBounds(getX() + (i + 1) * 50 / Configs.PPM, getY() + 40 / Configs.PPM, (panelWidth)/ containers.size(), 15 / Configs.PPM);
            btn.setTransform(true);
            btn.setScale(1 / PPM);

            addActor(btn);
            buttons[i] = btn;
        }

        setBounds(getX(), getY(), panelWidth, panelHeight);
        setDebug(true);

    }
}
