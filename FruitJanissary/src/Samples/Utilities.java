package Samples;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


class Utilities {
   Statement statement;
    private Connection connection;

    void goMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root,1000,600));
        stage.show();
    }
   void quit(ActionEvent event){
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    void initializeDB() {

        //Set properties for database connection
        //Use your own user name and password

        String url = "jdbc:mysql://127.0.0.1:3306/?user=root";
        Properties connectionProps = new Properties();
        connectionProps.setProperty("user","root");
        connectionProps.setProperty("password","1234");
        connectionProps.setProperty("useSSL","false");
        connectionProps.setProperty("serverTimezone","UTC");
        connectionProps.setProperty("allowPublicKeyRetrieval","true");

        //Create connection and statement
        try {
            connection = DriverManager.getConnection(url, connectionProps);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
