package client;


import Util.Conf;
import Util.ConfigReader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.apache.log4j.PropertyConfigurator;

import java.util.Timer;

public class Gui extends Application {

    private ChatClient client;

    public  void test() {
        System.out.println("test gui");
    }

    public void start(final Stage primaryStage) {

        this.client = new ChatClient("localhost", 8080);

        final Pane root = new Pane();
        root.setId("root");
        final TextField userTextField = new TextField();
        userTextField.setId("username");
        userTextField.setLayoutX(35);
        userTextField.setLayoutY(180);
        userTextField.setPrefSize(180,30);
        root.getChildren().add(userTextField);

        final PasswordField pwBox = new PasswordField();
        pwBox.setId("pwd");
        pwBox.setLayoutX(35);
        pwBox.setLayoutY(220);
        pwBox.setPrefSize(180,30);
        root.getChildren().add(pwBox);

        final Line line1 = new Line(40,210,215,210);
        line1.setStroke(Color.valueOf("#d3d6d7"));
        root.getChildren().add(line1);
        final Line line2 = new Line(40,250,215,250);
        line2.setStroke(Color.valueOf("#d3d6d7"));
        root.getChildren().add(line2);

        System.out.println(Gui.class.getCanonicalName());
        Image image = new Image("http://7xq64h.com1.z0.glb.clouddn.com/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202016-03-27%20%E4%B8%8A%E5%8D%884.45.04.png");
        ImageView view = new ImageView(image);
        view.setLayoutX(75);
        view.setLayoutY(35);
        view.setFitHeight(100);
        view.setFitWidth(100);
        Circle clip = new Circle(50, 50, 50);
        view.setClip(clip);
        root.getChildren().add(view);

        //登录按钮
        final ImageView submitView = new ImageView(new Image("http://7xq64h.com1.z0.glb.clouddn.com/submit.png"));
        submitView.setFitHeight(25);
        submitView.setFitWidth(25);
        final Button loginBtn = new Button("", submitView);
        loginBtn.setLayoutX(190);
        loginBtn.setLayoutY(218);
        root.getChildren().add(loginBtn);


        //登录界面
        Scene scene = new Scene(root, 250, 300);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("file:/Users/lyn/IdeaProjects/Login/out/production/Login/sample/Login.css");
        primaryStage.show();

        /*聊天界面*/
        Pane chat = new Pane();
        chat.setId("chat");
//        chat.setStyle("-fx-background-color: #fbfbfb;");
//        chat.setBackground();

        //顶部信息
        Label chatName = new Label("聊天室XXX");

        chatName.setLayoutX(20);
        chatName.setLayoutY(20);
        chat.getChildren().add(chatName);

        //顶部
        ImageView topView = new ImageView(("file:/Users/lyn/IdeaProjects/Login/out/production/Login/sample/top.png"));
        topView.setLayoutX(450);
        topView.setLayoutY(15);
        topView.setFitHeight(40);
        topView.setFitWidth(180);
        chat.getChildren().add(topView);

        //消息界面
        TextArea msg = new TextArea();
        msg.setId("msg");
        msg.setPrefSize(650,120);
        msg.setLayoutX(0);
        msg.setLayoutY(430);
        chat.getChildren().add(msg);

        //消息ICON
        ImageView msgView = new ImageView(("file:/Users/lyn/IdeaProjects/Login/out/production/Login/sample/msg.png"));
        msgView.setLayoutX(0);
        msgView.setLayoutY(400);
        msgView.setFitHeight(30);
        msgView.setFitWidth(200);
        chat.getChildren().add(msgView);

        //辅助线
        Line line3 = new Line(0,70,650,70);
        line3.setStroke(Color.valueOf("#d3d6d7"));
        chat.getChildren().add(line3);
        Line line4 = new Line(0,400,650,400);
        line4.setStroke(Color.valueOf("#d3d6d7"));
        chat.getChildren().add(line4);

        //面板
        final Scene chatScane = new Scene(chat, 650, 550);
//        primaryStage.setScene(chatScane);
        chatScane.getStylesheets().add(("file:/Users/lyn/IdeaProjects/Login/out/production/Login/sample/Login.css"));
        primaryStage.show();

        loginBtn.setOnAction(new EventHandler<ActionEvent>() {




            @Override
            public void handle(ActionEvent event) {
                //        加载icon
                ImageView load = new ImageView(new Image("file:/Users/lyn/GitHub/bookish-meme/src/gui/load.gif"));
                load.setLayoutY(200);
                load.setLayoutX(110);
                root.getChildren().add(load);
                root.getChildren().remove(line1);
                root.getChildren().remove(line2);
                root.getChildren().remove(userTextField);
                root.getChildren().remove(pwBox);
                root.getChildren().remove(loginBtn);
                  // 等待相应界面

//                try {
////                    client.Login("101","123456");
//                    Thread.sleep(2000);
//                } catch (Exception e) {
//                    System.out.println(e.getMessage());
//                }
//                if (false) {
//                    final Alert alert = new Alert(Alert.AlertType.INFORMATION); // 實體化Alert對話框物件，並直接在建構子設定對話框的訊息類型
//                    alert.setTitle("出错提示"); //設定對話框視窗的標題列文字
//                    alert.setHeaderText("错误的账户信息"); //設定對話框視窗裡的標頭文字。若設為空字串，則表示無標頭
//                    alert.setContentText("您输入的密码不正确。"); //設定對話框的訊息文字
//                    alert.showAndWait(); //顯示對話框，並等待對話框被關閉時才繼續執行之後的程式
//                } else {
//                    primaryStage.setScene(chatScane);
//                }
            }
        });
    }

    public static void main(String[] args) throws Exception {
        PropertyConfigurator.configure("config/log4j-client.property");
//        Timer timer = new Timer();
//        timer.schedule(new ClientLoggerTask(), 60 * 1000,  60 * 1000);

        ConfigReader configReader = new ConfigReader();
        Conf conf = configReader.readConf("config/conf.json");
        launch(args);
    }

}