package client;

import com.google.common.eventbus.Subscribe;
import event.*;
import javafx.concurrent.Service;
import javafx.stage.WindowEvent;
import message.ChatContent;

import Util.Conf;
import Util.ConfigReader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.apache.log4j.PropertyConfigurator;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class ClientGUI extends Application {

    private ChatClient client;

    //标志为,代表当前为哪个窗口. 0为登录, 1为聊天框
    private int flag = 0;

    //消息显示
    private final TextArea msgShow = new TextArea();

    //primayStage
    private static Stage pStage;

    //显示的scene
    private Scene sceneLogin;
    private Scene sceneChat;

    //登录界面辅助线
    private Line line1, line2;

    //用户帐号和密码文本框
    private TextField userTextField;
    private PasswordField pwBox;

    //登录Pane
    private Pane root;

    //loading Icon
    private ImageView load;

    //登录按钮
    private Button loginBtn;

    //保存stage的值
    private void setPrimaryStage(Stage pStage) {
        ClientGUI.pStage = pStage;
    }

    public void updateScene(Scene scene) {
        pStage.setScene(scene);
    }

    public void backLogin() {
        root.getChildren().remove(load);
        root.getChildren().add(line1);
        root.getChildren().add(line2);
        root.getChildren().add(userTextField);
        root.getChildren().add(pwBox);
        root.getChildren().add(loginBtn);
        pwBox.setText("");
    }

    public void updateMsg(String msg) {
        msgShow.appendText(msg + "\n\n");
    }

    public void start(final Stage primaryStage) {

        setPrimaryStage(primaryStage);
        PublicEvent.eventBus.register(this);

        //文件路径prefix

        final String prePath = "file:" + System.getProperty("user.dir") + "/src/gui";

        this.client = new ChatClient();

        root = new Pane();
        root.setId("root");
        userTextField = new TextField();
        userTextField.setId("username");
        userTextField.setLayoutX(35);
        userTextField.setLayoutY(180);
        userTextField.setPrefSize(180,30);
        root.getChildren().add(userTextField);

        pwBox = new PasswordField();
        pwBox.setId("pwd");
        pwBox.setLayoutX(35);
        pwBox.setLayoutY(220);
        pwBox.setPrefSize(180,30);
        root.getChildren().add(pwBox);

        line1 = new Line(40,210,215,210);
        line1.setStroke(Color.valueOf("#d3d6d7"));
        root.getChildren().add(line1);
        line2 = new Line(40,250,215,250);
        line2.setStroke(Color.valueOf("#d3d6d7"));
        root.getChildren().add(line2);

//        System.out.println(Gui.class.getCanonicalName());
        Image image = new Image(prePath + "/image.png");
        ImageView view = new ImageView(image);
        view.setLayoutX(75);
        view.setLayoutY(35);
        view.setFitHeight(100);
        view.setFitWidth(100);
        Circle clip = new Circle(50, 50, 50);
        view.setClip(clip);
        root.getChildren().add(view);

        //登录按钮
        final ImageView submitView = new ImageView(new Image(prePath + "/submit.png"));
        submitView.setFitHeight(25);
        submitView.setFitWidth(25);
        loginBtn = new Button("", submitView);
        loginBtn.setLayoutX(190);
        loginBtn.setLayoutY(218);
        root.getChildren().add(loginBtn);


        //登录界面
        final Scene scene = new Scene(root, 250, 300);
        sceneLogin = scene;
        primaryStage.setScene(sceneLogin);
        scene.getStylesheets().add(prePath + "/Login.css");
        primaryStage.show();

        /*聊天界面*/
        Pane chat = new Pane();
        chat.setId("chat");

        //顶部信息
        Label chatName = new Label("公共聊天室");

        chatName.setId("chatName");
        chatName.setLayoutX(20);
        chatName.setLayoutY(20);
        chat.getChildren().add(chatName);

        //顶部
        ImageView topView = new ImageView((prePath + "/top.png"));
        topView.setLayoutX(450);
        topView.setLayoutY(15);
        topView.setFitHeight(40);
        topView.setFitWidth(180);
        chat.getChildren().add(topView);

        //消息显示界面
        msgShow.setId("msgShow");
        msgShow.setPrefSize(650,310);
        msgShow.setLayoutX(0);
        msgShow.setLayoutY(70);
        msgShow.setEditable(false);
        msgShow.setText("");

        // 自动滚屏
        msgShow.textProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue,
                                Object newValue) {
                msgShow.setScrollTop(Double.MAX_VALUE); //this will scroll to the bottom
                //use Double.MIN_VALUE to scroll to the top
            }
        });


        //todo 将聊天界面变成滚动Pane 可以作气泡和头像效果
        // 滚动Pane
