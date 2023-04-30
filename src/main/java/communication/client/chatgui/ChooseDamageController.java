package communication.client.chatgui;

import communication.client.data_handlers.GameDataOutHandeler;
import game.data.Orientations;
import game.data.cards.CardName;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.tinylog.Logger;
import util.LoggingTags;

import java.util.ArrayList;

/** @author Sarah Controller for the window to choose damage cards */
public class ChooseDamageController {

    @FXML
    private VBox dropdownvbox;
    @FXML
    private Button sendbtn;
    private ArrayList<ChoiceBox> choiceboxes = new ArrayList<ChoiceBox>();

    /**
     * this method will always be called if the FXMLLoader loads a scene
     */
    public void initialize() {
        int count = Model.getInstance().getNumberofcards();
        ObservableList<CardName> piles = Model.getInstance().getAvailablePiles();
        for(int i = 0; i<count; i++){
            ChoiceBox<CardName> cb = new ChoiceBox<CardName>();
            cb.setItems(piles);
            dropdownvbox.getChildren().add(cb);
            choiceboxes.add(cb);
        }
    }


    /**
     * this function is called if the Send-button is pressed when choosing the robot
     */
    @FXML
    public void sendbtnpress(ActionEvent actionEvent) {
        boolean allselected = true;
        for (ChoiceBox cb: choiceboxes){
            if (cb.getSelectionModel().getSelectedItem()==null){
                allselected = false;
            }
        }
        if (!allselected) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You need to choose a card.");
            alert.showAndWait();
        } else {
            //send selection
            CardName[] cards = new CardName[Model.getInstance().getNumberofcards()];
            int i = 0;
            for (ChoiceBox cb: choiceboxes){
                CardName card = (CardName) cb.getSelectionModel().getSelectedItem();
                cards[i] = card;
                i++;
            }
            GameDataOutHandeler.getInstance().sendSelectedDamage(cards);
            //stop showing the window
            Model.getInstance().setChoosedamage(false, 0 , null);
            Stage window = (Stage) sendbtn.getScene().getWindow();
            window.close();
            Logger.tag(LoggingTags.gui.toString()).info("Choose damage window closed");
        }
    }

}
