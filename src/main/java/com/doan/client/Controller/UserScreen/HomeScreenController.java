package com.doan.client.Controller.UserScreen;

import com.doan.client.Controller.PublicController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeScreenController implements Initializable {
    public TextField searchBar;
    public AnchorPane clipPane;
    public AnchorPane slidePane;

    public AnchorPane clipPaneBackground;
    public AnchorPane slideCard;
    public AnchorPane outsideParent;
    PublicController publicController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(900);
        rectangle.setHeight(200);
        clipPane.setClip(rectangle);
        for (int i =0 ; i< 9; i++){
            ImageView imageView = new ImageView(new Image("http://localhost:8080/image/long.png"));
            imageView.setFitWidth(250);
            imageView.setFitHeight(170);
            imageView.setLayoutY(15);
            imageView.setLayoutX(25+ 300*i);
            Rectangle clip = new Rectangle();
            clip.setWidth(250);
            clip.setHeight(170);
            clip.setArcWidth(50);
            clip.setArcHeight(50);
            imageView.setClip(clip);
            imageView.setCursor(Cursor.HAND);
            slidePane.getChildren().add(imageView);

        }

        publicController = new PublicController();
        Rectangle rectangle1 = new Rectangle(clipPaneBackground.getPrefWidth() , clipPaneBackground.getPrefHeight());
        clipPaneBackground.setClip(rectangle1);

        for (int i =0 ; i < 9; i++ ){


            AnchorPane anchorPane = publicController.musicItem("http://localhost:8080/image/small-business.jpg","Born to Die", "Lana Del Rey", "PLAY", outsideParent, i);
            anchorPane.setLayoutX(10+ 180*i);
            slideCard.getChildren().add(anchorPane);
        }




    }
    public int index= 0;
    public void goPreviousSlide(ActionEvent mouseEvent) {
        if (index> 0){
            index= index-1;


            Timeline timeline= new Timeline();


            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0), new KeyValue(slidePane.translateXProperty(), -300*(index+1))));
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.25), new KeyValue(slidePane.translateXProperty(), -300*(index))));

            timeline.play();

        }

    }

    public void goNextSlide(ActionEvent mouseEvent) {

       if (index<6){

           index= index+1;
           Timeline timeline= new Timeline();


           timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0), new KeyValue(slidePane.translateXProperty(), -300*(index-1))));
           timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.25), new KeyValue(slidePane.translateXProperty(), -300*(index))));

           timeline.play();

       }
    }

    public void goNextCard(ActionEvent actionEvent) {
        if (PublicController.currentScrollIndex <6){

            PublicController.currentScrollIndex = PublicController.currentScrollIndex +1;
            Timeline timeline= new Timeline();


            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0), new KeyValue(slideCard.translateXProperty(), -180*(PublicController.currentScrollIndex -1))));
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.25), new KeyValue(slideCard.translateXProperty(), -180*(PublicController.currentScrollIndex))));

            timeline.play();

        }
    }

    public void goPreviousCard(ActionEvent actionEvent) {
        if (PublicController.currentScrollIndex > 0){
            PublicController.currentScrollIndex = PublicController.currentScrollIndex -1;
            Timeline timeline= new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0), new KeyValue(slideCard.translateXProperty(), -180*(PublicController.currentScrollIndex +1))));
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.25), new KeyValue(slideCard.translateXProperty(), -180*(PublicController.currentScrollIndex))));
            timeline.play();

        }
    }
}
