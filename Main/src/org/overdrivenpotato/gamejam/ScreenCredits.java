package org.overdrivenpotato.gamejam;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by marko on 26/01/14.
 */
public class ScreenCredits extends ScreenImageDisplay {
    private GameJamGame game;
    public ScreenCredits(Screen parentScreen, GameJamGame game)
    {
        super(parentScreen);
        this.game = game;
        setImage(new Imp(new Texture("Main/assets/credits.png"), 1, 1));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if(KeyboardMgr.startKey())
        {
            game.setScreen(getParent());
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
