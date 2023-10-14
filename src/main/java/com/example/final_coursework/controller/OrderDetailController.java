package com.example.final_coursework.controller;

import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeTableColumn;

public class OrderDetailController {

    @FXML
    private JFXTreeTableView<?> tblOrderDetails;

    @FXML
    private TreeTableColumn<?, ?> colOrderId;

    @FXML
    private TreeTableColumn<?, ?> colDate;

    @FXML
    private TreeTableColumn<?, ?> colTotal;

    @FXML
    private TreeTableColumn<?, ?> colCustName;

    @FXML
    private TreeTableColumn<?, ?> colContact;

    @FXML
    private TreeTableColumn<?, ?> colEmail;

    @FXML
    private TreeTableColumn<?, ?> colEmployee;

    @FXML
    private TreeTableColumn<?, ?> colarrears;

    @FXML
    private JFXTreeTableView<?> tblOrderItem;

    @FXML
    private TreeTableColumn<?, ?> colItemCode;

    @FXML
    private TreeTableColumn<?, ?> colDesc;

    @FXML
    private TreeTableColumn<?, ?> colQty;

    @FXML
    private TreeTableColumn<?, ?> colUnitPrice;

    @FXML
    private TreeTableColumn<?, ?> date;

    @FXML
    private TreeTableColumn<?, ?> colDiscount;

    @FXML
    private TreeTableColumn<?, ?> colType;

    @FXML
    private TreeTableColumn<?, ?> colSize;

    @FXML
    private TreeTableColumn<?, ?> colAmount;


    public void refreshOnAction(ActionEvent actionEvent) {
    }

    public void searchOnAction(ActionEvent actionEvent) {
    }
}
