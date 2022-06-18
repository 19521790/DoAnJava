package com.doan.client.Controller.Component;

import com.doan.client.Controller.UserScreen.PlayListScreenController;
import com.doan.client.Model.Playlist;
import com.doan.client.Model.Song;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
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
    public   PlayListScreenController playListScreenController;
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
        }
        
    }

    public void savePlaylist(ActionEvent actionEvent) {
        playListScreenController.imagePlaylist.setImage(imageProfile.getImage());
        playListScreenController.playlistName.setText(editPlaylistName.getText());
        ToggleButton toggleButton= (ToggleButton)playListScreenController.mainScreenController.Group1.getSelectedToggle();
        toggleButton.setText(editPlaylistName.getText());
        Playlist playlist= new Playlist();
        playlist.setId(playListScreenController.curId);
        playlist.setName(editPlaylistName.getText());

        HttpResponse<JsonNode> httpResponse = null;
        try {
            httpResponse = Unirest.put("http://localhost:8080/playlist/updatePlaylist").field("image", file).field("playlist",new Gson().toJson(playlist)).asJson();
            System.out.println(httpResponse.getBody());
            editContainer.setVisible(false);
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }


    }
}
