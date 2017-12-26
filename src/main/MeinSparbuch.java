package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MeinSparbuch extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{


        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("Icon.png")));
        primaryStage.setTitle("Mein Sparbuch");
        primaryStage.setResizable(false);
        StageFactory.setPrimaryStage(primaryStage);
        StageFactory stageFactory = new StageFactory();
        stageFactory.loadWelcome();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
