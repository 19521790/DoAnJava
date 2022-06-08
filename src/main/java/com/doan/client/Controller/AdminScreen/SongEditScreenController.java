package com.doan.client.Controller.AdminScreen;

import com.doan.client.Model.Album;
import com.doan.client.Model.Artist;
import com.doan.client.Model.Genre;
import com.doan.client.Model.Object.ArtistInSong;
import com.doan.client.Model.Song;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sun.mail.iap.Response;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SongEditScreenController implements Initializable {
    public HBox listAddedArtist;
    public ScrollPane scrollPaneSearchArtist;
    public VBox listNotAddedArtist;
    public Label fileSongName;
    public TextField songName;
    public Label duration;
    public Button addNewSongBtn;
    public ImageView imageProfile;
    public TextField addSongArtistField;
    public Media media;
    public HBox listAddedGenre;
    public ScrollPane scrollPaneSearchGenre;
    public VBox listNotAddedGenre;
    public ChoiceBox albumSelectForm;
    public TextField addGenreField;
    public TextField addAlbumField;
    public ScrollPane scrollPaneSearchAlbum;
    public VBox listNotAddedAlbum;
    public HBox listAddedAlbum;
    public ImageView testImage;
    public ToggleButton addSongMode;
    public ToggleButton editSongMode;
    public ToggleGroup Group1;

    File fileMusic;
    public List<Artist> artists;
    public List<Genre> genres;
    public List<Album> albums;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrollPaneSearchArtist.setVisible(false);
        initArtist();
        initGenre();
        initAlbum();


    }
    public void initGenre(){
        HttpResponse<JsonNode> apiResponse = null;
        try {
            apiResponse = Unirest.get("http://localhost:8080/genre/findAllGenres").asJson();
            String jsonValue = apiResponse.getBody().toString();

            ObjectMapper mapper = new ObjectMapper();
            genres = mapper.readValue(jsonValue, new TypeReference<>() {
            });
            addGenreToSearchFilter(genres);
        } catch (UnirestException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public void initArtist() {
        HttpResponse<JsonNode> apiResponse = null;
        try {
            apiResponse = Unirest.get("http://localhost:8080/artist/findAllArtists").asJson();
            String jsonValue = apiResponse.getBody().toString();

            ObjectMapper mapper = new ObjectMapper();
            artists = mapper.readValue(jsonValue, new TypeReference<>() {
            });
            addArtistToSearchFilter(artists);
        } catch (UnirestException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public void initAlbum() {
        HttpResponse<JsonNode> apiResponse = null;
        try {
            apiResponse = Unirest.get("http://localhost:8080/album/findAllAlbums").asJson();
            String jsonValue = apiResponse.getBody().toString();

            ObjectMapper mapper = new ObjectMapper();
            albums = mapper.readValue(jsonValue, new TypeReference<>() {
            });
            addAlbumToSelectForm(albums);
        } catch (UnirestException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public void addAlbumToSelectForm(List<Album> albums){
        for (Album album: albums) {
            Button button = new Button();
            button.setText(album.getName());
            button.setId(album.getId());
            button.getStyleClass().add("itemSearch");
            button.setPrefWidth(280);
            button.setAlignment(Pos.BASELINE_LEFT);
            button.setCursor(Cursor.HAND);
            button.setOnAction(this::clickItemSearchAlbum);
            listNotAddedAlbum.getChildren().add(button);
        }
        
    }

    public void addArtistToSearchFilter(List<Artist> artists) {
        for (Artist artist : artists) {
            Button button = new Button();
            button.setText(artist.getName());
            button.setId(artist.getId());
            button.getStyleClass().add("itemSearch");
            button.setPrefWidth(280);
            button.setAlignment(Pos.BASELINE_LEFT);
            button.setCursor(Cursor.HAND);
            button.setOnAction(this::clickItemSearchArtist);
            listNotAddedArtist.getChildren().add(button);
        }
    }
    public void addGenreToSearchFilter(List<Genre> genres) {
        for (Genre genre: genres) {
            Button button = new Button();
            button.setText(genre.getName());
            button.setId(genre.getId());
            button.getStyleClass().add("itemSearch");
            button.setPrefWidth(280);
            button.setAlignment(Pos.BASELINE_LEFT);
            button.setCursor(Cursor.HAND);
            button.setOnAction(this::clickItemSearchGenre);
            listNotAddedGenre.getChildren().add(button);
        }
    }

    public void clickItemSearchArtist(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        button.setPrefWidth(Region.USE_COMPUTED_SIZE);
        FontAwesomeIconView fontAwesomeIconView = new FontAwesomeIconView();
        fontAwesomeIconView.setGlyphName("REMOVE");
        button.setGraphic(fontAwesomeIconView);
        button.setContentDisplay(ContentDisplay.RIGHT);
        listAddedArtist.getChildren().add(button);
        listNotAddedArtist.getChildren().remove(button);
        button.setOnAction(this::returnSongToListArtist);

    }
    public void clickItemSearchGenre(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        button.setPrefWidth(Region.USE_COMPUTED_SIZE);
        FontAwesomeIconView fontAwesomeIconView = new FontAwesomeIconView();
        fontAwesomeIconView.setGlyphName("REMOVE");
        button.setGraphic(fontAwesomeIconView);
        button.setContentDisplay(ContentDisplay.RIGHT);
        listAddedGenre.getChildren().add(button);
        listNotAddedGenre.getChildren().remove(button);
        button.setOnAction(this::returnSongToListGenre);

    }
    public void clickItemSearchAlbum(ActionEvent actionEvent) {
        if (listAddedAlbum.getChildren().size()== 0){
            Button button = (Button) actionEvent.getSource();
            button.setPrefWidth(Region.USE_COMPUTED_SIZE);
            FontAwesomeIconView fontAwesomeIconView = new FontAwesomeIconView();
            fontAwesomeIconView.setGlyphName("REMOVE");
            button.setGraphic(fontAwesomeIconView);
            button.setContentDisplay(ContentDisplay.RIGHT);
            listAddedAlbum.getChildren().setAll(button);
            listNotAddedArtist.getChildren().remove(button);
            button.setOnAction(this::returnSongToListAlbum);
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("A song just belongs to one Album, Please remove album first!!!");
            alert.show();
        }

    }
    public void returnSongToListArtist(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        button.setPrefWidth(280);
        button.setGraphic(null);
        listNotAddedArtist.getChildren().add(button);
        listAddedArtist.getChildren().remove(button);
        button.setOnAction(this::clickItemSearchArtist);
    }
    public void returnSongToListAlbum(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        button.setPrefWidth(280);
        button.setGraphic(null);
        listNotAddedAlbum.getChildren().add(button);
        listAddedAlbum.getChildren().remove(button);
        button.setOnAction(this::clickItemSearchAlbum);
    }
    public void returnSongToListGenre(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        button.setPrefWidth(280);
        button.setGraphic(null);
        listNotAddedGenre.getChildren().add(button);
        listAddedGenre.getChildren().remove(button);
        button.setOnAction(this::clickItemSearchGenre);
    }


    public void filterArtist(KeyEvent keyEvent) {
        List<Artist> listFilter = artists.stream().filter(p -> p.getName().startsWith(addSongArtistField.getText())).toList();
        listNotAddedArtist.getChildren().clear();
        addArtistToSearchFilter(listFilter);
    }
    public void filterGenre(KeyEvent keyEvent) {
        List<Genre> listFilter = genres.stream().filter(p -> p.getName().startsWith(addGenreField.getText())).toList();
        listNotAddedGenre.getChildren().clear();
        addGenreToSearchFilter(listFilter);
    }
    public void filterAlbum(KeyEvent keyEvent) {
        List<Album> listFilter = albums.stream().filter(p -> p.getName().startsWith(addAlbumField.getText())).toList();
        listNotAddedAlbum.getChildren().clear();
        addAlbumToSelectForm(listFilter);
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
                    duration.setText("Duration: " + convertTimePlay(media.getDuration().toSeconds()));
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

    public void addNewSong(ActionEvent actionEvent) {
        if (songName.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Song name can't be empty");
            alert.show();
        } else if (fileMusic == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Please choose music file");
            alert.show();
        } else if (listAddedArtist.getChildren().size()==0 || listAddedAlbum.getChildren().size()==0 || listAddedGenre.getChildren().size()==0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Artist, Album, Genre can't be empty");
            alert.show();
        }
        else {
            addNewSongBtn.setText("Uploading...");
            ImageView imageView = new ImageView(new Image("http://localhost:8080/image/loading.gif"));
            imageView.setFitWidth(20);
            imageView.setFitHeight(20);
            addNewSongBtn.setGraphic(imageView);
            new Thread(() -> {

                Song song = new Song();
                song.setName(songName.getText());
                song.setDuration(media.getDuration().toSeconds());
                song.setArtists(getListAddedArtist());
                song.setGenres(getAddedGenre());
                song.setAlbum(getAddedAlbum());
                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String json = null;
                try {
                    json = ow.writeValueAsString(song);
                    HttpResponse<JsonNode> apiResponse=  Unirest.post("http://localhost:8080/song/addSong").field("file", fileMusic).field("song", json).asJson();

                    if (apiResponse.getStatus() == 200) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText("Successful upload");
                                alert.show();
                                addNewSongBtn.setText("Save");
                                addNewSongBtn.setGraphic(null);
                            }
                        });

                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText("Song's name has been exists!");
                                alert.show();
                                addNewSongBtn.setText("Save");
                                addNewSongBtn.setGraphic(null);
                            }
                        });
                    }
                    resetAllField();

                } catch (JsonProcessingException | UnirestException e) {
                    throw new RuntimeException(e);
                }
            }).start();


        }
    }

    public List<ArtistInSong> getListAddedArtist(){

        List<ArtistInSong> artistInSongList= new ArrayList<>();
        listAddedArtist.getChildren().forEach(e->{
            Button button= (Button) e;
            ArtistInSong artistInSong= new ArtistInSong();
            artistInSong.setName(button.getText());
            artistInSong.setId(button.getId());
            artistInSongList.add(artistInSong);
        });
        return artistInSongList;
    }
    public List<String> getAddedGenre(){
        List<String> genreArrayList= new ArrayList<>();
        listAddedGenre.getChildren().forEach(e->{
            Button button= (Button) e;
            genreArrayList.add(button.getText());
        });
        return genreArrayList;
    }
    public Album getAddedAlbum(){
        Album album = new Album();
        listAddedAlbum.getChildren().forEach(e->{
            Button button= (Button) e;
            album.setId(button.getId());
        });
       return album;
    }
    public void resetSong(ActionEvent actionEvent) {


        resetAllField();
    }


    public void closeSearchFormArtist(MouseEvent mouseEvent) {
        scrollPaneSearchArtist.setVisible(false);
        scrollPaneSearchGenre.setVisible(false);
        scrollPaneSearchAlbum.setVisible(false);
        
    }

    public void showSearchFormArtist(MouseEvent mouseEvent) {
        scrollPaneSearchArtist.setVisible(true);
        scrollPaneSearchGenre.setVisible(false);
        scrollPaneSearchAlbum.setVisible(false);
    }
    public void showSearchFormGenre(MouseEvent mouseEvent) {
        scrollPaneSearchArtist.setVisible(false);
        scrollPaneSearchGenre.setVisible(true);
        scrollPaneSearchAlbum.setVisible(false);
    }
    public void showSearchFormAlbum(MouseEvent mouseEvent) {
        scrollPaneSearchArtist.setVisible(false);
        scrollPaneSearchGenre.setVisible(false);
        scrollPaneSearchAlbum.setVisible(true);
    }
    public void resetAllField() {
        fileMusic = null;
        songName.setText("");
        fileSongName.setText("");
        duration.setText("");
        listAddedAlbum.getChildren().clear();
        listAddedGenre.getChildren().clear();
        listAddedArtist.getChildren().clear();
    }



    public void convertSongMode(ActionEvent actionEvent) {
        ToggleButton toggleButton = (ToggleButton) actionEvent.getSource();
        if (toggleButton.getId().equals("addSongMode")){
            addSongMode.setStyle("-fx-background-color:  #3b75ff; -fx-text-fill: white");
            editSongMode.setStyle("-fx-background-color: #d3dadb; -fx-text-fill: black");

        }else{
            editSongMode.setStyle("-fx-background-color:  #3b75ff; -fx-text-fill: white");
            addSongMode.setStyle("-fx-background-color: #d3dadb; -fx-text-fill: black");
        }
    }
}
