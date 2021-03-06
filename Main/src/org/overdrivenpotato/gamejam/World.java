package org.overdrivenpotato.gamejam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

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
        bg = new Wallpaper(new Texture("Main/assets/bg.png"));
        tileWorld = new TileWorld(this);
    }

    public boolean tick(KeyboardMgr keyb)
    {
        scrollSpeed *= 1.0001;
        bg.move(0, scrollSpeed);
        tileWorld.move(0, scrollSpeed);
        tileWorld.tick();
        for(Entity e : entities)
        {
            if(!(e instanceof EntityPlayer))
            {
//                if(e.isOffScreen())
//                    continue;
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

            e.tick(keyb, this);

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
        FXManager.update(screenGame.getPlayer().getX(), screenGame.getPlayer().getY());
        bg.draw();
        batch.begin();
        for(Entity e : entities)
        {
            if(e instanceof EntityPlayer)
                ((EntityPlayer) e).draw(batch, 0, 0);
            else if(e instanceof EntityDrawable)
                ((EntityDrawable) e).draw(batch, FXManager.getModX(), FXManager.getModY());
        }
        batch.end();
        tileWorld.render(FXManager.getModX(), FXManager.getModY());
    }

    public boolean collision(EntityDrawable entityDrawable) {
        return tileWorld.collision(entityDrawable);
    }

    public void move(int x, int y) {
        tileWorld.move(x, y);
    }

    public void killEntities() {
        ArrayList<Entity> e2 = (ArrayList<Entity>) entities.clone();
        for(Entity e : e2)
        {
            if(e instanceof EntityLineAI)
            {
                if(e.getY() < 0)
                {
//                    System.out.println("Killing at " + e.getY());
                    entities.remove(e);
                }
            }
        }
    }
}
