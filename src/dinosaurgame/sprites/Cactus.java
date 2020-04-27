package dinosaurgame.sprites;

import javafx.scene.image.Image;

public class Cactus extends Sprite {

    private long timeStarted;

    private float speed = 0.1f;
    private int initialX;

    private int cactusWidth = 50;

    private boolean animate = true;

    public Cactus(int x, int y) {
        image = new Image("file:assets/cactus.png", cactusWidth, 0, true, true);

        this.x = x;
        this.y = y;
        this.initialX = x;

        timeStarted = System.currentTimeMillis();
    }


    public void stop() {
        animate = false;
    }

    @Override
    public void update(long now) {
        if (animate) {
            int milliSecondsSinceStart = (int) (System.currentTimeMillis() - timeStarted);

            x = (int) (initialX - (milliSecondsSinceStart * speed));
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean isOutOfFrame() {
        return x < cactusWidth * -1;
    }
}
