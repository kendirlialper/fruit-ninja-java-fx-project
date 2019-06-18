package Samples;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

public class LogInController  {
    @FXML TextField userTF=new TextField();
    @FXML PasswordField passwordField=new PasswordField();
    @FXML Label infoLabel=new Label();
    private Statement statement;

    @FXML
    void Login(ActionEvent event) throws IOException, SQLException {
        Utilities utilities=new Utilities();
        utilities.initializeDB();
        statement=utilities.statement;
        if(logInControl()){

         Game.userName=userTF.getText();

         utilities.goMenu(event);
        }
        else{
             infoLabel.setText("Username or password incorrect.");
             infoLabel.setVisible(true);
        }
    }

    @FXML
    void signIn(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root,1000,600));
        stage.show();
    }

    private boolean logInControl() throws SQLException {
        int hash=31*7+ passwordField.getText().hashCode();
        String query = "SELECT * FROM game.usertable where UserName='"+userTF.getText()+"' AND UserPassword='"+hash+"'";
        ResultSet resultSet = statement.executeQuery(query);
        return (resultSet.next());
    }
}
