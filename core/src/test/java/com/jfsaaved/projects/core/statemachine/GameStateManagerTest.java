package com.jfsaaved.projects.core.statemachine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Stack;

@ExtendWith(MockitoExtension.class)
class GameStateManagerTest {

    @Mock
    private Stack<State> mockStateStack;

    private GameStateManager gameStateManager;

    @BeforeEach
    public void setup() {
        gameStateManager = new GameStateManager(mockStateStack);
    }

    @Test
    public void testUpdateMethodPeeks() {
        Float dt = MathUtils.random();
        MockState mockState = new MockState();
        Mockito.when(mockStateStack.peek()).thenReturn(mockState);
        gameStateManager.update(dt);
        Mockito.verify(mockStateStack,Mockito.atLeast(1)).peek();
    }

    private class MockState extends State {

        @Override
        protected void update(float dt) {

        }

        @Override
        protected void render(SpriteBatch spriteBatch) {

        }

        @Override
        protected void shapeRender(ShapeRenderer shapeRenderer) {

        }
    }

}