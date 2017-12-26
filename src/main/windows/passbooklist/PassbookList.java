package main.windows.passbooklist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import main.Cost;
import main.Passbook;
import main.windows.menu.Menu;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class PassbookList extends Menu {

    public TextField income;
    public TextField savings;
    public TableColumn costNameCol;
    public TableColumn costAmountCol;
    public TableView<Cost> table;
    public TextField costNameInput;
    public TextField costAmountInput;
    public TextField editIncomeInput;

    @FXML
    void initialize(){
        refreshSavings(passbook);
        setSaveBtnStatus();
        fillPassbookList();
        costAmountCol.setOnEditCommit(getCostAmmountEventHandler());
        costNameCol.setOnEditCommit(getCostNameEventHandler());
    }

    public void setIncome(){
        DecimalFormat df = new DecimalFormat("#.##");
        income.setText(df.format(passbook.getMonthlyIncome()));
    }

    public void refreshSavings(Passbook passbook) {
        setIncome();
        Double totalCost = 0d;
        Double income = passbook.getMonthlyIncome();
        for (Cost cost : passbook.getCosts()) {
            totalCost += Double.valueOf(cost.getAmount());
        }
        if (income != null) {
            DecimalFormat df = new DecimalFormat("#.##");
            savings.setText(df.format(income - totalCost));
        }
    }

    public EventHandler<TableColumn.CellEditEvent<Cost, String>> getCostAmmountEventHandler() {
        return new EventHandler<TableColumn.CellEditEvent<Cost, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Cost, String> t) {
                Cost editedCost = ((Cost) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                );

                try {
                    String newCost = "" + Double.valueOf(t.getNewValue());
                    editedCost.setAmount(newCost);
                    saveCost(editedCost);
                    showSuccess("Kosten editiert.");
                } catch (Exception e) {
                    showError("Ungültige Eingabe.\nDer Wert wurde nicht editiert.");
                    fillPassbookList();
                }
            }
        };
    }

    public EventHandler<TableColumn.CellEditEvent<Cost, String>> getCostNameEventHandler() {
        return new EventHandler<TableColumn.CellEditEvent<Cost, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Cost, String> t) {
                Cost editedCost = ((Cost) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                );

                editedCost.setName(t.getNewValue());
                if (deleteCost(editedCost)) {
                    showWarn("Eintrag gelöscht!");
                    return;
                }
                saveCost(editedCost);
                showSuccess("Name editiert.");
            }
        };
    }

    private void saveCost(Cost editedCost){
        for (Cost cost : passbook.getCosts()) {
            if (cost.getId().equals(editedCost.getId())) {
                cost = editedCost;
            }
        }
        refreshSavings(passbook);
    }

    private boolean deleteCost(Cost costToDelete){

        if (Objects.equals(costToDelete.getName(), "")){
            List<Cost> list = passbook.getCosts();
            for (Iterator<Cost> iterator = list.iterator(); iterator.hasNext();) {
                Cost cost = iterator.next();
                if (cost.getId().equals(costToDelete.getId())) {
                    iterator.remove();
                }
            }
            refreshSavings(passbook);
            fillPassbookList();
            return true;
        }

        return false;
    }

    private void fillPassbookList() {
        costNameCol.setCellValueFactory(new PropertyValueFactory<Cost, String>("name"));
        costNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        costAmountCol.setCellValueFactory(new PropertyValueFactory<Cost, Double>("amount"));
        costAmountCol.setCellFactory(TextFieldTableCell.forTableColumn());
        final ObservableList<Cost> data = FXCollections.observableArrayList();
        if (passbook != null) {
            for (Cost cost : passbook.getCosts()) {
                data.add(cost);
            }
        }
        table.setItems(data);
    }

    public void addNewCost(ActionEvent event) {
        String nameInput = costNameInput.getText();
        String amountInput = costAmountInput.getText();
        Double amount = 0d;

        if (isValid(nameInput) && isValid(amountInput)) {
            amount = stringToDouble(amountInput);
            if (amount != null) {
                if (passbook != null) {
                    Cost cost = new Cost(passbook.getCosts().size(), false, nameInput, "" + amount);
                    passbook.getCosts().add(cost);
                    costAmountInput.setText("");
                    costNameInput.setText("");
                    fillPassbookList();
                    refreshSavings(passbook);
                } else {
                    showError(
                            "Kein Sparbuch geöffnet!\nErstellen Sie ein neues oder önnen sie ein vorhandenes");
                }
            }
        }
    }

    public void editIncome(ActionEvent event) {
        String newValue = editIncomeInput.getText();
        passbook.setMonthlyIncome(stringToDouble(newValue));
        editIncomeInput.setText("");
        refreshSavings(passbook);
    }
}
