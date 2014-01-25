package org.overdrivenpotato.gamejam;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

/**
 * Created by marko on 25/01/14.
 */
public class MapIterator implements Iterable<TiledMapTileLayer.Cell> {

    private Array<TiledMapTileLayer.Cell> cellIterator;

    public void add(TiledMapTileLayer.Cell cell)
    {
        cellIterator.add(cell);
    }

    @Override
    public Iterator<TiledMapTileLayer.Cell> iterator() {
        return cellIterator.iterator();
    }
}
