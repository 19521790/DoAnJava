package com.doan.client.Controller.AdminScreen;

import com.doan.client.Model.Album;

import com.doan.client.Model.Object.AlbumOtd;
import com.doan.client.Model.Object.ArtistOtd;
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

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SongEditScreenController extends GetDataFromServerController implements Initializable {

    public ScrollPane scrollPaneSearchArtist;
    public Label fileSongName;
    public Label duration;
    public Media media;
    public ScrollPane scrollPaneSearchGenre;
    public ScrollPane scrollPaneSearchAlbum;
    public Label chooseFileLabel;
    public Button chooseFile;
    public ScrollPane scrollPaneSongName;
    public Double currentDuration;
    File fileMusic;
    public Song currentEditSong;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initArtist();
        initGenre();
        initAlbum();
    }


    @Override
    public void clickItemSearchSong(ActionEvent actionEvent) {
        resetAllField();
        Button button = (Button) actionEvent.getSource();
        addSongField.setText(button.getText());
        String currentSaveSongId = button.getId();
        scrollPaneSongName.setVisible(false);
        try {
            HttpResponse<JsonNode> response = Unirest.get("http://localhost:8080/song/findSongById/" + currentSaveSongId + "/false").asJson();
            Song song = new Gson().fromJson(response.getBody().getObject().get("song").toString(), Song.class);
            fileSongName.setText(song.getFile());

            duration.setText(convertTimePlay(song.getDuration()));
            currentEditSong= song;
            for (int i = listNotAddedArtist.getChildren().size() - 1; i > -1; i--) {
                Button button1 = (Button) listNotAddedArtist.getChildren().get(i);
                song.getArtists().forEach(e -> {
                    if (e.getId().equals(button1.getId())) {
                        button1.fire();
                    }
                });
            }
            for (int i = listNotAddedAlbum.getChildren().size() - 1; i > -1; i--) {
                Button button1 = (Button) listNotAddedAlbum.getChildren().get(i);
                if (button1.getId().equals(song.getAlbum().getId())) {
                    button1.fire();
                }
            }
            for (int i = listNotAddedGenre.getChildren().size() - 1; i > -1; i--) {
                Button button1 = (Button) listNotAddedGenre.getChildren().get(i);
                if (song.getGenres().contains(button1.getText())) {
                    button1.fire();
                }
            }
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }


    public void getFileMusic(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select your Image");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Music Files", "*.mp3", "*.wav", "*.wma"));
        fileMusic = fc.showOpenDialog(null);
        if (fileMusic != null) {
            fileSongName.setText(fileMusic.getName());

            media = new Media(fileMusic.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnReady(new Runnable() {
                @Override
                public void run() {

                    duration.setText(convertTimePlay(media.getDuration().toSeconds()));
                    currentDuration = media.getDuration().toSeconds();
                    System.out.println(currentDuration);
                }
            });

        }
    }

    public String convertTimePlay(double current) {
        int minutePlay = (int) Math.floor(current / 60);
        int secondPlay = (int) (Math.round(current) - Math.floor(current / 60) * 60);
        if (secondPlay == 60) {
            secondPlay = 0;
            minutePlay += 1;
        }
        String labelMinutePlay = String.valueOf(minutePlay);
        String labelSecondPlay = String.valueOf(secondPlay);
        if (secondPlay < 10) {
            labelSecondPlay = "0" + labelSecondPlay;
        }
        if (minutePlay < 10) {
            labelMinutePlay = "0" + labelMinutePlay;
        }
        return labelMinutePlay + ":" + labelSecondPlay;
    }
    @Override
    public void addAction(ActionEvent actionEvent) {
        if (addSongField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Song name can't be empty");
            alert.show();
        } else if (fileMusic == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Please choose music file");
            alert.show();
        } else if (listAddedArtist.getChildren().size() == 0 || listAddedAlbum.getChildren().size() == 0 || listAddedGenre.getChildren().size() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Artist, Album, Genre can't be empty");
            alert.show();
        } else {
            addBtn.setText("Uploading...");
            ImageView imageView = new ImageView(new Image("http://localhost:8080/image/loading.gif"));
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            addBtn.setGraphic(imageView);
            new Thread(() -> {

                Song song = new Song();
                song.setName(addSongField.getText());
                song.setDuration(media.getDuration().toSeconds());
                song.setArtists(getListAddedArtist());
                song.setGenres(getAddedGenre());
                song.setAlbum(getAddedAlbum());
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String json = null;
                try {
                    json = ow.writeValueAsString(song);
                    System.out.println(json);
                    HttpResponse<JsonNode> apiResponse = Unirest.post("http://localhost:8080/song/addSong").field("file", fileMusic).field("song", json).asJson();

                    if (apiResponse.getStatus() == 200) {

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                AdminScreenController.getSongData();
                                initSong();
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText("Successful upload");
                                alert.show();
                                addBtn.setText("Add Song");
                                addBtn.setGraphic(null);
                                resetAllField();

                            }
                        });

                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText("Song's name has been exists!");
                                alert.show();
                                addBtn.setText("Save");
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

    public List<ArtistOtd> getListAddedArtist() {
        List<ArtistOtd> artistOtdList = new ArrayList<>();
        listAddedArtist.getChildren().forEach(e -> {
            Button button = (Button) e;
            ArtistOtd artistOtd = new ArtistOtd();
            artistOtd.setName(button.getText());
            artistOtd.setId(button.getId());
            artistOtdList.add(artistOtd);
        });
        return artistOtdList;
    }

    public List<String> getAddedGenre() {
        List<String> genreArrayList = new ArrayList<>();
        listAddedGenre.getChildren().forEach(e -> {
            Button button = (Button) e;
            genreArrayList.add(button.getText());
        });
        return genreArrayList;
    }

    public AlbumOtd getAddedAlbum() {
        AlbumOtd album = new AlbumOtd();
        listAddedAlbum.getChildren().forEach(e -> {
            Button button = (Button) e;
            album.setId(button.getId());
        });
        return album;
    }

    public void resetSong(ActionEvent actionEvent) {
        resetAllField();
    }


    public void closeSearchForm(MouseEvent mouseEvent) {
        scrollPaneSearchArtist.setVisible(false);
        scrollPaneSearchGenre.setVisible(false);
        scrollPaneSearchAlbum.setVisible(false);
        scrollPaneSongName.setVisible(false);

    }

    public void deleteAction(ActionEvent actionEvent) throws UnirestException {
        Alert alert= new Alert(Alert.AlertType.WARNING);
        if (currentEditSong == null){
            alert.setHeaderText("Please choose songs to delete");
            alert.show();
        }else {

            alert.setHeaderText("Are you sure want to delete this song?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                deleteBtn.setText("Deleting...");
                ImageView imageView = new ImageView(new Image("http://localhost:8080/image/loading.gif"));
                imageView.setFitWidth(20);
                imageView.setFitHeight(20);
                deleteBtn.setGraphic(imageView);
                new Thread(()->{
                    HttpResponse<String> jsonNodeHttpResponse= null;
                    try {
                        jsonNodeHttpResponse = Unirest.delete("http://localhost:8080/song/deleteSong/" + currentEditSong.getId()).asString();
                    } catch (UnirestException e) {
                        throw new RuntimeException(e);
                    }
                    if (jsonNodeHttpResponse.getStatus()== 200){
                        AdminScreenController.getSongData();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                initSong();
                                resetAllField();
                                Alert alert1= new Alert(Alert.AlertType.WARNING);
                                alert1.setHeaderText("Successful deleted");
                                alert1.show();
                                deleteBtn.setGraphic(null);
                                deleteBtn.setText("Remove Song");
                            }
                        });
                    }
                }).start();
            }
        }
    }

    public void showSearchFormArtist(MouseEvent mouseEvent) {
        scrollPaneSearchArtist.setVisible(true);
        scrollPaneSearchGenre.setVisible(false);
        scrollPaneSearchAlbum.setVisible(false);
        scrollPaneSongName.setVisible(false);
    }

    public void showSearchFormGenre(MouseEvent mouseEvent) {
        scrollPaneSearchArtist.setVisible(false);
        scrollPaneSearchGenre.setVisible(true);
        scrollPaneSearchAlbum.setVisible(false);
        scrollPaneSongName.setVisible(false);
    }

    public void showSearchFormAlbum(MouseEvent mouseEvent) {
        scrollPaneSearchArtist.setVisible(false);
        scrollPaneSearchGenre.setVisible(false);
        scrollPaneSearchAlbum.setVisible(true);
        scrollPaneSongName.setVisible(false);
    }

    public void showSearchFormSong(MouseEvent mouseEvent) {
        scrollPaneSearchArtist.setVisible(false);
        scrollPaneSearchGenre.setVisible(false);
        scrollPaneSearchAlbum.setVisible(false);
        scrollPaneSongName.setVisible(true);
    }
   @Override
    public void resetAllField() {
        fileMusic = null;
        addSongField.setText("");
        fileSongName.setText("");
        duration.setText("");
        listAddedAlbum.getChildren().clear();
        listAddedGenre.getChildren().clear();
        listAddedArtist.getChildren().clear();
        listNotAddedAlbum.getChildren().clear();
        listNotAddedGenre.getChildren().clear();
        listNotAddedArtist.getChildren().clear();
        listNotAddedSong.getChildren().clear();
        initAlbum();
        initArtist();
        initGenre();
        initSong();
    }


    public void updateAction(ActionEvent actionEvent) {
        if (addSongField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Song name can't be empty");
            alert.show();
        } else if (listAddedArtist.getChildren().size() == 0 || listAddedAlbum.getChildren().size() == 0 || listAddedGenre.getChildren().size() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Artist, Album, Genre can't be empty");
            alert.show();
        } else {
            editBtn.setText("Updating...");
            ImageView imageView = new ImageView(new Image("http://localhost:8080/image/loading.gif"));
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            editBtn.setGraphic(imageView);
            new Thread(() -> {

                Song song = new Song();
                song.setId(currentEditSong.getId());
                song.setName(addSongField.getText());
                song.setDuration(currentEditSong.getDuration());
                song.setArtists(getListAddedArtist());
                song.setGenres(getAddedGenre());
                song.setAlbum(getAddedAlbum());
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String json = null;
                try {
                    json = ow.writeValueAsString(song);
                    System.out.println(json);
                    HttpResponse<JsonNode> apiResponse;
                    if (currentEditSong.getFile().equals(fileSongName.getText())) {

                        apiResponse = Unirest.put("http://localhost:8080/song/updateSong").field("file",  new File("src/main/resources/com/doan/client/Image/null")).field("song", json).asJson();
                    } else {
                        apiResponse = Unirest.put("http://localhost:8080/song/updateSong").field("file", fileMusic).field("song", json).asJson();
                    }
                    if (apiResponse.getStatus() == 200) {
                        AdminScreenController.getSongData();

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                initSong();
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText("Successful update");
                                alert.show();
                                editBtn.setText("Save Song");
                                editBtn.setGraphic(null);
                                resetAllField();
                            }
                        });

                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText("Somethings Wrong!");
                                alert.show();
                                editBtn.setText("Save Song");
                                editBtn.setGraphic(null);
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


}
