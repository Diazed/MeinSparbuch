package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileHandler {

  private Stage stage;

  public FileHandler() {
    this.stage = new Stage();
    stage.getIcons().add(new Image(this.getClass().getResourceAsStream("Icon.png")));
  }

  public File saveFile(){
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
    fileChooser.getExtensionFilters().add(extFilter);
    return fileChooser.showSaveDialog(stage);
  }

  public File saveFile(String path) {
    File file = new File(path);
    return file;
  }

  public File openFile(){
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
    fileChooser.getExtensionFilters().add(extFilter);
    return fileChooser.showOpenDialog(stage);
  }

  public void writeXmlFile(File file, String xml){
    try {
      FileWriter fileWriter = null;
      fileWriter = new FileWriter(file);
      fileWriter.write(xml);
      fileWriter.close();
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    }
  }

  public String readFile(File file){
    StringBuilder stringBuffer = new StringBuilder();
    BufferedReader bufferedReader = null;

    try {

      bufferedReader = new BufferedReader(new FileReader(file));

      String text;
      while ((text = bufferedReader.readLine()) != null) {
        stringBuffer.append(text);
      }

    } catch (Exception ex) {
      System.out.println(ex);
    } finally {
      try {
        bufferedReader.close();
      } catch (IOException ex) {
        System.out.println(ex);
      }
    }

    return stringBuffer.toString();
  }

}
