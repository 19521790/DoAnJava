/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.doan.client.Controller;


import com.doan.client.Model.User;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * FXML Controller class
 *
 * @author Admin
 */
public class HomeController implements Initializable {

    @FXML
    public AnchorPane loginPanel;
    public AnchorPane asynchronousLogin;
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
    private void userLogin(MouseEvent event) throws IOException, UnirestException {
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
            loginPanel.setVisible(false);
        asynchronousLogin.setVisible(true);
            new Thread(() -> {
                HttpResponse<JsonNode> apiResponse = null;
                try {
                    apiResponse = Unirest.get("http://localhost:8080/findUserByNameAndPassword/"+ userName.getText() +"/" + passWord.getText()).asJson();
                    User user = new Gson().fromJson(apiResponse.getBody().toString(), User.class);
                    if (user.getName() != null){
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run() {
                                System.out.println("test");
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setHeaderText("Login Success");
                                alert.setContentText("Hello " + user.getDisplayName());
                                alert.show();
                                setVisibleLogin(false);
                            }

                        });
                    }else{
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                asynchronousLogin.setVisible(false);
                                loginPanel.setVisible(true);
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setHeaderText("User or password is wrong");
                                alert.show();
                            }
                        });
                    }
                } catch (UnirestException e) {
                    throw new RuntimeException(e);
                }

            }).start();
            System.out.println("after asynchronous");

        }
    }

    @FXML
    private void createAccount(MouseEvent event) {
    }

    @FXML
    private void googleLogin(MouseEvent event) {
    }

}
