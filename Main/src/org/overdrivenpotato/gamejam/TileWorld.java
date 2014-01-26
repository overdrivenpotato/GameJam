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
import sun.org.mozilla.javascript.internal.ast.Block;

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
    private HashMap<Integer, BlockData> idDataMap;
    private World world;

    public TileWorld(World world)
    {
        this.world = world;
        this.batch = new SpriteBatch();
        this.map = new TmxMapLoader().load("Main/assets/maps/map2.tmx");
        offsetX = 0;
        offSetY = Gdx.graphics.getHeight();
        pointCellMap = new HashMap<Point, Imp>();
        idDataMap = new HashMap<Integer, BlockData>();
        setTextures();
        loadMap();
    }

    private void setTextures() {
        for(TiledMapTileSet set : map.getTileSets())
        {
            int firstgid = (Integer)set.getProperties().get("firstgid");
            String fileName = "Main/assets/maps/" + set.getProperties().get("imagesource");
            int width = (Integer) set.getProperties().get("imagewidth");
            int height = (Integer) set.getProperties().get("imageheight");
            int tileWidth = (Integer) set.getProperties().get("tilewidth");
            int tileHeight = (Integer) set.getProperties().get("tileheight");
            idDataMap.put(Integer.valueOf(firstgid),
                    new BlockData(new Imp(new Texture(fileName), width / tileWidth, height / tileHeight, 0.5f),
                            set.getProperties()
                    ));
        }
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
                        if(!tileLayer.getName().equalsIgnoreCase("entities"))
                        {
                            pointCellMap.put(new Point(i, j), idDataMap.get(cell.getTile().getId()).getImp());
                        }
                        else
                        {
                            BlockData data = idDataMap.get(cell.getTile().getId());
                            String s = (String) data.getProperties().get("imagesource");
                            if (s.contains("movers")) {
                                world.spawnEntity(new EntityLineAI(data.getImp(), i * (Integer) map.getProperties().get("tilewidth"),
                                        j * (Integer) map.getProperties().get("tileheight") + offSetY));
                            }
                        }
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
            Imp imp = pointCellMap.get(p);
            if(imp != null)
            {
                if((p.getX() < 0 || p.getX() - imp.getTextureStatic().getRegionWidth() > Gdx.graphics.getWidth()) ||
                        (p.getY() < 0 || p.getY() - imp.getTextureStatic().getRegionHeight() > Gdx.graphics.getHeight()))
                    continue;
                TextureRegion tex = imp.getTextureStatic();
                batch.draw(tex, p.x * (Integer) map.getProperties().get("tilewidth") + offsetX, p.y * (Integer) map.getProperties().get("tileheight") + offSetY);
            }
        }
        batch.end();
    }

    private void refreshAnims() {
        for(BlockData i : idDataMap.values())
        {
            i.getImp().updateAnim();
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
                Rectangle entity = e.getBoundingBox();
                Rectangle block = new Rectangle(
                        (int) (p.x * (Integer) map.getProperties().get("tilewidth") + offsetX),
                        (int) (p.y * (Integer) map.getProperties().get("tileheight") + offSetY),
                        cell.getTextureStatic().getRegionWidth(),
                        cell.getTextureStatic().getRegionHeight()
                );
                if(entity.intersects(block))
                {
                    return true;
                }
            }
        }
        return false;
    }
}
