package org.overdrivenpotato.gamejam;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by marko on 26/01/14.
 */
public abstract class ScreenImageDisplay implements Screen {
    private Imp image;
    private SpriteBatch batch;
    private Screen parent;

    public ScreenImageDisplay(Screen parent)
    {
        batch = new SpriteBatch();
        this.parent = parent;
    }



    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(image.getTexture(), 0, 0);
        batch.end();
    }

    protected void setImage(Imp imp)
    {
        this.image = imp;
    }

    protected void showParent()
    {
        parent.show();
    }


    public Screen getParent() {
        return parent;
    }
}
