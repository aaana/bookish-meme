package client;

import com.sun.deploy.util.SessionState;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import message.GroupContent;
import provider.ServiceProvider;

/**
 * Created by huanganna on 16/6/4.
 */
public class ModalCreateGroupDialog {
    ChatClient client;
    final String prePath = "file:" + System.getProperty("user.dir") + "/src/gui";
    Label groupInfo = new Label("创建小组:");
    TextField textField = new TextField();
    Button confirmBtn = new Button("确认");

    public ModalCreateGroupDialog(final Stage stg,ChatClient client) throws Exception {

        this.client = client;
        groupInfo.setId("groupInfo");
        groupInfo.setLayoutX(20);
        groupInfo.setLayoutY(50);

        confirmBtn.setId("confirm");
        confirmBtn.setLayoutX(250);
        confirmBtn.setLayoutY(130);

        textField.setId("createGroupField");
        textField.setLayoutX(110);
        textField.setLayoutY(50);




        final Stage stage = new Stage();
        //Initialize the Stage with type of modal
        stage.initModality(Modality.APPLICATION_MODAL);
        //Set the owner of the Stage
        stage.initOwner(stg);
        stage.setTitle("创建小组");
        Group root = new Group();
        Scene scene = new Scene(root, 300, 160, Color.WHITE);
        scene.getStylesheets().add(prePath+"/login.css");


        confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String groupId = textField.getText();
                if(groupId.length()!=0){
                    client.sendCreatingGroupMessage(new GroupContent(client.getAccount(),groupId));
//                    try {
//                        int result = ServiceProvider.getDbServer().createGroup(groupId);
//                        if(result==-1){
//                            Alert alert = new Alert(Alert.AlertType.ERROR,"创建失败");
//                            alert.setContentText("该组已存在！");
//                            alert.showAndWait();
//                        }else if(result==0){
//                            Alert alert = new Alert(Alert.AlertType.ERROR,"创建失败");
//                            alert.showAndWait();
//                        }else {
//                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"创建成功！");
//                            alert.showAndWait();
//                            ServiceProvider.getDbServer().addGroup(account, groupId);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"创建失败！");
                    alert.setContentText("组名不能为空！");
                    alert.showAndWait();
                }
                stage.hide();
            }
        });

        root.getChildren().add(groupInfo);
        root.getChildren().add(confirmBtn);
        root.getChildren().add(textField);
        stage.setScene(scene);
        stage.show();

    }
}
