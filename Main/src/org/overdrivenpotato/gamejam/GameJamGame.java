package org.overdrivenpotato.gamejam;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

/**
 * Created by marko on 24/01/14.
 */
public class GameJamGame extends Game {
    private LwjglApplication lwjglApp;
    private Screen gameScreen;

    public GameJamGame(LwjglApplication lwjglApplication) {
        this.lwjglApp = lwjglApplication;
    }

    @Override
    public void create() {
        gameScreen = new ScreenGame(this);
        setScreen(gameScreen);
    }

    @Override
    public void render() {
        super.render();
    }

    public void restart()
    {
        gameScreen.dispose();
        gameScreen = new ScreenGame(this);
        setScreen(gameScreen);
    }
}