package main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StageFactory {

    private static Stage primaryStage;

    public static void setPrimaryStage(Stage primaryStage) {
        StageFactory.primaryStage = primaryStage;
    }

    public void loadWelcome(){
        Parent root = getParent("welcome");
        primaryStage.setScene(new Scene(root, 400, 550));
    }

    public void loadPassbookCreation(){
        Parent root = getParent("passbookcreation");
        primaryStage.setScene(new Scene(root, 400, 550));
    }

    public void loadPassbookList(){
        Parent root = getParent("passbooklist");
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
