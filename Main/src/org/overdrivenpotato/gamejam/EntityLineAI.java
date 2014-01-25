package org.overdrivenpotato.gamejam;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by marko on 24/01/14.
 */
public class EntityLineAI extends EntityAI {

    public EntityLineAI(int traceSize, Imp tex, float x, float y) {
        super(traceSize, tex, x, y);
    }

    @Override
    public void tick(KeyboardMgr keyb) {
        super.tick(keyb);

        if(tick < (traceSize / 2))
            move(-1, 0);
        else
            move(1, 0);
    }
}
