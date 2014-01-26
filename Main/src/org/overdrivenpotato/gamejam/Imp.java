package org.overdrivenpotato.gamejam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sun.org.apache.regexp.internal.recompile;

import java.util.Date;

/**
 * Created by marko on 24/01/14.
 */
public class Imp {
    private Animation animation;
    private float stateTime;
    private String name;

    private Imp()
    {
        stateTime = 0f;
    }

    public Imp(Animation animation)
    {
        this();
        this.animation = animation;
    }

    public Imp(Texture tex, int x, int y, float... animSpeed)
    {
        this(tex, x, y, animSpeed != null && animSpeed.length > 0 ? animSpeed[0] : 1f, x * y);
    }

    public Imp(Texture tex, int x, int y, float animSpeed, int framec)
    {
        this(tex, x, y, animSpeed, framec, 0, 0);
    }

    public Imp(Texture tex, int x, int y, float animSpeed, int framec, int padX, int padY)
    {
        this();
        TextureRegion[][] tmp = TextureRegion.split(tex, tex.getWidth() / x, tex.getHeight() / y);
        TextureRegion[] frames = new TextureRegion[framec];
        int index = 0;
        boolean done = false;
        for(TextureRegion[] i : tmp)
        {
            if(done)
                break;
            for(TextureRegion j : i)
            {
                if(index >= framec)
                {
                    done = true;
                    break;
                }
                j.setRegion(j.getRegionX() + padX, j.getRegionY() + padY, j.getRegionWidth() - (2 * padX), j.getRegionHeight() - (2 * padY));
                frames[index++] = j;
            }
        }

        animation = new Animation(animSpeed, frames);
    }

    public Imp(TextureRegion textureRegion) {
        this(new Animation(1f, textureRegion));
    }

    public Imp setName(String name)
    {
        this.name = name;
        return this;
    }

    public TextureRegion getTexture()
    {
        updateAnim();
        return getTextureStatic();
    }

    public TextureRegion getTextureStatic() {
        return animation.getKeyFrame(stateTime, true);
    }

    public void updateAnim() {
        stateTime += Gdx.graphics.getDeltaTime();
    }

    public boolean equals(Imp i)
    {
        return i.getName().equalsIgnoreCase(name);
    }

    public String getName() {
        return name;
    }
}
