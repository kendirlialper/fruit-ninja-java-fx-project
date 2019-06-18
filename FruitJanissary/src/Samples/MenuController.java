package Samples;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MenuController {

    @FXML
    void playGame(ActionEvent event){
        Game game=new Game();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(game.startGame(),1000,600));
        stage.show();
    }

    @FXML
    void quit(ActionEvent event){
        Utilities utilities=new Utilities();
        utilities.quit(event);
    }

    @FXML
    void LeaderBoard(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("LeaderBoard.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root,1000,600));
        stage.show();
    }
    @FXML
    void MyScores(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("MyScores.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root,1000,600));
        stage.show();
    }
}
