package cat.xtec.ioc.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

public class Explosion {

    // TODO Exercici 4 - Explosion


    public float getDelta() {
        return delta;
    }

    public Animation getAnim() {
        return anim;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    public void setAnim(Animation anim) {
        this.anim = anim;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

        private Animation anim;
        private float x;
        private float y;
        private float width;
        private float height;
        private float delta;

    public Explosion (Animation anim, float x, float y, float width, float height, float delta){
        this.anim = anim;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.delta = delta;
    }


    public boolean isFinished(){
        return anim.isAnimationFinished(delta);
    }
}
