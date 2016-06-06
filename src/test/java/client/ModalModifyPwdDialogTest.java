package client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static org.junit.Assert.*;

/**
 * Created by huanganna on 16/6/6.
 */
public class ModalModifyPwdDialogTest extends Application {
    public static void main(String[] args) {
        Application.launch(ModalModifyPwdDialogTest.class, args);
    }

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("Hello World");
        Group root = new Group();
        Scene scene = new Scene(root, 500, 450, Color.LIGHTBLUE);
        Button btn = new Button();
        btn.setLayoutX(250);
        btn.setLayoutY(240);
        btn.setText("Show modal dialog");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                try {
                    ModalModifyPwdDialog md = new ModalModifyPwdDialog(primaryStage,"100");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        root.getChildren().add(btn);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}