package client;

import auth_server.LoginServer;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import provider.ServiceProvider;

/**
 * Created by huanganna on 16/6/6.
 */
public class ModalUserInfoDialog {

    private String account;

    final String prePath = "file:" + System.getProperty("user.dir") + "/src/gui";
    //    Label groupInfo = new Label("修改密码:");
    Button confirmBtn = new Button("确认");
    Label accountShow = new Label("用户名：");
    Label groupShow = new Label("所在群：");
    Label accountLabel = new Label();

    ListView<String> groups = new ListView();


    public ModalUserInfoDialog(final Stage stg,String account) throws Exception {

        this.account = account;

        accountShow.setId("accountShow");
        accountShow.setLayoutX(20);
        accountShow.setLayoutY(50);

        accountLabel.setId("accountLabel");
        accountLabel.setText(account);
        accountLabel.setLayoutX(80);
        accountLabel.setLayoutY(50);

        groupShow.setId("groupShow");
        groupShow.setLayoutX(20);
        groupShow.setLayoutY(100);

        groups.setId("groups");
        groups.setLayoutX(80);
        groups.setLayoutY(100);
        groups.setPrefWidth(170);
        groups.setPrefHeight(110);
        groups.setItems(FXCollections.observableArrayList(ServiceProvider.getDbServer().getGidByAcc(account)));

        confirmBtn.setId("confirm");
        confirmBtn.setLayoutX(170);
        confirmBtn.setLayoutY(260);



        final Stage stage = new Stage();
        //Initialize the Stage with type of modal
        stage.initModality(Modality.APPLICATION_MODAL);
        //Set the owner of the Stage
        stage.initOwner(stg);
        stage.setTitle("用户详情");
        Group root = new Group();
        Scene scene = new Scene(root, 259, 300, Color.WHITE);
        scene.getStylesheets().add(prePath + "/login.css");


        confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.hide();
            }
        });

//        root.getChildren().add(groupInfo);
        root.getChildren().add(confirmBtn);
        root.getChildren().add(accountShow);
        root.getChildren().add(accountLabel);
        root.getChildren().add(groupShow);
        root.getChildren().add(groups);
        stage.setScene(scene);
        stage.showAndWait();

    }
}