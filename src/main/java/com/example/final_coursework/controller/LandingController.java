package com.example.final_coursework.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LandingController implements Initializable {
    @FXML
    private BorderPane borderPane;

    @FXML
    private Text time;

    @FXML
    private Text date;

    public void homeAction(ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/home.fxml"));
        borderPane.setCenter(pane);

    }
    public void placeOrderAction(ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/placeOrder.fxml"));
        borderPane.setCenter(pane);
    }

    public void itemAction(ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/item.fxml"));
        borderPane.setCenter(pane);
    }

    public void supplierAction(ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/supplier.fxml"));
        borderPane.setCenter(pane);
    }

    public void orderAction(ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/orderDetails.fxml"));
        borderPane.setCenter(pane);
    }

    public void returnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/returns.fxml"));
        borderPane.setCenter(pane);
    }

    public void logoutAction(ActionEvent actionEvent) {
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to log out?", ButtonType.YES, ButtonType.NO).showAndWait();
        if(buttonType.get().equals(ButtonType.YES)){
            Stage stage = (Stage)borderPane.getScene().getWindow();
            try {
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/selectUser.fxml"))));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AnchorPane pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("/view/home.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        borderPane.setCenter(pane);

    }


}
