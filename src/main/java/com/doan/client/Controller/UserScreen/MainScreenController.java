/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.doan.client.Controller.UserScreen;


import com.doan.client.Controller.Component.LoginFormController;
import com.doan.client.Controller.PublicController;
import com.doan.client.Model.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jfoenix.controls.JFXSlider;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * FXML Controller class
 *
 * @author Admin
 */
public class MainScreenController implements Initializable {

    public AnchorPane parentHomePane;
    public AnchorPane loginPaneFromHome;
    public User user = null;
    public ImageView imageUser;
    public AnchorPane accountSetting;
    public boolean login = false;
    public ToggleButton touchBtn2;
    public ToggleButton touchBtn1;
    public ToggleGroup Group1;

    public Separator logoutBtnSeparator;
    public Label logoutBtn;
    public Label openLoginAccount;
    public AnchorPane accountAnchorPane;

    public ToggleButton newsBtn;
    public ToggleButton likeBtn;
    public ToggleButton albumBtn;
    public ToggleButton downloadBtn;
    public ToggleButton followsBtn;
    public ToggleButton homeBtn;
    public AnchorPane homePane;

    public ProgressBar songProgressBar;
    public Slider volumeSlider;

    public AnchorPane musicBar;
    public File directory;
    public File[] files;
    public ArrayList<Song> songs;
    public int songNumber = 0;
    public Timer timer;
    public TimerTask task;
    public boolean running;
    public Media media;
    public MediaPlayer mediaPlayer;
    public FontAwesomeIconView playGraphicBtn;
    public Menu currentSpeedMedia;
    public JFXSlider jfxSlider;
    public JFXSlider jfxProgressBar;

    public ScrollPane mainBoard;
    public Button nextMediaBtn;
    public TextField searchBar;
    public ToggleButton discoverBtn;

    public ToggleGroup Group2;
    public ToggleButton categoryBtn;
    public ToggleButton billBoardBtn;

    public AnchorPane discoverButtonTab;
    public ToggleButton offerBtn;

    public Label timePlay;
    public Label timeRemaining;
    public AnchorPane discoverPane;
    public DiscoverScreenController discoverController;
    public VBox mainVbox;
    public Group group3;
    public ImageView imageCurrentPlayMusic;
    public HomeScreenController homeScreenController;
    public Label nameCurrentPlayMusic;
    public Label artistCurrentPlayMusic;
    public Button playMediaBtn;
    public LoveScreenController loveScreenController;
    public FollowPaneController followPaneController;
    public static List<String> likedList;
    public static String idPlaylistLike;
    public static String idUser;
    public static List<String> albumFollowed;
    public static List<String> artistFollowed;
    public static List<Playlist> playlistOfUser;
    public ScrollPane searchBarPane;

