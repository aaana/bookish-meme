package client;

import com.google.common.eventbus.Subscribe;
import event.*;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
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
import message.GroupContent;
import org.apache.log4j.PropertyConfigurator;
import provider.ServiceProvider;

import javax.swing.text.html.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

public class ClientGUI extends Application {

    private ChatClient client;

    //文件路径prefix
    final String prePath = "file:" + System.getProperty("user.dir") + "/src/gui";

    //标志为,代表当前为哪个窗口. 0为登录, 1为聊天框
    private int flag = 0;

    //消息显示
    private final TextArea msgShow = new TextArea();

    private final TextField accountTextField = new TextField();
    private final PasswordField pwdBox = new PasswordField();

    private ChoiceBox<String> choiceBox = new ChoiceBox<String>();

    //primayStage
    private static Stage pStage;

    //显示的scene
    private Scene sceneLogin;
    private Scene sceneChat;
    private Scene sceneRegister;
    private Scene sceneAddGroup;
    private Scene sceneChooseGroup;

    //在线用户个数;
    private int haveUser = 0;

    //登录界面辅助线
    private Line line1, line2,line6,line7;

    //用户帐号和密码文本框
    private TextField userTextField;
    private PasswordField pwBox;

    //登录Pane
    private Pane root;
    private Pane chat;
    private Pane register;
    private Pane addGroup;
    private Pane chooseGroup;

    //loading Icon
    private ImageView load;

    //登录按钮
    private Button loginBtn;

    private Button registerBtn;

    private ChoiceBox<String> joinedGroupChoiceBox;

