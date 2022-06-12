package com.doan.client.Controller.AdminScreen;

import com.doan.client.Model.Album;
import com.doan.client.Model.Artist;
import com.doan.client.Model.Object.AlbumOtd;
import com.doan.client.Model.Object.ArtistOtd;
import com.doan.client.Model.Song;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ArtistEditScreenController extends GetDataFromServerController implements Initializable {
    public ScrollPane scrollPaneSearch;

    public VBox listNotAddedSong;
    public HBox listAddedSong;
    public ImageView imageArtist;
    public List<Song> songs;
    public TextField addSongField;
    public TextField nameArtistField;
    public Button addNewArtistBtn;
    File fileImage;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addSongField.setPromptText("Search for song name");
        addSongField.setOnKeyTyped(this::filterSong);
        addSongField.setOnMouseClicked(this::showSearchFormSong);
        initSong();
    }

    @Override
    public void resetAllField() {
        addArtistField.setText("");
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
        if (addArtistField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Artist name can't be empty");
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
                Artist artist= new Artist();
                artist.setName(addArtistField.getText());
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String json = null;
                try {
                    json = ow.writeValueAsString(artist);
                    System.out.println(json);
                    HttpResponse<JsonNode> apiResponse = Unirest.post("http://localhost:8080/artist/addArtist").field("artist", json).field("image", currentFileImage).asJson();

                    if (apiResponse.getStatus() == 200) {
                        Artist artist1 = new Gson().fromJson(apiResponse.getBody().toString(), Artist.class);
                        ArtistOtd artistOtd = new ArtistOtd(artist1.getId(), artist1.getName(), artist1.getImage());
                        List<String> listIdSong = getAddedSong();
                        for (String s : listIdSong) {
                            HttpResponse<JsonNode> songResponse = null;
                            try {
                                songResponse = Unirest.get("http://localhost:8080/song/findSongById/" + s + "/false").asJson();
                                Song song = new Gson().fromJson(songResponse.getBody().getObject().get("song").toString(), Song.class);
                                song.getArtists().add(artistOtd);
                                String json1 = ow.writeValueAsString(song);
                                Unirest.put("http://localhost:8080/song/updateSong").field("file", new File("src/main/resources/com/doan/client/Image/null")).field("song", json1).asJson();
                            } catch (UnirestException e) {
                                throw new RuntimeException(e);
                            }

                        }
                        AdminScreenController.getArtistData();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                initArtist();
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText("Successful add");
                                alert.show();
                                addBtn.setText("Add Artist");
                                addBtn.setGraphic(null);
                                resetAllField();
                            }
                        });
                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText("Can't add artist");
                                alert.show();
                                addBtn.setText("Add Artist");
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
