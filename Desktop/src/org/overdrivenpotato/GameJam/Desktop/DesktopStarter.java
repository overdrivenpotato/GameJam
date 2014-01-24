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
        cfg.title = "GameJam Game!";
        cfg.useGL20 = true;
        cfg.width = 800;
        cfg.height = 400;
        new LwjglApplication(new GameJamGame(), cfg);
    }
}
