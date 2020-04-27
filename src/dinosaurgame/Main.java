package dinosaurgame;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        initUI(primaryStage);;
    }

    private void initUI(Stage stage) {

        DinosaurGameController controller = new DinosaurGameController();
        controller.initUI(stage);
        controller.start();


        stage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
