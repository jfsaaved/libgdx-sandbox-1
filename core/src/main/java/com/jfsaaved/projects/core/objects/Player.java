package com.jfsaaved.projects.core.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jfsaaved.projects.core.handlers.SpriteHandler;

import static com.jfsaaved.projects.core.Main.HEIGHT;
import static com.jfsaaved.projects.core.Main.WIDTH;

public class Player {

    private static float MAX_VEL = 2f;
    private static float MAX_JUMP_SPEED = 7f;
    private static float GRAVITY = -15f;

    // Punch 01 Frames
    private static int PUNCH_01_FRAMES = 8;
    private static int PUNCH_01_FRAMES_CD = PUNCH_01_FRAMES + 5;

    private PlayerState playerState;
    private ActiveEvent activeEvent;
    private final SpriteHandler spriteHandler;

    private final Vector2 position;
    private final Vector2 velocity;
    private final Rectangle rectangle;

    private boolean pressedButton;
    private int punch01Frames = PUNCH_01_FRAMES_CD;

    private enum PlayerState{
        JUMPING, IDLE, WALKING, RECOVERY, ACTIVE, BLOCKING, START_UP
    }

    private enum ActiveEvent {
        NONE, PUNCH_01
    }

    public Player(TextureRegion textureRegion, int x, int y, int width, int height) {
        position = new Vector2();
        position.set(x, y);

        velocity = new Vector2();

        rectangle = new Rectangle();
        rectangle.width = width;
        rectangle.height = height;

        playerState = PlayerState.IDLE;
        activeEvent = ActiveEvent.NONE;

        spriteHandler = new SpriteHandler()
                .handleStandingSprite(textureRegion, width, height, 11)
                .handleWalkingBackwardSprite(textureRegion, width, height, 6)
                .handleWalkingForwardSprite(textureRegion, width, height, 6);
    }

    public void setAttackSheet(TextureRegion textureRegion) {
        spriteHandler.handleTestAttackSprite(textureRegion, 96, 144, 19);
    }

    public void setJumpSheet(TextureRegion textureRegion) {
        spriteHandler.handleStandingJumpSprite(textureRegion,64,144, 6);
    }

    public void setJumpForwardSheet(TextureRegion textureRegion) {
        spriteHandler.handleForwardJumpSprite(textureRegion,96,80, 6);
    }

    public void setJumpBackwardSheet(TextureRegion textureRegion) {
        spriteHandler.handleBackwardJumpSprite(textureRegion,112,129, 6);
    }

    public void setPunch01Sheet(TextureRegion textureRegion) {
        spriteHandler.handlePunch01Sprite(textureRegion, 80, 112, 2);
    }

    public void update(float dt){
        updateInput(dt);
        handlePunch01();
        handleJumpAnimation();
        spriteHandler.update(dt);
        updateMovement(dt);
    }

    public void render(SpriteBatch spriteBatch) {
        float drawingX = position.x;
        float drawingY = position.y;
        if(playerState.equals(PlayerState.JUMPING)) {
            if(velocity.x > 0) {
                drawingY = drawingY + 30;
            } else if(velocity.x < 0) {
                drawingY = drawingY - 10;
            }
        }
        spriteBatch.draw(spriteHandler.getActiveSprite(), drawingX, drawingY);
    }

    public void shapeRender(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(position.x, position.y, rectangle.width, rectangle.height);
    }

    private void handleJumpAnimation() {
        if(playerState.equals(PlayerState.JUMPING)) {
            if(velocity.x > 0) {
                if(velocity.y <= 7 && velocity.y > 5) spriteHandler.setAnimationState(SpriteHandler.AnimationState.JUMP_F_1);
                if(velocity.y <= 5 && velocity.y > 2) spriteHandler.setAnimationState(SpriteHandler.AnimationState.JUMP_F_2);
                if(velocity.y <= 2 && velocity.y > -1) spriteHandler.setAnimationState(SpriteHandler.AnimationState.JUMP_F_3);
                if(velocity.y <= -1 && velocity.y > -4) spriteHandler.setAnimationState(SpriteHandler.AnimationState.JUMP_F_4);
                if(velocity.y <= -4 && velocity.y >= -7) spriteHandler.setAnimationState(SpriteHandler.AnimationState.JUMP_MID2);
            }
            else if(velocity.x < 0) {
                if(velocity.y <= 7 && velocity.y > 6) spriteHandler.setAnimationState(SpriteHandler.AnimationState.JUMP_B_1);
                if(velocity.y <= 6 && velocity.y > 5) spriteHandler.setAnimationState(SpriteHandler.AnimationState.JUMP_B_2);
                if(velocity.y <= 5 && velocity.y > 4) spriteHandler.setAnimationState(SpriteHandler.AnimationState.JUMP_B_3);
                if(velocity.y <= 4 && velocity.y > 1) spriteHandler.setAnimationState(SpriteHandler.AnimationState.JUMP_B_4);
                if(velocity.y <= 1 && velocity.y > -2) spriteHandler.setAnimationState(SpriteHandler.AnimationState.JUMP_B_5);
                if(velocity.y <= -2 && velocity.y >= -7) spriteHandler.setAnimationState(SpriteHandler.AnimationState.JUMP_MID2);
            }
            else {
                if (velocity.y <= 7 && velocity.y > 3) spriteHandler.setAnimationState(SpriteHandler.AnimationState.JUMP_MID1);
                if (velocity.y <= 3 && velocity.y > 1.5) spriteHandler.setAnimationState(SpriteHandler.AnimationState.JUMP_APEX1);
                if (velocity.y <= 1.5 && velocity.y > -1.5) spriteHandler.setAnimationState(SpriteHandler.AnimationState.JUMP_APEX2);
                if (velocity.y <= -1.5 && velocity.y > -3) spriteHandler.setAnimationState(SpriteHandler.AnimationState.JUMP_APEX3);
                if (velocity.y <= -3 && velocity.y >= -7) spriteHandler.setAnimationState(SpriteHandler.AnimationState.JUMP_MID2);
            }
        }
    }

