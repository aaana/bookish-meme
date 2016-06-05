package client;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import message.GroupContent;
import provider.ServiceProvider;

/**
 * Created by huanganna on 16/6/5.
 */
public class ModalShowGroupDialog {

    private String account;
    Button confirmBtn = new Button("返回");
    ListView<String> groupList;
    final String prePath = "file:" + System.getProperty("user.dir") + "/src/gui";


    public ModalShowGroupDialog(final Stage stg,String account) throws Exception{
        this.account = account;

        groupList = new ListView<>();
        groupList.setPrefWidth(200);
        groupList.setPrefHeight(110);
        groupList.setLayoutX(20);
        groupList.setLayoutY(20);
        groupList.setItems(FXCollections.observableArrayList(ServiceProvider.getDbServer().getGidByAcc(account)));


        confirmBtn.setId("confirm");
        confirmBtn.setLayoutX(250);
        confirmBtn.setLayoutY(115);

        final Stage stage = new Stage();
        //Initialize the Stage with type of modal
        stage.initModality(Modality.APPLICATION_MODAL);
        //Set the owner of the Stage
        stage.initOwner(stg);
        stage.setTitle("显示所有小组");
        Group root = new Group();
        Scene scene = new Scene(root, 300, 160, Color.WHITE);
        scene.getStylesheets().add(prePath + "/login.css");


        confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.hide();
            }
        });

        root.getChildren().add(confirmBtn);
        root.getChildren().add(groupList);
        stage.setScene(scene);
        stage.show();
    }

}
