package communication.client.chatgui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import communication.client.*;
import org.tinylog.Logger;
import util.LoggingTags;

import java.io.IOException;

/**
 * @author Sarah, Tea Controller for the login window
 */
public class LoginController {
    @FXML
    private TextField iptext, porttext, version;
    @FXML
    private Button btn;
    @FXML
    private Label errortext;
    @FXML
    private CheckBox aicheckbox;
    private boolean servererror = false;
    private GuiUserClient client1;

    /**
     * this method will always be called if the FXMLLoader loads a scene
     */
    public void initialize() {
        // Bindings
        iptext.textProperty().bindBidirectional(Model.getInstance().getip());
        porttext.textProperty().bindBidirectional(Model.getInstance().getport());

        setupInput();
    }

    /**
     * handles enter key in username TextField
     */
    private void setupInput() {
        porttext.setOnKeyPressed(
                keyEvent -> {
                    if (keyEvent.getCode() == KeyCode.ENTER) {
                        btnPress(new ActionEvent(porttext, null));
                    }
                });
    }

    /**
     * this function is called if the Connect-button is pressed
     */
    @FXML
    public void btnPress(ActionEvent actionEvent) {
        // no empty inputs
        servererror = false;
        if (Model.getInstance().getip().isEmpty().get()
                || Model.getInstance().getport().isEmpty().get()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Input is empty");
            alert.showAndWait();
        } else {
            String ip = Model.getInstance().getip().get();
            int port = Integer.parseInt(Model.getInstance().getport().get());
            try {
                if(aicheckbox.isSelected()){
                    createAI(ip, port);
                }
                else {
                    client1 = new GuiUserClient(ip, port, version.getText());
                }
            } catch (Exception e) {
                Logger.tag(LoggingTags.gui.toString()).error(e.getMessage());
                errortext.setText("Server not available.");
                servererror = true;
            }
            if (!servererror&&!(aicheckbox.isSelected())) { // connection to server worked
                Model.getInstance().setMyClient(client1);
                client1.addDataModel(Model.getInstance());
                Parent root;
                try {
                    // load Robot Chooser
                    root = FXMLLoader.load(getClass().getResource("/chooserobot.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Roborally");
                    stage.setScene(new Scene(root));
                    stage.show();
                    Logger.tag(LoggingTags.gui.toString()).info("Loading GUI window to choose robot");
                    // get a handle to the old stage and close it
                    Stage stagetoclose = (Stage) btn.getScene().getWindow();
                    stagetoclose.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(aicheckbox.isSelected()){
                Stage stagetoclose = (Stage) btn.getScene().getWindow();
                stagetoclose.close();
            }

        }
    }

    private void createAI(String ip, int port) throws Exception{
        System.out.println("creating AI");
        new AIClient(ip, port, version.getText());
        Model.getInstance().setAI();

    }


}
