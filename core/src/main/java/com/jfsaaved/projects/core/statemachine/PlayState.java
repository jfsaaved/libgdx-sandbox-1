package com.jfsaaved.projects.core.statemachine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jfsaaved.projects.core.Main;
import com.jfsaaved.projects.core.objects.Player;

public class PlayState extends State{

    private Player player;

    public PlayState(GameStateManager gameStateManager){
        super(gameStateManager);
        player = new Player(Main.images.getAtlas("assets").findRegion("player"),
                (int) Main.WIDTH/2,
                (int) Main.HEIGHT/2, 36, 54);
    }

    @Override
    protected void update(float dt) {
        player.update(dt);
    }

    @Override
    protected void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(orthographicCamera.combined);
        spriteBatch.begin();
        player.render(spriteBatch);
        spriteBatch.end();
    }

    @Override
    protected void shapeRender(ShapeRenderer shapeRenderer) {
        shapeRenderer.setProjectionMatrix(orthographicCamera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        player.shapeRender(shapeRenderer);
        shapeRenderer.end();
    }

}