package com.doan.client.Controller.AdminScreen;


import com.doan.client.Model.Album;
import com.doan.client.Model.Object.AlbumOtd;
import com.doan.client.Model.Song;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


import java.io.File;
import java.net.URL;

import java.util.List;

import java.util.ResourceBundle;

public class AlbumEditScreenController extends PublicAdminMethodController implements Initializable {


    public TextField addSongField;
    public ToggleGroup Group2;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addSongField.setPromptText("Search for song name");
        addSongField.setOnKeyTyped(this::filterSong);
        addSongField.setOnMouseClicked(this::showSearchFormSong);
        initSong();
    }

    @Override
    public void resetAllField() {
        addAlbumField.setText("");
        listNotAddedSong.getChildren().clear();
        listAddedSong.getChildren().clear();
        imageDisplay.setImage(null);
        initSong();

    }

    @Override
    public void showSearchFormSong(MouseEvent mouseEvent) {

        scrollPaneSongName.setVisible(true);
    }

    @Override
    public void addAction(ActionEvent actionEvent) {
        if (addAlbumField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Album name can't be empty");
            alert.show();

        } else if (imageDisplay.getImage() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Please choose Image file");
            alert.show();
        } else {
            addBtn.setText("Adding... Please wait");
            ImageView imageView = new ImageView(new Image("http://localhost:8080/image/loading.gif"));
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            addBtn.setGraphic(imageView);
            new Thread(() -> {

                Album album = new Album();
                album.setName(addAlbumField.getText());
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String json = null;
                try {
                    json = ow.writeValueAsString(album);
                    System.out.println(json);
                    HttpResponse<JsonNode> apiResponse = Unirest.post("http://localhost:8080/album/addAlbum").field("album", json).field("image", currentFileImage).asJson();

                    if (apiResponse.getStatus() == 200) {
                        Album album1 = new Gson().fromJson(apiResponse.getBody().toString(), Album.class);
                        AlbumOtd albumOtd = new AlbumOtd(album1.getId(), album1.getName(), album1.getImage());
                        List<String> listIdSong = getAddedSong();
                        for (String s : listIdSong) {
                            HttpResponse<JsonNode> songResponse = null;
                            HttpResponse<JsonNode> putResponse = null;
                            try {
                                songResponse = Unirest.get("http://localhost:8080/song/findSongById/" + s + "/false").asJson();
                                Song song = new Gson().fromJson(songResponse.getBody().getObject().get("song").toString(), Song.class);
                                song.setAlbum(albumOtd);
                                String json1 = ow.writeValueAsString(song);
                                Unirest.put("http://localhost:8080/song/updateSong").field("file", new File("src/main/resources/com/doan/client/Image/null")).field("song", json1).asJson();
                            } catch (UnirestException e) {
                                throw new RuntimeException(e);
                            }

                        }
                        AdminScreenController.getAlbumData();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                initAlbum();
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText("Successful add");
                                alert.show();
                                addBtn.setText("Add Album");
                                addBtn.setGraphic(null);
                                resetAllField();
                            }
                        });

                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText("Can't add album");
                                alert.show();
                                addBtn.setText("Add Album");
                                addBtn.setGraphic(null);
                                resetAllField();
                            }
                        });
                    }


                } catch (JsonProcessingException | UnirestException e) {
                    throw new RuntimeException(e);
                }
            }).start();


        }
    }

    @Override
    public void closeSearchForm(MouseEvent mouseEvent) {

        scrollPaneSongName.setVisible(false);
    }



}
