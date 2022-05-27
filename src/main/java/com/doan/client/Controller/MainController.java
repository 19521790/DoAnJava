/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.doan.client.Controller;


import com.doan.client.Model.User;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


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
    public AnchorPane mainBoard;
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
        setAvatarUser();
        homeBtn.fire();

    }
    public  void setAvatarUser(){
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
        if (!toggleButton.isSelected()){
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
            mainBoard.getChildren().setAll(homePane);
        }
    }

    public void showAccountForm(MouseEvent mouseEvent) {
        accountSetting.setVisible(false);
        if (user == null) {
            loginPaneFromHome.setVisible(true);

        } else {
            mainBoard.getChildren().setAll(accountAnchorPane);
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
}
