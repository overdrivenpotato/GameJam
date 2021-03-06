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
import java.util.Random;

/**
 * Created by marko on 25/01/14.
 */
public class TileWorld {
    private SpriteBatch batch;
    private TiledMap map;
    private float offSetX, offSetY;
    private HashMap<Point, Imp> pointCllMap;
    private HashMap<Integer, BlockData> idDataMap;
    private BlockData[][] blocks;
    private World world;
    private int level = 0;

    public TileWorld(World world)
    {
        this.world = world;
        this.batch = new SpriteBatch();
//        this.map = new TmxMapLoader().load("Main/assets/maps/map2.tmx");
        offSetX = 0;
        offSetY = Gdx.graphics.getHeight();
        idDataMap = new HashMap<Integer, BlockData>();
        spliceNext();
//        blocks = new BlockData[(Integer) map.getProperties().get("width")][(Integer) map.getProperties().get("height")];
//        setTextures();
//        loadMap(0, 0);
    }

    private void setTextures() {
        if(idDataMap != null && idDataMap.size() > 0)
        {

        }
//        try
//        {
//            idDataMap.clear();
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
        for(TiledMapTileSet set : map.getTileSets())
        {

            int firstgid = (Integer)set.getProperties().get("firstgid");
            String fileName = "Main/assets/maps/" + set.getProperties().get("imagesource");
            int width = (Integer) set.getProperties().get("imagewidth");
            int height = (Integer) set.getProperties().get("imageheight");
            int tileWidth = (Integer) set.getProperties().get("tilewidth");
            int tileHeight = (Integer) set.getProperties().get("tileheight");
            if(idDataMap != null && idDataMap.size() > 0)
            {
                for(BlockData b : idDataMap.values())
                {
                    if(b.getImp().getName().equalsIgnoreCase(fileName))
                    {

                    }
                }
            }
            if(fileName.contains("movers"))
            {
                idDataMap.put(Integer.valueOf(firstgid), new BlockData(new Imp(new Texture("Main/assets/maps/moversg.png"), 6, 5, 0.125f, 16, 10).setName(fileName),
                        set.getProperties()
                ));
            }
            else
            {
                idDataMap.put(Integer.valueOf(firstgid),
                        new BlockData(new Imp(new Texture(fileName), width / tileWidth, height / tileHeight, 0.5f).setName(fileName),
                                set.getProperties()
                        ));
            }
        }
    }

