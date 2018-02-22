package main.windows.passbookcreation;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import main.model.Passbook;
import main.windows.Windows;
import main.windows.mainwindow.MainWindow;

public class PassbookCreationController extends MainWindow {

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
                stageFactory.load(Windows.PASSBOOKLIST);

            }
        }
    }



}
