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

    private SpriteHandler spriteHandler;

    private Vector2 position;
    private Vector2 acceleration;
    private Vector2 velocity;

    private Rectangle rectangle;

    private PlayerState playerState;

    private int startUpFrames = 0;
    private int activeFrames = 0;
    private int recoveryFrames = 0;

    private enum PlayerState{
        JUMPING, IDLE, WALKING;
    }

    public Player(TextureRegion textureRegion, int x, int y, int width, int height) {
        position = new Vector2();
        position.set(x, y);

        acceleration = new Vector2();
        velocity = new Vector2();

        rectangle = new Rectangle();
        rectangle.width = width;
        rectangle.height = height;

        playerState = PlayerState.IDLE;

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

    public void update(float dt){
        updateInput(dt);
        handleAnimation();
        spriteHandler.update(dt);
        updateMovement(dt);
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(spriteHandler.getActiveSprite(), position.x, position.y);
    }

    public void shapeRender(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(position.x, position.y, rectangle.width, rectangle.height);
    }

    private void handleAnimation() {
        if(playerState.equals(PlayerState.JUMPING)) {
            if(velocity.y < 7 && velocity.y > 3) spriteHandler.setAnimationState(SpriteHandler.AnimationState.JUMP_MID1);
            if(velocity.y < 3 && velocity.y > 1.5) spriteHandler.setAnimationState(SpriteHandler.AnimationState.JUMP_APEX1);
            if(velocity.y < 1.5 && velocity.y > -1.5) spriteHandler.setAnimationState(SpriteHandler.AnimationState.JUMP_APEX2);
            if(velocity.y < -1.5 && velocity.y > -3) spriteHandler.setAnimationState(SpriteHandler.AnimationState.JUMP_APEX3);
            if(velocity.y < -3 && velocity.y > -7) spriteHandler.setAnimationState(SpriteHandler.AnimationState.JUMP_MID2);
        }
    }

    private void updateMovement(float dt) {
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
        boolean pressedButton = false;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            pressedButton = true;
            spriteHandler.setFlip(true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            pressedButton = true;
            spriteHandler.setFlip(true);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            pressedButton = true;
            if (!playerState.equals(PlayerState.JUMPING)) {
                playerState = PlayerState.JUMPING;
                velocity.y = MAX_JUMP_SPEED;
                spriteHandler.setAnimationState(SpriteHandler.AnimationState.JUMP_MID1);
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            pressedButton = true;
            if (!playerState.equals(PlayerState.JUMPING)) {
                playerState = PlayerState.WALKING;
                spriteHandler.setAnimationState(SpriteHandler.AnimationState.WALKING_BACKWARD);
                velocity.x = -MAX_VEL;
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            pressedButton = true;
            if (!playerState.equals(PlayerState.JUMPING)) {
                playerState = PlayerState.WALKING;
                spriteHandler.setAnimationState(SpriteHandler.AnimationState.WALKING_FORWARD);
                velocity.x = MAX_VEL;
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.Z)) {
            pressedButton = true;
            spriteHandler.setAnimationState(SpriteHandler.AnimationState.TEST_ATTACK);
            velocity.x = MAX_VEL;
            velocity.y = 1;
        }

        if(!pressedButton) {
            if (!playerState.equals(PlayerState.JUMPING)) {
                playerState = PlayerState.IDLE;
                spriteHandler.setAnimationState(SpriteHandler.AnimationState.STANDING);
            }
            if(!playerState.equals(PlayerState.JUMPING)) velocity.x = 0;
        }

    }

}
