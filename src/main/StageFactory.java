package main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.windows.Windows;

import java.io.IOException;

public class StageFactory {

    private static Stage primaryStage;

    public static void setPrimaryStage(Stage primaryStage) {
        StageFactory.primaryStage = primaryStage;
    }

    public void load(Windows window){
        Parent root = getParent(window.name().toLowerCase());
        primaryStage.setScene(new Scene(root, 400, 550));
    }

    private Parent getParent(String resource) {
        try {
            return FXMLLoader.load(getClass().getResource("scenes/" + resource + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
