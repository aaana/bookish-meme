package client;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import message.GroupContent;
import provider.ServiceProvider;

import java.util.List;

/**
 * Created by huanganna on 16/6/5.
 */
public class ModalDeleteGroupDialog {
    ChatClient client;
    final String prePath = "file:" + System.getProperty("user.dir") + "/src/gui";
    Label groupInfo = new Label("退出小组");
    ChoiceBox<String> choiceBox = new ChoiceBox<>();
    Button confirmBtn = new Button("确认");
    Button cancelBtn = new Button("取消");

    public ModalDeleteGroupDialog(final Stage stg, ChatClient client) throws Exception {
        this.client = client;

        groupInfo.setId("groupInfo");
        groupInfo.setLayoutX(20);
        groupInfo.setLayoutY(50);

        confirmBtn.setId("confirm");
        confirmBtn.setLayoutX(230);
        confirmBtn.setLayoutY(120);

        cancelBtn.setId("cancel");
        cancelBtn.setLayoutX(20);
        cancelBtn.setLayoutY(120);

        List<String> groupList = ServiceProvider.getDbServer().getGidByAcc(client.getAccount());
        groupList.remove(client.getCurrentGroupId());

        choiceBox.setItems(FXCollections.observableArrayList(groupList));
        choiceBox.setValue(groupList.get(0));
        choiceBox.setId("choiceBox");
        choiceBox.setLayoutX(200);
        choiceBox.setLayoutY(50);
        final Stage stage = new Stage();
        //Initialize the Stage with type of modal
        stage.initModality(Modality.APPLICATION_MODAL);
        //Set the owner of the Stage
        stage.initOwner(stg);
        stage.setTitle("退出小组");
        Group root = new Group();
        Scene scene = new Scene(root, 300, 160, Color.WHITE);
        scene.getStylesheets().add(prePath+"/login.css");



        confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String groupId = choiceBox.getValue();
                GroupContent groupContent = new GroupContent(client.getAccount(), groupId);
                client.sendDeletingGroupMessage(groupContent);
                stage.hide();
            }
        });

        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.hide();
            }
        });



        root.getChildren().add(groupInfo);
        root.getChildren().add(choiceBox);
        root.getChildren().add(confirmBtn);
        root.getChildren().add(cancelBtn);
        stage.setScene(scene);
        stage.show();

    }

}