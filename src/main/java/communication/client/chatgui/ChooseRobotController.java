package communication.client.chatgui;

import communication.client.data_handlers.GameDataOutHandeler;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import communication.client.*;
import org.tinylog.Logger;
import util.LoggingTags;

import java.io.IOException;

/**
 * @author Sarah, Tea Controller for the window that lets you choose your robot and name
 */
public class ChooseRobotController {
    @FXML
    private TextField usernametext;
    @FXML
    private Button gobutton;
    @FXML
    private Label roboterrortext;
    @FXML
    private ToggleGroup robotbuttongroup;
    private GuiUserClient client1;
    private int robotId;
    private String username;
    private BooleanProperty loadlobby;
    private StringProperty errormessage;

    /**
     * this method will always be called if the FXMLLoader loads a scene
     */
    public void initialize() {
        // Bindings
        usernametext.textProperty().bindBidirectional(Model.getInstance().getusername());
        client1 = Model.getInstance().getMyClient();
        errormessage = LobbyModel.getInstance().getErrormessage();
        loadlobby = LobbyModel.getInstance().getLoadlobby();

        putChangeListeners();

    }


    /**
     * this function is called if the Go-button is pressed when choosing the robot
     */
    @FXML
    public void gobtnPress(ActionEvent actionEvent) {
        // no empty inputs
        if (Model.getInstance().getusername().isEmpty().get() || robotbuttongroup.getSelectedToggle() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please choose a robot and a username");
            alert.showAndWait();
        } else {
            username = Model.getInstance().getusername().get();
            ToggleButton selectedToggleButton = (ToggleButton) robotbuttongroup.getSelectedToggle();
            String buttonnumber = selectedToggleButton.getId().replaceAll("[^0-9]+", "");
            robotId = (Integer.parseInt(buttonnumber))-1;
            GameDataOutHandeler.getInstance().sendUsernameAndRobot(username, robotId);
        }
    }

    private void chooseRobotSuccessful(){
        LobbyModel.getInstance().setPlayername(robotId, username);
        Model.getInstance().setMyrobotid(robotId);
        loadLobbyandChat();
    }

    private void setErrormessage(String message){
        roboterrortext.setText(message);
    }

    private void loadLobbyandChat(){
        Parent root;
        Parent chatroot;
        try {
            //show the Lobby
            root = FXMLLoader.load(getClass().getResource("/lobby.fxml"));
            Stage stage = (Stage) gobutton.getScene().getWindow();
            stage.setTitle("Roborally Lobby");
            stage.setScene(new Scene(root));
            stage.show();
            Logger.tag(LoggingTags.gui.toString()).info("Loading GUI window for lobby");
            //show the Chatroom
            chatroot = FXMLLoader.load(getClass().getResource("/chatroom.fxml"));
            Stage chatStage = new Stage();
            chatStage.setTitle("Roborally Chatroom for " + Model.getInstance().getusername().get());
            chatStage.setScene(new Scene(chatroot));
            chatStage.show();
            Logger.tag(LoggingTags.gui.toString()).info("Loading GUI window Chatroom");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void putChangeListeners(){
        loadlobby.addListener((observableValue, aBoolean, t1) -> {
            if (t1){
                chooseRobotSuccessful();
            }
        });

        errormessage.addListener((observableValue, s, t1) -> {
            if (!t1.equals("Ok")){
                setErrormessage(t1);
            }
        });

    }

}
