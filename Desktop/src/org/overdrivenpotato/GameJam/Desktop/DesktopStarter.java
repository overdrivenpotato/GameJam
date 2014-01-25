package org.overdrivenpotato.GameJam.Desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.overdrivenpotato.gamejam.GameJamGame;

/**
 * Created by marko on 24/01/14.
 */
public class DesktopStarter {
    public static void main(String[] args)
    {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Eye!";
        cfg.useGL20 = true;
        cfg.width = 544;
        cfg.height = 600;
        cfg.resizable = false;
        LwjglApplication lwjglApplication = null;
        lwjglApplication = new LwjglApplication(new GameJamGame(lwjglApplication), cfg);
    }
}