package com.example.final_coursework.controller;

import com.example.final_coursework.db.DBConnection;
import com.example.final_coursework.entity.Supplier;
import com.example.final_coursework.entity.SupItem;
import com.example.final_coursework.dto.tm.SupItemTM;
import com.example.final_coursework.dto.tm.SupplierTM;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class SupplierController implements Initializable {

    @FXML
    private JFXTextField inpName;

    @FXML
    private JFXTextField inpContact;

    @FXML
    private JFXTextField inpCompany;

    @FXML
    private JFXTextField inpSearch;

    @FXML
    private JFXTreeTableView<SupplierTM> tblSupplier;

    @FXML
    private TreeTableColumn colSuplierID;

    @FXML
    private TreeTableColumn colTitle;

    @FXML
    private TreeTableColumn colName;

    @FXML
    private TreeTableColumn colCompany;

    @FXML
    private TreeTableColumn colContact;

    @FXML
    private TreeTableColumn colOption;

    @FXML
    private JFXComboBox<String> titleCombo;

    @FXML
    private Text supId;

    @FXML
    private JFXTreeTableView<SupItemTM> tblItem;

    @FXML
    private TreeTableColumn colItemCode;

    @FXML
    private TreeTableColumn colDescription;

    @FXML
    private TreeTableColumn colQty;


    public void searchOnAction(ActionEvent actionEvent) {
    }

    public void printOnAction(ActionEvent actionEvent) {
    }

    public void clearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void updateOnAction(ActionEvent actionEvent) {
        try {
            Supplier supplier = new Supplier(supId.getText(),titleCombo.getValue().toString(),inpName.getText(),inpContact.getText(),inpCompany.getText());
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("UPDATE supplier SET title=?, name=?, phone=?, company=? WHERE id=?");
            pstm.setString(1,supplier.getTitle());
            pstm.setString(2,supplier.getName());
            pstm.setString(3,supplier.getNumber());
            pstm.setString(4,supplier.getCompany());
            pstm.setString(5,supplier.getId());

            if(pstm.executeUpdate()>0){
                new Alert(Alert.AlertType.INFORMATION,"Supplier Updated !").show();
                loadTable();
                clearFields();
                genId();
            }

        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Update faild, Something went wrong !").show();
            throw new RuntimeException(e);
        }
    }

    public void supIDComboAction(ActionEvent actionEvent) {
    }

    public void addOnAction(ActionEvent actionEvent) {
        Supplier supplier = new Supplier(supId.getText(),titleCombo.getValue().toString(),inpName.getText(),inpContact.getText(),inpCompany.getText());
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("Insert into supplier values(?,?,?,?,?)");
            pstm.setString(1,supplier.getId());
            pstm.setString(2,supplier.getTitle());
            pstm.setString(3,supplier.getName());
            pstm.setString(4, supplier.getNumber());
            pstm.setString(5, supplier.getCompany());

            if(pstm.executeUpdate()>0){
                new Alert(Alert.AlertType.INFORMATION,"Supplier Saved !").show();
                clearFields();
                genId();
                loadTable();
            }

        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Something went wrong !").show();
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        inpCompany.clear();
        inpName.clear();
        inpContact.clear();
    }

    public void combTitleOnAction(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSuplierID.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new TreeItemPropertyValueFactory<>("title"));
        colName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new TreeItemPropertyValueFactory<>("number"));
        colCompany.setCellValueFactory(new TreeItemPropertyValueFactory<>("company"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btnDelete"));

        colItemCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
            genId();
            loadTitle();
            loadTable();

        tblSupplier.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue != null){
                setData(newValue);
                loadItemTable(newValue);
            }
        });

        inpSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                tblSupplier.setPredicate(new Predicate<TreeItem<SupplierTM>>() {
                    @Override
                    public boolean test(TreeItem<SupplierTM> supplierTMTreeItem) {
                        boolean flag = supplierTMTreeItem.getValue().getId().contains(newValue) ||
                                supplierTMTreeItem.getValue().getName().contains(newValue);
                        return flag;
                    }
                });
            }
        });
    }

    private void loadItemTable(TreeItem<SupplierTM> newValue) {
        ObservableList<SupItemTM> supItemTMS = FXCollections.observableArrayList();
        try {
            ArrayList<SupItem> supItems = new ArrayList<>();
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT item_code,description,qty FROM item WHERE sup_id=?");
            pstm.setString(1,newValue.getValue().getId());
            ResultSet rst = pstm.executeQuery();

            while (rst.next()){
                supItems.add(new SupItem(
                   rst.getString(1),
                   rst.getString(2),
                   rst.getInt(3)
                ));
            }

            for (SupItem supItem :supItems) {
                supItemTMS.add( new SupItemTM(
                        supItem.getItemCode(),
                        supItem.getDescription(),
                        supItem.getQty()
                        )
                );
            }

            TreeItem<SupItemTM> treeItem = new RecursiveTreeItem<>(supItemTMS, RecursiveTreeObject::getChildren);
            tblItem.setRoot(treeItem);
            tblItem.setShowRoot(false);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void setData(TreeItem<SupplierTM> value) {
        supId.setText(value.getValue().getId());
        inpName.setText(value.getValue().getName());
        inpContact.setText(value.getValue().getNumber());
        inpCompany.setText(value.getValue().getCompany());
    }

    private void loadTable() {
        ObservableList<SupplierTM> supplierTMS = FXCollections.observableArrayList();
        try {
            List<Supplier> suppliers = new ArrayList<>();
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM supplier");
            ResultSet rst = pstm.executeQuery();
            while (rst.next()){
                suppliers.add( new Supplier(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5)
                ));
            }

            for (Supplier supplier: suppliers) {
                JFXButton btn = new JFXButton("Delete");
                btn.setStyle("-fx-background-color: #3F1D38; -fx-text-fill: #fff");
                btn.setPrefSize(100,50);

                btn.setOnAction(actionEvent -> {
                    try {
                        PreparedStatement pst = connection.prepareStatement("DELETE FROM supplier WHERE id=?");
                        pst.setString(1,supplier.getId());
                        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete supplier " + supplier.getId(), ButtonType.YES, ButtonType.NO).showAndWait();
                        if(buttonType.get()==ButtonType.YES){
                            if(pst.executeUpdate()>0){
                                new Alert(Alert.AlertType.INFORMATION,"Deletion Successful..!").show();
                                loadTable();
                            }

                        }
                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR,"Deletion Failed..!").show();
                        throw new RuntimeException(e);
                    }
                });

                supplierTMS.add(new SupplierTM(
                        supplier.getId(),
                        supplier.getTitle(),
                        supplier.getName(),
                        supplier.getNumber(),
                        supplier.getCompany(),
                        btn
                ));
            }

            TreeItem<SupplierTM> treeItem = new RecursiveTreeItem<>(supplierTMS, RecursiveTreeObject::getChildren);
            tblSupplier.setRoot(treeItem);
            tblSupplier.setShowRoot(false);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTitle() {
        ObservableList<String> titles = FXCollections.observableArrayList();
        titles.addAll("Mr.","Ms.","Mrs.","Miss");
        titleCombo.setItems(titles);
    }

    private void genId(){
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT id FROM supplier ORDER BY id DESC LIMIT 1");
            ResultSet rst = pstm.executeQuery();

            if(rst.next()){
                int num = Integer.parseInt(rst.getString(1).split("_")[1]);
                num++;
                supId.setText(String.format("sup_%04d",num));

            }else{  
                supId.setText("sup_0001");
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
