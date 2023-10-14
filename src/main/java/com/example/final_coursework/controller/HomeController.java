package com.example.final_coursework.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Label time;

    @FXML
    private Label date;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manageDateAndTime();
    }

    private void manageDateAndTime() {
        Timeline dateCon = new Timeline(new KeyFrame(Duration.ZERO,actionEvent -> date.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))),new KeyFrame(Duration.INDEFINITE));
        dateCon.setCycleCount(Animation.INDEFINITE);
        dateCon.play();

        Timeline timeCon = new Timeline(new KeyFrame(
                Duration.ZERO,
                actionEvent -> time.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
        ),new KeyFrame(Duration.seconds(1)));

        timeCon.setCycleCount(Animation.INDEFINITE);
        timeCon.play();
    }
}
