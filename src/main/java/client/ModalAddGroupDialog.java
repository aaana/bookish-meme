package client;

/**
 * Created by huanganna on 16/6/4.
 */
import auth_server.LoginServer;
import javafx.collections.FXCollections;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import message.GroupContent;
import provider.ServiceProvider;

import java.util.List;

public class ModalAddGroupDialog {
    ChatClient client;
    final String prePath = "file:" + System.getProperty("user.dir") + "/src/gui";
//    Label groupInfo = new Label("加入小组");
    ChoiceBox<String> choiceBox = new ChoiceBox<>();
    Button confirmBtn = new Button("加入");
    Button searchBtn = new Button("搜索");
    TextField searchField = new TextField();

    public ModalAddGroupDialog(final Stage stg, ChatClient chatClient) throws Exception {
        this.client = chatClient;

//        groupInfo.setId("groupInfo");
//        groupInfo.setLayoutX(20);
//        groupInfo.setLayoutY(80);



        searchField.setId("searchField");

        searchField.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                KeyCode code = event.getCode();
                if (code == KeyCode.ENTER) {
                    doSearch();
                }
            }
        });


//        searchField.setOnKeyPressed(new EventHandler<InputMethodEvent>() {
//
//            @Override
//            public void handle(InputMethodEvent event) {
//                String keyword = searchField.getText();
////                if(keyword.length()!=0){
//                try {
//                    choiceBox.setItems(FXCollections.observableArrayList(ServiceProvider.getDbServer().searchByKeyWord(keyword)));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
////                }
//            }
//        });
        searchField.setLayoutX(20);
        searchField.setLayoutY(30);

        searchBtn.setId("search");
        searchBtn.setLayoutX(200);
        searchBtn.setLayoutY(30);

        searchBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                doSearch();
            }
        });


        choiceBox.setItems(FXCollections.observableArrayList(ServiceProvider.getDbServer().getAllGroupIds()));
        choiceBox.setValue("public");
        choiceBox.setId("choiceBox");
        choiceBox.setLayoutX(20);
        choiceBox.setLayoutY(80);
        choiceBox.setPrefWidth(100);

        confirmBtn.setId("confirm");
        confirmBtn.setLayoutX(200);
        confirmBtn.setLayoutY(80);

        final Stage stage = new Stage();
        //Initialize the Stage with type of modal
        stage.initModality(Modality.APPLICATION_MODAL);
        //Set the owner of the Stage
        stage.initOwner(stg);
        stage.setTitle("加入小组");
        Group root = new Group();
        Scene scene = new Scene(root, 300, 160, Color.WHITE);
        scene.getStylesheets().add(prePath+"/login.css");


        confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String groupId = choiceBox.getValue();
                GroupContent groupContent = new GroupContent(client.getAccount(), groupId);
                client.sendAddingGroupMessage(groupContent);
                stage.hide();
            }
        });



//        root.getChildren().add(groupInfo);
        root.getChildren().add(choiceBox);
        root.getChildren().add(confirmBtn);
        root.getChildren().add(searchBtn);
        root.getChildren().add(searchField);
        stage.setScene(scene);
        stage.show();

    }

    private void doSearch(){
        String keyword = searchField.getText();
        try {
            List<String> result = ServiceProvider.getDbServer().searchByKeyWord(keyword);
            choiceBox.setItems(FXCollections.observableArrayList(result));
            choiceBox.setValue(result.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}