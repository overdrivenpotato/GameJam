package org.overdrivenpotato.gamejam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by marko on 25/01/14.
 */
public class ScreenGame implements Screen {
    private GL20 graphics;
    private EntityPlayer entityPlayer;
    private KeyboardMgr keyb;
    private World world;
    private GameJamGame theGame;

    public ScreenGame(GameJamGame gameJamGame)
    {
        this.theGame = gameJamGame;
        entityPlayer = new EntityPlayer(new Imp(new Texture("Main/assets/playeranimboxes.png"), 2, 1, 0.12f), 100, 100);
        world = new World(550, 600, this);
        world.spawnEntity(entityPlayer);

        graphics = Gdx.graphics.getGL20();
        graphics.glClearColor(0.1f, 0.1f, 0.1f, 1f);
        keyb = new KeyboardMgr();
    }

    @Override
    public void render(float delta) {
        graphics.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        if(!world.tick(keyb))
        {
            theGame.restart();
            return;
        }
        world.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    public EntityPlayer getPlayer() {
        return entityPlayer;
    }
}
