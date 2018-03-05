package main.windows.addcost;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import main.model.Cost;
import main.windows.Windows;
import main.windows.mainwindow.MainWindow;

public class AddCost extends MainWindow {

    public Button abortButton;
    public TextField costIdentifierInput;
    public TextField costAmountInput;
    public RadioButton isIncome;
    public RadioButton isOutcome;

    public ToggleGroup toggleGroup = new ToggleGroup();

    @FXML
    void initialize() {

        isOutcome.setToggleGroup(toggleGroup);
        isIncome.setToggleGroup(toggleGroup);

        isOutcome.isSelected();

    }

    public void addNewCost(ActionEvent event) {
        String nameInput = costIdentifierInput.getText();
        String amountInput = costAmountInput.getText();
        Double amount = 0d;

        if (isValid(nameInput) && isValid(amountInput)) {
            amount = stringToDouble(amountInput);
            if (amount != null) {
                if (passbook != null) {

                    Cost cost = new Cost(passbook.getCosts().size(), false, nameInput, "");

                    if (isOutcome.isSelected()){
                        if (amount >= 0){
                            amount = amount * -1;
                        }
                        cost.setCostIsIncome(false);
                    } else if (isIncome.isSelected()){
                        if (amount < 0){
                            amount = amount * -1;
                        }
                        cost.setCostIsIncome(true);
                    } else {
                        showError("Keine Angeben zum Typ.");
                    }
                    cost.setAmount("" + amount);
                    passbook.getCosts().add(cost);
                    costAmountInput.setText("");
                    costIdentifierInput.setText("");
                    stageFactory.load(Windows.PASSBOOKLIST);
                } else {
                    showError("Kein Sparbuch geöffnet!\nErstellen Sie ein neues oder önnen sie ein vorhandenes");
                }
            }
        }

    }

}
