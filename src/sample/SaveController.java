package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.awt.im.spi.InputMethod;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class SaveController implements Initializable {
    public static final long HUNDRETHS = 10 * 1000000; // from nanos
    public static final long SECONDS = 100 * HUNDRETHS;
    public static final long MINUTES = 60 * SECONDS;

    private Background defaultBackground = new Background(new BackgroundFill(Paint.valueOf("#6fa7da"), null, null));
    @FXML
    private Button save;
    @FXML
    private Text date;
    @FXML
    private Text time;
    @FXML
    private ListView<String> lapList;
    @FXML
    private TextField sessionNameIn;
    @FXML
    private TextArea descriptionIn;

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
        lapList.setItems(Controller.laps);
        time.setText(Controller.timeText);
        Date d = new Date();
        String dateStr = d.getMonth() + "/" + d.getDay() + "/" + d.getYear();
        date.setText(dateStr);
    }

    public void savePressed(ActionEvent event) {
        Main.setMain();
    }

    public void textInput(ActionEvent event) {
        System.out.println(event);
        if (event.getSource() != sessionNameIn && event.getSource() != descriptionIn) {
            return;
        }
    }

}
