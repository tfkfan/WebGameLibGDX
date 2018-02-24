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
import com.webgame.game.stages.actor.SkillPanel;

import java.util.List;

public class SkillController extends AbstractController implements InputProcessor, EventListener {
    private Player player;
    private List<Enemy> enemies;

    private SkillPanel skillPanel;
    private ShapeRenderer sr;

    public SkillController() {
        Gdx.input.setInputProcessor(this);
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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (button == Input.Buttons.LEFT) {

            Vector3 trg = this.getStage().getCamera().unproject(new Vector3(screenX, screenY, 0), getStage().getViewport().getScreenX(), getStage().getViewport().getScreenY(),
                    getStage().getViewport().getScreenWidth(), getStage().getViewport().getScreenHeight());

            Vector2 target = new Vector2(trg.x, trg.y);
            fire(new AttackEvent((target)));
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
