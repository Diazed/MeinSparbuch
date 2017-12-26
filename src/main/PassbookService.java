package main;

import main.model.Passbook;
import main.windows.mainwindow.MainWindow;

import java.io.File;

public class PassbookService {

    private MainWindow mainWindow;
    private FileHandler fileHandler = new FileHandler();
    private PassbookXmlConverter passbookXmlConverter = new PassbookXmlConverter();

    public PassbookService(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void savePassbook(Passbook passbook) {
        if (passbook == null) {
            mainWindow.showError("Kein Sparbuch zum speichern vorhanden.");
        } else {

            if (passbook.getSavePath() != null && !passbook.getSavePath().equals("")) {
                File file = fileHandler.saveFile(passbook.getSavePath());
                try {
                    passbook.setSavePath(file.getAbsolutePath());
                    fileHandler.writeXmlFile(file, passbookXmlConverter.toXml(passbook));
                    mainWindow.showSuccess("Gespeichert!");
                    mainWindow.saveAtBtn.setDisable(false);
                } catch (Exception e) {
                    mainWindow.showError("Nicht gespeichert!");
                }
            } else {
                mainWindow.showError("Kein Speicherort hinterlegt. Nutzen sie die \"Speichern unter\" funktion.");
            }
        }
    }

    public void savePassbookAt(Passbook passbook) {
        if (passbook == null) {
            mainWindow.showError("Kein Sparbuch zum speichern vorhanden.");
        } else {
            File file = fileHandler.saveFile();
            try {
                passbook.setSavePath(file.getAbsolutePath());
                fileHandler.writeXmlFile(file, passbookXmlConverter.toXml(passbook));
                mainWindow.showSuccess("Gespeichert!");
                mainWindow.saveBtn.setDisable(false);
            } catch (Exception e) {
                mainWindow.showError("Nicht gespeichert!");
            }
        }
    }

    public Passbook openPassbook() {
        File file = fileHandler.openFile();
        if (file != null) {
            String xml = fileHandler.readFile(file);
            Passbook passbook = passbookXmlConverter.toPassbook(xml);
            passbook.setSavePath(file.getAbsolutePath());

            return passbook;
        } else {
            mainWindow.showWarn("Es wurde keine Datei ausgewählt.");
        }
        return null;
    }




}
