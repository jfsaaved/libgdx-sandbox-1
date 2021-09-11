package com.jfsaaved.projects.core.handlers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteHandler {

    public enum AnimationState{
        STANDING, WALKING_BACKWARD, WALKING_FORWARD, TEST_ATTACK,
        JUMP_START_UP, JUMP_MID1, JUMP_APEX1, JUMP_APEX2, JUMP_APEX3, JUMP_MID2,
        JUMP_F_1, JUMP_F_2, JUMP_F_3, JUMP_F_4,
        JUMP_B_1, JUMP_B_2, JUMP_B_3, JUMP_B_4, JUMP_B_5,
        PUNCH_01_1, PUNCH_01_2,
        PUNCH_02_1, PUNCH_02_2, PUNCH_02_3, PUNCH_02_4,
        KICK_01_1, KICK_01_2, KICK_01_3, KICK_01_4, KICK_01_5, KICK_01_6
    }

    private Sprite[] standingSprite;
    private Sprite[] walkingBackwardSprite;
    private Sprite[] walkingForwardSprite;
    private Sprite[] textAttackSprite;
    private Sprite[] jumpingStandingSprite;
    private Sprite[] jumpingForwardSprite;
    private Sprite[] jumpingBackwardSprite;
    private Sprite[] punch01Sprite;
    private Sprite[] punch02Sprite;
    private Sprite[] kick01Sprite;

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

    public SpriteHandler handleForwardJumpSprite(TextureRegion textureRegion, int width, int height, int frames) {
        jumpingForwardSprite = new Sprite[frames];
        for(int col = 0; col < frames; col++)
            jumpingForwardSprite[col] = new Sprite(textureRegion, width * col, 0, width, height);
        return this;
    }

    public SpriteHandler handleBackwardJumpSprite(TextureRegion textureRegion, int width, int height, int frames) {
        jumpingBackwardSprite = new Sprite[frames];
        for(int col = 0; col < frames; col++)
            jumpingBackwardSprite[col] = new Sprite(textureRegion, width * col, 0, width, height);
        return this;
    }

    public SpriteHandler handlePunch01Sprite(TextureRegion textureRegion, int width, int height, int frames) {
        punch01Sprite = new Sprite[frames];
        for(int col = 0; col < frames; col++)
            punch01Sprite[col] = new Sprite(textureRegion, width * (col), 0, width, height);
        return this;
    }

    public SpriteHandler handlePunch02Sprite(TextureRegion textureRegion, int width, int height, int frames) {
        punch02Sprite = new Sprite[frames];
        for(int col = 0; col < frames; col++)
            punch02Sprite[col] = new Sprite(textureRegion, width * (col), 0, width, height);
        return this;
    }

    public SpriteHandler handleKick01Sprite(TextureRegion textureRegion, int width, int height, int frames) {
        kick01Sprite = new Sprite[frames];
        for(int col = 0; col < frames; col++)
            kick01Sprite[col] = new Sprite(textureRegion, width * (col), 0, width, height);
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
        if(animationState == AnimationState.WALKING_BACKWARD) activeSprite = walkingBackwardSprite;
        else if(animationState == AnimationState.WALKING_FORWARD) activeSprite = walkingForwardSprite;
        else if(animationState == AnimationState.STANDING) activeSprite = standingSprite;
        else if(animationState == AnimationState.TEST_ATTACK) activeSprite = textAttackSprite;
        else if(animationState == AnimationState.JUMP_START_UP) activeSprite = jumpingStandingSprite;
        else if(animationState == AnimationState.JUMP_MID1) activeSprite = jumpingStandingSprite;
        else if(animationState == AnimationState.JUMP_APEX1) activeSprite = jumpingStandingSprite;
        else if(animationState == AnimationState.JUMP_APEX2) activeSprite = jumpingStandingSprite;
        else if(animationState == AnimationState.JUMP_APEX3) activeSprite = jumpingStandingSprite;
        else if(animationState == AnimationState.JUMP_MID2) activeSprite = jumpingStandingSprite;

        else if(animationState == AnimationState.JUMP_F_1) activeSprite = jumpingForwardSprite;
        else if(animationState == AnimationState.JUMP_F_2) activeSprite = jumpingForwardSprite;
        else if(animationState == AnimationState.JUMP_F_3) activeSprite = jumpingForwardSprite;
        else if(animationState == AnimationState.JUMP_F_4) activeSprite = jumpingForwardSprite;

        else if(animationState == AnimationState.JUMP_B_1) activeSprite = jumpingBackwardSprite;
        else if(animationState == AnimationState.JUMP_B_2) activeSprite = jumpingBackwardSprite;
        else if(animationState == AnimationState.JUMP_B_3) activeSprite = jumpingBackwardSprite;
        else if(animationState == AnimationState.JUMP_B_4) activeSprite = jumpingBackwardSprite;
        else if(animationState == AnimationState.JUMP_B_5) activeSprite = jumpingBackwardSprite;

        else if(animationState == AnimationState.PUNCH_01_1) activeSprite = punch01Sprite;
        else if(animationState == AnimationState.PUNCH_01_2) activeSprite = punch01Sprite;
        else if(animationState == AnimationState.PUNCH_02_1) activeSprite = punch02Sprite;
        else if(animationState == AnimationState.PUNCH_02_2) activeSprite = punch02Sprite;
        else if(animationState == AnimationState.PUNCH_02_3) activeSprite = punch02Sprite;
        else if(animationState == AnimationState.PUNCH_02_4) activeSprite = punch02Sprite;

        else if(animationState == AnimationState.KICK_01_1) activeSprite = kick01Sprite;
        else if(animationState == AnimationState.KICK_01_2) activeSprite = kick01Sprite;
        else if(animationState == AnimationState.KICK_01_3) activeSprite = kick01Sprite;
        else if(animationState == AnimationState.KICK_01_4) activeSprite = kick01Sprite;
        else if(animationState == AnimationState.KICK_01_5) activeSprite = kick01Sprite;
        else if(animationState == AnimationState.KICK_01_6) activeSprite = kick01Sprite;
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

        // Walking & Standing
        final int COL_FRAME_DELAY_MAX = 3;
        final int COL_FRAME_DELAY_MULTIPLIER = 70;
        if(activeSprite == null) activeSprite = standingSprite;
        currentDelay -= (dt * COL_FRAME_DELAY_MULTIPLIER);
        if(currentDelay < 0) {
            currentFrame++;
            currentDelay = COL_FRAME_DELAY_MAX;
        }
        if(currentFrame > activeSprite.length-1) currentFrame = 0;

        // Standard Jump
        if(animationState.equals(AnimationState.JUMP_START_UP)) currentFrame = 0;
        if(animationState.equals(AnimationState.JUMP_MID1)) currentFrame = 1;
        if(animationState.equals(AnimationState.JUMP_APEX1)) currentFrame = 2;
        if(animationState.equals(AnimationState.JUMP_APEX2)) currentFrame = 3;
        if(animationState.equals(AnimationState.JUMP_APEX3)) currentFrame = 4;
        if(animationState.equals(AnimationState.JUMP_MID2)) currentFrame = 5;

        // Forward Jump
        if(animationState.equals(AnimationState.JUMP_F_1)) currentFrame = 0;
        if(animationState.equals(AnimationState.JUMP_F_2)) currentFrame = 1;
        if(animationState.equals(AnimationState.JUMP_F_3)) currentFrame = 2;
        if(animationState.equals(AnimationState.JUMP_F_4)) currentFrame = 3;

        // Backwards Jump
        if(animationState.equals(AnimationState.JUMP_B_1)) currentFrame = 0;
        if(animationState.equals(AnimationState.JUMP_B_2)) currentFrame = 1;
        if(animationState.equals(AnimationState.JUMP_B_3)) currentFrame = 2;
        if(animationState.equals(AnimationState.JUMP_B_4)) currentFrame = 3;
        if(animationState.equals(AnimationState.JUMP_B_5)) currentFrame = 4;

        // Punch 01
        if(animationState.equals(AnimationState.PUNCH_01_1)) currentFrame = 0;
        if(animationState.equals(AnimationState.PUNCH_01_2)) currentFrame = 1;

        // Punch 02
        if(animationState.equals(AnimationState.PUNCH_02_1)) currentFrame = 0;
        if(animationState.equals(AnimationState.PUNCH_02_2)) currentFrame = 1;
        if(animationState.equals(AnimationState.PUNCH_02_3)) currentFrame = 2;
        if(animationState.equals(AnimationState.PUNCH_02_4)) currentFrame = 3;

        // Kick 01
        if(animationState == AnimationState.KICK_01_1) currentFrame = 0;
        if(animationState == AnimationState.KICK_01_2) currentFrame = 1;
        if(animationState == AnimationState.KICK_01_3) currentFrame = 2;
        if(animationState == AnimationState.KICK_01_4) currentFrame = 3;
        if(animationState == AnimationState.KICK_01_5) currentFrame = 4;
        if(animationState == AnimationState.KICK_01_6) currentFrame = 5;

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
