package main.windows.passbookcreation;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import main.Passbook;
import main.windows.menu.Menu;

public class PassbookCreationController extends Menu {

    public TextField monthlyIncomeInput;


    public void createNewPassbook(ActionEvent event) {
        if (isValid(monthlyIncomeInput.getText())) {
            Double incomeValue = stringToDouble(monthlyIncomeInput.getText());
            if (incomeValue != null) {
                passbook = new Passbook();
                passbook.setMonthlyIncome(incomeValue);
                monthlyIncomeInput.setText("");
                saveAtBtn.setDisable(false);
                saveBtn.setDisable(true);
                clearStatus();
                stageFactory.loadPassbookList();

            }
        }
    }

    public void abort(ActionEvent event) {
        if (passbook == null) {
            stageFactory.loadWelcome();
        } else {
            stageFactory.loadPassbookList();
        }
        clearStatus();
    }

}
