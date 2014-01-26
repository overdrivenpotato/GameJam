package org.overdrivenpotato.gamejam;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by marko on 26/01/14.
 */
public class ScreenIntro implements Screen {
    private Imp image;
    private SpriteBatch batch;
    private GameJamGame game;

    public ScreenIntro(GameJamGame game)
    {
        image = new Imp(new Texture("Main/assets/intro.png"), 1, 1);
        batch = new SpriteBatch();
        this.game = game;
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(image.getTexture(), 0, 0);
        batch.end();
        if(KeyboardMgr.anyKey())
        {
            game.showGame();
        }
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
}
