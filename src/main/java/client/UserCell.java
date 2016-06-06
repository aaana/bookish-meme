package client;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Created by huanganna on 16/6/6.
 */

public class UserCell extends ListCell<String> {

    final String prePath = "file:" + System.getProperty("user.dir") + "/src/gui";


        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                Pane pane = new Pane();
                Label userNameLabel = new Label(item);
                userNameLabel.setId("userName");
                userNameLabel.setLayoutX(70);
                userNameLabel.setLayoutY(17);
                pane.getChildren().add(userNameLabel);


                Image userImage = new Image(prePath + "/user.png");
                ImageView userView = new ImageView(userImage);
                userView.setLayoutX(15);
                userView.setLayoutY(0);
                userView.setFitHeight(45);
                userView.setFitWidth(45);
                Circle userClip = new Circle(25, 25, 25);
                userView.setClip(userClip);
                pane.getChildren().add(userView);

                setGraphic(pane);
            } else {
                setGraphic(null);
            }
        }
}
