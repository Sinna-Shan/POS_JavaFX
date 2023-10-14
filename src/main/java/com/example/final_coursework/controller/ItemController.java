package com.example.final_coursework.controller;

import com.example.final_coursework.db.DBConnection;
import com.example.final_coursework.model.Item;
import com.example.final_coursework.model.tm.ItemSupplier;
import com.example.final_coursework.model.tm.ItemTM;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ItemController implements Initializable {
    @FXML
    private Text lblItemCode;

    @FXML
    private JFXTextField inpSearch;

    @FXML
    private JFXComboBox<String> combType;

    @FXML
    private JFXTextField ItemDescInp;

    @FXML
    private JFXTextField itemQtyInp;

    @FXML
    private JFXTextField addQtyInp;

    @FXML
    private JFXTextField sellingPriceInp;

    @FXML
    private JFXTextField profitInp;

    @FXML
    private JFXTextField buyingPriceInp;

    @FXML
    private JFXTreeTableView<ItemTM> tblItems;

    @FXML
    private TreeTableColumn colItemCode;

    @FXML
    private TreeTableColumn colSupId;

    @FXML
    private TreeTableColumn colDesc;

    @FXML
    private TreeTableColumn colBuyPrice;

    @FXML
    private TreeTableColumn colSellPrice;

    @FXML
    private TreeTableColumn colType;

    @FXML
    private TreeTableColumn colSize;

    @FXML
    private TreeTableColumn colQty;

    @FXML
    private TreeTableColumn colProfit;

    @FXML
    private TreeTableColumn colOption;

    @FXML
    private JFXComboBox<String> combSupId;

    @FXML
    private JFXComboBox<String> combSize;

    public void itemSizeComboAction(ActionEvent actionEvent) {
    }

    public void supNameComboAction(ActionEvent actionEvent) {
    }

    public void supIDComboAction(ActionEvent actionEvent) {
    }

    public void addToStockOnAction(ActionEvent actionEvent) throws SQLException {
        Item item = new Item(
                lblItemCode.getText(),
                combSupId.getValue().toString(),
                ItemDescInp.getText(),
                Integer.parseInt(itemQtyInp.getText()),
                Double.parseDouble(buyingPriceInp.getText()),
                Double.parseDouble(sellingPriceInp.getText()),
                combType.getValue().toString(),
                combSize.getValue().toString(),
                Double.parseDouble(profitInp.getText())
        );

        ItemSupplier supplierItem = new ItemSupplier(
                lblItemCode.getText(),
                combSupId.getValue().toString()
        );
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO item VALUES (?,?,?,?,?,?,?,?,?)");
            pstm.setString(1,item.getItemCode());
            pstm.setString(2,item.getSupplierID());
            pstm.setString(3,item.getDescription());
            pstm.setInt(4,item.getQty());
            pstm.setDouble(5,item.getBuyPrice());
            pstm.setDouble(6,item.getSellPrice());
            pstm.setString(7,item.getType());
            pstm.setString(8,item.getSize());
            pstm.setDouble(9,item.getProfit());

            if(pstm.executeUpdate()>0){
                new Alert(Alert.AlertType.INFORMATION,"Item Add Successfully !").show();
                connection.commit();
            }
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            connection.rollback();
            throw new RuntimeException(e);
        }finally {
            connection.setAutoCommit(true);
        }
    }

    private void clearFields() {
        combSupId.setValue("");
        ItemDescInp.clear();
        itemQtyInp.clear();
        buyingPriceInp.clear();
        sellingPriceInp.clear();
        combType.setValue("");
        combSize.setValue("");
        profitInp.clear();
    }

    public void updateOnAction(ActionEvent actionEvent) {
    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearFields();
        genID();
    }

    public void printOnAction(ActionEvent actionEvent) {
    }

    public void searchOnAction(ActionEvent actionEvent) {
    }

    public void itemTypeComboAction(ActionEvent actionEvent) {
    }

    public void itemCodeComboAction(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemCode"));
        colSupId.setCellValueFactory(new TreeItemPropertyValueFactory<>("supplierID"));
        colDesc.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colBuyPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("buyPrice"));
        colSellPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("sellPrice"));
        colType.setCellValueFactory(new TreeItemPropertyValueFactory<>("type"));
        colSize.setCellValueFactory(new TreeItemPropertyValueFactory<>("size"));
        colProfit.setCellValueFactory(new TreeItemPropertyValueFactory<>("profit"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btnDelete"));

        tblItems.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue != null){
                setData(newValue);
            }
        });


        inpSearch.textProperty().addListener((observableValue, s, t1) -> {
            tblItems.setPredicate(new Predicate<TreeItem<ItemTM>>() {
                @Override
                public boolean test(TreeItem<ItemTM> itemTMTreeItem) {
                    boolean flag = itemTMTreeItem.getValue().getItemCode().contains(t1) ||
                            itemTMTreeItem.getValue().getDescription().contains(t1);
                    return flag;
                }
            });
        });

        loadType();
        loadSize();
        loadSupId();
        genID();
        loadItemTable();
    }

    private void setData(TreeItem<ItemTM> newValue) {
        lblItemCode.setText(newValue.getValue().getItemCode());
        combSupId.setValue(newValue.getValue().getSupplierID());
        ItemDescInp.setText(newValue.getValue().getDescription());
        itemQtyInp.setText(String.valueOf(newValue.getValue().getQty()));
        buyingPriceInp.setText(String.valueOf(newValue.getValue().getBuyPrice()));
        sellingPriceInp.setText(String.valueOf(newValue.getValue().getSellPrice()));
        combType.setValue(newValue.getValue().getType());
        combSize.setValue(newValue.getValue().getSize());
        profitInp.setText(String.valueOf(newValue.getValue().getProfit()));
    }

    private void loadItemTable() {
        ObservableList<ItemTM> itemTMS = FXCollections.observableArrayList();
        try {
            ArrayList<Item> items = new ArrayList<>();
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM item");
            ResultSet rst = pstm.executeQuery();

            while(rst.next()){
                items.add(new Item(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getInt(4),
                        rst.getDouble(5),
                        rst.getDouble(6),
                        rst.getString(7),
                        rst.getString(8),
                        rst.getDouble(9)
                ));
            }
            for (Item item:items) {
                JFXButton btn = new JFXButton("Delete");
                btn.setStyle("-fx-background-color: #3F1D38; -fx-text-fill: #fff");
                btn.setPrefSize(100,50);

                btn.setOnAction(actionEvent -> {
                    try {
                        PreparedStatement pstm1 = connection.prepareStatement("DELETE FROM item WHERE item_code=?");
                        pstm1.setString(1,item.getItemCode());
                        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete item " + item.getItemCode(), ButtonType.YES, ButtonType.NO).showAndWait();
                        if(buttonType.get()== ButtonType.YES){
                            if(pstm1.executeUpdate()>0){
                                new Alert(Alert.AlertType.INFORMATION,"Deletion Successful..!").show();
                                loadItemTable();
                            }

                        }
                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR,"Deletion Failed..!").show();
                        throw new RuntimeException(e);
                    }
                });

                itemTMS.add(new ItemTM(
                        item.getItemCode(),
                        item.getSupplierID(),
                        item.getDescription(),
                        item.getQty(),
                        item.getBuyPrice(),
                        item.getSellPrice(),
                        item.getType(),
                        item.getSize(),
                        item.getProfit(),
                        btn
                ));

                TreeItem<ItemTM> treeItem = new RecursiveTreeItem<>(itemTMS, RecursiveTreeObject::getChildren);
                tblItems.setRoot(treeItem);
                tblItems.setShowRoot(false);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void genID() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT item_code FROM item ORDER BY item_code DESC LIMIT 1");
            ResultSet rst = pstm.executeQuery();
            if(rst.next()){
                int itmCode = Integer.parseInt(rst.getString(1).split("_")[1]);
                itmCode++;
                lblItemCode.setText(String.format("itm_%04d",itmCode));
            }else{
                lblItemCode.setText("itm_0001");
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSupId() {
        try {
            ObservableList<String> supIds = FXCollections.observableArrayList();
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT id FROM supplier");
            ResultSet rst = pstm.executeQuery();
            while(rst.next()){
                supIds.add(rst.getString(1));
            }
            combSupId.setItems(supIds);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSize() {
        ObservableList<String> sizes = FXCollections.observableArrayList();
        sizes.addAll("XXL","XL","L","M","S","XS");
        combSize.setItems(sizes);
    }

    private void loadType() {
        ObservableList<String> types = FXCollections.observableArrayList();
        types.addAll("Ladies","Gents","Kids");
        combType.setItems(types);
    }
}
