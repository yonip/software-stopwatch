package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    public static ObservableList<ObservableList<String>> list;
    private static URL main;
    private static Scene mainScene;
    private static URL save;
    private static Scene saveScene;
    private static URL hist;
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        main = getClass().getResource("stopwatch-main.fxml");
        mainScene = new Scene(FXMLLoader.load(main));
        save = getClass().getResource("stopwatch-save.fxml");
        saveScene = new Scene(FXMLLoader.load(save));
        stage.setTitle("The Galaxy Nexus 4s plus 9001");
        stage.setScene(mainScene);
        stage.setMaxHeight(500);
        stage.setMaxWidth(300);
        stage.setMinHeight(500);
        stage.setMinWidth(300);
        stage.show();
    }

    public static void setSave() {
        try {
            saveScene = new Scene(FXMLLoader.load(save));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(saveScene);
        stage.show();
    }

    public static void setMain() {
        stage.close();
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
