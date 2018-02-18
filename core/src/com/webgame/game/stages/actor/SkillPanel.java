package com.webgame.game.stages.actor;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.webgame.game.entities.player.Player;

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
       // int d = player.getCurrentSkillIndex();
        // Gdx.app.log("",d + "");
        /*
        for (int i = 0; i < buttons.length; i++) {
            if (d == i)
                buttons[i].getStyle().fontColor = Color.RED;
            else
                buttons[i].getStyle().fontColor = Color.WHITE;
        }
        */
    }


    public void init() {
        /*
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
            SkillButton btn = new SkillButton(cont.getSkillOrig().getEntityState().getTitle(), textButtonStyle, i);
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
        */

    }
}
