/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.doan.client.Controller.UserScreen;


import com.doan.client.Controller.Component.LoginFormController;
import com.doan.client.Controller.PublicController;
import com.doan.client.Model.Song;
import com.doan.client.Model.User;

import com.jfoenix.controls.JFXSlider;

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
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;


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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginPaneFromHome.setVisible(true);
        FXMLLoader loginFxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/Component/LoginForm.fxml"));
        FXMLLoader homeFxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/UserScreen/HomeScreen.fxml"));
        FXMLLoader discoverFxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/UserScreen/DiscoverScreen.fxml"));
        songs = new ArrayList<Song>();
        try {
            //login
            AnchorPane newLoginPane = loginFxmlLoader.load();
            LoginFormController loginFormController = loginFxmlLoader.getController();
            loginPaneFromHome.getChildren().add(newLoginPane);
            loginFormController.mainController = this;
            // home
            homePane = homeFxmlLoader.load();
            homeScreenController = homeFxmlLoader.getController();
            homeScreenController.mainScreenController = this;
            homeScreenController.publicController.mainScreenController = this;
            homeScreenController.initFirstMedia();

            //discover
            discoverPane = discoverFxmlLoader.load();
            discoverController = discoverFxmlLoader.getController();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //avatar
        homeBtn.fire();
        offerBtn.fire();

        setAvatarUser();
        setSliderVolume();

        //set mainBoard

        mainBoard.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    }

    public void setMediaPlaying(Song song) {

        homeScreenController.currentLastListeningList.remove(song);
        homeScreenController.currentLastListeningList.add(0, song);
        homeScreenController.addChildrenToLastListening(homeScreenController.currentLastListeningList);
        convertMediaPlaying(song);

        for (Song songItem : songs) {
            if (songItem.getId().equals(song.getId())) {
                songs.remove(songItem);
                break;
            }
        }

        songs.add(song);
        songNumber = songs.size() - 1;

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

    public void pushScreen(ToggleButton toggleButton) {
        discoverButtonTab.setVisible(false);

        if (toggleButton.getId().equals("homeBtn")) {
            mainBoard.setContent(homePane);
        } else if (toggleButton.getId().equals("discoverBtn")) {
            mainBoard.setContent(discoverPane);
            discoverButtonTab.setVisible(true);

        }else if  (toggleButton.getId().equals("downloadBtn")){
            FXMLLoader loveFxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/UserScreen/DownloadScreen.fxml"));
            try {
                AnchorPane anchorPane = loveFxmlLoader.load();
                mainBoard.setContent(anchorPane);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }



        }else {
            if (!login) {
                loginPaneFromHome.setVisible(true);
            }
            else {
                if (toggleButton.getId().equals("likeBtn")) {
                    FXMLLoader loveFxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/UserScreen/LoveScreen.fxml"));
                    try {
                        AnchorPane anchorPane = loveFxmlLoader.load();
                        mainBoard.setContent(anchorPane);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } else if (toggleButton.getId().equals("albumBtn")) {


                } else if (toggleButton.getId().equals("followsBtn")) {


                }else{
                    FXMLLoader playlistFxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/UserScreen/PlaylistScreen.fxml"));
                    FXMLLoader editFxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/Component/EditPlaylistForm.fxml"));
                    try {
                        AnchorPane anchorPane = playlistFxmlLoader.load();

                        AnchorPane editPlaylistPane = editFxmlLoader.load();
                        parentHomePane.getChildren().add(editPlaylistPane);
                        editPlaylistPane.setVisible(false);

                        PlayListScreenController playListScreenController = playlistFxmlLoader.getController();
                        playListScreenController.editPlaylistFormController = editFxmlLoader.getController();
                        playListScreenController.editPlaylistPane = editPlaylistPane;
                        playListScreenController.setPlaylistName("Playlist # " + (count - 1));
                        mainBoard.setContent(anchorPane);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

    }

    public void showAccountForm(MouseEvent mouseEvent) {
        accountSetting.setVisible(false);
        if (user == null) {
            loginPaneFromHome.setVisible(true);

        } else {
            mainBoard.setContent(homePane);
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
        if (songNumber < songs.size() - 1) {
            songNumber++;

        } else {
            songNumber = 0;
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

        ToggleButton button = new ToggleButton();
        button.setText("Playlist # " + count);
        FontAwesomeIconView fontAwesomeIconView = new FontAwesomeIconView();
        fontAwesomeIconView.setGlyphName("CHECK");
        button.setGraphic(fontAwesomeIconView);
        button.getStyleClass().add("playlistBtn");
        VBox.setMargin(button, new Insets(10, 0, 0, 0));
        mainVbox.getChildren().add(button);
        button.setToggleGroup(Group1);
        count += 1;
        button.setOnAction(this::toggleBtn);
        button.setId("Playlist" + count);
        button.fire();

    }

    public void skipBack10Second(ActionEvent actionEvent) {
        double current = mediaPlayer.getCurrentTime().toSeconds() - 10;
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

    public void skipForward30Second(ActionEvent actionEvent) {
        double current = mediaPlayer.getCurrentTime().toSeconds() + 30;
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
}
