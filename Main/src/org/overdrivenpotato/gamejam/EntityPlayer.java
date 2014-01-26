package org.overdrivenpotato.gamejam;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;

/**
 * Created by marko on 24/01/14.
 */
public class EntityPlayer extends EntityDrawable {

    private boolean barelling = false;
    private float barellingTime = 1f;
    private boolean lastBarrelRight = false;
    private final float movementSpeed = 8f;

    public EntityPlayer(Texture tex, float x, float y) {
        super(tex, x, y);
    }

    public EntityPlayer(Imp imp, float x, float y) {
        super(imp, x, y);
    }

    public EntityPlayer(Animation anim, float x, float y) {
        super(anim, x, y);
    }

    public EntityPlayer(float x, float y) {
        super(x, y);
    }

    public void tick(KeyboardMgr keyb, World world)
    {
        if(!keyb.barrel() && !barelling)
        {
            Vector2 vec = new Vector2();
            if(keyb.isRight())
            {
                lastBarrelRight = true;
                vec.x += 1;
            }
            if(keyb.isLeft())
            {
                lastBarrelRight = false;
                vec.x -= 1;
            }
            if(keyb.isDown())
                vec.y -= 1;
            if(keyb.isUp())
                vec.y += 1;

            vec.nor();
            vec.scl(movementSpeed);
            this.move(vec.x, vec.y);
        }
        else if(!barelling)
        {
            barelling = true;
        }
        else if(barellingTime <= 0)
        {
            barellingTime = 1f;
            barelling = false;
        }
        else
        {
            move(lastBarrelRight ? movementSpeed * 1.5f : movementSpeed * -1.5f, 0 );
            barellingTime -= 0.05f;
        }
    }

    private final int padding = 3;
    @Override
    public Rectangle getBoundingBox() {
        return new Rectangle((int) getX() + padding, (int) (getY() + (getHeight() / 2f)) + padding, getWidth() - padding, getHeight() / 2 - padding);
    }
}