    private void handlePunch01() {
        if(activeEvent.equals(ActiveEvent.PUNCH_01)) {
            if(punch01Frames <= PUNCH_01_FRAMES/2)  {
                spriteHandler.setAnimationState(SpriteHandler.AnimationState.PUNCH_01_1);
                playerState = PlayerState.START_UP;
            }
            if(punch01Frames > PUNCH_01_FRAMES/2) {
                spriteHandler.setAnimationState(SpriteHandler.AnimationState.PUNCH_01_2);
                position.x += 1;
                playerState = PlayerState.ACTIVE;
            }
            punch01Frames++;
            if(punch01Frames > PUNCH_01_FRAMES) {
                activeEvent = ActiveEvent.NONE;
                playerState = PlayerState.IDLE;
            }
        } else {
            punch01Frames++;
            if(punch01Frames > PUNCH_01_FRAMES_CD) {
                punch01Frames = PUNCH_01_FRAMES_CD;
            }
        }
    }

    private void updateMovement(float dt) {
        if(!pressedButton) {
            if(!playerState.equals(PlayerState.JUMPING)) velocity.x = 0;
        }

        if(velocity.y > -50) velocity.y += GRAVITY * dt;

        if (velocity.x > MAX_VEL) {
            velocity.x = MAX_VEL;
        }
        if (velocity.x < -MAX_VEL) {
            velocity.x = -MAX_VEL;
        }

        position.add(velocity.x, velocity.y);

        if (position.y < HEIGHT/2) {
            position.y = HEIGHT/2;
            if (playerState.equals(PlayerState.JUMPING)) {
                playerState = PlayerState.IDLE;
            }
        }

        if (position.x < 0) {
            position.x = 0;
        }
        if (position.x > WIDTH - rectangle.width ) {
            position.x = WIDTH - rectangle.width;
        }
    }

    private void updateInput(float dt) {
        pressedButton = false;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            pressedButton = true;
            spriteHandler.setFlip(true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            pressedButton = true;
            spriteHandler.setFlip(true);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            pressedButton = true;
            if (!playerState.equals(PlayerState.JUMPING)
                    && !playerState.equals(PlayerState.START_UP)
                    && !playerState.equals(PlayerState.ACTIVE)
                    && punch01Frames >= PUNCH_01_FRAMES_CD) {
                punch01Frames = 0;
                activeEvent = ActiveEvent.PUNCH_01;
                playerState = PlayerState.START_UP;
            }
        }

        if (!playerState.equals(PlayerState.JUMPING) && !playerState.equals(PlayerState.START_UP) && !playerState.equals(PlayerState.ACTIVE)) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                pressedButton = true;
                playerState = PlayerState.WALKING;
                activeEvent = ActiveEvent.NONE;
                spriteHandler.setAnimationState(SpriteHandler.AnimationState.WALKING_BACKWARD);
                velocity.x = -MAX_VEL;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                pressedButton = true;
                playerState = PlayerState.WALKING;
                activeEvent = ActiveEvent.NONE;
                spriteHandler.setAnimationState(SpriteHandler.AnimationState.WALKING_FORWARD);
                velocity.x = MAX_VEL;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            pressedButton = true;
            if (!playerState.equals(PlayerState.JUMPING)
                    && !playerState.equals(PlayerState.START_UP)
                    && !playerState.equals(PlayerState.ACTIVE)) {
                playerState = PlayerState.JUMPING;
                velocity.y = MAX_JUMP_SPEED;
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.Z)) {
            pressedButton = true;
            spriteHandler.setAnimationState(SpriteHandler.AnimationState.TEST_ATTACK);
        }

        if(!pressedButton) {
            if (!playerState.equals(PlayerState.JUMPING)) {
                playerState = PlayerState.IDLE;
                spriteHandler.setAnimationState(SpriteHandler.AnimationState.STANDING);
            }
        }

    }

}
