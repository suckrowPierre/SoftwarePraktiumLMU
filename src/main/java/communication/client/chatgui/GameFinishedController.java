package communication.client.chatgui;

import communication.client.lobby.ConnectedClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

/** @author Sarah Controller for the window that shows the winner */
public class GameFinishedController {
    @FXML
    private Label winnername;
    @FXML
    private ImageView winnerimage;

    /** this method will always be called if the FXMLLoader loads a scene */
    public void initialize() {
        int winnerid = Model.getInstance().getWinnerid();
        ConnectedClient winnerclient = Model.getInstance().getMyClient().getLobbyHandler().getByID(winnerid);
        String name = winnerclient.getName();
        int robotid = winnerclient.getFigure();
        winnername.setText(name);
        winnerimage.setImage(new Image("/Robots/robot"+(++robotid)+".png"));
    }

}
