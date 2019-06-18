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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SingInController {
    private Statement statement;
    @FXML
    TextField UserName =new TextField();
    @FXML
    PasswordField password =new PasswordField();
    @FXML
    TextField Name =new TextField();
    @FXML
    TextField Surname =new TextField();
    @FXML
    Label infoLabel=new Label();
    private Utilities utilities=new Utilities();



    @FXML
    void back(ActionEvent event) throws IOException {

            Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root,1000,600));
            stage.show();

    }
    @FXML
    void signIn() throws SQLException {
        utilities.initializeDB();
         if(isAlreadyRegistered()){
             infoLabel.setText("this user already registered");
             infoLabel.setVisible(true);
        }
        else
        {
            register();
            infoLabel.setVisible(true);
            infoLabel.setText("Registred");
        }
    }

    private boolean isAlreadyRegistered() throws SQLException {

        statement=utilities.statement;
        System.out.println("sdsdsdsd");
        String userID = UserName.getText();
        String query = "SELECT * FROM game.usertable where UserName='"+userID+"'";
        ResultSet resultSet = statement.executeQuery(query);
        return (resultSet.next());
    }

    private void register() throws SQLException {
        int hash=31*7+ password.getText().hashCode();
        String query = "INSERT INTO game.usertable Values('"+UserName.getText()+"','"+hash+"','"+Name.getText()+"','"+Surname.getText()+"')";
        statement.executeUpdate(query);
    }



}
