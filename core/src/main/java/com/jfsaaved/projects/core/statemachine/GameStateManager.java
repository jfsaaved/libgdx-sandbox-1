package com.jfsaaved.projects.core.statemachine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Stack;

public class GameStateManager {

    private final Stack<State> states;

    public GameStateManager(Stack<State> states) {
        this.states = states;
    }

    public void update(float dt){
        State currentState = states.peek();
        currentState.update(dt);
    }

    public void render(SpriteBatch spriteBatch){
        states.peek().render(spriteBatch);
    }

    public void shapeRender(ShapeRenderer shapeRenderer){
        states.peek().shapeRender(shapeRenderer);
    }

    public void push(State state) {
        states.push(state);
    }

    public void pop(){
        states.pop();
    }

    public void set(State state){
        states.pop();
        states.push(state);
    }

}
