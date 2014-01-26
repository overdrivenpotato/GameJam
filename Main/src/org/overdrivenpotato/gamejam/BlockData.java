package org.overdrivenpotato.gamejam;

import com.badlogic.gdx.maps.MapProperties;

/**
 * Created by marko on 25/01/14.
 */
public class BlockData {
    private Imp imp;
    private MapProperties props;

    public BlockData(Imp imp, MapProperties props)
    {
        this.imp = imp;
        this.props = props;
    }

    public MapProperties getProperties()
    {
        return props;
    }

    public Imp getImp()
    {
        return imp;
    }
}
