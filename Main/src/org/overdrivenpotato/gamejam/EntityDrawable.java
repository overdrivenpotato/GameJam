package org.overdrivenpotato.gamejam;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.*;

/**
 * Created by marko on 24/01/14.
 */
public abstract class EntityDrawable extends Entity {
    private Imp texture;
    private SpriteBatch batch;

    public EntityDrawable(Texture tex, float x, float y)
    {
        this(x, y);
        this.texture = new Imp(new TextureRegion(tex));
    }

    public EntityDrawable(Imp imp, float x, float y)
    {
        this(x, y);
        this.texture = imp;
    }

    public EntityDrawable(Animation anim, float x, float y)
    {
        this(x, y);
        this.texture = new Imp(anim);
    }

    public EntityDrawable(float x, float y) {
        super(x, y);
        batch = new SpriteBatch();
    }

    public void draw()
    {
        batch.begin();
        batch.draw(texture.getTexture(), x, y);
        batch.end();
    }

    public int getHeight()
    {
        return texture.getTextureStatic().getRegionHeight();
    }

    public int getWidth()
    {
        return texture.getTextureStatic().getRegionWidth();
    }

    public Rectangle getBoundingBox()
    {
        return new Rectangle((int) getX(), (int) getY(), getWidth(), getHeight());
    }
}
