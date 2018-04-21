package com.webgame.server.dto;

import com.webgame.client.enums.SkillAnimationState;

public class SpriteAttributesDTO implements DTO {
    private Integer standTextureRegionX;
    private Integer standTextureRegionY;

    private Integer standTextureRegionWidth;
    private Integer standTextureRegionHeight;

    private String animationClazz;

    private Integer numFrames;
    private SkillAnimationState animationState;
    private boolean isLooping;

    public SpriteAttributesDTO() {

    }

    public SpriteAttributesDTO(Integer standX, Integer standY, Integer standW, Integer standH, String animationClazz, Integer numFrames, SkillAnimationState animationState, boolean isLooping) {
        setAnimationClazz(animationClazz);
        setNumFrames(numFrames);
        setStandTextureRegionHeight(standH);
        setStandTextureRegionWidth(standW);
        setStandTextureRegionX(standX);
        setStandTextureRegionY(standY);
        setLooping(isLooping);
        setAnimationState(animationState);
    }

    public Integer getStandTextureRegionX() {
        return standTextureRegionX;
    }

    public void setStandTextureRegionX(Integer standTextureRegionX) {
        this.standTextureRegionX = standTextureRegionX;
    }

    public Integer getStandTextureRegionY() {
        return standTextureRegionY;
    }

    public void setStandTextureRegionY(Integer standTextureRegionY) {
        this.standTextureRegionY = standTextureRegionY;
    }

    public Integer getStandTextureRegionWidth() {
        return standTextureRegionWidth;
    }

    public void setStandTextureRegionWidth(Integer standTextureRegionWidth) {
        this.standTextureRegionWidth = standTextureRegionWidth;
    }

    public Integer getStandTextureRegionHeight() {
        return standTextureRegionHeight;
    }

    public void setStandTextureRegionHeight(Integer standTextureRegionHeight) {
        this.standTextureRegionHeight = standTextureRegionHeight;
    }

    public String getAnimationClazz() {
        return animationClazz;
    }

    public void setAnimationClazz(String animationClazz) {
        this.animationClazz = animationClazz;
    }

    public Integer getNumFrames() {
        return numFrames;
    }

    public void setNumFrames(Integer numFrames) {
        this.numFrames = numFrames;
    }

    public SkillAnimationState getAnimationState() {
        return animationState;
    }

    public void setAnimationState(SkillAnimationState animationState) {
        this.animationState = animationState;
    }

    public boolean isLooping() {
        return isLooping;
    }

    public void setLooping(boolean looping) {
        isLooping = looping;
    }


}
