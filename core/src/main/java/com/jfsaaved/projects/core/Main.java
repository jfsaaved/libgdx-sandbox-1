package com.jfsaaved.projects.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
	public static TextureAtlas images;
	public static TextureAtlas imagesAttack;
	public static TextureAtlas imagesJump;
	public static TextureAtlas imagesJumpForward;
	public static TextureAtlas imagesJumpBackward;
	public static TextureAtlas imagesPunch01;
	public static TextureAtlas imagesPunch02;
	public static TextureAtlas imagesKick01;

	private int currentDelay = 0;
	private int timer = 0;

	@Override
	public void create () {
		gameStateManager = new GameStateManager(new Stack<State>());
		spriteBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		images = new TextureAtlas("kim.txt");
		imagesAttack = new TextureAtlas("kim-attack.txt");
		imagesJump = new TextureAtlas("kim-jump.txt");
		imagesJumpForward = new TextureAtlas("kim-f-jump.txt");
		imagesJumpBackward = new TextureAtlas("kim-b-jump.txt");
		imagesPunch01 = new TextureAtlas("kim-punch-01.txt");
		imagesPunch02 = new TextureAtlas("kim-punch-02.txt");
		imagesKick01 = new TextureAtlas("kim-kick-01.txt");
		gameStateManager.push(new PlayState());
	}

	@Override
	public void resize(int i, int i1) {

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		adjustTime();
		if(timer >= currentDelay) {
			timer = 0;
			gameStateManager.update(Gdx.graphics.getDeltaTime());
		}
		gameStateManager.render(spriteBatch);
		gameStateManager.shapeRender(shapeRenderer);
		timer++;
	}

	private void adjustTime() {
		if(Gdx.input.isKeyJustPressed(Input.Keys.P)) {
			currentDelay++;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.O)) {
			currentDelay--;
			if(currentDelay < 0) {
				currentDelay = 0;
			}
		}
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
