package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Scene main;
    public static Scene save;
    public static Scene hist;

    @Override
    public void start(Stage primaryStage) throws Exception{
        main = new Scene(FXMLLoader.load(getClass().getResource("stopwatch-main.fxml")));
        save = new Scene(FXMLLoader.load(getClass().getResource("stopwatch-save.fxml")));
        primaryStage.setTitle("The Galaxy Nexus 4s plus 9001");
        primaryStage.setScene(main);
        primaryStage.setMaxHeight(500);
        primaryStage.setMaxWidth(300);
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(300);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
