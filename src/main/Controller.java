package main;

import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Controller {

  public Label statusLabel;
  public TextField costNameInput;
  public TextField costAmountInput;
  public TextField editIncomeInput;
  public TextField savings;
  public Pane passbookPane;
  public Pane passbookCreationPane;
  public TextField monthlyIncomeInput;
  public Label welcomeLbl;
  public TableColumn costNameCol;
  public TableColumn costAmountCol;
  public TableView table;
  private Passbook passbook;
  private PassbookXml passbookXml = new PassbookXml();
  private FileHandler fileHandler = new FileHandler();


  public Controller() {

  }

  public void savePassbook(ActionEvent event) {
    if (passbook == null) {
      showError("Kein Sparbuch zum speichern vorhanden.");
    } else {
      File file;
      if (passbook.getSavePath() != null && !passbook.getSavePath().equals("")) {
        file = fileHandler.saveFile(passbook.getSavePath());
      } else {
        file = fileHandler.saveFile();
        passbook.setSavePath(file.getAbsolutePath());
      }
      if (file != null) {
        try {
          fileHandler.writeXmlFile(file, passbookXml.toXml(passbook));
          showSuccess("Gespeichert!");
        } catch (Exception e) {
          showError("Es ist ein Fehler aufgetreten.");
        }
      }
    }
  }

  public void openPassbook(ActionEvent event) {
    File file = fileHandler.openFile();
    if (file != null) {
      String xml = fileHandler.readFile(file);
      passbook = passbookXml.toPassbook(xml);
      showPassbookList();
      refreshSavings(passbook);
    } else {
      showError("Es wurde keine Datei zum öffnen Gewählt");
    }
  }


  public void createNewPassbook(ActionEvent event) {
    if (isValid(monthlyIncomeInput.getText())) {
      Double income = stringToDouble(monthlyIncomeInput.getText());
      if (income != null) {
        passbook = new Passbook();
        passbook.setMonthlyIncome(income);
        monthlyIncomeInput.setText("");
        clearStatus();
        showPassbookList();
      }
    }
  }

  public void abort(ActionEvent event) {
    if (passbook == null) {
      passbookPane.setVisible(false);
      passbookCreationPane.setVisible(false);
      welcomeLbl.setVisible(true);
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

  public EventHandler<CellEditEvent<Cost, String>> getCostAmmountEventHandler(){
    return new EventHandler<CellEditEvent<Cost, String>>() {
      @Override
      public void handle(CellEditEvent<Cost, String> t) {
        Cost editedCost = ((Cost) t.getTableView().getItems().get(
            t.getTablePosition().getRow())
        );

        try{
          Double newCost = Double.valueOf(t.getNewValue());

          editedCost.setAmount(t.getNewValue());
          for (Cost cost : passbook.getCosts()) {
            if (cost.getId().equals(editedCost.getId())) {
              cost = editedCost;
            }
          }
          refreshSavings(passbook);
          showSuccess("Kosten editiert.");
        } catch (Exception e){
          showError("Ungültige Eingabe.\nDer Wert wurde nicht editiert.");
        }
      }
    };
  }

  public EventHandler<CellEditEvent<Cost, String>> getCostNameEventHandler(){
    return new EventHandler<CellEditEvent<Cost, String>>() {
      @Override
      public void handle(CellEditEvent<Cost, String> t) {
        Cost editedCost = ((Cost) t.getTableView().getItems().get(
            t.getTablePosition().getRow())
        );

        editedCost.setName(t.getNewValue());
        for (Cost cost : passbook.getCosts()) {
          if (cost.getId().equals(editedCost.getId())) {
            cost = editedCost;
          }
        }
        showSuccess("Name editiert.");
      }
    };
  }

  private void showPassbookList() {
    fillPassbookList();
    passbookPane.setVisible(true);
    passbookCreationPane.setVisible(false);
    welcomeLbl.setVisible(false);
    costAmountCol.setOnEditCommit(getCostAmmountEventHandler());
    costNameCol.setOnEditCommit(getCostNameEventHandler());
  }

  public void editIncome(ActionEvent event){
    String newValue = editIncomeInput.getText();
    passbook.setMonthlyIncome(stringToDouble(newValue));
    editIncomeInput.setText("");
    refreshSavings(passbook);
  }

  public void showNewPassbookMask(ActionEvent event) {
    welcomeLbl.setVisible(false);
    passbookPane.setVisible(false);
    passbookCreationPane.setVisible(true);
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

  private void refreshSavings(Passbook passbook) {
    Double totalCost = 0d;
    Double income = passbook.getMonthlyIncome();
    for (Cost cost : passbook.getCosts()) {
      totalCost += Double.valueOf(cost.getAmount());
    }
    if (income != null)
      savings.setText("" + (income - totalCost) + "€");

  }

  private Double stringToDouble(String stringToParse) {
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

  private boolean isValid(String text) {
    if (text.isEmpty()) {
      showError("Bitte füllen Sie alle Felder aus.");
      return false;
    } else {
      return true;
    }
  }

  private void showError(String text) {
    statusLabel.setText(text);
    statusLabel.setTextFill(Color.RED);
    statusLabel.setVisible(true);
  }

  private void showSuccess(String text) {
    statusLabel.setText(text);
    statusLabel.setTextFill(Color.GREEN);
    statusLabel.setVisible(true);
  }

  private void clearStatus() {
    statusLabel.setText("");
    statusLabel.setVisible(false);
  }

}