    private ListView<String> listView;

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
        root.getChildren().add(registerBtn);
        pwBox.setText("");
    }

    public void updateMsg(String msg) {
        msgShow.appendText(msg + "\n\n");
    }

    public void start(final Stage primaryStage) throws Exception{

        setPrimaryStage(primaryStage);
        PublicEvent.eventBus.register(this);


        this.client = new ChatClient();

        root = new Pane();
        root.setId("root");
        userTextField = new TextField();
        userTextField.setId("username");
        userTextField.setLayoutX(35);
        userTextField.setLayoutY(180);
        userTextField.setPrefSize(180, 30);
        root.getChildren().add(userTextField);

        pwBox = new PasswordField();
        pwBox.setId("pwd");
        pwBox.setLayoutX(35);
        pwBox.setLayoutY(220);
        pwBox.setPrefSize(180, 30);
        root.getChildren().add(pwBox);

        pwBox.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                KeyCode code =event.getCode();
                if(code == KeyCode.ENTER) {
                    doLogin();
                }
            }
        });

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

        //注册按钮
        registerBtn = new Button("注册账号");
        registerBtn.setId("register");
        registerBtn.setLayoutX(170);
        registerBtn.setLayoutY(260);

        root.getChildren().add(registerBtn);


        //登录界面
        final Scene scene = new Scene(root, 250, 300);
        sceneLogin = scene;
        primaryStage.setScene(sceneLogin);
        scene.getStylesheets().add(prePath + "/Login.css");
        primaryStage.show();

        //注册界面
        register = new Pane();
        register.setId("registerPane");

        accountTextField.setId("username");
        accountTextField.setLayoutX(35);
        accountTextField.setLayoutY(80);
        accountTextField.setPrefSize(180, 30);
        register.getChildren().add(accountTextField);

        pwdBox.setId("pwd");
        pwdBox.setLayoutX(35);
        pwdBox.setLayoutY(120);
        pwdBox.setPrefSize(180, 30);

        pwdBox.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                KeyCode code = event.getCode();
                if (code == KeyCode.ENTER) {
                    String account = accountTextField.getText();
                    String password = pwdBox.getText();
                    try {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        if(account.length()==0||password.length()==0){
                            errorAlert.setHeaderText("注册失败");
                            errorAlert.setContentText("请输入用户名和密码！");
                            errorAlert.show();
                        }else {
                            doRegister(account, password);

//                            if (result == -1) {
//                                errorAlert.setHeaderText("注册失败");
//                                errorAlert.setContentText("用户名已存在！");
//                                errorAlert.show();
//                            } else if (result == 0) {
//                                errorAlert.setHeaderText("注册失败");
//                                errorAlert.show();
//                            } else {
//                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "注册成功");
//                                alert.showAndWait();
//                                updateScene(sceneLogin);
//                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        register.getChildren().add(pwdBox);

        line6 = new Line(40,110,215,110);
        line6.setStroke(Color.valueOf("#d3d6d7"));
        register.getChildren().add(line6);
        line7 = new Line(40,150,215,150);
        line7.setStroke(Color.valueOf("#d3d6d7"));
        register.getChildren().add(line7);

        //返回登录
        ImageView backImg = new ImageView(prePath+"/back.png");
        backImg.setFitHeight(25);
        backImg.setFitWidth(25);
        Button backBtn = new Button("",backImg);
        backBtn.setLayoutX(10);
        backBtn.setLayoutY(10);
        register.getChildren().add(backBtn);
        backBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateScene(sceneLogin);

            }
        });

        //注册按钮
        Button regBtn = new Button("注册");
        regBtn.setId("reg");
        regBtn.setLayoutX(195);
        regBtn.setLayoutY(260);


        regBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String account = accountTextField.getText();
                System.out.println(account.length());
                String password = pwdBox.getText();
                try {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    if (account.length() == 0 || password.equals("")) {
                        errorAlert.setHeaderText("注册失败");
                        errorAlert.setContentText("请输入用户名和密码！");
                        errorAlert.show();
                    } else {
                        doRegister(account, password);

//                        if (result == -1) {
//                            errorAlert.setHeaderText("注册失败");
//                            errorAlert.setContentText("用户名已存在！");
//                            errorAlert.show();
//                        } else if (result == 0) {
//                            errorAlert.setHeaderText("注册失败");
//                            errorAlert.show();
//                        } else {
//                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "注册成功");
//                            alert.showAndWait();
//                            updateScene(sceneLogin);
//                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        register.getChildren().add(regBtn);
        sceneRegister = new Scene(register,250,300);
        sceneRegister.getStylesheets().add(prePath + "/Login.css");

        /*选择组*/
        chooseGroup = new Pane();
        chooseGroup.setId("chooseGroup");

        Label chooseInfo = new Label("请选择进入一个小组");
        chooseInfo.setId("chooseInfo");
        chooseInfo.setLayoutX(20);
        chooseInfo.setLayoutY(50);
        chooseGroup.getChildren().add(chooseInfo);
        joinedGroupChoiceBox = new ChoiceBox<>();
//        joinedGroupChoiceBox.setItems(FXCollections.observableArrayList("0", "1", "2", "3"));
//        joinedGroupChoiceBox.setValue("0");
        joinedGroupChoiceBox.setId("choiceBox");
        joinedGroupChoiceBox.setLayoutX(210);
        joinedGroupChoiceBox.setLayoutY(50);
        chooseGroup.getChildren().add(joinedGroupChoiceBox);

        Button modifyBtn = new Button("修改密码");
        modifyBtn.setId("modify");
        modifyBtn.setLayoutX(20);
        modifyBtn.setLayoutY(125);
        chooseGroup.getChildren().add(modifyBtn);

        modifyBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    ModalModifyPwdDialog modalModifyPwdDialog = new ModalModifyPwdDialog(primaryStage,client.getAccount());
                    if(modalModifyPwdDialog.getResult()>0){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("修改密码成功");
                        alert.setContentText("请重新登录");
                        alert.showAndWait();
                        backLogin();
                        updateScene(sceneLogin);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        Button enterBtn = new Button("进入");
        enterBtn.setId("enter");
        enterBtn.setLayoutX(240);
        enterBtn.setLayoutY(125);

        enterBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String currentGroupId = joinedGroupChoiceBox.getValue();
                GroupContent groupContent = new GroupContent(client.getAccount(), currentGroupId);
                client.sendEnteringGroupMessage(groupContent);
            }
        });
        chooseGroup.getChildren().add(enterBtn);
        sceneChooseGroup = new Scene(chooseGroup,300,160);
        sceneChooseGroup.getStylesheets().add(prePath+"/login.css");


        /*添加组*/
