package core.init.alphafactory;

import javafx.application.Application;
import javafx.stage.Stage;
import game_logic.Game;

import java.io.*;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("THE Manufactures v 0.0.0");
        Game.init(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}