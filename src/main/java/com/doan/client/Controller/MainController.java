/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.doan.client.Controller;


import com.doan.client.Model.User;

import com.jfoenix.controls.JFXSlider;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
public class MainController implements Initializable {

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
    public Button testBtn;
    public ToggleButton newsBtn;
    public ToggleButton likeBtn;
    public ToggleButton albumBtn;
    public ToggleButton downloadBtn;
    public ToggleButton followsBtn;
    public ToggleButton homeBtn;
    public AnchorPane homePane;
    public ProgressBar songProgressBar;
    public Slider volumeSlider;
    public Label testLabel;
    public AnchorPane musicBar;
    public File directory;
    public File[] files;
    public ArrayList<File> songs;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginPaneFromHome.setVisible(true);
        FXMLLoader loginFxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/LoginForm.fxml"));
        FXMLLoader homeFxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/Home.fxml"));

        try {
            //login
            AnchorPane newLoginPane = loginFxmlLoader.load();
            LoginFormController loginFormController = loginFxmlLoader.getController();
            loginPaneFromHome.getChildren().add(newLoginPane);
            loginFormController.mainController = this;
            // home
            homePane = homeFxmlLoader.load();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //avatar
        homeBtn.fire();
        setAvatarUser();
        setSliderVolume();

        initMedia();
        //set mainBoard

        mainBoard.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    }

    public void initMedia() {
        songs = new ArrayList<File>();
        directory = new File("src/main/resources/com/doan/client/Music");
        files = directory.listFiles();
        if (files != null) {
            songs.addAll(Arrays.asList(files));
        }
        media = new Media(songs.get(songNumber).toURI().toString());
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


        jfxProgressBar.setPrefWidth(452);
        jfxProgressBar.setLayoutY(64);
        jfxProgressBar.setLayoutX(240);
        jfxProgressBar.setBlockIncrement(10);

        jfxProgressBar.getStyleClass().add("timelineProgress");
        jfxProgressBar.setValue(0);
        jfxProgressBar.setMax(100);
        jfxProgressBar.setCursor(Cursor.HAND);
        musicBar.getChildren().add(jfxProgressBar);
        jfxProgressBar.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mediaPlayer.seek(Duration.seconds(jfxProgressBar.getValue()*0.01* media.getDuration().toSeconds()));
                System.out.println(jfxProgressBar.getValue());
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

    public void setUser(User user) {
        this.user = user;
        System.out.println(user.getImage());
        Image image = new Image(user.getImage());
        imageUser.setImage(image);
        System.out.println(image);
        login = true;
        logoutBtn.setDisable(false);
        FXMLLoader accountFxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/AccountSetting.fxml"));
        try {
            accountAnchorPane = accountFxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AccountSettingController accountSettingController = accountFxmlLoader.getController();
        accountSettingController.setUser(user);


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

    public void testVbox(MouseEvent mouseEvent) {
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
        if (toggleButton.getId().equals("homeBtn")) {
            mainBoard.setContent(homePane);
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
        songProgressBar.setProgress(0);
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
        mediaPlayer.pause();
        jfxProgressBar.setValue(0);
        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
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

                    jfxProgressBar.setValue(current / end *100);
                    if (current / end == 1) {
                        cancelTimer();
                        nextMediaBtn.fire();
                    }
                } catch (Exception e){
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
}
