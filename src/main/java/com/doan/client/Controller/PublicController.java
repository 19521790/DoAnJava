package com.doan.client.Controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class PublicController {
    public static int currentScrollIndex = 0;

    public AnchorPane musicItem(String imgUrl, String title, String description, String type, AnchorPane parentAnchorPane, int position) {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(160);
        anchorPane.setPrefHeight(220);
        anchorPane.getStyleClass().add("background_shadow");

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
        if (type.equals("PLAY")) {
            fontAwesomeIconView.setGlyphName("PLAY");
            fontAwesomeIconView.setWrappingWidth(13);
        } else {
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
        button.setCursor(Cursor.HAND);

        Button more = new Button();
        MaterialIconView materialIconView = new MaterialIconView();
        materialIconView.setGlyphName("MORE_HORIZ");
        materialIconView.setSize("18");
        more.setGraphic(materialIconView);
        more.setText("");
        more.setLayoutY(170);
        more.setLayoutX(120);
        more.getStyleClass().add("more_opacity");
        more.setCursor(Cursor.HAND);
        more.setBackground(Background.fill(Paint.valueOf("white")));

        more.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                more.getStyleClass().remove("more_opacity");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/Component/MoreOptionCard.fxml"));

                try {

                    AnchorPane card = fxmlLoader.load();
                    card.setLayoutY(520);
                    card.setLayoutX(180*(position- currentScrollIndex) );

                    AnchorPane layer= new AnchorPane();
                    layer.setPrefWidth(parentAnchorPane.getPrefWidth());
                    layer.setPrefHeight(parentAnchorPane.getPrefHeight());
                    layer.getChildren().add(card);

                    parentAnchorPane.getChildren().add(layer);
                    layer.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            parentAnchorPane.getChildren().remove(layer);
                            more.getStyleClass().add("more_opacity");
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }
        });
        anchorPane.getChildren().add(titleLabel);
        anchorPane.getChildren().add(descriptionLabel);
        anchorPane.getChildren().add(imageView);
        anchorPane.getChildren().add(button);

        anchorPane.getChildren().add(more);


        return anchorPane;
    }
}
