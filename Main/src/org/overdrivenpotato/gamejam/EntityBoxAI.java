package org.overdrivenpotato.gamejam;

public abstract class EntityBoxAI extends EntityAI {

    public EntityBoxAI(int traceSize, Imp tex, float x, float y) {
        super(traceSize, tex, x, y);
    }

    @Override
    public void tick(KeyboardMgr keyb, World world) {
        switch(tick / (traceSize / 4))
        {
            case 0:
                move(1, 0);
                break;
            case 1:
                move(0, 1);
                break;
            case 2:
                move(-1, 0);
                break;
            case 3:
                move(0, -1);
                break;
        }

        tick++;
        if(tick > traceSize)
            tick = 0;
    }
}
