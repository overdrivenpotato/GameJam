package org.overdrivenpotato.gamejam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by marko on 25/01/14.
 */
public class TileWorld {
    private SpriteBatch batch;
    private TiledMap map;
    private float offsetX, offSetY;
    private HashMap<Point, Imp> pointCellMap;
    private HashMap<Integer, Imp> idImpMap;

    public TileWorld()
    {
        this.batch = new SpriteBatch();
        this.map = new TmxMapLoader().load("Main/assets/maps/map2.tmx");
        offsetX = 0;
        offSetY = Gdx.graphics.getHeight();
        pointCellMap = new HashMap<Point, Imp>();
        idImpMap = new HashMap<Integer, Imp>();
        setTextures();
        loadMap();
    }

    private void setTextures() {
        for(TiledMapTileSet set : map.getTileSets())
        {
            int firstgid = (Integer)set.getProperties().get("firstgid");
            String fileName = "Main/assets/maps/" + (String) set.getProperties().get("imagesource");
            int width = (Integer) set.getProperties().get("imagewidth");
            int height = (Integer) set.getProperties().get("imageheight");
            idImpMap.put(Integer.valueOf(firstgid), new  Imp(new Texture(fileName), width / height, height / height, 0.25f));
        }
//        idImpMap.put(Integer.valueOf(1), new Imp(new Texture("Main/assets/maps/wallnew.png"), 2, 1, 0.25f));
//        idImpMap.put(Integer.valueOf(2), new Imp(new Texture("Main/assets/maps/movers.png"), 2, 1, 0.25f));
    }

    private void loadMap() {
        for(MapLayer mapLayer : map.getLayers())
        {
            TiledMapTileLayer tileLayer = (TiledMapTileLayer)mapLayer;

            for(int i = 0; i < tileLayer.getWidth(); i++)
            {
                for(int j = 0; j < tileLayer.getHeight(); j++)
                {
                    TiledMapTileLayer.Cell cell = tileLayer.getCell(i, j);
                    if(cell != null)
                    {
                        pointCellMap.put(new Point(i, j), idImpMap.get(cell.getTile().getId()));
                    }
                }
            }
        }
    }

    public void render(float offX, float offY)
    {
        refreshAnims();
        batch.begin();
        for(Point p : pointCellMap.keySet())
        {
            Imp cell = pointCellMap.get(p);
            if(cell != null)
            {
                if((p.getX() < 0 || p.getX() - cell.getTextureStatic().getRegionWidth() > Gdx.graphics.getWidth()) ||
                        (p.getY() < 0 || p.getY() - cell.getTextureStatic().getRegionHeight() > Gdx.graphics.getHeight()))
                    continue;
                TextureRegion tex = cell.getTextureStatic();
                batch.draw(tex, p.x * tex.getRegionWidth() + offsetX, p.y * tex.getRegionHeight() + offSetY);
            }
        }
        batch.end();
    }

    private void refreshAnims() {
        for(Imp i : idImpMap.values())
        {
            i.updateAnim();
        }
    }

    public void move(float x, float y) {
        this.offsetX += x;
        this.offSetY += y;
    }

    public boolean collision(EntityDrawable e) {
        for(Point p : pointCellMap.keySet())
        {
            Imp cell = pointCellMap.get(p);
            if(cell != null)
            {
                Rectangle player = e.getBoundingBox();
                Rectangle block = new Rectangle(
                        (int) (p.x * cell.getTextureStatic().getRegionWidth() + offsetX),
                        (int) (p.y * cell.getTextureStatic().getRegionHeight() + offSetY),
                        cell.getTextureStatic().getRegionWidth(),
                        cell.getTextureStatic().getRegionHeight()
                );
                if(player.intersects(block))
                {
                    return true;
                }
            }
        }
        return false;
    }
}
