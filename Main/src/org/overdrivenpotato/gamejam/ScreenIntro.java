package org.overdrivenpotato.gamejam;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by marko on 26/01/14.
 */
public class ScreenIntro extends ScreenImageDisplay{

    private GameJamGame game;

    public ScreenIntro(GameJamGame game)
    {
        super(null);
        setImage(new Imp(new Texture("Main/assets/intro.png"), 1, 1));
        this.game = game;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if(KeyboardMgr.credits())
        {
            game.setScreen(new ScreenCredits(this, game));
        }
        else if(KeyboardMgr.startKey())
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
