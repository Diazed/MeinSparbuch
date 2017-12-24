package main;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Controller {

  public Button openBtn;
  public Button newBtn;
  public MenuItem saveAtBtn;
  public MenuItem saveBtn;
  public Label statusLabel;
  public Label openLbl;
  public Label newLbl;
  public TextField costNameInput;
  public TextField costAmountInput;
  public TextField editIncomeInput;
  public TextField savings;
  public TextField income;
  public Pane passbookPane;
  public Pane passbookCreationPane;
  public TextField monthlyIncomeInput;
  public Label welcomeLbl;
  public TableColumn costNameCol;
  public TableColumn costAmountCol;
  public TableView<Cost> table;
  PassbookService passbookService = new PassbookService(this);
  private Passbook passbook;

  public void savePassbook(ActionEvent event) {
    passbookService.savePassbook(passbook);
  }

  public void savePassbookAt(ActionEvent event) {
    passbookService.savePassbookAt(passbook);
  }

  public void openPassbook(ActionEvent event) {
    this.passbook = passbookService.openPassbook();
    showPassbookList();
    refreshSavings(passbook);
    clearStatus();
  }

  public void createNewPassbook(ActionEvent event) {
    this.passbook = passbookService.createNewPassbook();
  }

  public void abort(ActionEvent event) {
    if (passbook == null) {
      passbookPane.setVisible(false);
      passbookCreationPane.setVisible(false);
      welcomeLbl.setVisible(true);
      newBtn.setVisible(true);
      openBtn.setVisible(true);
      newLbl.setVisible(true);
      openLbl.setVisible(true);
      clearStatus();
    } else {
      showPassbookList();
    }
    clearStatus();
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

  public EventHandler<CellEditEvent<Cost, String>> getCostAmmountEventHandler() {
    return new EventHandler<CellEditEvent<Cost, String>>() {
      @Override
      public void handle(CellEditEvent<Cost, String> t) {
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

  public EventHandler<CellEditEvent<Cost, String>> getCostNameEventHandler() {
    return new EventHandler<CellEditEvent<Cost, String>>() {
      @Override
      public void handle(CellEditEvent<Cost, String> t) {
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

  public void setIncome(Double value){
    DecimalFormat df = new DecimalFormat("#.##");
    income.setText(df.format(value));
  }

  public void showPassbookList() {
    fillPassbookList();
    passbookPane.setVisible(true);
    passbookCreationPane.setVisible(false);
    welcomeLbl.setVisible(false);
    newBtn.setVisible(false);
    newLbl.setVisible(false);
    openBtn.setVisible(false);
    openLbl.setVisible(false);
    costAmountCol.setOnEditCommit(getCostAmmountEventHandler());
    costNameCol.setOnEditCommit(getCostNameEventHandler());
  }

  public void editIncome(ActionEvent event) {
    String newValue = editIncomeInput.getText();
    passbook.setMonthlyIncome(stringToDouble(newValue));
    editIncomeInput.setText("");
    refreshSavings(passbook);
  }

  public void showNewPassbookMask(ActionEvent event) {
    welcomeLbl.setVisible(false);
    passbookPane.setVisible(false);
    newBtn.setVisible(false);
    openBtn.setVisible(false);
    openLbl.setVisible(false);
    newLbl.setVisible(false);
    passbookCreationPane.setVisible(true);
    clearStatus();
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

  public void refreshSavings(Passbook passbook) {
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

  public Double stringToDouble(String stringToParse) {
    Double result = 0d;
    try {
      result = Double.valueOf(stringToParse);
    } catch (Exception e) {
      showError(
          "Kosten sollten als Ziffer angegeben werden.\nKommerstellen folgen nach einem . nicht , !");
      return null;
    }
    clearStatus();
    return result;
  }

  public boolean isValid(String text) {
    if (text.isEmpty()) {
      showError("Bitte füllen Sie alle Felder aus.");
      return false;
    } else {
      return true;
    }
  }

  public void showError(String text) {
    statusLabel.setText(text);
    statusLabel.setTextFill(Color.RED);
    statusLabel.setVisible(true);
  }

  public void showWarn(String text) {
    statusLabel.setText(text);
    statusLabel.setTextFill(Color.GOLD);
    statusLabel.setVisible(true);
  }

  public void showSuccess(String text) {
    statusLabel.setText(text);
    statusLabel.setTextFill(Color.GREEN);
    statusLabel.setVisible(true);
  }

  public void clearStatus() {
    statusLabel.setText("");
    statusLabel.setVisible(false);
  }

}