    public TextField searchBarField;
    public ImageView loadingSearchBar;
    public Button hideSearchPaneBtn;
    public HBox songHbox;
    public HBox artistHbox;
    public HBox albumHbox;
    public AnchorPane searchBarAnchorPane;
    public ArrayList<Song> previousSong;
    public AtomicBoolean enableShuffle = new AtomicBoolean(false);
    public AtomicBoolean enableRepeat = new AtomicBoolean(false);
    public LyricScreenController lyricScreenController;
    public AnchorPane bannerPrice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        songs = new ArrayList<Song>();
        playlistOfUser = new ArrayList<>();
        try {
            URL url1 = new URL("http://www.google.com");
            URLConnection connection = url1.openConnection();
            connection.connect();
            try {
                loginPaneFromHome.setVisible(true);
                FXMLLoader loginFxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/Component/LoginForm.fxml"));
                FXMLLoader homeFxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/UserScreen/HomeScreen.fxml"));
                FXMLLoader discoverFxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/UserScreen/DiscoverScreen.fxml"));
                //login
                AnchorPane newLoginPane = loginFxmlLoader.load();
                LoginFormController loginFormController = loginFxmlLoader.getController();
                loginPaneFromHome.getChildren().add(newLoginPane);
                loginFormController.mainController = this;
                // home
                homePane = homeFxmlLoader.load();
                homeScreenController = homeFxmlLoader.getController();
                homeScreenController.mainScreenController = this;

                homeScreenController.initFirstMedia();
                homeScreenController.initNewAndPopular();
                //discover
                discoverPane = discoverFxmlLoader.load();
                discoverController = discoverFxmlLoader.getController();
                discoverController.mainScreenController = this;

                PublicController.mainScreenController = this;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //avatar
            homeBtn.fire();
            offerBtn.fire();
            setAvatarUser();
            setSliderVolume();

            //set mainBoard


        } catch (IOException e) {
            System.out.println("Internet is not connected");
            downloadBtn.fire();
            setSliderVolume();
        }


        mainBoard.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public void setMediaPlaying(Song song) {

        try {
            homeScreenController.currentLastListeningList.remove(song);
            homeScreenController.currentLastListeningList.add(0, song);
            homeScreenController.addChildrenToLastListening(homeScreenController.currentLastListeningList);
        } catch (Exception e) {
        }
        convertMediaPlaying(song);

        for (Song songItem : songs) {
            if (songItem.getId().equals(song.getId())) {
                songs.remove(songItem);
                break;
            }
        }
        songs.add(song);
        songNumber = songs.size() - 1;
        new Thread(() -> {
            try {
                HttpResponse<String> httpResponse = Unirest.put("http://localhost:8080/song/countPlay/" + song.getId()).asString();
            } catch (UnirestException e) {
                throw new RuntimeException(e);
            }

        }).start();

    }

    public void convertMediaPlaying(Song song) {

        imageCurrentPlayMusic.setImage(new Image(song.getAlbum().getImage()));
        nameCurrentPlayMusic.setText(song.getName());
        artistCurrentPlayMusic.setText(song.getArtists().get(0).getName());
        timeRemaining.setText(PublicController.setTimePlay(song.getDuration()));
        timePlay.setText("00:00");
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            playGraphicBtn.setGlyphName("PLAY");
        }

        media = new Media(song.getFile());
        mediaPlayer = new MediaPlayer(media);

    }


