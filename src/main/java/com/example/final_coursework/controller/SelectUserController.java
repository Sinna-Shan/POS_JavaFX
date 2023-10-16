package com.example.final_coursework.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class SelectUserController {

    @FXML
    private AnchorPane paneSelectUser;
    public void userOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)paneSelectUser.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/userLogin.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void adminOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)paneSelectUser.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/adminLogin.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
