package com.doan.client.Controller.Component;

import com.doan.client.Model.Song;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class EditPlaylistFormController implements Initializable {

    public AnchorPane editContainer;
    public File file;
    public ImageView imageProfile;
    public TextField editPlaylistName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        }
    public void closeForm(ActionEvent actionEvent) {
        editContainer.setVisible(false);
    }

    public void editProfile(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select your Image");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        file = fc.showOpenDialog(null);
        if (file != null){
            imageProfile.setImage(new Image(file.toURI().toString()));
            Media media = new Media(file.toURI().toString());

        }
    }


}
