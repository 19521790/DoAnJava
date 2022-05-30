package com.doan.client.Controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class PublicController {
    public AnchorPane musicItem(String imgUrl, String title, String description, String type){
        AnchorPane anchorPane= new AnchorPane();
        anchorPane.setPrefWidth(160);
        anchorPane.setPrefHeight(220);
        anchorPane.getStyleClass().add("background_shadow");
        anchorPane.setPadding(new Insets(0,6,0,0));
        ImageView imageView = new ImageView(new Image(imgUrl));
        imageView.setFitWidth(130);
        imageView.setFitHeight(135);
        imageView.setLayoutX(15);
        imageView.setLayoutY(14);

        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(130);
        rectangle.setHeight(135);
        rectangle.setArcWidth(10);
        rectangle.setArcHeight(10);
        imageView.setClip(rectangle);

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = imageView.snapshot(parameters, null);
        // remove the rounding clip so that our effect can show through.
        imageView.setClip(null);
        // apply a shadow effect.
        imageView.getStyleClass().add("image_shadow");
        // store the rounded image in the imageView.
        imageView.setImage(image);


        Label titleLabel = new Label();
        titleLabel.setText(title);
        Label descriptionLabel = new Label();
        descriptionLabel.setText(description);
        titleLabel.setLayoutX(22);
        titleLabel.setLayoutY(160);
        titleLabel.getStyleClass().add("label_card");

        descriptionLabel.setLayoutX(22);
        descriptionLabel.setLayoutY(181);

        Button button = new Button();
        FontAwesomeIconView fontAwesomeIconView = new FontAwesomeIconView();
        if (type.equals("PLAY")){
            fontAwesomeIconView.setGlyphName("PLAY");
            fontAwesomeIconView.setWrappingWidth(13);
        }
        else{
            fontAwesomeIconView.setGlyphName("SHARE");
            fontAwesomeIconView.setWrappingWidth(19.6);
        }
        fontAwesomeIconView.setSize("20");

        fontAwesomeIconView.setFill(Paint.valueOf("white"));
        button.setPrefWidth(36);
        button.setPrefHeight(36);
        button.setLayoutY(124);
        button.setLayoutX(117);
        button.setGraphic(fontAwesomeIconView);
        button.getStyleClass().add("button_opacity");

        anchorPane.getChildren().add(titleLabel);
        anchorPane.getChildren().add(descriptionLabel);
        anchorPane.getChildren().add(imageView);
        anchorPane.getChildren().add(button);
        anchorPane.setCursor(Cursor.HAND);
        return anchorPane;
    }
}
