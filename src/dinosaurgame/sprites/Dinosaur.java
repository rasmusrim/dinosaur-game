package dinosaurgame.sprites;

import javafx.scene.image.Image;

public class Dinosaur extends Sprite {
    private int initialY;

    private int jumpDurationInMilliSeconds = 1300;
    private int jumpHeight = 100;

    private long timestampWhenJumpStarted = -1;
    private double maxDeltaY;

    public Dinosaur(int x, int y) {
        image = new Image("file:assets/dinosaur.png", 50, 100, true, true);
        this.x = x;
        this.y = y;
        initialY = y;

    }

    public void jump() {
        if (!isJumping()) {
            timestampWhenJumpStarted = System.currentTimeMillis();
        }
    }

    public void update(long now) {
        if (isJumping()) {
            int milliSecondsDinosaurHasJumped = (int) (System.currentTimeMillis() - timestampWhenJumpStarted);
            y = calculateYPosition(milliSecondsDinosaurHasJumped);

        }

    }

    public boolean isJumping() {
        return timestampWhenJumpStarted > -1;
    }

    private int calculateYPosition(int milliSecondsSinceDinosaurStartedJump) {
        double deltaY = -(Math.pow(milliSecondsSinceDinosaurStartedJump - jumpDurationInMilliSeconds / 2, 2) * 4 * jumpHeight / (Math.pow(jumpDurationInMilliSeconds, 2)));
        deltaY *= -1;
        deltaY -= jumpHeight;

        if (milliSecondsSinceDinosaurStartedJump >= jumpDurationInMilliSeconds) {
            timestampWhenJumpStarted = -1;
            return initialY;
        }

        if (deltaY < maxDeltaY) {
            maxDeltaY = deltaY;
        }

        return (int) (initialY + deltaY);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}
