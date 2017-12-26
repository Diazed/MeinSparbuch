package main.windows.addcost;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.model.Cost;
import main.windows.mainwindow.MainWindow;

public class AddCost extends MainWindow {

    public Button abortButton;
    public TextField costIdentifierInput;
    public TextField costAmountInput;

    public void abortAddingCost(ActionEvent event){
        stageFactory.loadPassbookList();
    }

    public void addNewCost(ActionEvent event) {
        String nameInput = costIdentifierInput.getText();
        String amountInput = costAmountInput.getText();
        Double amount = 0d;

        if (isValid(nameInput) && isValid(amountInput)) {
            amount = stringToDouble(amountInput);
            if (amount != null) {
                if (passbook != null) {
                    Cost cost = new Cost(passbook.getCosts().size(), false, nameInput, "" + amount);
                    passbook.getCosts().add(cost);
                    costAmountInput.setText("");
                    costIdentifierInput.setText("");
                    stageFactory.loadPassbookList();
                } else {
                    showError("Kein Sparbuch geöffnet!\nErstellen Sie ein neues oder önnen sie ein vorhandenes");
                }
            }
        }

    }

}
