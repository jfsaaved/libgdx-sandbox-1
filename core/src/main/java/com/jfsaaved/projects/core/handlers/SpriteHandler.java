package com.jfsaaved.projects.core.handlers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteHandler {

    public enum AnimationState{
        STANDING, WALKING_BACKWARD, WALKING_FORWARD, TEST_ATTACK, JUMP_START_UP, JUMP_MID1, JUMP_APEX1, JUMP_APEX2, JUMP_APEX3, JUMP_MID2
    }

    private Sprite[] standingSprite;
    private Sprite[] walkingBackwardSprite;
    private Sprite[] walkingForwardSprite;
    private Sprite[] textAttackSprite;
    private Sprite[] jumpingStandingSprite;

    private int currentFrame;
    protected int currentDelay;
    private Sprite[] activeSprite;

    private boolean flip;
    private boolean hide;

    private AnimationState animationState;

    public SpriteHandler(){
        flip = false;
        hide = false;
        animationState = AnimationState.STANDING;
    }

    public SpriteHandler handleStandingSprite(TextureRegion textureRegion, int width, int height, int frames) {
        standingSprite = new Sprite[frames];
        for(int col = 0; col < frames; col++)
            standingSprite[col] = new Sprite(textureRegion, width * col, 0, width, height);
        return this;
    }

    public SpriteHandler handleWalkingBackwardSprite(TextureRegion textureRegion, int width, int height, int frames) {
        walkingBackwardSprite = new Sprite[frames];
        for(int col = 0; col < frames; col++)
            walkingBackwardSprite[col] = new Sprite(textureRegion, width * (col + 11), 0, width, height);
        return this;
    }

    public SpriteHandler handleWalkingForwardSprite(TextureRegion textureRegion, int width, int height, int frames) {
        walkingForwardSprite = new Sprite[frames];
        for(int col = 0; col < frames; col++)
            walkingForwardSprite[col] = new Sprite(textureRegion, width * (col + 17), 0, width, height);
        return this;
    }

    public SpriteHandler handleStandingJumpSprite(TextureRegion textureRegion, int width, int height, int frames) {
        jumpingStandingSprite = new Sprite[frames];
        for(int col = 0; col < frames; col++)
            jumpingStandingSprite[col] = new Sprite(textureRegion, width * col, 0, width, height);
        return this;
    }

    public SpriteHandler handleTestAttackSprite(TextureRegion textureRegion, int width, int height, int frames) {
        textAttackSprite = new Sprite[frames];
        for(int col = 0; col < frames; col++)
            textAttackSprite[col] = new Sprite(textureRegion, width * (col), 0, width, height);
        return this;
    }

    public void setAnimationState(AnimationState animationState){
        this.animationState = animationState;
        if(animationState == AnimationState.WALKING_BACKWARD)
            activeSprite = walkingBackwardSprite;
        else if(animationState == AnimationState.WALKING_FORWARD)
            activeSprite = walkingForwardSprite;
        else if(animationState == AnimationState.STANDING)
            activeSprite = standingSprite;
        else if(animationState == AnimationState.TEST_ATTACK)
            activeSprite = textAttackSprite;
        else if(animationState == AnimationState.JUMP_MID1)
            activeSprite = jumpingStandingSprite;
    }

    public AnimationState getAnimationState(){
        return this.animationState;
    }

    public void setStandingSprite(TextureRegion textureRegion, int width, int height, int frames){
        standingSprite = new Sprite[frames];
        for(int col = 0; col < frames; col++)
            standingSprite[col] = new Sprite(textureRegion, width * col, 0, width, height);
    }

    public void setMovingSprite(TextureRegion textureRegion, int width, int height, int frames){
        walkingBackwardSprite = new Sprite[frames];
        for(int col = 0; col < frames; col++)
            walkingBackwardSprite[col] = new Sprite(textureRegion, width * (col + 5), 0, width, height);
    }

    public void update(float dt){
        final int COL_FRAME_DELAY_MAX = 8;
        final int COL_FRAME_DELAY_MULTIPLIER = 70;

        if(activeSprite == null)
            activeSprite = standingSprite;

        currentDelay -= (dt * COL_FRAME_DELAY_MULTIPLIER);

        if(currentDelay < 0) {
            currentFrame++;
            currentDelay = COL_FRAME_DELAY_MAX;
        }

        if(currentFrame > activeSprite.length-1)
            currentFrame = 0;

        if(animationState.equals(AnimationState.JUMP_START_UP))
            currentFrame = 0;
        if(animationState.equals(AnimationState.JUMP_MID1))
            currentFrame = 1;
        if(animationState.equals(AnimationState.JUMP_APEX1))
            currentFrame = 2;
        if(animationState.equals(AnimationState.JUMP_APEX2))
            currentFrame = 3;
        if(animationState.equals(AnimationState.JUMP_APEX3))
            currentFrame = 4;
        if(animationState.equals(AnimationState.JUMP_MID2))
            currentFrame = 5;

        activeSprite[currentFrame].setFlip(flip, false);
    }

    public Sprite getActiveSprite(){
        return activeSprite[currentFrame];
    }

    public boolean isFlip() {
        return flip;
    }

    public void setFlip(boolean flip) {
        this.flip = flip;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

}
