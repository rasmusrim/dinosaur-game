package com.rasmusrim.dinosaur.game.sprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

import javax.imageio.ImageIO;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Dinosaur extends Sprite {
    final private int initialY;

    private int jumpDurationInMilliSeconds = 1300;
    private int jumpHeight = 100;
    private Image image1;
    private Image image2;

    private double rotationAngle = 0;

    private long timestampWhenJumpStarted = -1;
    private long timestampWhenDieStarted = -1;

    private boolean isDead = false;

    public Dinosaur(int x, int y) {
        image1 = new Image(getClass().getResource("/dinosaur.png").toString(), 50, 100, true, true);
        image2 = new Image(getClass().getResource("/dinosaur2.png").toString(), 50, 100, true, true);
        image = image1;
        this.x = x;
        this.y = y;
        initialY = y;

    }

    public void jump() {
        if (canJump()) {
            timestampWhenJumpStarted = System.currentTimeMillis();
        }
    }

    public void die() {
        rotationAngle = 0;
        timestampWhenDieStarted = System.currentTimeMillis();
    }

    public void update(long now) {
        if (isJumping()) {

            if (!isDeadOrDieing()) {
                calculateJumpRotation();
            }
            calculateJumpPosition();
        }

        if (isDieing()) {
            calculateDieRotation();
        }

        if (!isDieing() && !isJumping() && !isDead()) {
            switchDinosaurCostume();
        }

    }


    private void switchDinosaurCostume() {
        if ((int) (System.currentTimeMillis() / 100) % 2 == 0) {
            if (image.equals(image1)) {
                image = image2;
            } else {
                image = image1;
            }
        }

    }

    private void calculateDieRotation() {
        rotationAngle -= 5 ;
        if (rotationAngle < -130) {
            isDead = true;
        }
    }

    public void render() {
        GraphicsContext gc = getGraphicsContext();
        gc.save();
        Rotate r = new Rotate(rotationAngle, x + getImage().getWidth() / 2, y + getImage().getHeight() / 2);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
        gc.drawImage(image, x, y);
        gc.restore();
    }


    public boolean canJump() {
        return !isJumping() && !isDieing() && !isDead();
    }

    public boolean isJumping() {
        return timestampWhenJumpStarted > -1;
    }

    public boolean isDieing() {
        return timestampWhenDieStarted > -1 && !isDead;
    }

    public boolean isDeadOrDieing() {
        return isDead() || isDieing();
    }

    private void calculateJumpRotation() {
        int milliSecondsDinosaurHasJumped = (int) (System.currentTimeMillis() - timestampWhenJumpStarted);
        double percentJumpCompleted = (double)milliSecondsDinosaurHasJumped / (double)jumpDurationInMilliSeconds;
        rotationAngle = 360 * percentJumpCompleted;

        if (rotationAngle > 360) {
            rotationAngle = 0;
        }
    }

    private void calculateJumpPosition() {
        int milliSecondsDinosaurHasJumped = (int) (System.currentTimeMillis() - timestampWhenJumpStarted);

        double deltaY = -(Math.pow(milliSecondsDinosaurHasJumped - jumpDurationInMilliSeconds / 2, 2) * 4 * jumpHeight / (Math.pow(jumpDurationInMilliSeconds, 2)));
        deltaY *= -1;
        deltaY -= jumpHeight;

        if (milliSecondsDinosaurHasJumped >= jumpDurationInMilliSeconds) {
            timestampWhenJumpStarted = -1;
            y = initialY;
            return;
        }

        y = (int) (initialY + deltaY);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public boolean isDead() {
        return isDead;
    }
}
