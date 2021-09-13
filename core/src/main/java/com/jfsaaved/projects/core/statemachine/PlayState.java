package com.jfsaaved.projects.core.statemachine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jfsaaved.projects.core.Main;
import com.jfsaaved.projects.core.objects.Player;
import com.jfsaaved.projects.core.objects.RectangleEnemy;

import static com.jfsaaved.projects.core.Main.HEIGHT;

public class PlayState extends State{

    private Player player;
    private RectangleEnemy rectangleEnemy;

    public PlayState(){
        stateInit();
        player = new Player(Main.images.findRegion("standing/Kim"),
                (int) Main.WIDTH/2 - 300,
                (int) Main.HEIGHT/2 + 50, 64, 112);
        player.setAttackSheet(Main.imagesAttack.findRegion("test-attack/Kim"));
        player.setJumpSheet(Main.imagesJump.findRegion("jump/Kim"));
        player.setJumpForwardSheet(Main.imagesJumpForward.findRegion("Kim"));
        player.setJumpBackwardSheet(Main.imagesJumpBackward.findRegion("Kim"));
        player.setPunch01Sheet(Main.imagesPunch01.findRegion("Kim"));
        player.setPunch02Sheet(Main.imagesPunch02.findRegion("Kim"));
        player.setKick01Sheet(Main.imagesKick01.findRegion("Kim"));
        player.setKick02Sheet(Main.imagesKick02.findRegion("Kim"));

        rectangleEnemy = new RectangleEnemy((int) Main.WIDTH/2 - 200,
                (int) Main.HEIGHT/2 + 500, 64, 112);
    }

    @Override
    protected void update(float dt) {
        //handleFlip();
        player.update(dt);
        rectangleEnemy.update(dt);
        handleCollisions();
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
        player.hitBoxRender(shapeRenderer);
        //player.shapeRender(shapeRenderer);
        rectangleEnemy.shapeRender(shapeRenderer);
        for(int i = 0; i < 4; i ++) {
            for(int j = 0; j < 4; j++) {
                shapeRenderer.rect(i * 320, j * 192, 320, 192);
            }
        }
        shapeRenderer.end();
    }

    private void handleFlip() {
        if(player.position.x > rectangleEnemy.position.x && player.position.y == HEIGHT/2) {
            player.flip = -1;
        } else if(player.position.y == HEIGHT/2) {
            player.flip = 1;
        }
    }

    private void handleCollisions() {
        rectangleEnemy.velocity.x = 0;
        if(rectangleEnemy.rectangle.overlaps(player.rectangle)) {
            rectangleEnemy.position.x = player.rectangle.x + player.rectangle.width;
        }
        if(player.rightHandHitBox.overlaps(rectangleEnemy.rectangle)) {
            rectangleEnemy.velocity.x = 1;
            rectangleEnemy.velocity.y = 2;
            player.position.x -= 2;
        }
        if(player.rightLegHitBox.overlaps(rectangleEnemy.rectangle)) {
            rectangleEnemy.velocity.x = 1;
            rectangleEnemy.velocity.y = 2;
            player.position.x -= 2;
            if(player.linkEvent == Player.LinkEvent.KICK_01_TO_KICK_02 || player.linkEvent == Player.LinkEvent.KICK_02_TO_KICK_01) {
                player.position.x += 7;
            }
        }
    }

}
