package com.webgame.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.webgame.game.entities.player.Enemy;
import com.webgame.game.entities.player.Player;
import com.webgame.game.entities.skill.Skill;
import com.webgame.game.enums.PlayerAnimationState;
import com.webgame.game.events.AttackEvent;

import java.util.List;

public class SkillController extends AbstractController implements  EventListener {
    private Player player;
    private List<Enemy> enemies;

    private ShapeRenderer sr;

    public SkillController() {
    }

    public void init(Player player, List<Enemy> enemies) {
        this.player = player;
        this.enemies = enemies;
        this.addListener(this);
    }


    @Override
    public void act(float dt) {
        super.act(dt);
        if (sr == null)
            sr = new ShapeRenderer();
        sr.setProjectionMatrix(this.getStage().getCamera().combined);

        List<Skill> skills = player.getActiveSkills();
        for (Skill skill : skills)
            skill.update(dt);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        List<Skill> skills = player.getActiveSkills();
        for (Skill skill : skills)
            skill.draw(batch, parentAlpha);
    }

    @Override
    public boolean handle(Event event) {
        if (event instanceof AttackEvent) {
            //Casting skill
            if (!player.getCurrAnimationState().equals(PlayerAnimationState.ATTACK)) {
                player.clearTimers();
                player.setCurrAnimationState(PlayerAnimationState.ATTACK);
            }
            player.castSkill(((AttackEvent) event).getTargetVector());
        }
        return true;
    }
}
