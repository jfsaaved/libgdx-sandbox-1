package com.jfsaaved.projects.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jfsaaved.projects.core.assets.Images;
import com.jfsaaved.projects.core.statemachine.GameStateManager;
import com.jfsaaved.projects.core.statemachine.PlayState;
import com.jfsaaved.projects.core.statemachine.State;

import java.util.Stack;

public class Main implements ApplicationListener {

	public static float WIDTH = 1280;
	public static float HEIGHT = 768;

	public static GameStateManager gameStateManager;
	public static SpriteBatch spriteBatch;
	public static ShapeRenderer shapeRenderer;
	public static Images images;

	@Override
	public void create () {
		gameStateManager = new GameStateManager(new Stack<State>());
		spriteBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		images = new Images("pack1.pack","assets");
		gameStateManager.push(new PlayState());
	}

	@Override
	public void resize(int i, int i1) {

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameStateManager.update(Gdx.graphics.getDeltaTime());
		gameStateManager.render(spriteBatch);
		gameStateManager.shapeRender(shapeRenderer);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose () {
		spriteBatch.dispose();
	}
}
