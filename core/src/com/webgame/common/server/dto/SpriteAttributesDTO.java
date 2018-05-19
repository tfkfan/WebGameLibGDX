package com.webgame.common.server.dto;

import com.webgame.common.client.enums.SkillAnimationState;

public class SpriteAttributesDTO implements DTO {
    private Integer standTextureRegionX;
    private Integer standTextureRegionY;

    private Integer standTextureRegionWidth;
    private Integer standTextureRegionHeight;

    private Integer numFrames;
    private SkillAnimationState animationState;
    private boolean isLooping;

    private Float animationDuration;
    private int width;
    private int height;
    private int xIterations;
    private int yIterations;
    private int xOffset;
    private int yOffset;

    public SpriteAttributesDTO() {

    }

    public SpriteAttributesDTO(Integer standX, Integer standY, Integer standW, Integer standH, Integer numFrames, SkillAnimationState animationState,
                               boolean isLooping, Float animationDuration, int width, int height, int xIterations, int yIterations, int xOffset, int yOffset) {
        setNumFrames(numFrames);
        setStandTextureRegionHeight(standH);
        setStandTextureRegionWidth(standW);
        setStandTextureRegionX(standX);
        setStandTextureRegionY(standY);
        setLooping(isLooping);
        setAnimationState(animationState);
        setAnimationDuration(animationDuration);
        setWidth(width);
        setHeight(height);
        setxIterations(xIterations);
        setyIterations(yIterations);
        setxOffset(xOffset);
        setyOffset(yOffset);
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


    public Float getAnimationDuration() {
        return animationDuration;
    }

    public void setAnimationDuration(Float animationDuration) {
        this.animationDuration = animationDuration;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getxIterations() {
        return xIterations;
    }

    public void setxIterations(int xIterations) {
        this.xIterations = xIterations;
    }

    public int getyIterations() {
        return yIterations;
    }

    public void setyIterations(int yIterations) {
        this.yIterations = yIterations;
    }

    public int getxOffset() {
        return xOffset;
    }

    public void setxOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    public void setyOffset(int yOffset) {
        this.yOffset = yOffset;
    }
}
