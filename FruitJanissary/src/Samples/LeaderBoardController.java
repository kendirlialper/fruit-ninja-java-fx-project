package Samples;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.*;

public class LeaderBoardController  {
    @FXML private Button showButton=new Button("Show");
    @FXML private TableView<Score> table;
    @FXML private TableColumn<Score,String > userName;
    @FXML private TableColumn<Score,Integer > score;
    @FXML private TableColumn<Score,String > date;
    @FXML private TableColumn<Score,Integer > time;
    private ObservableList<Score> data = FXCollections.observableArrayList();

    @FXML
   void show() throws SQLException {
   showTable();
   }



    @FXML
    void back(ActionEvent event) throws IOException {
       Utilities utilities=new Utilities();
       utilities.goMenu(event);
    }
    private void showTable() throws SQLException {
        Utilities utilities=new Utilities();
        utilities.initializeDB();
        Statement statement = utilities.statement;

        String query="SELECT * FROM game.scoretable order by Score desc limit 10";
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            data.add(new Score(resultSet.getString(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getInt(4)));
        }
        userName.setCellValueFactory(new PropertyValueFactory<Score, String>("UserName"));
        score.setCellValueFactory(new PropertyValueFactory<Score, Integer>("Score"));
        date.setCellValueFactory(new PropertyValueFactory<Score, String>("Date"));
        time.setCellValueFactory(new PropertyValueFactory<Score, Integer>("Time"));
        table.setItems(data);
        table.setVisible(true);
        showButton.setVisible(false);
    }
}
