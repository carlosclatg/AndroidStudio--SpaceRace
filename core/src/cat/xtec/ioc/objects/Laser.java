package cat.xtec.ioc.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import java.util.ArrayList;

import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Settings;

public class Laser extends Scrollable {

    private Rectangle rectangle;
    private boolean isRigthOfScreen;

    public Laser(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);
        rectangle = new Rectangle();
        setBounds(position.x, position.y, width, height);
        setTouchable(Touchable.enabled);
        rectangle.set(position.x, position.y + 3, width, 10);
        setRigthOfScreen(false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Actualitzem el cercle de col·lisions (punt central de l'asteroid i el radi.
        rectangle.set(position.x, position.y + 3, width, 10);

        // Si està fora de la pantalla canviem la variable a true
        if (position.x + width > Settings.GAME_WIDTH) {
            isRigthOfScreen = true;
        }

    }

    public boolean collides(Asteroid a) {

        if (position.x <= a.getX() + a.getWidth()) {
            // Comprovem si han col·lisionat sempre i quan l'asteroid estigui a la mateixa alçada que la spacecraft
            return (Intersector.overlaps(a.getCollisionCircle(), this.rectangle));
        }
        return false;
    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(AssetManager.laserBullet, position.x, position.y, this.getOriginX(), this.getOriginY(), width, height, this.getScaleX(), this.getScaleY(), this.getRotation());
    }

    public boolean isRigthOfScreen() {
        return isRigthOfScreen;
    }

    public void setRigthOfScreen(boolean rigthOfScreen) {
        isRigthOfScreen = rigthOfScreen;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
