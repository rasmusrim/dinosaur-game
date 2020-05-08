package com.rasmusrim.dinosaur.game;

import com.rasmusrim.dinosaur.game.sprites.*;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.net.URISyntaxException;
import java.util.*;

public class DinosaurGameController extends AnimationTimer {
    private Stage stage;
    private GraphicsContext gc;

    private int yValueOfGround = 215;
    private int initialDinosaurX = 100;

    private int sceneWidth = 800;
    private int sceneHeight = 400;

    private long timeLastCactusAppeared;

    private boolean cactusPresent = false;
    private Set<Cactus> cactuses;
    private Set<Cloud> clouds;

    private Dinosaur dinosaur;
    private Image background;

    private Canvas canvas;

    private int score;

    public void initUI(Stage mainStage) throws URISyntaxException {
        stage = mainStage;

        Group root = new Group();
        Scene gameScene = new Scene(root, sceneWidth, sceneHeight);
        stage.setScene(gameScene);
        canvas = new Canvas(sceneWidth, sceneHeight);
        root.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();

        background = new Image(getClass().getResource("/background.png").toString());

        dinosaur = new Dinosaur(initialDinosaurX, yValueOfGround);
        dinosaur.setGraphicsContext(gc);

        stage.setTitle("Dinosaur Game");

        gameScene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.SPACE)) {
                dinosaur.jump();
            }

            if (dinosaur.isDead()) {
                try {
                    restartGame();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });

        cactuses = new HashSet<>();
        clouds = new HashSet<>();
        score = 0;

        for (int i = 0; i < 5; i++) {
            Cloud cloud = new Cloud((int) (Math.random() * sceneWidth), (int) (Math.random() * 100));
            cloud.setGraphicsContext(gc);
            clouds.add(cloud);
        }


    }

    private void restartGame() throws URISyntaxException {
        this.initUI(stage);
    }

    @Override
    public void handle(long now) {

        gc.drawImage(background, 0, 0);

        if (!dinosaur.isDeadOrDieing()) {
            checkCollisions();
        }

        handleCactuses(now);
        handleClouds(now);

        handleDinosaur(now);
        handleScore();

        if (dinosaur.isDead()) {
            showGameOverText();
        }


    }

    private void showGameOverText() {

        gc.setFill(Color.RED);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        Font theFont = Font.font("Times New Roman", FontWeight.BOLD, 32);
        gc.setFont(theFont);
        Text text = new Text("Game over. You dead.\nPress any key to restart.");
        text.setFont(theFont);
        gc.fillText(text.getText(), sceneWidth / 2 - text.getLayoutBounds().getWidth() / 2, sceneHeight / 2 - text.getLayoutBounds().getHeight() / 2);
    }

    private void checkCollisions() {

        for (var cactus : cactuses) {
            if (dinosaur.checkForCollision(cactus)) {
                stopAllSprites();
                dinosaur.die();
            }
        }
    }

    private void stopAllSprites() {

        for (var cactus : cactuses) {
            cactus.stop();
        }
        for (var cloud : clouds) {
            cloud.stop();
        }

    }

    private void handleClouds(long now) {
        int random = (int) (Math.random() * 1000);

        if (random < 5 && !dinosaur.isDeadOrDieing()) {
            Cloud cloud = new Cloud(sceneWidth, (int) (Math.random() * 100));

            cloud.setGraphicsContext(gc);
            clouds.add(cloud);
        }

        Iterator iterator = clouds.iterator();

        while (iterator.hasNext()) {
            Cloud cloud = (Cloud) iterator.next();

            cloud.update(now);
            cloud.render();

            if (cloud.isOutOfFrame()) {
                iterator.remove();
            }
        }

    }

    private void handleScore() {
        gc.setFill(Color.RED);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        Font theFont = Font.font("Times New Roman", FontWeight.BOLD, 16);
        gc.setFont(theFont);
        gc.fillText("Score: " + score, 10, 40);
    }

    private void handleDinosaur(long now) {
        dinosaur.update(now);
        dinosaur.render();
    }

    private void handleCactuses(long now) {
        long timestampInSeconds = now / 1_000_000_000;

        if (timestampInSeconds - timeLastCactusAppeared > 1.6 || cactuses.size() == 0) {

            int random = (int) (Math.random() * 500);
            if (random < 20 && !dinosaur.isDeadOrDieing()) {
                Cactus cactus = new Cactus(sceneWidth, yValueOfGround - 20);

                cactus.setSpeed(0.1F);

                cactus.setGraphicsContext(gc);
                cactuses.add(cactus);
                timeLastCactusAppeared = timestampInSeconds;
            }
        }

        Iterator iterator = cactuses.iterator();

        while (iterator.hasNext()) {
            Cactus cactus = (Cactus) iterator.next();

            cactus.update(now);
            cactus.render();

            if (cactus.isOutOfFrame()) {
                iterator.remove();
                score++;
            }
        }

    }


}
