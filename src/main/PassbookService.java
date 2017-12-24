package main;

import java.io.File;
import java.util.Objects;

public class PassbookService {

    private Controller controller;
    private FileHandler fileHandler = new FileHandler();
    private PassbookXmlConverter passbookXmlConverter = new PassbookXmlConverter();

    public PassbookService(Controller controller) {
        this.controller = controller;
    }

    public void savePassbook(Passbook passbook) {
        if (passbook == null) {
            controller.showError("Kein Sparbuch zum speichern vorhanden.");
        } else {

            if (passbook.getSavePath() != null && !passbook.getSavePath().equals("")) {
                File file = fileHandler.saveFile(passbook.getSavePath());
                try {
                    passbook.setSavePath(file.getAbsolutePath());
                    fileHandler.writeXmlFile(file, passbookXmlConverter.toXml(passbook));
                    controller.showSuccess("Gespeichert!");
                    controller.saveAtBtn.setDisable(false);
                } catch (Exception e) {
                    controller.showError("Nicht gespeichert!");
                }
            } else {
                controller.showError("Kein Speicherort hinterlegt. Nutzen sie die \"Speichern unter\" funktion.");
            }
        }
    }

    public void savePassbookAt(Passbook passbook) {
        if (passbook == null) {
            controller.showError("Kein Sparbuch zum speichern vorhanden.");
        } else {
            File file = fileHandler.saveFile();
            try {
                passbook.setSavePath(file.getAbsolutePath());
                fileHandler.writeXmlFile(file, passbookXmlConverter.toXml(passbook));
                controller.showSuccess("Gespeichert!");
                controller.saveBtn.setDisable(false);
            } catch (Exception e) {
                controller.showError("Nicht gespeichert!");
            }
        }
    }

    public Passbook openPassbook() {
        File file = fileHandler.openFile();
        if (file != null) {
            String xml = fileHandler.readFile(file);
            Passbook passbook = passbookXmlConverter.toPassbook(xml);
            passbook.setSavePath(file.getAbsolutePath());
            controller.setIncome(passbook.getMonthlyIncome());
            if (passbook.getSavePath() != null && !Objects.equals(passbook.getSavePath(), "")){
                controller.saveBtn.setDisable(false);
            } else {
                controller.saveBtn.setDisable(true);
            }

            controller.saveAtBtn.setDisable(false);
            return passbook;
        } else {
            controller.showWarn("Es wurde keine Datei ausgewählt.");
        }
        return null;
    }


    public Passbook createNewPassbook() {
        if (controller.isValid(controller.monthlyIncomeInput.getText())) {
            Double incomeValue = controller.stringToDouble(controller.monthlyIncomeInput.getText());
            if (incomeValue != null) {
                Passbook passbook = new Passbook();
                passbook.setMonthlyIncome(incomeValue);
                controller.monthlyIncomeInput.setText("");
                controller.saveAtBtn.setDisable(false);
                controller.saveBtn.setDisable(true);
                controller.setIncome(incomeValue);
                controller.refreshSavings(passbook);
                controller.clearStatus();
                controller.showPassbookList();
                return passbook;
            }
        }
        return null;
    }

}
