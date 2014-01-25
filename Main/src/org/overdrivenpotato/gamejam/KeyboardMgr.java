package org.overdrivenpotato.gamejam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Created by marko on 24/01/14.
 */
public class KeyboardMgr {
    private int down, up, left, right, barrel;
    private boolean barreled = false;

    public KeyboardMgr()
    {
        down = Input.Keys.DOWN;
        up = Input.Keys.UP;
        left = Input.Keys.LEFT;
        right = Input.Keys.RIGHT;
        barrel = Input.Keys.SPACE;
    }

    public boolean isDown()
    {
        return keyPressed(down);
    }

    public boolean barrel() {
        if(!keyPressed(barrel) && barreled)
            return barreled = false;
        if(keyPressed(barrel) && barreled)
            return false;
        if(keyPressed(barrel) && !barreled)
            return barreled = true;
        return keyPressed(barrel);
    }

    public boolean isUp()
    {
        return keyPressed(up);
    }

    public boolean isLeft()
    {
        return keyPressed(left);
    }

    public boolean isRight()
    {
        return keyPressed(right);
    }

    private boolean keyPressed(int key)
    {
        return Gdx.input.isKeyPressed(key);
    }
}
