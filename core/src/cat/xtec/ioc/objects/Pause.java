package cat.xtec.ioc.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Random;

import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Settings;

public class Pause extends Scrollable {

    private Rectangle rectangle;

    public Pause(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);

        rectangle = new Rectangle();
        this.setOrigin(width/2 + 1, height/2);



    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }

    @Override
    public void reset(float newX) {
        super.reset(newX);
        // La posició serà un valor aleatòri entre 0 i l'alçada de l'aplicació menys l'alçada
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(AssetManager.pause, position.x, position.y, this.getOriginX(), this.getOriginY(), width, height, this.getScaleX(), this.getScaleY(), this.getRotation());
    }
}
