package dinosaurgame;

import com.intellij.vcs.log.Hash;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import dinosaurgame.sprites.*;

import java.util.*;

public class DinosaurGameController extends AnimationTimer {
    private Stage stage;
    private GraphicsContext gc;

    private int yValueOfGround = 200;
    private int initialDinosaurX = 100;

    private int sceneWidth = 800;
    private int sceneHeight = 400;

    private long timeLastCactusAppeared;

    private boolean cactusPresent = false;
    private Set<Cactus> cactuses = new HashSet<>();
    private Set<Cloud> clouds = new HashSet<>();

    private Dinosaur dinosaur;
    private Image background;

    private int score;

    public void initUI(Stage mainStage) {
        stage = mainStage;

        Group root = new Group();
        Scene gameScene = new Scene(root, sceneWidth, sceneHeight);
        stage.setScene(gameScene);
        Canvas canvas = new Canvas(sceneWidth, sceneHeight);
        root.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();

        background = new Image("file:assets/background.png");

        dinosaur = new Dinosaur(initialDinosaurX, yValueOfGround);
        dinosaur.setGraphicsContext(gc);

        stage.setTitle("Dinosaur Game");

        gameScene.setOnKeyPressed(event -> dinosaur.jump());




    }

    @Override
    public void handle(long now) {

        gc.drawImage(background, 0, 0);
        handleDinosaur(now);
        handleCactuses(now);
        handleClouds(now);
        handleScore();


    }

    private void handleClouds(long now) {
        int random = (int)Math.random() * 1000;

        if (random < 10) {
            Cloud cloud = new Cloud(sceneWidth, (int)(Math.random() * 600));
            clouds.add(cloud);
        }

        Iterator iterator = clouds.iterator();

        while(iterator.hasNext()) {
            Cloud cloud = (Cloud)iterator.next();

            cloud.update(now);
            cloud.render();

            if (cloud.isOutOfFrame()) {
                iterator.remove();
                score++;
            }
        }

    }

    private void handleScore() {
        gc.setFill( Color.RED );
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(2);
        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 16 );
        gc.setFont( theFont );
        gc.fillText( "Score: " + score, 10, 40 );
    }

    private void handleDinosaur(long now) {
        dinosaur.update(now);
        dinosaur.render();
    }

    private void handleCactuses(long now) {
        long timestampInSeconds = now / 1_000_000_000;

        if (timestampInSeconds - timeLastCactusAppeared > 2 || cactuses.size() == 0) {

            int random = (int)(Math.random() * 500);
            if (random < 10) {
                Cactus cactus = new Cactus(sceneWidth, yValueOfGround);

                cactus.setSpeed(0.01F * score + 0.1F);

                cactus.setGraphicsContext(gc);
                cactuses.add(cactus);
                timeLastCactusAppeared = timestampInSeconds;
            }
        }

        Iterator iterator = cactuses.iterator();

        while(iterator.hasNext()) {
            Cactus cactus = (Cactus)iterator.next();

            cactus.update(now);
            cactus.render();

            if (cactus.isOutOfFrame()) {
                iterator.remove();
                score++;
            }
        }

    }


}