//        Pane show = new Pane();
//        ScrollPane sp = new ScrollPane();
//        show.setStyle("-fx-background-color: transparent;");
//        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
//        show.setId("show");
//        sp.setContent(show);
//        sp.setLayoutX(0);
//        sp.setLayoutY(70);
//        sp.setPrefSize(650, 330);
//        chat.getChildren().add(sp);

        chat.getChildren().add(msgShow);

        //消息界面
        final TextArea msg = new TextArea();
        msg.setId("msg");
        msg.setPrefSize(650,120);
        msg.setLayoutX(0);
        msg.setLayoutY(430);

        msg.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                // e.consume(); //blocks all key bingdings
                KeyCode code =e.getCode();
                if(code == KeyCode.ENTER){
                    try {
                        if (!msg.getText().equals("")) {
                            //todo 将信息展示界面优化
//                            byte[] buff=msg.getText().getBytes();
//                            int f=buff.length;
//                            int wordNum = (f - msg.getText().length()) / 2;
//                            int prefix  = 60 - msg.getText().length() - wordNum;
//                            String pre  = "   ";
//                            for (int i=0; i<prefix; i++) pre += "      ";
//                            System.out.println(f);
                            msgShow.appendText("   我:" + msg.getText() + "\n\n");
                            System.out.println(msg.getText());
                            ChatContent chatContent = new ChatContent(msg.getText());
                            client.sendMessage(chatContent);
                            msg.setText("");
                        }
                    } catch (Exception e1) {
                    }
                    e.consume();
                }

            }
        });

        chat.getChildren().add(msg);

        //消息ICON
        ImageView msgView = new ImageView((prePath + "/msg.png"));
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
        Scene chatScane = new Scene(chat, 650, 550);
        chatScane.getStylesheets().add(prePath + "/Login.css");
        sceneChat = chatScane;
//        primaryStage.setScene(sceneChat);
        primaryStage.show();
        Task<Void> progressTask = new Task<Void>(){
            protected Void call() throws Exception {
                int i;
                for (i=0; i<100; i++){
                    Thread.sleep(50);
                    updateProgress(i ++, 100);
                    updateMessage("Loading..." + (i + 1) + "%");
                }
                updateMessage("Finish");
                return null;
            }
        };

        loginBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //        加载icon
                load = new ImageView(new Image(prePath + "/load.gif"));
                load.setLayoutY(200);
                load.setLayoutX(110);
                root.getChildren().add(load);
                root.getChildren().remove(line1);
                root.getChildren().remove(line2);
                root.getChildren().remove(userTextField);
                root.getChildren().remove(pwBox);
                root.getChildren().remove(loginBtn);
                // 等待相应界面
                System.out.println("loginTask2");
                String username = userTextField.getText();
                String pwd      = pwBox.getText();
                try {
                    client.Login(username, pwd);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        //注册窗口关闭事件
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent arg0) {
                if (flag == 1) {
                    client.closeConnection();
                }
            }
        });
    }

    @Subscribe
    public void LoginSuccess(LoginSuccessEvent loginSuccessEvent){
        flag = 1;
        System.out.println("success final");
        Platform.runLater(new Runnable(){
            @Override public void run() {
                updateScene(sceneChat);
            }
        });
    }

    @Subscribe
    public void LoginFail(LoginFailEvent loginFailEvent){
        client.closeConnection();
        System.out.println("wrong account");
        Platform.runLater(new Runnable(){
            @Override public void run() {
                backLogin();
                final Alert alert = new Alert(Alert.AlertType.INFORMATION); // 實體化Alert對話框物件，並直接在建構子設定對話框的訊息類型
                alert.setTitle("出错提示"); //設定對話框視窗的標題列文字
                alert.setHeaderText("错误的账户信息"); //設定對話框視窗裡的標頭文字。若設為空字串，則表示無標頭
                alert.setContentText("您输入的密码不正确。"); //設定對話框的訊息文字
                alert.showAndWait(); //顯示對話框，並等待對話框被關閉時才繼續執行之後的程式
            }
        });
    }

    @Subscribe
    public void ReLogin(ReLoginEvent event) {
        System.out.println("ReLogin");
        Platform.runLater(new Runnable(){
            @Override public void run() {
                msgShow.setText("");
                client.closeConnection();
                updateScene(sceneLogin);
                backLogin();
                final Alert alert = new Alert(Alert.AlertType.INFORMATION); // 實體化Alert對話框物件，並直接在建構子設定對話框的訊息類型
                alert.setTitle("出错提示"); //設定對話框視窗的標題列文字
                alert.setHeaderText("请重新登录"); //設定對話框視窗裡的標頭文字。若設為空字串，則表示無標頭
                alert.setContentText("您发送的消息已经达到上限,请重新登录。"); //設定對話框的訊息文字
                alert.showAndWait(); //顯示對話框，並等待對話框被關閉時才繼續執行之後的程式
            }
        });
    }

    @Subscribe
    public void receiveOtherMessage(ReceiveMessageEvent event){
        ChatContent chatContent = event.getChatContent();
        msgShow.appendText("   游客:" + chatContent.getMessage() + "\n\n");
    }

    @Subscribe
    public void TooFrequant(TooFrequentEvent event) {
        Platform.runLater(new Runnable(){
            @Override public void run() {
                msgShow.setText("");
                client.closeConnection();
                updateScene(sceneLogin);
                backLogin();
                final Alert alert = new Alert(Alert.AlertType.INFORMATION); // 實體化Alert對話框物件，並直接在建構子設定對話框的訊息類型
                alert.setTitle("出错提示"); //設定對話框視窗的標題列文字
                alert.setHeaderText("请重新登录"); //設定對話框視窗裡的標頭文字。若設為空字串，則表示無標頭
                alert.setContentText("您发送的消息频率太高,请重新登录"); //設定對話框的訊息文字
                alert.showAndWait(); //顯示對話框，並等待對話框被關閉時才繼續執行之後的程式
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