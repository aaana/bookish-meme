package client;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import static org.junit.Assert.*;

/**
 * Created by huanganna on 16/6/6.
 */
public class UserCellTest extends Application{


    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane pane = new Pane();
        ListView listView = new ListView<String>();
        listView.setCellFactory(e -> new UserCell());
        listView.setItems(FXCollections.observableArrayList("100", "200"));
        pane.getChildren().add(listView);
        Scene scene = new Scene(pane,100,400);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}