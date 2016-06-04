package client;

/**
 * Created by huanganna on 16/6/4.
 */
import auth_server.LoginServer;
import javafx.collections.FXCollections;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import message.GroupContent;
import provider.ServiceProvider;

public class ModalAddGroupDialog {
    ChatClient client;
    final String prePath = "file:" + System.getProperty("user.dir") + "/src/gui";
    Label groupInfo = new Label("加入小组");
    ChoiceBox<String> choiceBox = new ChoiceBox<>();
    Button confirmBtn = new Button("确认");

    public ModalAddGroupDialog(final Stage stg, ChatClient chatClient) throws Exception {
        this.client = chatClient;

        groupInfo.setId("groupInfo");
        groupInfo.setLayoutX(20);
        groupInfo.setLayoutY(50);

        confirmBtn.setId("confirm");
        confirmBtn.setLayoutX(250);
        confirmBtn.setLayoutY(130);


        choiceBox.setItems(FXCollections.observableArrayList(ServiceProvider.getDbServer().getAllGroupIds()));
        choiceBox.setValue("public");
        choiceBox.setId("choiceBox");
        choiceBox.setLayoutX(200);
        choiceBox.setLayoutY(50);
        final Stage stage = new Stage();
        //Initialize the Stage with type of modal
        stage.initModality(Modality.APPLICATION_MODAL);
        //Set the owner of the Stage
        stage.initOwner(stg);
        stage.setTitle("加入小组");
        Group root = new Group();
        Scene scene = new Scene(root, 300, 160, Color.WHITE);
        scene.getStylesheets().add(prePath+"/login.css");

//        btn.setOnAction(new EventHandler<ActionEvent>() {
//
//            public void handle(ActionEvent event) {
//                stage.hide();
//
//            }
//        });

        confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String groupId = choiceBox.getValue();
                GroupContent groupContent = new GroupContent(client.getAccount(), groupId);
                client.sendAddingGroupMessage(groupContent);
                stage.hide();
            }
        });



        root.getChildren().add(groupInfo);
        root.getChildren().add(choiceBox);
        root.getChildren().add(confirmBtn);
        stage.setScene(scene);
        stage.show();

    }

}