package communication.client.chatgui;

import communication.client.upgradeshop.UpgradeShop;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.tinylog.Logger;
import util.LoggingTags;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Sarah, Tea Controller for the upgrade shop
 */
public class UpgradeShopController {
    @FXML
    private Label cubetext;
    @FXML
    private Button skipbtn, buybtn;
    @FXML
    private ToggleButton card0, card1, card2, card3, card4, card5;
    @FXML
    private ToggleGroup buttongroup;
    private ArrayList<ToggleButton> buttons;

    /**
     * this method will always be called if the FXMLLoader loads a scene
     */
    public void initialize() {
        UpgradeModel.getInstance().setController(this);
        cubetext.textProperty().bind(UpgradeModel.getInstance().getEnergypoints().asString());
        buttons = new ArrayList<ToggleButton>(Arrays.asList(card0, card1, card2, card3, card4, card5));
        showcardimages();
        skipbtn.setTooltip(new Tooltip("If you don't want to buy any \nupgrades this round, click here."));

        //closing window equals skip button
        Platform.runLater(()->{
            Stage stage = (Stage) skipbtn.getScene().getWindow();
            stage.setOnCloseRequest(event -> {
                closeShop(null);
                event.consume();
            });
        });

        //listens if shop needs to be closed
        BooleanProperty closeShop = UpgradeModel.getInstance().getUpgradePurchaseTurn();
        closeShop.addListener((new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if (!(Boolean) t1) {
                    Stage stage = (Stage) skipbtn.getScene().getWindow();
                    stage.close();
                    Logger.tag(LoggingTags.gui.toString()).info("GUI window Upgrade Shop closed");
                }
            }
        }));
    }

    private void showcardimages() {
        int index = 0;
        for (ToggleButton btn : buttons) {
            if (index < UpgradeModel.getInstance().getNumberofcards()) {
                GUICard card = new GUICard(UpgradeModel.getInstance().getCard(index));
                ImageView iv = new ImageView(card);
                iv.setFitHeight(132);
                iv.setFitWidth(92);
                btn.setGraphic(iv);
                if (card.getTooltip() != null) {
                    btn.setTooltip(card.getTooltip());
                }
            } else {
                ImageView iv = new ImageView(new GUICard("Empty"));
                iv.setFitHeight(132);
                iv.setFitWidth(92);
                btn.setGraphic(iv);
                btn.setTooltip(null);
            }
            index++;
        }
    }

    /**called when skip button is pressed*/
    @FXML
    public void closeShop(ActionEvent actionEvent){
        UpgradeShop.getInstance().skip();
        UpgradeModel.getInstance().setUpgradePurchaseTurn(false);
    }

    /**called when buy button is pressed*/
    @FXML
    public void buyUpgrade(ActionEvent actionEvent){
        ToggleButton selectedbtn;
        int id;
        if (buttongroup.getSelectedToggle() == null){
            selectedbtn = null;
            id = 0;
        }
        else {
            selectedbtn = (ToggleButton) buttongroup.getSelectedToggle();
            id = Character.getNumericValue(selectedbtn.getId().charAt(4));
        }
        if (selectedbtn == null || id>=UpgradeModel.getInstance().getNumberofcards()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "You need to choose a card to buy.");
            alert.showAndWait();
        } else {
            UpgradeShop.getInstance().sendBoughtCard(id);
        }
    }

}