    public void setSliderVolume() {
        jfxSlider = new JFXSlider();
        jfxSlider.setPrefHeight(10);
        jfxSlider.setPrefWidth(80);
        jfxSlider.setLayoutY(26);
        jfxSlider.setLayoutX(902);
        jfxSlider.setMax(200);
        jfxSlider.setBlockIncrement(10);
        jfxSlider.setValue(100);
        jfxSlider.setCursor(Cursor.HAND);
        musicBar.getChildren().add(jfxSlider);
        jfxSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mediaPlayer.setVolume(jfxSlider.getValue() * 0.01);
            }
        });
        jfxProgressBar = new JFXSlider();


        jfxProgressBar.setPrefWidth(450);
        jfxProgressBar.setLayoutY(64);
        jfxProgressBar.setLayoutX(270);
        jfxProgressBar.setBlockIncrement(10);

        jfxProgressBar.getStyleClass().add("timelineProgress");
        jfxProgressBar.setValue(0);
        jfxProgressBar.setMax(100);
        jfxProgressBar.setCursor(Cursor.HAND);
        musicBar.getChildren().add(jfxProgressBar);
        jfxProgressBar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double currentTime = jfxProgressBar.getValue() / 100 * media.getDuration().toSeconds();
                mediaPlayer.seek(Duration.seconds(currentTime));
                timePlay.setText(PublicController.setTimePlay(currentTime));

                String timeRemainString = PublicController.setTimePlay(media.getDuration().toSeconds() - currentTime);
                timeRemaining.setText(timeRemainString);
            }
        });


    }

    public void setAvatarUser() {
        Rectangle clip = new Rectangle();
        clip.setWidth(40);
        clip.setHeight(40);
        clip.setArcHeight(40);
        clip.setArcWidth(40);
        imageUser.setClip(clip);
        accountSetting.setVisible(false);

    }

    public void setLoginPaneVisible() {
        loginPaneFromHome.setVisible(false);
    }

    public void setUser(User user) throws UnirestException, IOException {
        this.user = user;

        Image image = new Image(user.getImage());
        imageUser.setImage(image);

        login = true;
        logoutBtn.setDisable(false);

        FXMLLoader accountFxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/UserScreen/AccountScreen.fxml"));
        try {
            accountAnchorPane = accountFxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AccountSettingScreenController accountSettingController = accountFxmlLoader.getController();
        accountSettingController.setUser(user);

        PlayListScreenController.image = user.getImage();
        PlayListScreenController.name = user.getName();
        PlayListScreenController.isSetUser = true;
    }


    public void showSettingUser(MouseEvent mouseEvent) {
        accountSetting.setVisible(!accountSetting.isVisible());

    }

    public void exitSystem(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure want to exit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    public void toggleBtn(ActionEvent actionEvent) {

        ToggleButton toggleButton = (ToggleButton) actionEvent.getSource();

        if (!login & (toggleButton.getId().equals("likeBtn") || toggleButton.getId().equals("albumBtn") || toggleButton.getId().equals("followsBtn"))) {
            loginPaneFromHome.setVisible(true);
        } else {
            if (!toggleButton.isSelected()) {
                toggleButton.fire();
            }
            List<Toggle> toggleButtonList = toggleButton.getToggleGroup().getToggles();

            for (Toggle toggle : toggleButtonList) {
                ToggleButton indexToggleButton = (ToggleButton) toggle;
                if (indexToggleButton.isSelected()) {
                    indexToggleButton.setStyle("-fx-background-color: #3b75ff; -fx-text-fill: white");

                    try {
                        FontAwesomeIconView fontAwesomeIconView = (FontAwesomeIconView) indexToggleButton.getGraphic();
                        fontAwesomeIconView.setFill(Paint.valueOf("white"));
                    } catch (Exception e) {

                        MaterialIconView fontAwesomeIconView = (MaterialIconView) indexToggleButton.getGraphic();
                        fontAwesomeIconView.setFill(Paint.valueOf("white"));
                    }
                    pushScreen(indexToggleButton);
                } else {
                    indexToggleButton.setStyle("-fx-background-color: white; -fx-text-fill: black");
                    try {
                        FontAwesomeIconView fontAwesomeIconView = (FontAwesomeIconView) indexToggleButton.getGraphic();
                        fontAwesomeIconView.setFill(Paint.valueOf("black"));
                    } catch (Exception e) {
                        MaterialIconView fontAwesomeIconView = (MaterialIconView) indexToggleButton.getGraphic();
                        fontAwesomeIconView.setFill(Paint.valueOf("black"));
                    }
                }

            }

        }

    }

    public void pushScreen(ToggleButton toggleButton) {
        discoverButtonTab.setVisible(false);
        double temp = mainBoard.getVvalue();
        mainBoard.setVvalue(0);
        if (toggleButton.getId().equals("homeBtn")) {
            mainBoard.setContent(loadingPane());
            new Thread(() -> {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        homeScreenController.initArtist();
                        homeScreenController.initAlbum();
                        mainBoard.setContent(homePane);
                    }
                });
            }).start();


        } else if (toggleButton.getId().equals("discoverBtn")) {
            mainBoard.setContent(discoverPane);
            discoverButtonTab.setVisible(true);


        } else if (toggleButton.getId().equals("downloadBtn")) {
            mainBoard.setContent(loadingPane());
            FXMLLoader downloadFxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/UserScreen/DownloadScreen.fxml"));

            new Thread(() -> {

                try {
                    AnchorPane anchorPane = downloadFxmlLoader.load();
                    DownloadScreenController downloadScreenController = downloadFxmlLoader.getController();
                    downloadScreenController.mainScreenController = this;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            mainBoard.setContent(anchorPane);
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }).start();

        } else {

            if (toggleButton.getId().equals("likeBtn")) {
                mainBoard.setContent(loadingPane());
                FXMLLoader loveFxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/UserScreen/LoveScreen.fxml"));

                new Thread(() -> {
                    try {
                        AnchorPane lovePane = loveFxmlLoader.load();
                        loveScreenController = loveFxmlLoader.getController();
                        loveScreenController.mainScreenController = this;
                        loveScreenController.initSongLiked();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                mainBoard.setContent(lovePane);
                            }
                        });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();


            } else if (toggleButton.getId().equals("albumBtn")) {

                mainBoard.setContent(loadingPane());
                FXMLLoader followPane = new FXMLLoader(getClass().getResource("/com/doan/client/View/UserScreen/FollowPane.fxml"));
                new Thread(() -> {
                    try {
                        AnchorPane albumPane = followPane.load();
                        followPaneController = followPane.getController();
                        followPaneController.mainScreenController = this;

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                followPaneController.initAlbum();
                                mainBoard.setContent(albumPane);
                            }
                        });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }).start();


            } else if (toggleButton.getId().equals("followsBtn")) {
                mainBoard.setContent(loadingPane());

                FXMLLoader followPane = new FXMLLoader(getClass().getResource("/com/doan/client/View/UserScreen/FollowPane.fxml"));
                new Thread(() -> {

                    try {
                        AnchorPane artistPane = followPane.load();
                        FollowPaneController followPaneController = followPane.getController();
                        followPaneController.mainScreenController = this;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                followPaneController.initArtist();
                                mainBoard.setContent(artistPane);
                            }
                        });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }).start();

            } else {
                mainBoard.setVvalue(temp);
                mainBoard.setContent(loadingPane());
                FXMLLoader playlistFxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/UserScreen/PlaylistScreen.fxml"));
                FXMLLoader editFxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/Component/EditPlaylistForm.fxml"));
                new Thread(() -> {

                    try {
                        AnchorPane anchorPane = playlistFxmlLoader.load();
                        AnchorPane editPlaylistPane = editFxmlLoader.load();
                        PlayListScreenController playListScreenController = playlistFxmlLoader.getController();
                        playListScreenController.editPlaylistFormController = editFxmlLoader.getController();
                        playListScreenController.editPlaylistPane = editPlaylistPane;
                        playListScreenController.mainScreenController = this;
                        Platform.runLater(new Runnable() {

                            @Override
                            public void run() {
                                parentHomePane.getChildren().add(editPlaylistPane);
                                editPlaylistPane.setVisible(false);
                                playListScreenController.initPlaylist(toggleButton.getId());
                                playListScreenController.initRecommend(toggleButton.getId());
                                mainBoard.setContent(anchorPane);
                            }
                        });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }).start();
            }
        }
    }


    public AnchorPane loadingPane() {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(1000);
        anchorPane.setPrefHeight(500);
        ImageView imageView = new ImageView(new Image("http://localhost:8080/image/loading.gif"));
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        anchorPane.getChildren().add(imageView);
        imageView.setLayoutX(450);
        imageView.setLayoutY(200);
        return anchorPane;
    }

    public void showAccountForm(MouseEvent mouseEvent) {
        accountSetting.setVisible(false);
        if (user == null) {
            loginPaneFromHome.setVisible(true);

        } else {
            mainBoard.setContent(accountAnchorPane);
        }
    }

    public void logoutAccount(MouseEvent mouseEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure want to logout?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            user = null;
            imageUser.setImage(new Image("http://localhost:8080/image/anonymous.png"));
            logoutBtn.setDisable(true);
            accountSetting.setVisible(false);
            Alert logoutPopup = new Alert(Alert.AlertType.INFORMATION);
            logoutPopup.setHeaderText("Logout Success");
            logoutPopup.show();
            homeBtn.fire();
            PlayListScreenController.isSetUser = false;
            login = false;

        }


    }

    public void setActiveComponent(ActionEvent actionEvent) {
        homeBtn.fire();
    }

    public void playMedia(ActionEvent actionEvent) {
        if (playGraphicBtn.getGlyphName().equals("PLAY")) {
            mediaPlayer.play();
            playGraphicBtn.setGlyphName("PAUSE");
            playGraphicBtn.setWrappingWidth(17);
            beginTimer();
        } else {
            mediaPlayer.pause();
            playGraphicBtn.setGlyphName("PLAY");
            playGraphicBtn.setWrappingWidth(13);
            cancelTimer();
        }

    }

    public void previousMedia(ActionEvent actionEvent) {
        if (songNumber > 0) {
            songNumber--;

        } else {
            songNumber = songs.size() - 1;
        }
        changMedia();
    }

    public void resetMedia(ActionEvent actionEvent) {
        mediaPlayer.seek(Duration.seconds(0));
        jfxProgressBar.setValue(0);
        timeRemaining.setText(PublicController.setTimePlay(media.getDuration().toSeconds()));
        timePlay.setText("00:00");
    }

    public void nextMedia(ActionEvent actionEvent) {
        if (!enableRepeat.get()) {
            if (enableShuffle.get()) {
                int randomNum = ThreadLocalRandom.current().nextInt(0, songs.size());
                songNumber = randomNum;
            } else {
                if (songNumber < songs.size() - 1) {
                    songNumber++;

                } else {
                    songNumber = 0;
                }
            }
        }
        changMedia();
    }

    public void changMedia() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                timePlay.setText("00:00");
            }
        });
        mediaPlayer.pause();
        jfxProgressBar.setValue(0);
        convertMediaPlaying(songs.get(songNumber));
        if (playGraphicBtn.getGlyphName().equals("PAUSE")) {
            mediaPlayer.play();
        }
        currentSpeedMedia.setText("Speed 1.0 x");
        mediaPlayer.setVolume(jfxSlider.getValue() * 0.01);
    }

    public void changeSpeedMedia(ActionEvent actionEvent) {
        MenuItem menuItem = (MenuItem) actionEvent.getSource();

        double cur_speed = Double.parseDouble(menuItem.getText().substring(0, menuItem.getText().length() - 2));
        mediaPlayer.setRate(cur_speed);
        currentSpeedMedia.setText("Speed " + cur_speed + " x");

    }

    public void beginTimer() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                running = true;
                try {
                    double current = mediaPlayer.getCurrentTime().toSeconds();

                    double end = media.getDuration().toSeconds();
                    double timeRemain = end - current;

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                            String time = PublicController.setTimePlay(current);
                            timePlay.setText(time);
                            String timeRemainString = PublicController.setTimePlay(timeRemain);
                            timeRemaining.setText(timeRemainString);

                            if (lyricScreenController != null) {
                                int curTimeLyrics = (int) current;
                                lyricScreenController.changeLyrics(curTimeLyrics);
                            }
                        }
                    });


                    jfxProgressBar.setValue(current / end * 100);
                    if (current / end == 1) {

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                nextMediaBtn.fire();
                            }
                        });
                    }
                } catch (Exception e) {
                    cancelTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public void cancelTimer() {
        running = false;
        timer.cancel();
    }

    public void fireTab(ActionEvent actionEvent) {
        List<Toggle> toggles = Group2.getToggles();
        for (Toggle value : toggles) {
            ToggleButton toggle = (ToggleButton) value;
            if (toggle.isSelected()) {
                toggle.getStyleClass().add("button_toggle");
                discoverController.setTab(toggle.getId());
            } else {
                toggle.getStyleClass().remove("button_toggle");
            }
        }
    }

    int count = 1;

    public void addNewPlaylist(ActionEvent actionEvent) {
        if (login) {
            ToggleButton button = new ToggleButton();
            button.setText("Playlist # " + count);
            FontAwesomeIconView fontAwesomeIconView = new FontAwesomeIconView();
            fontAwesomeIconView.setGlyphName("CLOSE");

            button.setGraphic(fontAwesomeIconView);
            button.getStyleClass().add("playlistBtn");
            VBox.setMargin(button, new Insets(10, 0, 0, 0));
            mainVbox.getChildren().add(button);
            button.setToggleGroup(Group1);
            count += 1;

            button.setId("Playlist" + count);
            button.setPrefWidth(165);
            button.setAlignment(Pos.BASELINE_LEFT);
            Playlist playlist = new Playlist();
            playlist.setName("Playlist # " + count);
            playlist.setIdUser(MainScreenController.idUser);

            try {
                HttpResponse<JsonNode> jsonNodeHttpResponse = Unirest.post("http://localhost:8080/playlist/addPlaylist/").field("image", new File("src/main/resources/com/doan/client/Image/anonymous.png")).field("playlist", new Gson().toJson(playlist)).asJson();
                Playlist playlist1 = new Gson().fromJson(jsonNodeHttpResponse.getBody().toString(), Playlist.class);
                playlistOfUser.add(playlist1);
                button.setId(playlist1.getId());
                button.setOnAction(this::toggleBtn);
                fontAwesomeIconView.setOnMouseClicked(mouseEvent -> removePlaylist(playlist1.getId(), button));
                button.fire();
            } catch (UnirestException e) {
                throw new RuntimeException(e);
            }
        } else {
            loginPaneFromHome.setVisible(true);
        }
    }

    public void removePlaylist(String id, ToggleButton button) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Are you sure want to delete this Playlist?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            mainVbox.getChildren().remove(button);
            try {
                HttpResponse<String> httpResponse = Unirest.delete("http://localhost:8080/playlist/deletePlaylist/" + id).asString();
                playlistOfUser.removeIf(playlist -> playlist.getId().equals(button.getId()));
                homeBtn.fire();
            } catch (UnirestException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void skipFunction(double current) {
        mediaPlayer.seek(Duration.seconds(current));
        double end = media.getDuration().toSeconds();
        double timeRemain = end - current;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String time = PublicController.setTimePlay(current);
                timePlay.setText(time);
                String timeRemainString = PublicController.setTimePlay(timeRemain);
                timeRemaining.setText(timeRemainString);
            }
        });

        jfxProgressBar.setValue(current / end * 100);
        if (current / end == 1) {
            nextMediaBtn.fire();
        }
    }

    public void skipBack10Second(ActionEvent actionEvent) {
        skipFunction(mediaPlayer.getCurrentTime().toSeconds() - 10);
    }

    public void skipForward30Second(ActionEvent actionEvent) {
        skipFunction(mediaPlayer.getCurrentTime().toSeconds() + 30);
    }


    public void hideSearchPane(ActionEvent mouseEvent) {
        searchBarPane.setVisible(false);
        loadingSearchBar.setVisible(false);
        hideSearchPaneBtn.setVisible(false);
    }

    public void setSearchPaneVisible(KeyEvent keyEvent) {
        if (searchBarField.getText().equals("")) {

            searchBarPane.setVisible(false);
            loadingSearchBar.setVisible(false);
            hideSearchPaneBtn.setVisible(false);
        } else {
            searchBarPane.setVisible(true);
            hideSearchPaneBtn.setVisible(false);
            loadingSearchBar.setVisible(true);


            new Thread(() -> {
                try {
                    HttpResponse<JsonNode> jsonNodeHttpResponse = Unirest.get("http://localhost:8080/search/" + searchBarField.getText()).asJson();
                    List<Song> songList = PublicController.getSongFromJson(jsonNodeHttpResponse);
                    JSONArray artistArray = new JSONArray(jsonNodeHttpResponse.getBody().getObject().get("artists").toString());
                    List<Artist> artistList = new ArrayList<>();
                    for (int i = 0; i < artistArray.length(); i++) {
                        JSONObject jsonobject = artistArray.getJSONObject(i);
                        Artist artist = new Gson().fromJson(String.valueOf(jsonobject), Artist.class);
                        artistList.add(artist);
                    }
                    JSONArray albumsArray = new JSONArray(jsonNodeHttpResponse.getBody().getObject().get("albums").toString());
                    List<Album> albumList = new ArrayList<>();
                    for (int i = 0; i < albumsArray.length(); i++) {
                        JSONObject jsonobject = albumsArray.getJSONObject(i);
                        Album album = new Gson().fromJson(String.valueOf(jsonobject), Album.class);
                        albumList.add(album);
                    }


                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {


                            addSong(songList);
                            addArtist(artistList);
                            addAlbum(albumList);
                            loadingSearchBar.setVisible(false);
                            hideSearchPaneBtn.setVisible(true);
                        }
                    });
                } catch (UnirestException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }


    }

    private void addAlbum(List<Album> albumList) {
        PublicController publicController = new PublicController();

        albumHbox.getChildren().clear();
        for (int i = 0; i < albumList.size(); i++) {
            Album album = albumList.get(i);
            AnchorPane anchorPane = publicController.albumItem(album.getId(), album.getImage(), album.getName());
            HBox.setMargin(anchorPane, new Insets(10, 10, 10, 10));
            anchorPane.setPadding(new Insets(0, 0, 0, 5));
            albumHbox.getChildren().add(anchorPane);
            if (i == 4) {
                break;
            }
        }
    }

    private void addSong(List<Song> songList) {
        PublicController publicController = new PublicController();
        songHbox.getChildren().clear();

        for (int i = 0; i < songList.size(); i++) {
            Song curr_song = songList.get(i);
            AnchorPane anchorPane = publicController.musicItem(curr_song.getId(), curr_song.getAlbum().getImage(), curr_song.getName(), curr_song.getArtists().get(0).getName(), curr_song.getFile(), "PLAY", searchBarAnchorPane, i, 260.0);

            HBox.setMargin(anchorPane, new Insets(10, 10, 10, 10));
            anchorPane.setPadding(new Insets(0, 0, 0, 5));
            songHbox.getChildren().add(anchorPane);
            if (i == 4) {
                break;
            }
        }

    }

    private void addArtist(List<Artist> artists) {
        PublicController publicController = new PublicController();
        artistHbox.getChildren().clear();
        for (int i = 0; i < artists.size(); i++) {
            Artist artist = artists.get(i);
            AnchorPane anchorPane = publicController.artistItem(artist.getId(), artist.getImage(), artist.getName());
            HBox.setMargin(anchorPane, new Insets(10, 10, 10, 10));
            anchorPane.setPadding(new Insets(0, 0, 0, 5));
            artistHbox.getChildren().add(anchorPane);
            if (i == 4) {
                break;
            }
        }
    }


    private void goToArtistPage(String id) {
        try {
            HttpResponse<JsonNode> jsonNodeHttpResponse = Unirest.get("http://localhost:8080/album/findAlbumById/" + id).asJson();
            FXMLLoader albumScreenFxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/UserScreen/ComponentScreen.fxml"));
            AnchorPane anchorPane = albumScreenFxmlLoader.load();
            ComponentScreenController albumController = albumScreenFxmlLoader.getController();
            albumController.mainScreenController = this;
            mainBoard.setContent(anchorPane);
            mainBoard.setVvalue(0);
            albumController.setImageBanner("http://localhost:8080/image/bannerAlbum.jpg");
            albumController.bannerTitle.setText("Album");
            albumController.setBanner(jsonNodeHttpResponse);

        } catch (UnirestException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void shuffeSong(ActionEvent actionEvent) {
        setEnableButton(actionEvent, enableShuffle);

    }

    public void repeatSong(ActionEvent actionEvent) {
        setEnableButton(actionEvent, enableRepeat);
    }

    public void setEnableButton(ActionEvent actionEvent, AtomicBoolean enableButton) {
        Button button = (Button) actionEvent.getSource();
        FontAwesomeIconView fontAwesomeIconView = (FontAwesomeIconView) button.getGraphic();
        if (!enableButton.get()) {
            enableButton.set(true);
            fontAwesomeIconView.setFill(Paint.valueOf("#3b75ff"));

        } else {
            enableButton.set(true);
            fontAwesomeIconView.setFill(Paint.valueOf("black"));
        }
    }

    public void getLyrics(ActionEvent actionEvent) {
        try {
            HttpResponse<JsonNode> httpResponse = Unirest.get("http://localhost:8080/song/findLyricsBySong/" + songs.get(songNumber).getId()).asJson();

            if (httpResponse.getBody().toString().equals("{}")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("No lyrics found for this song");
                alert.show();
                lyricScreenController = null;
            } else {
                JSONArray jsonArray = new JSONArray(httpResponse.getBody().getObject().get("lyrics").toString());
                List<Lyrics> lyrics = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonobject = jsonArray.getJSONObject(i);
                    Lyrics lyric = new Gson().fromJson(String.valueOf(jsonobject), Lyrics.class);
                    lyrics.add(lyric);
                }
                FXMLLoader lyricFxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/UserScreen/LyricScreen.fxml"));
                AnchorPane anchorPane = lyricFxmlLoader.load();
                lyricScreenController = lyricFxmlLoader.getController();
                lyricScreenController.songTitle.setText(songs.get(songNumber).getName());
                lyricScreenController.initLyrics(lyrics);
                mainBoard.setContent(anchorPane);
            }
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void closePriceBanner(ActionEvent actionEvent) {
        bannerPrice.setVisible(false);
    }
}
