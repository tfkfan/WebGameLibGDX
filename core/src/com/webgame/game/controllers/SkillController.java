package com.webgame.game.controllers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.webgame.game.entities.player.Enemy;
import com.webgame.game.entities.player.Player;
import com.webgame.game.entities.skill.Skill;
import com.webgame.game.enums.EntityState;

import java.util.List;

public class SkillController extends AbstractController {
    private Player player;
    private List<Enemy> enemies;


    private ShapeRenderer sr;

    public SkillController() {

    }

    public void init(Player player, List<Enemy> enemies) {
        this.player = player;
        this.enemies = enemies;


    }


    @Override
    public void act(float dt) {
        super.act(dt);
        if (sr == null)
            sr = new ShapeRenderer();
        sr.setProjectionMatrix(this.getStage().getCamera().combined);

        List<Skill> skills = player.getSkills();
        for (Skill skill : skills)
            skill.update(dt);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        List<Skill> skills = player.getSkills();
        for (Skill skill : skills)
            skill.draw(batch, parentAlpha);

        /*
        batch.end();
        sr.setColor(Color.BLUE);
        sr.begin(ShapeRenderer.ShapeType.Line);

            if(skill.getSkillZone() instanceof Rectangle) {
                Rectangle shape = (Rectangle) skill.getSkillZone();
                sr.rect(shape.x, shape.y, shape.width, shape.height);
            }else if(skill.getSkillZone() instanceof Circle){
                Circle shape = (Circle) skill.getSkillZone();
                sr.circle(shape.x, shape.y, shape.radius);
            }

        sr.end();
        batch.begin();

        */

    }
}
