/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.doan.client.Controller;


import com.doan.client.Model.User;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
public class HomeController implements Initializable {

    public AnchorPane parentHomePane;

    public AnchorPane loginPaneFromHome;
    public User user= null;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginPaneFromHome.setVisible(true);
        FXMLLoader loginFxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/LoginForm.fxml"));
        try {
            AnchorPane newLoginPane = loginFxmlLoader.load();
            LoginFormController loginFormController = loginFxmlLoader.getController();
            loginPaneFromHome.getChildren().add(newLoginPane);
            loginFormController.homeController = this;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

        List<Toggle> toggleButtonList = toggleButton.getToggleGroup().getToggles();

        for (int i = 0; i < toggleButtonList.size(); i++) {
            ToggleButton indexToggleButton = (ToggleButton) toggleButtonList.get(i);
            if (indexToggleButton.isSelected()) {
                indexToggleButton.setStyle("-fx-background-color: #3b75ff; -fx-text-fill: white");
                try {
                    FontAwesomeIconView fontAwesomeIconView = (FontAwesomeIconView) indexToggleButton.getGraphic();
                    fontAwesomeIconView.setFill(Paint.valueOf("white"));
                } catch (Exception e) {

                    MaterialIconView fontAwesomeIconView = (MaterialIconView) indexToggleButton.getGraphic();
                    fontAwesomeIconView.setFill(Paint.valueOf("white"));
                }
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

    public void showAccountForm(MouseEvent mouseEvent) {
        accountSetting.setVisible(false);
        if (user==null){
            loginPaneFromHome.setVisible(true);

        }else{
            FXMLLoader loginFxmlLoader = new FXMLLoader(getClass().getResource("/com/doan/client/View/AccountSetting.fxml"));
            try {
                AnchorPane accountSetting = loginFxmlLoader.load();
//                LoginFormController loginFormController = loginFxmlLoader.getController();

                mainBoard.getChildren().setAll(accountSetting);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void logoutAccount(MouseEvent mouseEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure want to logout?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            user=null;
            imageUser.setImage(new Image("http://localhost:8080/image/anonymous.png"));
            logoutBtn.setDisable(true);
            accountSetting.setVisible(false);
            Alert logoutPopup = new Alert(Alert.AlertType.INFORMATION);
            logoutPopup.setHeaderText("Logout Success");
            logoutPopup.show();

        }


    }
}
