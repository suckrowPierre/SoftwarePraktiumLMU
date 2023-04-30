package communication.server.servergui;

import communication.client.chatgui.Model;
import communication.server.Server;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/** @author controller for the server startwindow */
public class StartServerController {
    @FXML
    private ChoiceBox<Integer> playerchoicebox;
    @FXML
    private TextField portnumbertext;
    @FXML
    private Button startbtn;

    /**
     * this method will always be called if the FXMLLoader loads a scene
     */
    public void initialize() {
        // Bindings
        portnumbertext.textProperty().bindBidirectional(GuiModelServer.getInstance().getPortnumber());
        playerchoicebox.setItems(FXCollections.observableArrayList(2,3,4,5,6));

    }

    @FXML
    private void startserver(ActionEvent event) throws IOException {
        if (GuiModelServer.getInstance().getPortnumber().isEmpty().get()
                || playerchoicebox.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "One or more inputs are empty");
            alert.showAndWait();
        }
        else {
            int port = Integer.parseInt(GuiModelServer.getInstance().getPortnumber().get());
            int minPlayer = playerchoicebox.getSelectionModel().getSelectedItem();
            Stage stagetoclose = (Stage) startbtn.getScene().getWindow();
            stagetoclose.close();
            new Server(port,minPlayer);
        }
    }

}
