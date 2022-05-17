/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.doan.client.Controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


/**
 * FXML Controller class
 *
 * @author Admin
 */
public class HomeController implements Initializable {

    @FXML
    private AnchorPane overlay;
    @FXML
    private AnchorPane loginForm;
    @FXML
    private TextField userName;
    @FXML
    private Label errorLogin;
    @FXML
    private PasswordField passWord;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setVisibleLogin(true);
    }

    @FXML
    private void hideOverlay(MouseEvent event) {
        setVisibleLogin(false);
    }

    @FXML
    private void resetPassWord(MouseEvent event) {
        System.out.println("hello world");
    }

    @FXML
    private void guestLogin(MouseEvent event) {
        setVisibleLogin(false);
    }

    public void setVisibleLogin(boolean x) {
        overlay.setVisible(x);
        loginForm.setVisible(x);
    }

    @FXML
    private void userLogin(MouseEvent event) throws IOException {
        if ("".equals(userName.getText()) && "".equals(passWord.getText())) {
            errorLogin.setStyle("-fx-text-fill: red");
            errorLogin.setText("Username and Password can't be empty");
        } else if ("".equals(userName.getText())) {
            errorLogin.setStyle("-fx-text-fill: red");
            errorLogin.setText("Username can't be empty");

        } else if ("".equals(passWord.getText())) {
            errorLogin.setStyle("-fx-text-fill: red");
            errorLogin.setText("Password can't be empty");

        } else {

        }
    }

    @FXML
    private void createAccount(MouseEvent event) {
    }

    @FXML
    private void googleLogin(MouseEvent event) {
    }

}