//        addGroup = new Pane();
//        addGroup.setId("addGroup");
//
//        Label groupInfo = new Label("加入小组");
//        groupInfo.setId("groupInfo");
//        groupInfo.setLayoutX(20);
//        groupInfo.setLayoutY(50);
//        addGroup.getChildren().add(groupInfo);
//
//        choiceBox.setItems(FXCollections.observableArrayList(ServiceProvider.getDbServer().getAllGroupIds()));
//        choiceBox.setValue("public");
//        choiceBox.setId("choiceBox");
//        choiceBox.setLayoutX(200);
//        choiceBox.setLayoutY(50);
//        addGroup.getChildren().add(choiceBox);
//
//        Button confirmBtn = new Button("确认");
//        confirmBtn.setId("confirm");
//        confirmBtn.setLayoutX(250);
//        confirmBtn.setLayoutY(130);
//        confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                String groupId = choiceBox.getValue();
//                GroupContent groupContent = new GroupContent(client.getAccount(),groupId);
//                client.sendAddingGroupMessage(groupContent);
////                LoginServer loginServer
////                updateScene(sceneChat);
//            }
//        });
//        addGroup.getChildren().add(confirmBtn);
//        sceneAddGroup = new Scene(addGroup,300,160);
//        sceneAddGroup.getStylesheets().add(prePath+"/login.css");
        /*聊天界面*/
        chat = new Pane();
        chat.setId("chat");

        //顶部信息
        Label chatName = new Label("公共聊天室");

        chatName.setId("chatName");
        chatName.setLayoutX(170);
        chatName.setLayoutY(20);
        chat.getChildren().add(chatName);

        //
        Button addButton = new Button("添加小组");
        addButton.setId("addGroup");
        addButton.setLayoutX(530);
        addButton.setLayoutY(20);
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    ModalAddGroupDialog md = new ModalAddGroupDialog(primaryStage, client);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        chat.getChildren().add(addButton);

        Button createButton = new Button("创建小组");
        createButton.setId("addGroup");
        createButton.setLayoutX(370);
        createButton.setLayoutY(20);
        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    ModalCreateGroupDialog md = new ModalCreateGroupDialog(primaryStage,client);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        chat.getChildren().add(createButton);

        Button showButton = new Button("我的小组");
        showButton.setId("addGroup");
        showButton.setLayoutX(290);
        showButton.setLayoutY(20);
        showButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    ModalShowGroupDialog modalShowGroupDialog = new ModalShowGroupDialog(primaryStage,client.getAccount());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        chat.getChildren().add(showButton);

        Button deleteButton = new Button("退出小组");
        deleteButton.setId("addGroup");
        deleteButton.setLayoutX(450);
        deleteButton.setLayoutY(20);
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    ModalDeleteGroupDialog modalDeleteGroupDialog = new ModalDeleteGroupDialog(primaryStage,client);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        chat.getChildren().add(deleteButton);


        //顶部
        ImageView topView = new ImageView((prePath + "/top.png"));
        topView.setLayoutX(600);
        topView.setLayoutY(15);
        topView.setFitHeight(40);
        topView.setFitWidth(180);
        chat.getChildren().add(topView);

        //消息显示界面
        msgShow.setId("msgShow");
        msgShow.setPrefSize(650, 310);
        msgShow.setLayoutX(150);
        msgShow.setLayoutY(70);
        msgShow.setEditable(false);
        msgShow.setText("");

        listView = new ListView<String>();
        listView.setCellFactory(e -> new UserCell());
        listView.setLayoutX(0);
        listView.setLayoutY(0);
        listView.setPrefWidth(150);
        listView.setPrefHeight(550);
        chat.getChildren().add(listView);

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
        msg.setPrefSize(650, 120);
        msg.setLayoutX(150);
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
                            Date now = new Date();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
                            String snow = dateFormat.format(now);
                            msgShow.appendText(snow+"\n"+"我:" + msg.getText() + "\n\n");
                            System.out.println(msg.getText());
                            ChatContent chatContent = new ChatContent(msg.getText());
                            chatContent.setSender(client.getAccount());
                            chatContent.setSendDate(snow);
                            chatContent.setGroupId(client.getCurrentGroupId());
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
        msgView.setLayoutX(150);
        msgView.setLayoutY(400);
        msgView.setFitHeight(30);
        msgView.setFitWidth(200);
        chat.getChildren().add(msgView);

        //辅助线
        Line line3 = new Line(150,70,800,70);
        line3.setStroke(Color.valueOf("#d3d6d7"));
        chat.getChildren().add(line3);
        Line line4 = new Line(150,400,800,400);
        line4.setStroke(Color.valueOf("#d3d6d7"));
        chat.getChildren().add(line4);
        Line line5 = new Line(150,0,150,550);
        line5.setStroke(Color.valueOf("#d3d6d7"));
        chat.getChildren().add(line5);

        //面板
        Scene chatScane = new Scene(chat, 800, 550);
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
                doLogin();
            }
        });

        registerBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateScene(sceneRegister);
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

    private void showClientName(){
        Label clientName = new Label("Welcome,"+client.getAccount()+"   group:"+client.getCurrentGroupId());

        clientName.setId("clientName");
        clientName.setLayoutX(170);
        clientName.setLayoutY(45);
        chat.getChildren().add(clientName);
    }


    private void doRegister(String account,String password) throws Exception {
        client.register(account, password);
    }

    private void doLogin(){
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
        root.getChildren().remove(registerBtn);
        // 等待相应界面
        System.out.println("loginTask2");
        String username = userTextField.getText();
        String pwd      = pwBox.getText();
        try {
            client.Login(username, pwd);
            client.setAccount(username);
            List list = FXCollections.observableArrayList();
            joinedGroupChoiceBox.setItems((FXCollections.observableArrayList(ServiceProvider.getDbServer().getGidByAcc(username))));
            System.out.println((FXCollections.observableArrayList(ServiceProvider.getDbServer().getGidByAcc(username))));
            joinedGroupChoiceBox.setValue("public");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private void writeUser(List<String> users) {


        listView.setItems(FXCollections.observableArrayList(users));
//        int num = 0;
//
//        int userNum = users.size();
//
//        System.out.println(userNum + " userNum! " + haveUser);
//
//        if (haveUser > 0) {
////              chat.getChildren().remove(2);
////              chat.getChildren().remove(1);
//            for (int i = haveUser * 2; i > 0; i--) {
//                chat.getChildren().remove(i);
//            }
//        }
//
//
//        haveUser = userNum;
//
//        userNum = 0;
//
//        for(String user : users) {
//            userNum += 1;
//            //在线用户信息
//            Image userImage = new Image(prePath + "/user.png");
//            ImageView userView = new ImageView(userImage);
//            userView.setLayoutX(15);
//            userView.setLayoutY(20 + 80 * num);
//            userView.setFitHeight(45);
//            userView.setFitWidth(45);
//            Circle userClip = new Circle(25, 25, 25);
//            userView.setClip(userClip);
//            chat.getChildren().add(userNum*2 - 1, userView);
//            //用户名字
//            Label userName = new Label(user);
//            userName.setId("userName");
//            userName.setLayoutX(70);
//            userName.setLayoutY(30 + 80 * num);
//            chat.getChildren().add(userNum * 2, userName);
//            num += 1;
//        }

    }

    @Subscribe
    public void registerSuccess(RegisterSuccessEvent registerSuccessEvent){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "注册成功");
                alert.showAndWait();
                updateScene(sceneLogin);
            }
        });
    }

    @Subscribe
    public void registerFail(RegisterFailEvent registerFailEvent){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("注册失败");
                errorAlert.show();
            }
        });
    }

    @Subscribe
    public void accountExsits(AccountExistEvent accountExistEvent){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("注册失败");
                errorAlert.setContentText("用户名已存在！");
                errorAlert.show();
            }
        });
    }

    @Subscribe
    public void LoginSuccess(LoginSuccessEvent loginSuccessEvent) {
        System.out.println("login succeed");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    updateScene(sceneChooseGroup);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Subscribe
    public void enterGroup(EnterGroupEvent enterGroupEvent){

        flag = 1;
        System.out.println("success final");
        String gid = enterGroupEvent.getGroupId();
        client.setCurrentGroupId(gid);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    updateScene(sceneChat);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                showClientName();
//
                List<ChatContent> chatContents = enterGroupEvent.getChatContents();
                List<String> onlineAccounts = enterGroupEvent.getOnlineAccounts();
                client.setOnlineAccounts((ArrayList<String>) onlineAccounts);
                final List<String> users = onlineAccounts;
                msgShow.appendText("在线账号:\n");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        writeUser(users);
                    }
                });
                for (String account : onlineAccounts) {
                    msgShow.appendText(account + " ");
                }
                msgShow.appendText("\n");
