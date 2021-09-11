package com.jfsaaved.projects.core.statemachine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jfsaaved.projects.core.Main;
import com.jfsaaved.projects.core.objects.Player;
import com.jfsaaved.projects.core.objects.RectangleEnemy;

public class PlayState extends State{

    private Player player;
    private RectangleEnemy rectangleEnemy;

    public PlayState(){
        stateInit();
        player = new Player(Main.images.findRegion("standing/Kim"),
                (int) Main.WIDTH/2,
                (int) Main.HEIGHT/2 + 50, 64, 112);
        player.setAttackSheet(Main.imagesAttack.findRegion("test-attack/Kim"));
        player.setJumpSheet(Main.imagesJump.findRegion("jump/Kim"));
        player.setJumpForwardSheet(Main.imagesJumpForward.findRegion("Kim"));
        player.setJumpBackwardSheet(Main.imagesJumpBackward.findRegion("Kim"));
        player.setPunch01Sheet(Main.imagesPunch01.findRegion("Kim"));
        player.setPunch02Sheet(Main.imagesPunch02.findRegion("Kim"));
        player.setKick01Sheet(Main.imagesKick01.findRegion("Kim"));

        rectangleEnemy = new RectangleEnemy((int) Main.WIDTH/2 + 100,
                (int) Main.HEIGHT/2 + 500, 64, 112);
    }

    @Override
    protected void update(float dt) {
        player.update(dt);
        rectangleEnemy.update(dt);
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
        rectangleEnemy.shapeRender(shapeRenderer);
        for(int i = 0; i < 4; i ++) {
            for(int j = 0; j < 4; j++) {
                shapeRenderer.rect(i * 320, j * 192, 320, 192);
            }
        }
        shapeRenderer.end();
    }

}
