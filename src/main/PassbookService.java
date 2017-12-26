package main;

import main.windows.menu.Menu;

import java.io.File;
import java.util.Objects;

public class PassbookService {

    private Menu menu;
    private FileHandler fileHandler = new FileHandler();
    private PassbookXmlConverter passbookXmlConverter = new PassbookXmlConverter();

    public PassbookService(Menu menu) {
        this.menu = menu;
    }

    public void savePassbook(Passbook passbook) {
        if (passbook == null) {
            menu.showError("Kein Sparbuch zum speichern vorhanden.");
        } else {

            if (passbook.getSavePath() != null && !passbook.getSavePath().equals("")) {
                File file = fileHandler.saveFile(passbook.getSavePath());
                try {
                    passbook.setSavePath(file.getAbsolutePath());
                    fileHandler.writeXmlFile(file, passbookXmlConverter.toXml(passbook));
                    menu.showSuccess("Gespeichert!");
                    menu.saveAtBtn.setDisable(false);
                } catch (Exception e) {
                    menu.showError("Nicht gespeichert!");
                }
            } else {
                menu.showError("Kein Speicherort hinterlegt. Nutzen sie die \"Speichern unter\" funktion.");
            }
        }
    }

    public void savePassbookAt(Passbook passbook) {
        if (passbook == null) {
            menu.showError("Kein Sparbuch zum speichern vorhanden.");
        } else {
            File file = fileHandler.saveFile();
            try {
                passbook.setSavePath(file.getAbsolutePath());
                fileHandler.writeXmlFile(file, passbookXmlConverter.toXml(passbook));
                menu.showSuccess("Gespeichert!");
                menu.saveBtn.setDisable(false);
            } catch (Exception e) {
                menu.showError("Nicht gespeichert!");
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
            menu.showWarn("Es wurde keine Datei ausgewählt.");
        }
        return null;
    }




}
