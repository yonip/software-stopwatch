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
    /**
     * a list to preserve past sessions to be displayed in the history menu
     */
    public static ObservableList<ObservableList<String>> list;
    /**
     * the url to the main menu's FXML file
     */
    private static URL main;
    /**
     * a reference to the main menu scene
     */
    private static Scene mainScene;
    /**
     * the url to the save menu's FXML file
     */
    private static URL save;
    /**
     * a reference to the save scene
     */
    private static Scene saveScene;
    /**
     * the url to the history menu's FXML file (once it is created)
     */
    private static URL hist;
    /**
     * a reference to the primary stage
     */
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage; // keep a reference of the stage for later
        // load the scenes
        main = getClass().getResource("stopwatch-main.fxml");
        mainScene = new Scene(FXMLLoader.load(main));
        save = getClass().getResource("stopwatch-save.fxml");
        saveScene = new Scene(FXMLLoader.load(save));

        // configure the stage
        stage.setTitle("The Galaxy Nexus 4s plus 9001");
        stage.setScene(mainScene);
        stage.setMaxHeight(500);
        stage.setMaxWidth(300);
        stage.setMinHeight(500);
        stage.setMinWidth(300);
        stage.show();
    }

    /**
     * sets the stage to a new save scene (save scene is reloaded from the FXML to ensure that the state is updated)
     */
    public static void setSave() {
        try {
            saveScene = new Scene(FXMLLoader.load(save));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(saveScene);
        stage.show();
    }

    /**
     * sets the stage to the original main menu. the state of the main menu is maintained by its internal timer
     */
    public static void setMain() {
        stage.close();
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
