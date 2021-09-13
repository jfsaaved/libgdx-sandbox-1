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

    // Punch Frames
    private static int PUNCH_01_FRAMES = 8;
    private static int PUNCH_02_FRAMES = 16;
    private static int KICK_01_FRAMES = 26;
    private static int KICK_02_FRAMES = 26;

    private PlayerState playerState;
    private ActiveEvent activeEvent;
    public LinkEvent linkEvent;
    private final SpriteHandler spriteHandler;

    public final Vector2 position;
    private final Vector2 velocity;
    public final Rectangle rectangle;

    public final Rectangle leftHandHitBox;
    public final Rectangle rightHandHitBox;
    public final Rectangle rightLegHitBox;

    //Punch 01 Hit Boxes
    private static float PUNCH_01_POSITION_X = 65;
    private static float PUNCH_01_POSITION_Y = 80;
    private static float PUNCH_01_WIDTH = 20;
    private static float PUNCH_01_HEIGHT = 20;

    //Punch 02 Hit Boxes
    private static float PUNCH_02_POSITION_X = 110;
    private static float PUNCH_02_POSITION_Y = 62;
    private static float PUNCH_02_WIDTH = 20;
    private static float PUNCH_02_HEIGHT = 20;

    //Kick 01 Hit Boxes
    private static float KICK_01_POSITION_X = 65;
    private static float KICK_01_POSITION_Y = 27;
    private static float KICK_01_WIDTH = 20;
    private static float KICK_01_HEIGHT = 20;

    //Kick 02 Hit Boxes
    private static float KICK_02_POSITION_X = 70;
    private static float KICK_02_POSITION_Y = 60;
    private static float KICK_02_WIDTH = 20;
    private static float KICK_02_HEIGHT = 20;

    private void refreshHitBox() {
        rightHandHitBox.x = 0;
        rightHandHitBox.y = 0;
        rightHandHitBox.height = 0;
        rightHandHitBox.width = 0;

        rightLegHitBox.x = 0;
        rightLegHitBox.y = 0;
        rightLegHitBox.width = 0;
        rightLegHitBox.height = 0;
    }

    private boolean pressedButton;
    public int flip;
    private int punch01Frames = 0;
    private int punch02Frames = 0;
    private int kick01Frames = 0;
    private int kick02Frames = 0;

    private float drawingX;
    private float drawingY;

    public enum LinkEvent {
        NONE, KICK_01_TO_KICK_02, KICK_02_TO_KICK_01
    }

    private enum PlayerState{
        JUMPING, IDLE, WALKING, RECOVERY, ACTIVE, BLOCKING, START_UP
    }

    private enum ActiveEvent {
        NONE, PUNCH_01, PUNCH_02, KICK_01, KICK_02
    }

    public Player(TextureRegion textureRegion, int x, int y, int width, int height) {
        position = new Vector2();
        position.set(x, y);

        velocity = new Vector2();

        rectangle = new Rectangle();
        rectangle.width = width - 25;
        rectangle.height = height;

        leftHandHitBox = new Rectangle();
        rightHandHitBox = new Rectangle();
        rightLegHitBox = new Rectangle();

        playerState = PlayerState.IDLE;
        activeEvent = ActiveEvent.NONE;
        linkEvent = LinkEvent.NONE;

        drawingX = position.x;
        drawingY = position.y;

        spriteHandler = new SpriteHandler()
                .handleStandingSprite(textureRegion, width, height, 11)
                .handleWalkingBackwardSprite(textureRegion, width, height, 6)
                .handleWalkingForwardSprite(textureRegion, width, height, 6);

        flip = 1;
        spriteHandler.setFlip(true);
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

    public void setPunch02Sheet(TextureRegion textureRegion) {
        spriteHandler.handlePunch02Sprite(textureRegion, 128, 112, 4);
    }

    public void setKick01Sheet(TextureRegion textureRegion) {
        spriteHandler.handleKick01Sprite(textureRegion,112,112,6);
    }

    public void setKick02Sheet(TextureRegion textureRegion) {
        spriteHandler.handleKick02Sprite(textureRegion,128,112,6);
    }

    public void update(float dt){
        refreshHitBox();
        refreshDrawing();
        updateRectangle();;
        updateInput(dt);
        handlePunch01();
        handlePunch02();
        handleKick01();
        handleKick02();
        handleJumpAnimation();
        handleFlip();
        spriteHandler.update(dt);
        updateMovement(dt);
    }

    private void handleFlip() {
        if(flip > 0) {
            spriteHandler.setFlip(true);
        } else {
            spriteHandler.setFlip(false);
        }
    }

    private void refreshDrawing() {
        drawingX = position.x;
        drawingY = position.y;
    }

    private void updateRectangle(){
        rectangle.setPosition(position.x + 23 * flip, position.y);
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(spriteHandler.getActiveSprite(), drawingX, drawingY);
    }

    public void shapeRender(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public void hitBoxRender(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(rightLegHitBox.x, rightLegHitBox.y, rightLegHitBox.width, rightLegHitBox.height);
        shapeRenderer.rect(rightHandHitBox.x, rightHandHitBox.y, rightHandHitBox.width, rightHandHitBox.height);
    }

    private void handleJumpAnimation() {
        if(playerState.equals(PlayerState.JUMPING)) {
            rectangle.y += 100 * flip;
            if(velocity.x > 0) {
                drawingY = drawingY + 30 * flip;
                if(velocity.y <= 7 && velocity.y > 5) spriteHandler.setAnimationState(SpriteHandler.AnimationState.JUMP_F_1);
                if(velocity.y <= 5 && velocity.y > 2) spriteHandler.setAnimationState(SpriteHandler.AnimationState.JUMP_F_2);
                if(velocity.y <= 2 && velocity.y > -1) spriteHandler.setAnimationState(SpriteHandler.AnimationState.JUMP_F_3);
                if(velocity.y <= -1 && velocity.y > -4) spriteHandler.setAnimationState(SpriteHandler.AnimationState.JUMP_F_4);
                if(velocity.y <= -4 && velocity.y >= -7) spriteHandler.setAnimationState(SpriteHandler.AnimationState.JUMP_MID2);
            }
            else if(velocity.x < 0) {
                drawingY = drawingY - 10 * flip;
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
                drawingX -= 8 * flip;
                drawingY -= 1 * flip;
            }
            if(punch01Frames > PUNCH_01_FRAMES/2) {
                spriteHandler.setAnimationState(SpriteHandler.AnimationState.PUNCH_01_2);
                playerState = PlayerState.ACTIVE;
                drawingX += 7 * flip;
                drawingY -= 1 * flip;
                rightHandHitBox.x = position.x + PUNCH_01_POSITION_X;
                rightHandHitBox.y = position.y + PUNCH_01_POSITION_Y;
                rightHandHitBox.width = PUNCH_01_WIDTH;
                rightHandHitBox.height = PUNCH_01_HEIGHT;
            }
            punch01Frames++;
            if(punch01Frames > PUNCH_01_FRAMES) {
                activeEvent = ActiveEvent.NONE;
                playerState = PlayerState.IDLE;
            }
        } else {
            if(punch01Frames <= PUNCH_01_FRAMES) {
                punch01Frames++;
            }
        }
    }

    private void handlePunch02() {
        if(activeEvent.equals(ActiveEvent.PUNCH_02)) {
            if(punch02Frames <= 4)  {
                spriteHandler.setAnimationState(SpriteHandler.AnimationState.PUNCH_01_1);
                playerState = PlayerState.START_UP;
                drawingX -= 7 * flip;
            }
            else if(punch02Frames <= 8) {
                spriteHandler.setAnimationState(SpriteHandler.AnimationState.PUNCH_02_2);
                playerState = PlayerState.START_UP;
                drawingY -= 15 * flip;
                drawingX -= 37 * flip;
            }
            else if(punch02Frames <= 12) {
                spriteHandler.setAnimationState(SpriteHandler.AnimationState.PUNCH_02_3);
                playerState = PlayerState.ACTIVE;
                drawingY -= 15 * flip;
                drawingX += 6 * flip;
                rightHandHitBox.x = position.x + PUNCH_02_POSITION_X;
                rightHandHitBox.y = position.y + PUNCH_02_POSITION_Y;
                rightHandHitBox.width = PUNCH_02_WIDTH;
                rightHandHitBox.height = PUNCH_02_HEIGHT;
            }
            else if(punch02Frames <= 16) {
                spriteHandler.setAnimationState(SpriteHandler.AnimationState.PUNCH_02_4);
                playerState = PlayerState.ACTIVE;
                drawingY -= 17 * flip;
                drawingX -= 11 * flip;
                rightHandHitBox.x = position.x + PUNCH_02_POSITION_X;
                rightHandHitBox.y = position.y + PUNCH_02_POSITION_Y;
                rightHandHitBox.width = PUNCH_02_WIDTH;
                rightHandHitBox.height = PUNCH_02_HEIGHT;
            }
            punch02Frames++;
            if(punch02Frames > PUNCH_02_FRAMES) {
                activeEvent = ActiveEvent.NONE;
                playerState = PlayerState.IDLE;
            }
        } else {
            if(punch02Frames <= PUNCH_02_FRAMES) {
                punch02Frames++;
            }
        }
    }

    private void handleKick01() {
        if(activeEvent.equals(ActiveEvent.KICK_01)) {
            if(kick01Frames <= 4)  {
                spriteHandler.setAnimationState(SpriteHandler.AnimationState.KICK_01_1);
                playerState = PlayerState.START_UP;
                drawingX -= 47 * flip;
                drawingY += 1 * flip;
            }
            else if(kick01Frames <= 8) {
                spriteHandler.setAnimationState(SpriteHandler.AnimationState.KICK_01_2);
                playerState = PlayerState.ACTIVE;
                drawingX -= 58 * flip;
                drawingY -= 2 * flip;
            }
            else if(kick01Frames <= 12) {
                spriteHandler.setAnimationState(SpriteHandler.AnimationState.KICK_01_3);
                playerState = PlayerState.ACTIVE;
                drawingX -= 25 * flip;
                drawingY -= 2 * flip;
                rightLegHitBox.x = position.x + KICK_01_POSITION_X;
                rightLegHitBox.y = position.y + KICK_01_POSITION_Y;
                rightLegHitBox.width = KICK_01_WIDTH;
                rightLegHitBox.height = KICK_01_HEIGHT;
            }
            else if(kick01Frames <= 16) {
                spriteHandler.setAnimationState(SpriteHandler.AnimationState.KICK_01_4);
                playerState = PlayerState.ACTIVE;
                drawingX -= 25 * flip;
                drawingY -= 2 * flip;
                rightLegHitBox.x = position.x + KICK_01_POSITION_X;
                rightLegHitBox.y = position.y + KICK_01_POSITION_Y;
                rightLegHitBox.width = KICK_01_WIDTH;
                rightLegHitBox.height = KICK_01_HEIGHT;
            }
            else if(kick01Frames <= 20) {
                spriteHandler.setAnimationState(SpriteHandler.AnimationState.KICK_01_5);
                playerState = PlayerState.ACTIVE;
                drawingX -= 40 * flip;
                drawingY -= 2 * flip;
            }
            else if(kick01Frames <= KICK_01_FRAMES) {
                spriteHandler.setAnimationState(SpriteHandler.AnimationState.KICK_01_6);
                playerState = PlayerState.ACTIVE;
                drawingX -= 27 * flip;
                drawingY -= 2 * flip;
            }
            kick01Frames++;
            if(kick01Frames > KICK_01_FRAMES) {
                activeEvent = ActiveEvent.NONE;
                playerState = PlayerState.IDLE;
                linkEvent = LinkEvent.NONE;
            }
        } else {
            if(kick01Frames <= KICK_01_FRAMES) {
                kick01Frames++;
            }
        }
    }

    private void handleKick02() {
        if(activeEvent.equals(ActiveEvent.KICK_02)) {
            if(kick02Frames <= 4)  {
                spriteHandler.setAnimationState(SpriteHandler.AnimationState.KICK_02_1);
                playerState = PlayerState.START_UP;
                drawingX -= 62 * flip;
                drawingY += 1 * flip;
            }
            else if(kick02Frames <= 8) {
                spriteHandler.setAnimationState(SpriteHandler.AnimationState.KICK_02_2);
                playerState = PlayerState.ACTIVE;
                drawingX -= 54 * flip;
                drawingY -= 2 * flip;
            }
            else if(kick02Frames <= 12) {
                spriteHandler.setAnimationState(SpriteHandler.AnimationState.KICK_02_3);
                playerState = PlayerState.ACTIVE;
                drawingX -= 23 * flip;
                drawingY -= 2 * flip;
                rightLegHitBox.x = position.x + KICK_02_POSITION_X;
                rightLegHitBox.y = position.y + KICK_02_POSITION_Y;
                rightLegHitBox.width = KICK_02_WIDTH;
                rightLegHitBox.height = KICK_02_HEIGHT;
            }
            else if(kick02Frames <= 16) {
                spriteHandler.setAnimationState(SpriteHandler.AnimationState.KICK_02_4);
                playerState = PlayerState.ACTIVE;
                drawingX -= 31 * flip;
                drawingY -= 2 * flip;
                rightLegHitBox.x = position.x + KICK_02_POSITION_X;
                rightLegHitBox.y = position.y + KICK_02_POSITION_Y;
                rightLegHitBox.width = KICK_02_WIDTH;
                rightLegHitBox.height = KICK_02_HEIGHT;
            }
            else if(kick02Frames <= 20) {
                spriteHandler.setAnimationState(SpriteHandler.AnimationState.KICK_02_5);
                playerState = PlayerState.ACTIVE;
                drawingX -= 44 * flip;
                drawingY -= 2 * flip;
            }
            else if(kick02Frames <= KICK_02_FRAMES) {
                spriteHandler.setAnimationState(SpriteHandler.AnimationState.KICK_02_6);
                playerState = PlayerState.ACTIVE;
                drawingX -= 43 * flip;
                drawingY -= 2 * flip;
            }
            kick02Frames++;
            if(kick02Frames > KICK_02_FRAMES) {
                activeEvent = ActiveEvent.NONE;
                playerState = PlayerState.IDLE;
                linkEvent = LinkEvent.NONE;
            }
        } else {
            if(kick02Frames <= KICK_02_FRAMES) {
                kick02Frames++;
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
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            pressedButton = true;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            pressedButton = true;
            if (playerState.equals(PlayerState.IDLE)) {
                punch01Frames = 0;
                activeEvent = ActiveEvent.PUNCH_01;
                playerState = PlayerState.START_UP;
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            pressedButton = true;
            if (playerState.equals(PlayerState.IDLE)) {
                punch02Frames = 0;
                activeEvent = ActiveEvent.PUNCH_02;
                playerState = PlayerState.START_UP;
            }
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            pressedButton = true;
            if (playerState.equals(PlayerState.IDLE)) {
                kick01Frames = 0;
                activeEvent = ActiveEvent.KICK_01;
                playerState = PlayerState.START_UP;
            }
            if(kick02Frames > 18 && kick02Frames < 24) {
                kick02Frames = 25;
                kick01Frames = 2;
                activeEvent = ActiveEvent.KICK_01;
                playerState = PlayerState.ACTIVE;
                linkEvent = LinkEvent.KICK_02_TO_KICK_01;
                System.out.println("COMBO");
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            pressedButton = true;
            if (playerState.equals(PlayerState.IDLE)) {
                kick02Frames = 0;
                activeEvent = ActiveEvent.KICK_02;
                playerState = PlayerState.START_UP;
            }
            if(kick01Frames > 18 && kick01Frames < 24) {
                kick01Frames = 25;
                kick02Frames = 2;
                activeEvent = ActiveEvent.KICK_02;
                playerState = PlayerState.ACTIVE;
                linkEvent = LinkEvent.KICK_01_TO_KICK_02;
                System.out.println("COMBO");
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
