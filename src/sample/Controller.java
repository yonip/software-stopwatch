package sample;

import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

public class Controller implements Initializable {
    public static final long HUNDRETHS = 10 * 1000000; // from nanos
    public static final long SECONDS = 100 * HUNDRETHS;
    public static final long MINUTES = 60 * SECONDS;

    @FXML
    private Button startStop;
    private Background startBackground = new Background(new BackgroundFill(Paint.valueOf("#43D59C"), null, null));
    private Background stopBackground = new Background(new BackgroundFill(Paint.valueOf("#DE6666"), null, null));
    private Background defaultBackground = new Background(new BackgroundFill(Paint.valueOf("#6fa7da"), null, null));
    @FXML
    private Button lapReset;
    @FXML
    private Button history;
    @FXML
    private Button save;
    @FXML
    private Text lapName;
    @FXML
    private Text lapNum;
    @FXML
    private Text time;
    protected static String timeText = "";
    @FXML
    private ListView<String> lapList;
    protected static ObservableList<String> laps;

    private boolean reseted;
    private boolean restarted;
    private long timeStarted;
    private boolean relapped;
    private long timeLapped;
    private long startOffset;
    private long lapOffset;
    private long lastNow;
    private long timeStopped;

    AnimationTimer timer;

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
        lapReset.setBackground(defaultBackground);
        history.setBackground(defaultBackground);
        save.setBackground(defaultBackground);
        laps = FXCollections.observableArrayList();
        lapList.setItems(laps);
        timer = new AnimationTimer() {

            @Override
            public void start() {
                lapOffset += timeStopped;
                startOffset += timeStopped;
                restarted = true;
                super.start();
            }

            @Override
            public void handle(long now) {
                if (restarted) {
                    restarted = false;
                    lapOffset -= now;
                    startOffset -= now;
                }
                if (relapped) {
                    relapped = false;
                    timeLapped = now;
                    lapOffset = 0;
                }
                if (reseted) {
                    reseted = false;
                    timeStarted = now;
                    timeLapped = now;
                    timeStopped = now;
                    startOffset = 0;
                    lapOffset = 0;
                }
                time.setText(timeToString((now - timeStarted)+startOffset));
                timeText = time.getText();
                lapName.setText(timeToString((now - timeLapped) + lapOffset));
                lastNow = now;
            }

            @Override
            public void stop() {
                timeStopped = lastNow;
                super.stop();
            }
        };
        stop();
        reset();
    }

    public void toggleStartStop(ActionEvent event) {
        if (startStop.getText().equalsIgnoreCase("start")) {
            start();
        } else {
            stop();
        }
    }

    public void lapResetPressed(ActionEvent event) {
        if (lapReset.getText().equalsIgnoreCase("lap")) {
            lap();
        } else {
            reset();
        }
    }

    public void historyPressed(ActionEvent event) {
        // open the history scene
    }

    public void savePressed(ActionEvent event) throws IOException {
        // open the save menu
        timeText = time.getText();
        Main.setSave();
    }

    private void stop() {
        startStop.setText("START");
        lapReset.setText("RESET");
        startStop.setBackground(startBackground);
        timer.stop();
    }

    private void start() {
        startStop.setText("STOP");
        lapReset.setText("LAP");
        startStop.setBackground(stopBackground);
        timer.start();
    }

    private void reset() {
        reseted = true;
        time.setText("0:00.00");
        timeText = time.getText();
        lapName.setText("0:00.00");
        laps.clear();
        lapNum.setText("lap 1");
        // gray out save
        // gray out this button
    }

    private void lap() {
        relapped = true;
        // add things to the list here
        laps.add(0, lapNum.getText() + "   " + lapName.getText());
        lapNum.setText("lap "+(laps.size()+1));
    }
}
