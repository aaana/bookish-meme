package client;

import auth_server.LoginServer;
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
import message.GroupContent;
import provider.ServiceProvider;

/**
 * Created by huanganna on 16/6/6.
 */
public class ModalModifyPwdDialog {

    private String account;
    private int result;

    final String prePath = "file:" + System.getProperty("user.dir") + "/src/gui";
//    Label groupInfo = new Label("修改密码:");
    Button confirmBtn = new Button("确认");
    Line line1;
    Line line2;
    TextField oldPwdTextField;
    TextField newPwdTextField;

    public int getResult() {
        return result;
    }

    public ModalModifyPwdDialog(final Stage stg,String account) throws Exception {

        this.result = 0;
        this.account = account;
//        groupInfo.setId("groupInfo");
//        groupInfo.setLayoutX(10);
//        groupInfo.setLayoutY(10);

        confirmBtn.setId("confirm");
        confirmBtn.setLayoutX(170);
        confirmBtn.setLayoutY(260);


        line1 = new Line(40,110,215,110);
        line1.setStroke(Color.valueOf("#d3d6d7"));

        line2 = new Line(40,150,215,150);
        line2.setStroke(Color.valueOf("#d3d6d7"));

        oldPwdTextField = new TextField();
        oldPwdTextField.setText("old");
        oldPwdTextField.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                oldPwdTextField.setText("");
            }
        });
        oldPwdTextField.setId("username");
        oldPwdTextField.setLayoutX(35);
        oldPwdTextField.setLayoutY(80);
        oldPwdTextField.setPrefSize(180, 30);

        newPwdTextField = new TextField();
        newPwdTextField.setId("pwd");
        newPwdTextField.setLayoutX(35);
        newPwdTextField.setLayoutY(120);
        newPwdTextField.setPrefSize(180, 30);
        newPwdTextField.setText("new");
        newPwdTextField.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                newPwdTextField.setText("");
            }
        });




        final Stage stage = new Stage();
        //Initialize the Stage with type of modal
        stage.initModality(Modality.APPLICATION_MODAL);
        //Set the owner of the Stage
        stage.initOwner(stg);
        stage.setTitle("修改密码");
        Group root = new Group();
        Scene scene = new Scene(root, 259, 300, Color.WHITE);
        scene.getStylesheets().add(prePath + "/login.css");


        confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String oldPwd = oldPwdTextField.getText();
                String newPwd = newPwdTextField.getText();

                if (!oldPwd.equals("old") && !newPwd.equals("new")) {
                    LoginServer loginServer = new LoginServer();
                    try {
                        if (loginServer.login(account, oldPwd)) {
                            result = loginServer.modifyPwd(account, newPwd);
                            if (result == -1) {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "不存在该用户");
                                alert.showAndWait();
                            } else if (result == 0) {
                                Alert alert = new Alert(Alert.AlertType.ERROR, "修改失败");
                                alert.showAndWait();
                            } else {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION, "修改密码成功");
                                alert.showAndWait();
                                stage.hide();
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "原密码错误");
                            alert.showAndWait();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(result);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "修改失败");
                    alert.setContentText("原密码和新密码不能为空");
                    alert.showAndWait();
                }

            }
        });

//        root.getChildren().add(groupInfo);
        root.getChildren().add(confirmBtn);
        root.getChildren().add(oldPwdTextField);
        root.getChildren().add(newPwdTextField);
        root.getChildren().add(line1);
        root.getChildren().add(line2);
        stage.setScene(scene);
        stage.showAndWait();

    }
}

