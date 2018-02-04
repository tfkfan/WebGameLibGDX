package com.webgame.game.world.skills.state;

import com.badlogic.gdx.math.Rectangle;

public class SkillState {
    protected Integer level;
    protected String title;

    protected Double damage;
    protected Double healHP;

    protected boolean isActive;
    protected boolean isAOE;
    protected boolean isFalling;
    protected boolean isTimed;
    protected boolean isStatic;
    protected boolean isMarked;
    protected boolean isBuff;
    protected boolean isHeal;
    protected Rectangle area;

    public SkillState(){
        init();
    }

    public SkillState(SkillState skillState){
       setDamage(skillState.getDamage());
       setTitle(skillState.getTitle());
       setActive(skillState.isActive());
       setStatic(skillState.isStatic());
       setAOE(skillState.isAOE());
       setFalling(skillState.isFalling());
       setTimed(skillState.isTimed());
       setMarked(skillState.isMarked());
       setBuff(skillState.isBuff());
       setHeal(skillState.isHeal());
       setArea(skillState.getArea());
       setHealHP(skillState.getHealHP());
       setLevel(skillState.getLevel());
    }

    public void init(){
        damage = 1d;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Double getHealHP() {
        return healHP;
    }

    public void setHealHP(Double healHP) {
        this.healHP = healHP;
    }


    public Double getDamage() {
        return damage;
    }

    public void setDamage(Double damage) {
        this.damage = damage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isAOE() {
        return isAOE;
    }

    public void setAOE(boolean AOE) {
        isAOE = AOE;
    }

    public boolean isFalling() {
        return isFalling;
    }

    public void setFalling(boolean falling) {
        isFalling = falling;
    }

    public boolean isTimed() {
        return isTimed;
    }

    public void setTimed(boolean timed) {
        isTimed = timed;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public boolean isBuff() {
        return isBuff;
    }

    public void setBuff(boolean buff) {
        isBuff = buff;
    }

    public boolean isHeal() {
        return isHeal;
    }

    public void setHeal(boolean heal) {
        isHeal = heal;
    }

    public Rectangle getArea() {
        return area;
    }

    public void setArea(Rectangle area) {
        this.area = area;
    }
}
