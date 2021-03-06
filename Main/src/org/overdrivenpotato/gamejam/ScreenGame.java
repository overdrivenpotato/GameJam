package org.overdrivenpotato.gamejam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.GL10;
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
    private Sound death;

    public ScreenGame(GameJamGame gameJamGame)
    {
        death = Gdx.audio.newSound(Gdx.files.internal("Main/assets/death.ogg"));
        this.theGame = gameJamGame;
        FXManager.setPlayer(entityPlayer = new EntityPlayer(new Imp(new Texture("Main/assets/eye2.png"), 5, 5, 0.125f, 24), Gdx.graphics.getWidth() / 2, 100));
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
            death.play();
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
//        music.stop();
    }

    public EntityPlayer getPlayer() {
        return entityPlayer;
    }
}
