package com.jfsaaved.projects.core.statemachine;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.jfsaaved.projects.core.Main;

public abstract class State {

    protected OrthographicCamera orthographicCamera;
    protected Vector3 mouse;

    protected State () {

    }

    protected void stateInit() {
        this.orthographicCamera = new OrthographicCamera();
        this.mouse = new Vector3();
        this.updateCam((int) Main.WIDTH, (int) Main.HEIGHT, Main.WIDTH/2, Main.HEIGHT/2);
    }

    protected void updateCam(int width, int height, float x, float y){
        orthographicCamera.setToOrtho(false, width, height);
        orthographicCamera.position.set(x, y, 0);
        orthographicCamera.update();
    }

    protected abstract void update(float dt);
    protected abstract void render(SpriteBatch spriteBatch);
    protected abstract void shapeRender(ShapeRenderer shapeRenderer);
    public OrthographicCamera getOrthographicCamera(){ return this.orthographicCamera; }

}
