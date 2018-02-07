package com.webgame.game.controllers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.webgame.game.entities.player.Enemy;
import com.webgame.game.entities.player.Player;
import com.webgame.game.entities.skill.Skill;
import com.webgame.game.enums.SkillState;

import java.util.List;

public class SkillController extends AbstractController {
    private Player player;
    private List<Enemy> enemies;

    private Skill skill;

    private ShapeRenderer sr;

    public SkillController(){

    }

    public void init(Player player, List<Enemy> enemies) {
        this.player = player;
        this.enemies = enemies;

        skill = player.getSkill();
    }


    @Override
    public void act(float dt){
        super.act(dt);
        if (sr == null)
            sr = new ShapeRenderer();
        sr.setProjectionMatrix(this.getStage().getCamera().combined);

        skill.update(dt);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(skill.getSkillState().equals(SkillState.ACTIVE)) {
            skill.draw(batch, parentAlpha);

            batch.end();
            sr.setColor(Color.BLUE);
            sr.begin(ShapeRenderer.ShapeType.Line);
            sr.rect(skill.getSkillZone().x, skill.getSkillZone().y,skill.getSkillZone().width, skill.getSkillZone().height);
            sr.end();
            batch.begin();
        }
    }
}
