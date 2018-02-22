package main;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.windows.Windows;

public class MeinSparbuch extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{


        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("Icon.png")));
        primaryStage.setTitle("Mein Sparbuch");
        primaryStage.setResizable(false);
        StageFactory.setPrimaryStage(primaryStage);
        StageFactory stageFactory = new StageFactory();
        stageFactory.load(Windows.WELCOME);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
