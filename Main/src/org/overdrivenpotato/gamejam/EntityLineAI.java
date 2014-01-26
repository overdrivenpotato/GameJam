package org.overdrivenpotato.gamejam;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by marko on 24/01/14.
 */
public class EntityLineAI extends EntityAI {
    private boolean isRight = true;
    private final float movementSpeed = 1.2f;

    public EntityLineAI(Imp tex, float x, float y) {
        super(0, tex, x, y);
    }

    public void tick(KeyboardMgr keyb, World world) {
        super.tick(keyb, world);
        x += isRight ? movementSpeed : -movementSpeed;
        if(world != null)
            if(world.collision(this))
            {
                isRight = !isRight;
            }
    }
}
