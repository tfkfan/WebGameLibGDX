package com.webgame.game.enums;

public enum SkillKind {
    BLIZZARD(0, SkillClass.AOE, "Blizzard", true, true, true),
    FIRE_BALL(1, SkillClass.SINGLE, "Fire ball", false, false, false),
    ICE_BOLT(2, SkillClass.SINGLE, "Ice bolt", false, false, false),
    HEAL(3, SkillClass.BUFF, "Simple heal", true, false, false),
    MAGIC_DEFENCE(4, SkillClass.BUFF, "Magic defence", true, false, false),
    LIGHTNING(5, SkillClass.STATIC_SINGLE, "Lightning", true, false, false),
    FIRE_EXPLOSION(6, SkillClass.AOE, "Fire explosion", true, false, true),
    TORNADO(7, SkillClass.AOE, "Tornado", true, false, true);


    private SkillClass skillClass;
    private String name;
    private boolean isStatic;
    private boolean isFalling;
    private boolean isTimed;
    private Integer property;

    SkillKind(Integer property, SkillClass skillClass, String name, boolean isStatic, boolean isFalling, boolean isTimed) {
        setSkillClass(skillClass);
        setName(name);
        setFalling(isFalling);
        setStatic(isStatic);
        setProperty(property);
        setTimed(isTimed);
    }

    public SkillKind findByProperty(Integer property) {
        for (SkillKind skillKind : this.getDeclaringClass().getEnumConstants()) {
            if (skillKind.getProperty().equals(property))
                return skillKind;
        }

        return null;
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

    public Integer getProperty() {
        return property;
    }

    public void setProperty(Integer property) {
        this.property = property;
    }

    public boolean isTimed() {
        return isTimed;
    }

    public void setTimed(boolean timed) {
        isTimed = timed;
    }
}