    private void loadMap(int offX, int offY) {
//        System.out.println("Loading map " + map.getProperties().get("source"));
        for(MapLayer mapLayer : map.getLayers())
        {
            TiledMapTileLayer tileLayer = (TiledMapTileLayer)mapLayer;
            System.out.println("Loading layer " + tileLayer.getName());
            for(int i = 0; i < tileLayer.getWidth(); i++)
            {
                for(int j = 0; j < tileLayer.getHeight(); j++)
                {
                    TiledMapTileLayer.Cell cell = tileLayer.getCell(i, j);
                    if(cell != null)
                    {
                        if(!tileLayer.getName().equalsIgnoreCase("entities"))
                        {
                            blocks[i][j + offY] = idDataMap.get(cell.getTile().getId());
                        }
                        else
                        {
                            BlockData data = idDataMap.get(cell.getTile().getId());
                            String s = (String) data.getProperties().get("imagesource");
                            if (s.contains("movers")) {
                                world.spawnEntity(new EntityLineAI(data.getImp(), i * (Integer) map.getProperties().get("tilewidth"),
                                        j * (Integer) map.getProperties().get("tileheight") + offSetY + Gdx.graphics.getHeight() + getMapHeight()));
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
        for(int i = 0; i < blocks.length; i++)
        {
            for(int j = 0; j < blocks[i].length; j++)
            {
                if(blocks[i][j] == null)
                    continue;
                Imp imp = blocks[i][j].getImp();
                if(imp != null)
                {
                    if((i < 0 || i - imp.getTextureStatic().getRegionWidth() > Gdx.graphics.getWidth()) ||
                            (j < 0 || j - imp.getTextureStatic().getRegionHeight() > Gdx.graphics.getHeight()))
                        continue;
                    TextureRegion tex = imp.getTextureStatic();
                    batch.draw(tex, i * (Integer) map.getProperties().get("tilewidth") + offSetX + offX, j * (Integer) map.getProperties().get("tileheight") + offSetY + offY);
                }
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
        this.offSetX += x;
        this.offSetY += y;
    }

    public void setPos(float x, float y)
    {
        this.offSetX = x;
        this.offSetY = y;
    }

    public boolean collision(EntityDrawable e) {
        if(e instanceof EntityLineAI)
        {
            for(int i = 0; i < blocks.length; i++)
            {
                int j = (int) ((e.getY() - offSetY) / getTileHeight());
                if(checkBlockCollision(i, j, e))
                {
                    return true;
                }
            }
        }
        else
        {
            for(int i = 0; i < blocks.length; i++)
            {
                for(int j = 0; j < blocks[i].length; j++)
                {
                    if(checkBlockCollision(i, j, e))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int getTileWidth()
    {
        return (Integer) map.getProperties().get("tilewidth");
    }

    public int getTileHeight()
    {
        return (Integer) map.getProperties().get("tileheight");
    }

    private boolean checkBlockCollision(int i, int j, EntityDrawable e)
    {
        if(j * getTileHeight() + offSetY > Gdx.graphics.getHeight() || j * getTileHeight() + offSetY < 0)
            return false;
        if(blocks[i][j] == null)
            return false;
        Imp imp = blocks[i][j].getImp();
        if(imp != null)
        {
            Rectangle entity = e.getBoundingBox();
            Rectangle block = new Rectangle(
                    (int) (i * getTileWidth() + offSetX),
                    (int) (j * getTileHeight() + offSetY),
                    imp.getTextureStatic().getRegionWidth(),
                    imp.getTextureStatic().getRegionHeight()
            );
            if(entity.intersects(block))
            {
                return true;
            }
        }
        return false;
    }

    public void tick() {
        if(blocks[0].length * getTileHeight() + offSetY <= Gdx.graphics.getHeight())
        {
            level++;
            if(level > 4)
                level = 4;
            spliceNext();
        }
    }

    private void spliceNext() {
        System.out.println("Splicing at " + level);
        int loader;
        String path = null;
        if(new Random().nextInt(3) == 1 && level > 0)
        {
            path = "Main/assets/maps/random" + (new Random().nextInt(6) + 1) + ".tmx";
            spliceMap(path);
            level--;
            return;
        }
        switch (level)
        {
            case 0:
                loader = new Random().nextInt(6) + 1;
                path = "Main/assets/maps/easy" + loader + ".tmx";
                map = new TmxMapLoader().load(path);
                blocks = new BlockData[(Integer) map.getProperties().get("width")][(Integer) map.getProperties().get("height")];
                setTextures();
                loadMap(0, 0);

                break;
            case 1:
            case 2:
                loader = new Random().nextInt(7) + 1;
                spliceMap(path = "Main/assets/maps/medium" + loader + ".tmx");
//                try{
//                    path = "Main/assets/maps/medium" + loader + ".tmx";
//                    TiledMap tempMap = new TmxMapLoader().load(path);
//                    int width = (Integer) tempMap.getProperties().get("width");
//                    int height = (Integer) tempMap.getProperties().get("height");
//                    int cutoffLength = (int) Math.ceil(Gdx.graphics.getHeight() / getTileHeight());
//                    map = tempMap;
//
//                    BlockData[][] blocksT = new BlockData[width][height + cutoffLength];
//                    for(int i = 0; i < blocks.length; i++)
//                    {
//                        for(int j = blocks[0].length - 1; j >= blocks[0].length - cutoffLength; j--)
//                        {
//                            blocksT[i][j - (blocks[0].length - 1 - cutoffLength)] = blocks[i][j];
//                        }
//                    }
//                    blocks = blocksT;
//                    setTextures();
//                    loadMap(0, cutoffLength);
////                    offSetY = -cutoffLength + getTileHeight() * 2;
//                    world.move(0, 0);
//                }
//                catch(Exception e)
//                {
//                    System.err.println(loader);
//                    e.printStackTrace();
//                    System.exit(0);
//                }
                break;
            case 3:
            case 4:
                loader = new Random().nextInt(10) + 1;
                path = "Main/assets/maps/hard" + loader + ".tmx";
                spliceMap(path);
//                TiledMap tempMap = new TmxMapLoader().load(path);
//                int width = (Integer) tempMap.getProperties().get("width");
//                int height = (Integer) tempMap.getProperties().get("height");
//                int cutoffLength = (int) Math.ceil(Gdx.graphics.getHeight() / getTileHeight());
//                map = tempMap;
//
//                BlockData[][] blocksT = new BlockData[width][height + cutoffLength];
//                for(int i = 0; i < blocks.length; i++)
//                {
//                    for(int j = blocks[0].length - 1; j >= blocks[0].length - cutoffLength; j--)
//                    {
//                        blocksT[i][j - (blocks[0].length - 1 - cutoffLength)] = blocks[i][j];
//                    }
//                }
//                blocks = blocksT;
//                setTextures();
//                loadMap(0, cutoffLength);
////                offSetY = -cutoffLength + getTileHeight() * 2;
//                world.move(0, -cutoffLength + getTileHeight() * 2);
                break;
        }
        System.out.println("Loaded " + path);
    }

    public void spliceMap(String path)
    {
        world.killEntities();
        map = new TmxMapLoader().load(path);
        int width = (Integer) map.getProperties().get("width");
        int height = (Integer) map.getProperties().get("height");
        int cutoffLength = (int) Math.ceil(Gdx.graphics.getHeight() / getTileHeight());

        BlockData[][] blocksT = new BlockData[width][height + cutoffLength];
        for(int i = 0; i < blocks.length; i++)
        {
            for(int j = blocks[0].length - cutoffLength; j < blocks[0].length; j++)
            {
                blocksT[i][j - (blocks[0].length - cutoffLength)] = blocks[i][j];
            }
        }
        blocks = blocksT;
        System.out.println((height + cutoffLength));

        setTextures();
        loadMap(0, cutoffLength);
        world.move(0, (int) -offSetY);
    }

    public int getMapHeight() {
        return getTileHeight() * (Integer) map.getProperties().get("height");
    }
}
