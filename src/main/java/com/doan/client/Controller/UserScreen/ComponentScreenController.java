package com.doan.client.Controller.UserScreen;

import com.doan.client.Controller.Component.TopCardController;
import com.doan.client.Controller.PublicController;
import com.doan.client.Model.Song;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ComponentScreenController implements Initializable {
    public MainScreenController mainScreenController;
    public ImageView imageArtist;
    public Label nameArtist;
    public VBox artistTable;
    public AnchorPane bannerImage;
    public ImageView bigImageBanner;
    public Label bannerTitle;

    List<Song> songs;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(160);
        rectangle.setHeight(160);
        rectangle.setArcHeight(160);
        rectangle.setArcWidth(160);
        imageArtist.setClip(rectangle);
    }

    public void setBanner(HttpResponse<JsonNode> jsonNodeHttpResponse) {
        songs= PublicController.getSongFromJson(jsonNodeHttpResponse);
        nameArtist.setText(jsonNodeHttpResponse.getBody().getObject().get("name").toString());
        imageArtist.setImage(new Image(jsonNodeHttpResponse.getBody().getObject().get("image").toString()));
        setListSong(songs);
    }

    public void setListSong(List<Song> songs) {

        PublicController.addDataToTable(artistTable, songs);


    }

    public void setImageBanner(String image) {
        bigImageBanner.setImage(new Image(image));
    }

}
