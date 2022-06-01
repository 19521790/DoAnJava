package com.doan.client.Controller.Screen;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayListScreenController implements Initializable {
    public static String image;
    public static String name;
    public ImageView avatarUser;
    public Label username;
    public static boolean isSetUser;
    public Label playlistName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (isSetUser) {
            avatarUser.setImage(new Image(image));
            username.setText(name);
        } else {
            username.setText("Anonymous");
        }

    }

    public void setPlaylistName(String playlistName) {
        this.playlistName.setText(playlistName);
    }
}
