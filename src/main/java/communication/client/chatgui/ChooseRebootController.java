package communication.client.chatgui;

import communication.client.data_handlers.GameDataOutHandeler;
import game.data.Orientations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import org.tinylog.Logger;
import util.LoggingTags;

/** @author Sarah Controller for the window to choose reboot direction */
public class ChooseRebootController {

    @FXML
    private ChoiceBox choicebox;
    @FXML
    private Button sendbtn;

    /**
     * this method will always be called if the FXMLLoader loads a scene
     */
    public void initialize() {
        ObservableList<Orientations> direction = FXCollections.observableArrayList();
        direction.addAll(Orientations.left, Orientations.right, Orientations.bottom, Orientations.top);
        choicebox.setItems(direction);

    }


    /**
     * this function is called if the Send-button is pressed when choosing the robot
     */
    @FXML
    public void sendbtnPress(ActionEvent actionEvent) {
        if (choicebox.getSelectionModel().getSelectedItem()==null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You need to choose a direction.");
            alert.showAndWait();
        } else {
            Orientations selection = (Orientations) choicebox.getSelectionModel().getSelectedItem();
            GameDataOutHandeler.getInstance().sendRebootDirection(selection);
            //stop showing the window
            Model.getInstance().setRebooting(false);
            Stage window = (Stage) sendbtn.getScene().getWindow();
            window.close();
            Logger.tag(LoggingTags.gui.toString()).info("Reboot direction window closed");
        }
    }

}
