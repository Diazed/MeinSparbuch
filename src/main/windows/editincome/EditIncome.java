package main.windows.editincome;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import main.windows.mainwindow.MainWindow;



public class EditIncome extends MainWindow {

    public TextField editIncomeInput;

    public void editIncome(ActionEvent event) {
        Double newValue = stringToDouble(editIncomeInput.getText());
        if (newValue != null){
            passbook.setMonthlyIncome(newValue);
            stageFactory.loadPassbookList();
        }
        editIncomeInput.setText("");

    }

    public void abort(ActionEvent event){
        stageFactory.loadPassbookList();
    }

}
