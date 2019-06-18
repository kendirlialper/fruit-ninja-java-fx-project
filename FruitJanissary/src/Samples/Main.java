package Samples;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root= FXMLLoader.load(getClass().getResource("LogIn.fxml"));
        primaryStage.setScene(new Scene(root,990,590));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Fruit Janissary");
        primaryStage.show();
    }
}
