package main.windows.menu;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import main.Passbook;
import main.PassbookService;
import main.StageFactory;

import java.util.Objects;

public class Menu {

    protected static Passbook passbook;

    private PassbookService passbookService = new PassbookService(this);
    protected StageFactory stageFactory = new StageFactory();

    public Label statusLabel;
    public Button openBtn;
    public Button newBtn;
    public MenuItem saveAtBtn;
    public MenuItem saveBtn;

    public static void setPassbook(Passbook passbook) {
        Menu.passbook = passbook;
    }

    public void savePassbook(ActionEvent event){
        passbookService.savePassbook(passbook);
    }

    public void createPassbook(ActionEvent event) {
        stageFactory.loadPassbookCreation();
    }

    public void savePassbookAt(ActionEvent event) {
        passbookService.savePassbookAt(passbook);
    }

    public void openPassbook(ActionEvent event) {
        Menu.setPassbook(passbookService.openPassbook());
        stageFactory.loadPassbookList();
        clearStatus();
    }

    protected void setSaveBtnStatus(){
        if (passbook.getSavePath() != null && !Objects.equals(passbook.getSavePath(), "")){
            saveBtn.setDisable(false);
        } else {
            saveBtn.setDisable(true);
        }
        if (passbook != null)
            saveAtBtn.setDisable(false);
    }

    protected Double stringToDouble(String stringToParse) {
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

    protected boolean isValid(String text) {
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

    protected void clearStatus() {
        statusLabel.setText("");
        statusLabel.setVisible(false);
    }

}
