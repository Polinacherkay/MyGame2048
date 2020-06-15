package game;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Игра 2048");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        Controller controller = loader.getController();
        controller.setSceneAndSetupListeners(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}