//        msgShow.appendText("   游客:" + chatContent.getMessage() + "\n\n");
                if (chatContents.size() != 0) {
                    for (ChatContent chatContent : chatContents) {
                        msgShow.appendText(chatContent.getSendDate() + "\n" + chatContent.getSender() + ": " + chatContent.getMessage() + "\n\n");
                    }
                }
            }
        });

    }

    @Subscribe
    public void enterGroupFail(EnterGroupFailEvent enterGroupFailEvent){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.ERROR,"进入小组失败");
                alert.setContentText("已经进入该组了,不能重复进入！");
                alert.showAndWait();
                updateScene(sceneChooseGroup);
            }
        });
    }

    @Subscribe
    public void SomeOneOnline(SomeOneOnlineEvent someOneOnlineEvent) {
        String account = someOneOnlineEvent.getAccount();
        client.addOnlineAccount(account);
        List<String> onlineAccounts = client.getOnlineAccounts();
        final List<String> users = onlineAccounts;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                writeUser(users);
            }
        });
        msgShow.appendText("您的好友: " + account + "已上线\n");
    }

    @Subscribe
    public void someOneAddGroup(SomeOneAddGroupEvent someOneAddGroupEvent) {
        String account = someOneAddGroupEvent.getAccount();
        msgShow.appendText("用户: " + account + "已加入该组\n");
    }

    @Subscribe
    public void SomeOneOffline(SomeOneOfflineEvent someOneOfflineEvent) {
        String account = someOneOfflineEvent.getAccount();
        client.deleteOnlineAccount(account);
        msgShow.appendText("您的好友: " + account + "已下线\n");
        List<String> offlineAccounts = client.getOnlineAccounts();
        final List<String> users = offlineAccounts;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                writeUser(users);
            }
        });
    }

    @Subscribe
    public void LoginFail(LoginFailEvent loginFailEvent) {
//                  client.closeConnection();
        System.out.println("wrong account");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
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
         Platform.runLater(new Runnable() {
             @Override
             public void run() {
                 msgShow.setText("");
//                 client.closeConnection();
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
    public void receiveOtherMessage(ReceiveMessageEvent event) {
        ChatContent chatContent = event.getChatContent();
//       msgShow.appendText("   游客:" + chatContent.getMessage() + "\n\n");
        msgShow.appendText(chatContent.getSendDate() + "\n" + chatContent.getSender() + ": " + chatContent.getMessage() + "\n\n");
    }

    @Subscribe
    public void TooFrequant(TooFrequentEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                final Alert alert = new Alert(Alert.AlertType.INFORMATION); // 實體化Alert對話框物件，並直接在建構子設定對話框的訊息類型
                alert.setTitle("出错提示"); //設定對話框視窗的標題列文字
                alert.setHeaderText("发送过快"); //設定對話框視窗裡的標頭文字。若設為空字串，則表示無標頭
                alert.setContentText("您发送的消息频率太高,请稍后再重新发送"); //設定對話框的訊息文字
                alert.showAndWait(); //顯示對話框，並等待對話框被關閉時才繼續執行之後的程式
            }
        });
    }

    @Subscribe
    public void createGroupSuccess(CreateGroupSuccessEvent createGroupSuccessEvent){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"创建成功！");
                alert.showAndWait();
            }
        });
    }

    @Subscribe
    public void groupAlreadyExist(GroupAlreadyExist groupAlreadyExist){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.ERROR,"创建失败");
                alert.setContentText("该组已存在！");
                alert.showAndWait();
            }
        });

    }

    @Subscribe
    public void addGroupSuccess(AddGroupEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("add group succeed");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "添加成功");
                alert.showAndWait();
            }
        });
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println(event.getGroupId());
//                showClientName();
//                updateScene(sceneChat);
//            }
//        });
//        List<String> onlineAccounts = event.getOnlineAccounts();
//        client.setOnlineAccounts((ArrayList<String>) onlineAccounts);
//        final List<String> users = onlineAccounts;
//        msgShow.appendText("在线账号:\n");
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                writeUser(users);
//            }
//        });
//        for (String account : onlineAccounts) {
//            msgShow.appendText(account + " ");
//        }
//        msgShow.appendText("\n");
    }


    @Subscribe
    public void addGroupFail(AddGroupFailEvent addGroupFailEvent) throws Exception {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.ERROR, "加入失败！");
                alert.setContentText("你已经在该组中了！");
                alert.showAndWait();
            }
        });
    }

    @Subscribe
    public void deleteGroupSuccess(DeleteGroupEvent event) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("delete group succeed");
                String account = event.getAccount();
                String groupId = event.getGroupId();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "删除退出");
                alert.setContentText(account+"已成功退出"+groupId);
                alert.showAndWait();
            }
        });

    }

    public static void main(String[] args) throws Exception {
        PropertyConfigurator.configure("config/log4j-client.property");
        Timer timer = new Timer();
        timer.schedule(new ClientLoggerTask(), 60 * 1000, 60 * 1000);
        ConfigReader configReader = new ConfigReader();
        Conf conf = configReader.readConf("config/conf.json");
        launch(args);
    }
}


