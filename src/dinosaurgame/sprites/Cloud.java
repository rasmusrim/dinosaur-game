package dinosaurgame.sprites;

public class Cloud extends Sprite {

    double speed;
    long cloudStarted;
    int initialX;

    int cloudWidth =

    public Cloud(int x, int y) {
        this.x = x;
        this.y = y;
        initialX = x;

        speed = Math.random();
        cloudStarted = System.currentTimeMillis();

    }

    public boolean isOutOfFrame() {
        return x < cactusWidth * -1;
    }


    @Override
    public void update(long now) {

        long milliSeconds = now / 1_000_000;

        x = (int)(initialX - milliSeconds * speed);

    }
}
