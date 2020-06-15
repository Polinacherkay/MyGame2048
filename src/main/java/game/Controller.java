package game;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Pane panel;
    public Label lblScore;
    Game game = new Game();

    public void draw()
    {
        ObservableList<Node> list = panel.getChildren();

        for (int i = 0; i < Game.SIZE; i++) {
            for (int j = 0; j < Game.SIZE; j++) {
                Button button = (Button)list.get(i * Game.SIZE + j);
                String value = String.valueOf(game.get(i, j));
                if (value.equals("0"))
                    value = "";

                button.setText(value);
            }
        }

        lblScore.setText(String.valueOf(game.getScore()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        draw();
    }

    public void setSceneAndSetupListeners(Scene scene)
    {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                Direction direction = Direction.None;
                if (keyEvent.getCode() == KeyCode.LEFT)
                    direction = Direction.Left;
                else if (keyEvent.getCode() == KeyCode.RIGHT)
                    direction = Direction.Right;
                else if (keyEvent.getCode() == KeyCode.UP)
                    direction = Direction.Up;
                else if (keyEvent.getCode() == KeyCode.DOWN)
                    direction = Direction.Down;
                if (direction != Direction.None)
                {
                    game.move(direction);
                    draw();
                    checkStateGame();
                }
            }
        });
    }

    private void showAlert(String message)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Игра 2048");
        alert.setHeaderText(null);
        alert.setContentText(message);
        if (alert.showAndWait().get() == ButtonType.OK)
            NewGame();
        else
            close();
    }

    private void checkStateGame()
    {
        if (game.getStatus() == Status.Win)
            showAlert("Вы выйграли! Новая игра?");
        else if (game.getStatus() == Status.Lose)
            showAlert("Вы проиграли.");
    }

    private void close()
    {
        Stage stage = (Stage) panel.getScene().getWindow();
        stage.close();
    }

    public void NewGame()
    {
        game.initialize();
        draw();
    }

}
