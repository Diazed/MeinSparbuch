package main.windows.editincome;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import main.windows.Windows;
import main.windows.mainwindow.MainWindow;



public class EditIncome extends MainWindow {

    public TextField editIncomeInput;

    public void editIncome(ActionEvent event) {
        Double newValue = stringToDouble(editIncomeInput.getText());
        if (newValue != null){
            passbook.setMonthlyIncome(newValue);
            stageFactory.load(Windows.PASSBOOKLIST);
        }
        editIncomeInput.setText("");

    }

}
