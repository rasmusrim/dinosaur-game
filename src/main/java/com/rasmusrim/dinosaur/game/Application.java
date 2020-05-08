package com.rasmusrim.dinosaur.game;

import javafx.stage.Stage;

import java.net.URISyntaxException;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        initUI(primaryStage);;
    }

    private void initUI(Stage stage) throws URISyntaxException {

        DinosaurGameController controller = new DinosaurGameController();
        controller.initUI(stage);
        controller.start();


        stage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
