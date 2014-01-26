package org.overdrivenpotato.gamejam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Date;

/**
 * Created by marko on 24/01/14.
 */
public class Imp {
    private Animation animation;
    private float stateTime;
    private Date date;
    private float time;

    private Imp()
    {
        stateTime = 0f;
        date = new Date();
        time = date.getTime() / 1000f;
    }

    public Imp(Animation animation)
    {
        this();
        this.animation = animation;
    }

    public Imp(Texture tex, int x, int y, float... animSpeed)
    {
        this();
        TextureRegion[][] tmp = TextureRegion.split(tex, tex.getWidth() / x, tex.getHeight() / y);
        TextureRegion[] frames = new TextureRegion[x * y];
        int index = 0;
        for(TextureRegion[] i : tmp)
        {
            for(TextureRegion j : i)
            {
                frames[index++] = j;
            }
        }

        animation = new Animation(animSpeed != null && animSpeed.length > 0? animSpeed[0] : 1f, frames);
    }

    public Imp(TextureRegion textureRegion) {
        this(new Animation(1f, textureRegion));
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
}
