package com.jfsaaved.projects.core.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jfsaaved.projects.core.handlers.SpriteHandler;

public class Player {

    private SpriteHandler spriteHandler;

    private Vector2 position;
    private Rectangle rectangle;

    public Player(TextureRegion textureRegion, int x, int y, int width, int height) {
        position = new Vector2();
        position.set(x, y);

        rectangle = new Rectangle();
        rectangle.width = width;
        rectangle.height = height;

        spriteHandler = new SpriteHandler()
                .handleStandingSprite(textureRegion, width, height, 4)
                .handleWalkingSprite(textureRegion, width, height, 10);
    }

    public void update(float dt){
        updateInput(dt);
        spriteHandler.update(dt);
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(spriteHandler.getActiveSprite(), position.x, position.y);
    }

    public void shapeRender(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(position.x, position.y, rectangle.width, rectangle.height);
    }

    private void updateMovement(float dt) {

    }

    private void updateInput(float dt) {
        boolean pressedButton = false;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            pressedButton = true;
            spriteHandler.setFlip(true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            pressedButton = true;
            spriteHandler.setFlip(false);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            pressedButton = true;
            spriteHandler.setAnimationState(SpriteHandler.AnimationState.WALKING);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            pressedButton = true;
            spriteHandler.setAnimationState(SpriteHandler.AnimationState.WALKING);
        }

        if(!pressedButton) {
            spriteHandler.setAnimationState(SpriteHandler.AnimationState.STANDING);
        }
    }

}
