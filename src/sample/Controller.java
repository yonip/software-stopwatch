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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    // constants for converting the nanosecond timestamp to minutes seconds and huntreths
    public static final long HUNDRETHS = 10 * 1000000; // from nanos
    public static final long SECONDS = 100 * HUNDRETHS;
    public static final long MINUTES = 60 * SECONDS;

    /**
     * the button that starts and stops the timer
     */
    @FXML
    private Button startStop;
    /**
     * the background for the start button (a nice green)
     */
    private Background startBackground = new Background(new BackgroundFill(Paint.valueOf("#43D59C"), null, null));
    /**
     * the background for the stop button (a nice red)
     */
    private Background stopBackground = new Background(new BackgroundFill(Paint.valueOf("#DE6666"), null, null));
    /**
     * the background for the rest of the buttons (a nice blue)
     */
    private Background defaultBackground = new Background(new BackgroundFill(Paint.valueOf("#6fa7da"), null, null));
    /**
     * the button for starting the next lap if the timer is running, or to reset the timer and laps if the timer is not running
     */
    @FXML
    private Button lapReset;
    /**
     * the button for showing the previous sessions
     */
    @FXML
    private Button history;
    /**
     * the button for saving the current session
     */
    @FXML
    private Button save;
    /**
     * the Text to show the elapsed time in the current lap
     */
    @FXML
    private Text lapTime;
    /**
     * the Text to show the what lap the timer is on
     */
    @FXML
    private Text lapNum;
    /**
     * the Text to show the time elapsed during this session
     */
    @FXML
    private Text time;
    /**
     * a static variable set at the same time {@link #time} is set, to share with other views (like the save view)
     */
    protected static String timeText = "";
    /**
     * the ListView to show the previous laps and their times
     */
    @FXML
    private ListView<String> lapList;
    /**
     * a static variable to hold the previous laps and their times to share with other vies (like the save view)
     */
    protected static ObservableList<String> laps;

    /**
     * whether the timer was reset <br/>
     * sets the laps and total time and their offsets to be reinitialized
     */
    private boolean reseted;
    /**
     * whether the timer was started again <br/>
     * increases the magnitude of the lap and timer offsets so that the time they display is accurate
     */
    private boolean restarted;
    /**
     * the nanosecond timestamp given by the animation timer when the timer started after a reset
     */
    private long timeStarted;
    /**
     * if the lap button was just pressed. used to reset the lap start time and offset
     */
    private boolean relapped;
    /**
     * the amount of time elapsed since the beginning of this lap
     */
    private long timeLapped;
    /**
     * the amount of time since the start that the timer was stopped. used to keep {@link #time} correct
     */
    private long startOffset;
    /**
     * the amount of time since the start of this lap that the timer was stopped. used to keep {@link #lapTime} correct
     */
    private long lapOffset;
    /**
     * the last time the timer was running. used to set {@link #timeStopped} in the timer's stop method
     */
    private long lastNow;
    /**
     * the time the timer was stopped. used to keep offsets correct
     */
    private long timeStopped;

    /**
     * the timer used to keep the time
     */
    AnimationTimer timer;

    /**
     * a method to turn a timestamp to a time string
     * @param time the nanosecond timestamp to turn to a string
     * @return a string representing the given time
     */
    public static synchronized String timeToString(long time) {
        long minutes = ((time / MINUTES) % 10); // minutes will be truncated at 10 (to fit the text space)
        long seconds = ((time / SECONDS) % 60); // seconds will be truncated at 60 (because there are 60 seconds in a minute
        long hund = ((time / HUNDRETHS) % 100); // hundreths will be truncated at 100 (because there are 100 hundreths in a second)
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
        // set backgrounds for buttons
        lapReset.setBackground(defaultBackground);
        history.setBackground(defaultBackground);
        save.setBackground(defaultBackground);
        // initialize the laps
        laps = FXCollections.observableArrayList();
        lapList.setItems(laps);
        timer = new AnimationTimer() {

            @Override
            public void start() { // when the timer is restarted, update the offsets with the timestamp we stopped
                lapOffset += timeStopped;
                startOffset += timeStopped;
                restarted = true;
                super.start();
            }

            @Override
            public void handle(long now) {
                if (restarted) { // subtract the current time form the offsets to get the time elapsed while the timer was stopped
                    restarted = false;
                    lapOffset -= now;
                    startOffset -= now;
                }
                if (relapped) {  // reset the lap times
                    relapped = false;
                    timeLapped = now;
                    lapOffset = 0;
                }
                if (reseted) { // reset lap and start times
                    reseted = false;
                    timeStarted = now;
                    timeLapped = now;
                    timeStopped = now;
                    startOffset = 0;
                    lapOffset = 0;
                }
                time.setText(timeToString((now - timeStarted)+startOffset)); // set the text for the current time
                timeText = time.getText(); // keep the shared reference of the current time
                lapTime.setText(timeToString((now - timeLapped) + lapOffset)); // set the lap time
                lastNow = now; // remember when we last updated in case this is the last update before a stop
            }

            @Override
            public void stop() {
                timeStopped = lastNow; // set the time stopped to the last update
                super.stop();
            }
        };
        // make sure the initial state is fine
        stop();
        reset();
    }

    /**
     * calls {@link #start} or {@link #stop} depending on the text of the {@link #startStop}
     * @param event the event to trigger this method (ignored)
     */
    public void toggleStartStop(ActionEvent event) {
        if (startStop.getText().equalsIgnoreCase("start")) {
            start();
        } else {
            stop();
        }
    }

    /**
     * calls {@link #lap} or {@link #reset} depending on the text of the {@link #lapReset}
     * @param event the event to trigger this method (ignored)
     */
    public void lapResetPressed(ActionEvent event) {
        if (lapReset.getText().equalsIgnoreCase("lap")) {
            lap();
        } else {
            reset();
        }
    }

    /**
     * opens the history screen
     * @param event the event to trigger this method (ignored)
     */
    public void historyPressed(ActionEvent event) {
        // TODO open the history scene
    }

    /**
     * opens the save menu
     * @param event the event to trigger this method (ignored)
     */
    public void savePressed(ActionEvent event) {
        timeText = time.getText();
        Main.setSave();
    }

    /**
     * stops the timer and updates the buttons
     */
    private void stop() {
        startStop.setText("START");
        lapReset.setText("RESET");
        startStop.setBackground(startBackground);
        timer.stop();
    }

    /**
     * starts the timer and update sthe buttons
     */
    private void start() {
        startStop.setText("STOP");
        lapReset.setText("LAP");
        startStop.setBackground(stopBackground);
        timer.start();
    }

    /**
     * resets the timer, updates the buttons, text fields, list of laps and sets the timer to reset next iteration
     */
    private void reset() {
        reseted = true;
        time.setText("0:00.00");
        timeText = time.getText();
        lapTime.setText("0:00.00");
        laps.clear();
        lapNum.setText("lap 1");
        // gray out save
        // gray out this button
    }

    /**
     * updates the lap indicator at the top, adds the current lap to the top of the list and sets the timer to update lap time next iteration
     */
    private void lap() {
        relapped = true;
        // add things to the list here
        laps.add(0, lapNum.getText() + "   " + lapTime.getText());
        lapNum.setText("lap "+(laps.size()+1));
    }
}
