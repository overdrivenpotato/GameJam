package org.overdrivenpotato.gamejam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * Created by marko on 24/01/14.
 */
public class World {
    private ArrayList<Entity> entities;
    private int width, height;
    private Wallpaper bg;
    private TileWorld tileWorld;
    private float scrollSpeed;
    private ScreenGame screenGame;
    private SpriteBatch batch;

    public World(int width, int height, ScreenGame screenGame)
    {
        batch = new SpriteBatch();
        this.screenGame = screenGame;
        this.scrollSpeed = -4;
        this.width = width;
        this.height = height;
        entities = new ArrayList<Entity>();
        bg = new Wallpaper(new Texture("Main/assets/backgroundnew.png"));
        tileWorld = new TileWorld(this);
    }

    public boolean tick(KeyboardMgr keyb)
    {
        scrollSpeed *= 1.0001;
        bg.move(0, scrollSpeed);
        tileWorld.move(0, scrollSpeed);
        for(Entity e : entities)
        {
            e.tick(keyb, this);
            if(!(e instanceof EntityPlayer))
            {
                if(((EntityDrawable)e).collision(screenGame.getPlayer()))
                    return false;
                e.move(0, scrollSpeed);
            }
            else
            {
                if(tileWorld.collision((EntityPlayer) e))
                {
                    return false;
                }
            }

            if(e instanceof EntityPlayer)
            {
                if(e.getX() > width - ((EntityPlayer) e).getWidth())
                    e.setX(width - ((EntityPlayer) e).getWidth());
                else if(e.getX() < 0)
                    e.setX(0);

                if(e.getY() > height - ((EntityPlayer) e).getHeight())
                    e.setY(height - ((EntityPlayer) e).getHeight());
                else if(e.getY() < 0)
                    e.setY(0);
            }
        }
        return true;
    }

    public void spawnEntity(Entity e)
    {
        entities.add(e);
    }

    public void draw()
    {
        bg.draw();
        batch.begin();
        for(Entity e : entities)
        {
            if(e instanceof EntityDrawable)
                ((EntityDrawable) e).draw(batch);
        }
        batch.end();
        tileWorld.render(0, 0);
    }

    public boolean collision(EntityDrawable entityDrawable) {
        return tileWorld.collision(entityDrawable);
    }
}
