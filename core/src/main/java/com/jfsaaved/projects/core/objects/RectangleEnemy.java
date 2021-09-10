package com.jfsaaved.projects.core.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import static com.jfsaaved.projects.core.Main.HEIGHT;
import static com.jfsaaved.projects.core.Main.WIDTH;

public class RectangleEnemy {

    private static float MAX_VEL = 2f;
    private static float MAX_JUMP_SPEED = 7f;
    private static float GRAVITY = -15f;

    private final Vector2 position;
    private final Vector2 velocity;
    private final Rectangle rectangle;

    private ObjectState objectState;

    private enum ObjectState{
        JUMPING, IDLE, WALKING, RECOVERY, ACTIVE, BLOCKING, START_UP
    }

    public RectangleEnemy(int x, int y, int width, int height) {
        position = new Vector2();
        position.set(x, y);

        velocity = new Vector2();

        rectangle = new Rectangle();
        rectangle.width = width;
        rectangle.height = height;

        objectState = ObjectState.IDLE;
    }

    public void update(float dt) {
        updateMovement(dt);
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
            if (objectState.equals(ObjectState.JUMPING)) {
                objectState = ObjectState.IDLE;
            }
        }

        if (position.x < 0) {
            position.x = 0;
        }
        if (position.x > WIDTH - rectangle.width ) {
            position.x = WIDTH - rectangle.width;
        }
    }


    public void shapeRender(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(position.x, position.y, rectangle.width, rectangle.height);
    }

}
