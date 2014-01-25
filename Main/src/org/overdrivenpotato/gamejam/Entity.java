package org.overdrivenpotato.gamejam;

/**
 * Created by marko on 24/01/14.
 */
public abstract class Entity {
    protected float x, y;

    public Entity(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public void move(float x, float y)
    {
        this.x += x;
        this.y += y;
    }

    public void setPos(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public abstract void tick(KeyboardMgr keyb);

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
