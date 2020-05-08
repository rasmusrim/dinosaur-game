package com.rasmusrim.dinosaur.game.sprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.geom.Rectangle2D;

abstract public class Sprite {

    private GraphicsContext graphicsContext;
    protected int x;
    protected int y;
    protected Image image;


    abstract public void update(long now);

    public void render() {
        getGraphicsContext().drawImage(image, x, y);
    }

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    protected Dimension getImageDimensions() {
        Dimension dimensions = new Dimension((int) image.getWidth(), (int) image.getHeight());
        return dimensions;
    }

    public Rectangle getBounds() {
        Dimension dimensions = getImageDimensions();
        return new Rectangle(x, y, (int) dimensions.getWidth(), (int) dimensions.getHeight());
    }

    public boolean checkForCollision(Sprite otherSprite) {
        Rectangle thisSpriteBoundaries = getBounds();
        Rectangle otherSpriteBoundaries = otherSprite.getBounds();

        int collidingPixels = 0;

        if (thisSpriteBoundaries.intersects(otherSpriteBoundaries)) {
            Rectangle2D intersection = thisSpriteBoundaries.createIntersection(otherSpriteBoundaries);

            int otherSpriteImageStartY = (int) intersection.getY() - otherSprite.getY();
            int thisSpriteImageStartY = (int) intersection.getY() - getY();

            PixelReader otherSpritePixelReader = otherSprite.getImage().getPixelReader();
            PixelReader thisSpritePixelReader = getImage().getPixelReader();


            for (int x = 0; x < intersection.getWidth(); x++) {
                for (int y = 0; y < intersection.getHeight(); y++) {
                    Color thisSpritePixel = thisSpritePixelReader.getColor(x, y + thisSpriteImageStartY);
                    Color otherSpritePixel = otherSpritePixelReader.getColor((int) (otherSprite.getImage().getWidth() - 1 - x), y + otherSpriteImageStartY);

                    if (thisSpritePixel.getOpacity() == 1 && otherSpritePixel.getOpacity() == 1) {
                        collidingPixels++;
                    }
                }
            }
        }

        return collidingPixels > 5;
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
