package com.example.final_coursework.controller;

import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.text.Text;

public class ReturnController {
    @FXML
    private JFXTreeTableView<?> tblOrderDetails;

    @FXML
    private TreeTableColumn<?, ?> colItemCode;

    @FXML
    private TreeTableColumn<?, ?> colDescription;

    @FXML
    private TreeTableColumn<?, ?> colQty;

    @FXML
    private TreeTableColumn<?, ?> colUnitPrice;

    @FXML
    private TreeTableColumn<?, ?> colDate;

    @FXML
    private TreeTableColumn<?, ?> colDiscount;

    @FXML
    private TreeTableColumn<?, ?> colType;

    @FXML
    private TreeTableColumn<?, ?> colSize;

    @FXML
    private TreeTableColumn<?, ?> colAmount;

    @FXML
    private TreeTableColumn<?, ?> colOption;

    @FXML
    private Text total;
    
    public void returnOnAction(ActionEvent actionEvent) {
    }

    public void ClearOnAction(ActionEvent actionEvent) {
    }

    public void qtyOnAction(ActionEvent actionEvent) {
    }

    public void updateOnAction(ActionEvent actionEvent) {
    }

    public void empIDOnAction(ActionEvent actionEvent) {
    }
}
