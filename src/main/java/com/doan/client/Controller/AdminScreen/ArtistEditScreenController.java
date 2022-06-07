package com.doan.client.Controller.AdminScreen;

import com.doan.client.Model.Song;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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

public class ArtistEditScreenController implements Initializable {
    public ScrollPane scrollPaneSearch;

    public VBox listNotAddedSong;
    public HBox listAddedSong;
    public ImageView imageArtist;
    public List<Song> songs;
    public TextField addSongField;
    File fileImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrollPaneSearch.setVisible(false);
        initSongList();
    }

    public void initSongList() {
        HttpResponse<JsonNode> apiResponse = null;
        try {
            apiResponse = Unirest.get("http://localhost:8080/song/findAllSongs").asJson();
            String jsonValue = apiResponse.getBody().toString();

            ObjectMapper mapper = new ObjectMapper();
            songs = mapper.readValue(jsonValue, new TypeReference<>() {
            });
            addSongToSearchFilter(songs);

        } catch (UnirestException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void addSongToSearchFilter(List<Song> arrList) {
        for (Song song : arrList) {
            Button button = new Button();
            button.setText(song.getName());
            button.setId(song.getId());
            button.getStyleClass().add("itemSearch");
            button.setPrefWidth(280);
            button.setAlignment(Pos.BASELINE_LEFT);
            button.setCursor(Cursor.HAND);
            button.setOnAction(this::clickItemSearch);
            listNotAddedSong.getChildren().add(button);
        }
    }

    public void clickItemSearch(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        button.setPrefWidth(Region.USE_COMPUTED_SIZE);
        FontAwesomeIconView fontAwesomeIconView = new FontAwesomeIconView();
        fontAwesomeIconView.setGlyphName("REMOVE");
        button.setGraphic(fontAwesomeIconView);
        button.setContentDisplay(ContentDisplay.RIGHT);
        listAddedSong.getChildren().add(button);

        listNotAddedSong.getChildren().remove(button);
        button.setOnAction(this::returnSongToList);

    }

    public void returnSongToList(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        button.setPrefWidth(280);
        button.setGraphic(null);
        listNotAddedSong.getChildren().add(button);

        listAddedSong.getChildren().remove(button);
        button.setOnAction(this::clickItemSearch);
    }

    public void filterSong(KeyEvent keyEvent) {
        List<Song> listFilter = songs.stream().filter(p -> p.getName().startsWith(addSongField.getText())).toList();
        listNotAddedSong.getChildren().clear();
        addSongToSearchFilter(listFilter);

    }

    public void closeSearch(MouseEvent mouseEvent) {
        scrollPaneSearch.setVisible(false);
    }

    public void showSearchForm(MouseEvent mouseEvent) {
        scrollPaneSearch.setVisible(true);
    }


    public void addImageArtist(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select your Image");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        fileImage = fc.showOpenDialog(null);
        if (fileImage != null) {
            imageArtist.setImage(new Image(fileImage.toURI().toString()));
        }
    }


}
