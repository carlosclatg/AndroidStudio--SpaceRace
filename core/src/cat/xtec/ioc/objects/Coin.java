package cat.xtec.ioc.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Methods;
import cat.xtec.ioc.utils.Settings;

// TODO Exercici 2 - classe moneda
public class Coin extends Scrollable {

    private Circle captureCoinCircle;
    private boolean pause;

    public Coin(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);

        captureCoinCircle = new Circle();

        this.setOrigin(width/2 + 1, height/2);



    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Actualitzem el cercle de col·lisions (punt central de l'asteroid i el radi.
        captureCoinCircle.set(position.x + width / 2.0f, position.y + width / 2.0f, width / 2.0f);

    }

    @Override
    public void reset(float newX) {
        super.reset(newX);
        // La posició serà un valor aleatòri entre 0 i l'alçada de l'aplicació menys l'alçada
        position.y =  new Random().nextInt(Settings.GAME_HEIGHT - (int) height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(AssetManager.coins, position.x, position.y, this.getOriginX(), this.getOriginY(), width, height, this.getScaleX(), this.getScaleY(), this.getRotation());
    }


    //Mètode per saber si l'hem capturat
    public boolean captureCoin(Spacecraft nau) {

        if (position.x <= nau.getX() + nau.getWidth()) {
            // Comprovem si han col·lisionat sempre i quan l'asteroid estigui a la mateixa alçada que la spacecraft
            return (Intersector.overlaps(captureCoinCircle, nau.getCollisionRect()));
        }
        return false;
    }


}
