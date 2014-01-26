package org.overdrivenpotato.gamejam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by marko on 24/01/14.
 */
public class Wallpaper {
    private TextureRegion background;
    private float y = 0;
    private SpriteBatch batch;

    public Wallpaper(TextureRegion textureRegion)
    {
        this.background = textureRegion;
        batch = new SpriteBatch();
    }

    public Wallpaper(Texture texture)
    {
        this(new TextureRegion(texture));
    }

    public void move(float offX, float offY)
    {
        y += offY / 2;
    }

    public float getY() {
        return y;
    }

    public void draw() {
        if(getY() <= -(background.getRegionHeight()))
        {

//            System.out.println("y" + getY() + ", " + background.getRegionHeight());
            y = 0;
        }
            batch.begin();
        batch.draw(background, 0, getY());
//        if(getY() + background.getRegionHeight() != Gdx.graphics.getHeight())
        if(getY() <= -(background.getRegionHeight() - Gdx.graphics.getHeight()))
        {
            batch.draw(background, 0, getY() + background.getRegionHeight());// +  Gdx.graphics.getHeight());
        }

        batch.end();
    }
}
