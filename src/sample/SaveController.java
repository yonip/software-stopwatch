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
    /**
     * the "default" background for the buttons. A nice light blue color
     */
    private Background defaultBackground = new Background(new BackgroundFill(Paint.valueOf("#6fa7da"), null, null));
    /**
     * the save button. When this button is pressed, {@link #savePressed(ActionEvent)} will be called to save the current
     * session
     */
    @FXML
    private Button save;
    /**
     * the date of the current session
     */
    @FXML
    private Text date;
    /**
     * the total time elapsed during this session
     */
    @FXML
    private Text time;
    /**
     * the list containing all the laps and their durations
     */
    @FXML
    private ListView<String> lapList;
    /**
     * the TextField where the user will input the name they want to give this session
     */
    @FXML
    private TextField sessionNameIn;
    /**
     * the TextArea where the user will input a description for the current session
     */
    @FXML
    private TextArea descriptionIn;

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
        lapList.setItems(Controller.laps); // get the laps from the controller for the main menu
        time.setText(Controller.timeText); // get the time from the controller for the main menu
        Date d = new Date();
        String dateStr = d.getMonth() + "/" + d.getDay() + "/" + d.getYear(); // get the current date
        date.setText(dateStr);
    }

    /**
     * Method to be called when the "save" button is pressed. <br/>
     * Currently only switches back to the main screen. In theory here the current session will be saved to the log and
     * only then will the main screen be reopened
     * @param event
     */
    public void savePressed(ActionEvent event) {
        Main.setMain();
    }

}
