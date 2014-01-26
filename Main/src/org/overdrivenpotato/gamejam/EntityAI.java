package org.overdrivenpotato.gamejam;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by marko on 24/01/14.
 */
public abstract class EntityAI extends EntityDrawable
{
    protected int tick = 0;
    protected int traceSize;

    public EntityAI(int traceSize, Imp tex, float x, float y)
    {
        super(tex, x , y);
        this.traceSize = traceSize;
    }

    public EntityAI(Texture tex, float x, float y) {
        super(tex, x, y);
    }

    public EntityAI(float x, float y) {
        super(x, y);
    }

    public void tick(KeyboardMgr keyb, World world) {
        tick++;
        if(tick > traceSize)
            tick = 0;
    }
}
