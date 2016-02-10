package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root, 300, 500);
        primaryStage.setTitle("The Galaxy Nexus 4s plus");
        primaryStage.setScene(scene);
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
