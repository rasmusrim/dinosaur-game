package dinosaurgame.sprites;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

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

}
