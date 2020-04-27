package dinosaurgame.sprites;

import javafx.scene.image.Image;

public class Cloud extends Sprite {

    double speed;
    long cloudStarted;
    int initialX;

    int cloudWidth = 100;

    boolean animate = true;

    public Cloud(int x, int y) {
        this.x = x;
        this.y = y;
        initialX = x;

        speed = Math.random() / 100 + 0.05F;
        cloudStarted = System.currentTimeMillis();

        image = new Image("file:assets/cloud.png", cloudWidth, 100, true, true);

    }

    public void stop() {
        animate = false;
    }

    public boolean isOutOfFrame() {
        return x < cloudWidth * -1;
    }


    @Override
    public void update(long now) {
        if (animate) {
            int milliSecondsTravelled = (int) (System.currentTimeMillis() - cloudStarted);

            x = (int) (initialX - milliSecondsTravelled * speed);
        }
    }
}
