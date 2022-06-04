package com.doan.client.Controller.Screen;

import com.doan.client.Model.Song;
import com.doan.client.Model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AdminScreenController implements Initializable {


    public ToggleGroup Group1;
    public AnchorPane mainBoard;

    public ToggleButton addMusicBtn;
    public TabPane tabPane;
    public ToggleButton addAlbumBtn;
    public Label fileMusicName;
    public Label duration;
    public ToggleButton addArtistBtn;
    public TextField addSongOfArtist;
    public List<Song> songs;
    public ScrollPane scrollPaneSearch;
    public VBox listSongSearch;
    public HBox listAddedSong;
    public ImageView imageProfile;
    public File fileMusic;
    public TextField songName;
    public Media media;
    public Button addNewSongBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addMusicBtn.fire();

        HttpResponse<JsonNode> apiResponse = null;
        try {
            apiResponse = Unirest.get("http://localhost:8080/song/findAllSongs").asJson();
            String jsonValue = apiResponse.getBody().toString();

            ObjectMapper mapper = new ObjectMapper();
            songs = mapper.readValue(jsonValue, new TypeReference<>() {
            });
            for (int i=0 ; i< songs.size(); i++){
                Button button= new Button();
                button.setText(songs.get(i).getName());
                button.setId(songs.get(i).getId());
                button.getStyleClass().add("itemSearch");
                button.setPrefWidth(280);
                button.setAlignment(Pos.BASELINE_LEFT);
                button.setCursor(Cursor.HAND);
                button.setOnAction(this::clickItemSearch);
                listSongSearch.getChildren().add(button);
            }

        } catch (UnirestException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }
    public void clickItemSearch(ActionEvent actionEvent){
        Button button =(Button)  actionEvent.getSource();
        button.setPrefWidth(Region.USE_COMPUTED_SIZE);
        FontAwesomeIconView fontAwesomeIconView= new FontAwesomeIconView();
        fontAwesomeIconView.setGlyphName("REMOVE");
        button.setGraphic(fontAwesomeIconView);
        button.setContentDisplay(ContentDisplay.RIGHT);
        listAddedSong.getChildren().add(button);

        listSongSearch.getChildren().remove(button);
        button.setOnAction(this::returnSongToList);
        
    }
    public  void returnSongToList(ActionEvent actionEvent){
        Button button =(Button)  actionEvent.getSource();
        button.setPrefWidth(280);
        button.setGraphic(null);
        listSongSearch.getChildren().add(button);

        listAddedSong.getChildren().remove(button);
        button.setOnAction(this::clickItemSearch);
    }


    public void addMusicTab(ActionEvent actionEvent) {

        ToggleButton toggleButton = (ToggleButton) actionEvent.getSource();
        if (!toggleButton.isSelected()) {
            toggleButton.fire();
        }
        List<Toggle> toggleButtonList = toggleButton.getToggleGroup().getToggles();

        for (Toggle toggle : toggleButtonList) {
            ToggleButton indexToggleButton = (ToggleButton) toggle;
            if (indexToggleButton.isSelected()) {
                indexToggleButton.setStyle("-fx-background-color: #3b75ff; -fx-text-fill: white");

                MaterialIconView materialIconView = (MaterialIconView) indexToggleButton.getGraphic();
                materialIconView.setFill(Paint.valueOf("white"));

                pushScreen(indexToggleButton);
            } else {
                indexToggleButton.setStyle("-fx-background-color: white; -fx-text-fill: black");

                MaterialIconView materialIconView = (MaterialIconView) indexToggleButton.getGraphic();
                materialIconView.setFill(Paint.valueOf("black"));

            }
        }

    }

    public void pushScreen(ToggleButton toggleButton) {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        if (toggleButton.getId().equals("addMusicBtn")) {

            selectionModel.select(0);
        } else if (toggleButton.getId().equals("addArtistBtn")) {
            selectionModel.select(1);
        } else {
            selectionModel.select(2);
        }
    }


    public void getFileMusic(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select your Image");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Music Files", "*.mp3", "*.wav", "*.wma"));
        fileMusic = fc.showOpenDialog(null);
        if (fileMusic != null) {
            fileMusicName.setText(fileMusic.getName());

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

   

    public void closeSearch(MouseEvent mouseEvent) {
        scrollPaneSearch.setVisible(false);
    }

    public void showSearchForm(MouseEvent mouseEvent) {
        scrollPaneSearch.setVisible(true);
    }
    public  File fileImage;
    public void editProfile(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select your Image");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        fileImage = fc.showOpenDialog(null);
        if (fileImage != null){
            imageProfile.setImage(new Image(fileImage.toURI().toString()));
        }
    }

    public void addNewSong(ActionEvent actionEvent) {
       if (songName.getText().equals("")){
           Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setHeaderText("Name of song can't be empty");
            alert.show();
       } else if (fileMusic == null || fileImage == null){
           Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setHeaderText("Please choose file");
           alert.show();
       }else{
           addNewSongBtn.setText("Uploading...");
           ImageView imageView = new ImageView(new Image("http://localhost:8080/image/loading.gif"));
           imageView.setFitWidth(20);
           imageView.setFitHeight(20);
           addNewSongBtn.setGraphic(imageView);
           new Thread(()->{

               Song song = new Song();
               song.setName(songName.getText());
               song.setDuration(media.getDuration().toSeconds());

               ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
               String json = null;
               try {
                   json = ow.writeValueAsString(song);
                   System.out.println(fileMusic);
                   HttpResponse<JsonNode> apiResponse = Unirest.post("http://localhost:8080/song/addSong").field("file", fileMusic).field("song", json).field("image", fileImage).asJson();

                   if (apiResponse.getStatus() == 200){
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

                   }else{
                       Platform.runLater(new Runnable() {
                           @Override
                           public void run() {
                               Alert alert = new Alert(Alert.AlertType.INFORMATION);
                               alert.setHeaderText("Something wrong!");
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

    public void resetSong(ActionEvent actionEvent) {
        resetAllField();
    }
    public void resetAllField(){
        imageProfile.setImage(null);
        fileImage=null;
        fileMusic=null;
        songName.setText("");
    }
}
