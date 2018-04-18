package com.webgame.game.enums;

public enum SkillKind {
    BLIZZARD(SkillClass.AOE, "Blizzard",true, true),
    FIRE_BALL(SkillClass.SINGLE, "Fire ball", false, false),
    ICE_BOLT(SkillClass.SINGLE, "Ice bolt", false, false),
    HEAL(SkillClass.BUFF, "Simple heal", true, false),
    MAGIC_DEFENCE(SkillClass.BUFF, "Magic defence", true, false);

    private SkillClass skillClass;
    private String name;
    private boolean isStatic;
    private boolean isFalling;

    SkillKind(SkillClass skillClass, String name, boolean isStatic, boolean isFalling) {
        setSkillClass(skillClass);
        setName(name);
        setFalling(isFalling);
        setStatic(isStatic);
    }

    public boolean isFalling() {
        return isFalling;
    }

    public void setFalling(boolean falling) {
        isFalling = falling;
    }

    public boolean isAoe() {
        return getSkillClass().equals(SkillClass.AOE);
    }

    public enum SkillClass {
        AOE, SINGLE, BUFF
    }

    public SkillClass getSkillClass() {
        return skillClass;
    }

    public void setSkillClass(SkillClass skillClass) {
        this.skillClass = skillClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }
}
