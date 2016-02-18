package sample;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SaveController implements Initializable {
    public static final long HUNDRETHS = 10 * 1000000; // from nanos
    public static final long SECONDS = 100 * HUNDRETHS;
    public static final long MINUTES = 60 * SECONDS;

    private Background defaultBackground = new Background(new BackgroundFill(Paint.valueOf("#6fa7da"), null, null));
    @FXML
    private Button save;
    @FXML
    private ListView<String> lapList;
    private ObservableList<String> laps;

    public static synchronized String timeToString(long time) {
        long minutes = ((time / MINUTES) % 10);
        long seconds = ((time / SECONDS) % 60);
        long hund = ((time / HUNDRETHS) % 100);
        return minutes + ":" + ((seconds < 10) ? "0" : "") + seconds + "." + ((hund < 10) ? "0" : "") + hund;
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        save.setBackground(defaultBackground);
        laps = lapList.getItems();
    }

    public void savePressed(ActionEvent event) throws IOException {
        Stage s = (Stage) save.getScene().getWindow();
        s.setScene(new Scene(FXMLLoader.load(getClass().getResource("stopwatch-main.fxml"))));
        s.show();
    }

}
