package communication.client.chatgui;

import communication.client.data_handlers.GameDataOutHandeler;
import game.clientgame.clientgamehandler.GameHandlerSingelton;
import game.data.cards.CardName;
import javafx.collections.FXCollections;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.tinylog.Logger;
import util.LoggingTags;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Sarah Controller for the window that shows the board and information about the game
 */
public class BoardController {

    @FXML
    private ScrollPane scrollpane;
    @FXML
    private Label bottomlabel;
    @FXML
    private ListView checkpointlistview, toremovelistview;
    @FXML
    private VBox checkpointvbox, adminprivvbox;
    @FXML
    private HBox memoryswapbox;
    @FXML
    private Pane chatroombox;
    @FXML
    private ImageView card0, card1, card2, card3, card4, card5, card6, card7, card8;
    @FXML
    private ImageView slot0, slot1, slot2, slot3, slot4, upgrade0, upgrade1, upgrade2, upgrade3, upgrade4;
    @FXML
    private Label timertext, hinttext, newcard1, newcard2, newcard3;
    @FXML
    private ChoiceBox<Integer> privilegechoicebox;
    @FXML
    private Button sendbtn;

    private ArrayList<ImageView> upgradecards, programmingcards;
    private boolean draganddroppossible;
    private boolean timerrunning = false;
    private boolean temoraryupgradesklickable = false;
    private final int STARTTIME = 30;
    private Integer timeSeconds = STARTTIME;
    private Timeline timeline;
    private EventHandler<MouseEvent> klickeventHandler, dehighlighteventHandler, highlighteventHandler;


    public void initialize() throws IOException {
        //create Board
        BoardGUI board = new BoardGUI(Model.getInstance().getX(), Model.getInstance().getY(), this);
        scrollpane.setContent(board);
        scrollpane.setMaxSize(board.getX()+5, board.getY()+5);

        //create Chat and reached checkpoints
        checkpointlistview.itemsProperty().set(Model.getInstance().playerstatuslistProperty());
        VBox chat = FXMLLoader.load(getClass().getResource("/chatroom.fxml"));
        this.chatroombox.getChildren().add(chat);

        addListeners();
        createMouseEvents();
        upgradecards = new ArrayList<ImageView>(Arrays.asList(upgrade0, upgrade1, upgrade2, upgrade3, upgrade4));
        programmingcards = new ArrayList<ImageView>(Arrays.asList(card0, card1, card2, card3, card4, card5, card6, card7, card8));
        initializeUpgradeCards();
        emptyHandcards();
        emptyRegisters();
        hinttext.setVisible(false);
        hinttext.setManaged(false);
        ProgrammingBoardModel.getInstance().setViewhandel(this);
        UpgradeModel.getInstance().setViewhandel(this);
        initializeUpgradeCardFunctionality();

    }

    private void initializeUpgradeCardFunctionality() {
        privilegechoicebox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));
        adminprivvbox.setManaged(false);
        adminprivvbox.setVisible(false);
        sendbtn.setDisable(true);
        memoryswapbox.setManaged(false);
        memoryswapbox.setVisible(false);
        toremovelistview.itemsProperty().set(UpgradeModel.getInstance().getToremovelist());
        toremovelistview.setTooltip(new Tooltip("Drag the three cards which\nyou want to exchange into here.\nThen click send."));
    }

    private void addListeners() {
        //listens if there are new cards
        BooleanProperty initializecards = ProgrammingBoardModel.getInstance().getinitializeCards();
        initializecards.addListener((observableValue, aBoolean, t1) -> {
            if (t1){
                initializeProgrammingPhase();
                Logger.tag(LoggingTags.gui.toString()).debug("GUI programming phase initialized");
            }
        });

        //listens if timer was started or stopped
        BooleanProperty timerRunning = ProgrammingBoardModel.getInstance().getTimerRunning();
        timerRunning.addListener((observableValue, aBoolean, t1) -> {
            if (t1){
                timerrunning = true;
                startTimer();
                Logger.tag(LoggingTags.gui.toString()).debug("GUI timer started");
            }
            else {
                timerrunning = false;
                Logger.tag(LoggingTags.gui.toString()).debug("GUI timer stopped");
            }
        });

        //listens if game was finished
        BooleanProperty gameFinished = Model.getInstance().getGameFinished();
        gameFinished.addListener((observableValue, aBoolean, t1) -> {
            if (t1){
                loadGameFinishedWindow();
                Logger.tag(LoggingTags.gui.toString()).info("Loading GUI window game finished");
            }
        });

        //listens if a player can buy upgrades
        BooleanProperty loadShop = UpgradeModel.getInstance().getUpgradePurchaseTurn();
        loadShop.addListener((observableValue, aBoolean, t1) -> {
            if (t1){
                showUpgradeShop(null);
                sendbtn.setDisable(true);
                Logger.tag(LoggingTags.gui.toString()).info("Loading GUI window upgrade shop");
            }
        });

        //listens if a player rebooted
        BooleanProperty loadRebootWindow = Model.getInstance().getRebooting();
        loadRebootWindow.addListener((observableValue, aBoolean, t1) -> {
            if (t1){
                showRebootWindow();
                Logger.tag(LoggingTags.gui.toString()).info("Loading GUI window to choose reboot direction");
            }
        });

        //listens if a player needs to pick damage cards
        BooleanProperty loadPickDamage = Model.getInstance().getChoosedamage();
        loadPickDamage.addListener((observableValue, aBoolean, t1) -> {
            if (t1){
                showPickDamageWindow();
                Logger.tag(LoggingTags.gui.toString()).info("Loading GUI window to pick damage cards");
            }
        });

        //listens if memory swap was played
        BooleanProperty loadMemorySwap = UpgradeModel.getInstance().getMemoryswapactive();
        loadMemorySwap.addListener((observableValue, aBoolean, t1) -> {
            if (t1){
                String card1 = UpgradeModel.getInstance().getNewCard(0);
                String card2 = UpgradeModel.getInstance().getNewCard(1);
                String card3 = UpgradeModel.getInstance().getNewCard(2);
                addMemorySwapFunctionality(card1, card2, card3);
                Logger.tag(LoggingTags.gui.toString()).info("Loading Memory Swap in GUI");
            }
        });
    }

    private void createMouseEvents(){
        klickeventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(temoraryupgradesklickable) {
                    ImageView view = (ImageView) e.getSource();
                    GUICard card = (GUICard) view.getImage();
                    GameDataOutHandeler.getInstance().sendCardtoPlay(CardName.valueOf(card.getName()));
                }
            }
        };
        highlighteventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(temoraryupgradesklickable) {
                    sendbtn.getScene().setCursor(Cursor.HAND);
                }
            }
        };
        dehighlighteventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                sendbtn.getScene().setCursor(Cursor.DEFAULT);
            }
        };

    }

    private void showPickDamageWindow() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/choosedamage.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Pick Damage");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showRebootWindow() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/choosereboot.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Rebooting...");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initializeUpgradeCards(){
        for(ImageView image: upgradecards){
            image.setImage(new GUICard("Empty"));
        }
    }

    private void loadGameFinishedWindow() {
        Parent root;
        Parent chatroot;
        try {
            root = FXMLLoader.load(getClass().getResource("/gamefinished.fxml"));
            Stage stage = (Stage) scrollpane.getScene().getWindow();
            stage.setTitle("Roborally Game Ended");
            stage.setScene(new Scene(root));
            stage.setMaxHeight(450);
            stage.setMaxWidth(650);
            stage.setResizable(false);
            stage.show();
            //Pop out chatroom
            chatroot = FXMLLoader.load(getClass().getResource("/chatroom.fxml"));
            Stage stage2 = new Stage();
            stage2.setTitle("Chatroom");
            stage2.setScene(new Scene(chatroot));
            stage2.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Shows text below map */
    public void showText(String text){
        bottomlabel.setText(text);
    }

    private void showUpgradeShop(ActionEvent actionEvent){
        Parent root;
        try {
            //show the Upgradeshop
            root = FXMLLoader.load(getClass().getResource("/upgradeshop.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Upgradeshop of "+ Model.getInstance().getusername().get());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void sendadminprivilege(ActionEvent event){
        //check if selected
        if (privilegechoicebox.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "You need to choose a register.");
            alert.showAndWait();
        } else {
            GameDataOutHandeler.getInstance().sendChooseRegister(privilegechoicebox.getSelectionModel().getSelectedItem());
            Logger.tag(LoggingTags.gui.toString()).debug("GUI privilege for register chosen");
            sendbtn.setDisable(true);
        }
    }

    @FXML
    private void handleDrop(DragEvent event){
        String cardname = event.getDragboard().getString();
        ImageView target = (ImageView) event.getSource();
        target.setImage(new GUICard(cardname));
        event.setDropCompleted(true);
        if (allslotsfull()){
            draganddroppossible = false;
            emptyHandcards();
        }
        if (target.getId().startsWith("slot")){
            int slotnumber = Character.getNumericValue(target.getId().charAt(4)) +1;
            String card = cardname;

            GameHandlerSingelton.getInstance().getClientGameHandler().setCardInProgrammingDeckwithName(card, slotnumber-1);

        }


    }

    @FXML
    private void handleDragOver(DragEvent event){
        ImageView target = (ImageView) event.getSource();
        ImageView source = (ImageView) event.getGestureSource();
        GUICard image = (GUICard) target.getImage();
        if(event.getDragboard().hasString()&&image.getName().equals("Empty")
                && !(event.getDragboard().getString().equals("Again")&&target.getId().equals("slot0"))
                && !(target.getId().startsWith("slot") && source.getId().startsWith("slot"))){
            event.acceptTransferModes(TransferMode.ANY);
        }

    }

    @FXML
    private void handleDragDetection(MouseEvent event){
        ImageView source = (ImageView) event.getSource();
        GUICard image = (GUICard) source.getImage();
        if (!image.getName().equals("Empty") && draganddroppossible) {
            Dragboard db = source.startDragAndDrop(TransferMode.ANY);
            ClipboardContent cbc = new ClipboardContent();
            cbc.putString(image.getName());
            db.setContent(cbc);
        }
        event.consume();

    }

    @FXML
    public void handleSuccess(DragEvent event) {
        /* the drag and drop gesture ended */
        /* if the data was successfully moved, clear it */
        if (event.getTransferMode() == TransferMode.MOVE) {
            ImageView source = (ImageView) event.getSource();
            source.setImage(new GUICard("Empty"));
            if(source.getId().startsWith("slot")){
                int slotnumber = Character.getNumericValue(source.getId().charAt(4)) +1;
                String card = "Null";
                GameHandlerSingelton.getInstance().getClientGameHandler().clearCardInProgrammingDeckatIndex(slotnumber-1);
            }
        }
        event.consume();
        hinttext.setVisible(false);
        hinttext.setManaged(false);
        temoraryupgradesklickable = false;
    }

    @FXML
    public void handleDroponListView(DragEvent event){
        String cardname = event.getDragboard().getString();
        UpgradeModel.getInstance().addCardtoRemoveList(cardname);
        event.setDropCompleted(true);
    }

    @FXML
    public void handleDragOverListView(DragEvent event){
        if(event.getDragboard().hasString() && UpgradeModel.getInstance().getToremovelist().size()<3){
            event.acceptTransferModes(TransferMode.ANY);
        }

    }

    private void emptyHandcards(){
        for(ImageView card : programmingcards){
            card.setImage(new GUICard("Empty"));
        }
    }

    private void initializeProgrammingPhase(){
        emptyRegisters();
        sendbtn.setDisable(false);
        ProgrammingBoardModel.getInstance().clearRegistercontents();
        hinttext.setVisible(true);
        hinttext.setManaged(true);
        int i = 0;
        for(ImageView card: programmingcards){
            card.setImage(new GUICard(ProgrammingBoardModel.getInstance().getProgrammingcards().get(i)));
            i++;
        }
        draganddroppossible = true;
        temoraryupgradesklickable = true;
        ProgrammingBoardModel.getInstance().setInitalizeCards(false);
    }

    private void emptyRegisters() {
        slot0.setImage(new GUICard("Empty"));
        slot1.setImage(new GUICard("Empty"));
        slot2.setImage(new GUICard("Empty"));
        slot3.setImage(new GUICard("Empty"));
        slot4.setImage(new GUICard("Empty"));
    }

    private boolean allslotsfull(){
        GUICard s1 = (GUICard) slot0.getImage();
        GUICard s2 = (GUICard) slot1.getImage();
        GUICard s3 = (GUICard) slot2.getImage();
        GUICard s4 = (GUICard) slot3.getImage();
        GUICard s5 = (GUICard) slot4.getImage();

        return !(s1.getName().equals("Empty") || s2.getName().equals("Empty")|| s3.getName().equals("Empty")
                || s4.getName().equals("Empty")|| s5.getName().equals("Empty"));

    }

    public void showCardInRegister(int register, String cardname){
        switch (register) {
            case 0 -> slot0.setImage(new GUICard(cardname));
            case 1 -> slot1.setImage(new GUICard(cardname));
            case 2 -> slot2.setImage(new GUICard(cardname));
            case 3 -> slot3.setImage(new GUICard(cardname));
            case 4 -> slot4.setImage(new GUICard(cardname));
        }

    }

    private void showCardinEmptyProgrammingSlot(String name){
        for(ImageView card: programmingcards){
            GUICard image = (GUICard) card.getImage();
            if(image.getName().equals("Empty")){
                card.setImage(new GUICard(name));
                break;
            }
        }
    }

    public void showCardInUpgrades(String cardname){
        if (cardname.equals("AdminPrivilege")){
            showAdminPrivilegeChoice();
        }
        for (ImageView upgradeslot: upgradecards){
            GUICard currentcard = (GUICard) upgradeslot.getImage();
            if(currentcard.getName().equals("Empty")){
                upgradeslot.setImage(new GUICard(cardname));
                if(cardname.equals("MemorySwap")||cardname.equals("SpamBlocker")){
                    upgradeslot.addEventFilter(MouseEvent.MOUSE_CLICKED, klickeventHandler);
                    upgradeslot.addEventFilter(MouseEvent.MOUSE_ENTERED, highlighteventHandler);
                    upgradeslot.addEventFilter(MouseEvent.MOUSE_EXITED, dehighlighteventHandler);
                    upgradeslot.addEventFilter(MouseEvent.MOUSE_CLICKED, dehighlighteventHandler);
                }
                break;
            }
        }
    }

    public void removeCardfromUpgrades(String cardname){
        for (ImageView upgradeslot: upgradecards){
            GUICard currentcard = (GUICard) upgradeslot.getImage();
            if(currentcard.getName().equals(cardname)){
                upgradeslot.setImage(new GUICard("Empty"));
                upgradeslot.removeEventFilter(MouseEvent.MOUSE_CLICKED, klickeventHandler);
                upgradeslot.removeEventFilter(MouseEvent.MOUSE_ENTERED, highlighteventHandler);
                upgradeslot.removeEventFilter(MouseEvent.MOUSE_EXITED, dehighlighteventHandler);
                upgradeslot.removeEventFilter(MouseEvent.MOUSE_CLICKED, dehighlighteventHandler);
                break;
            }
        }
    }

    private void addMemorySwapFunctionality(String c1, String c2, String c3){
        UpgradeModel.getInstance().clearToRemoveCards();
        newcard1.setText(c1);
        newcard2.setText(c2);
        newcard3.setText(c3);
        memoryswapbox.setManaged(true);
        memoryswapbox.setVisible(true);
    }

    @FXML
    private void sendmemoryswapcards(){
        if(UpgradeModel.getInstance().getToremovelist().size() < 3){
            Alert alert = new Alert(Alert.AlertType.ERROR, "You need to choose three cards to remove.");
            alert.showAndWait();
        }
        else {
            //send Cards
            CardName[] returns = new CardName[3];
            returns[0] = CardName.valueOf(UpgradeModel.getInstance().getToremovelist().get(0));
            returns[1] = CardName.valueOf(UpgradeModel.getInstance().getToremovelist().get(1));
            returns[2] = CardName.valueOf(UpgradeModel.getInstance().getToremovelist().get(2));
            GameDataOutHandeler.getInstance().sendReturnCards(returns);
            //load cards into window
            showCardinEmptyProgrammingSlot(UpgradeModel.getInstance().getNewCard(0));
            showCardinEmptyProgrammingSlot(UpgradeModel.getInstance().getNewCard(1));
            showCardinEmptyProgrammingSlot(UpgradeModel.getInstance().getNewCard(2));

            memoryswapbox.setVisible(false);
            memoryswapbox.setManaged(false);
            UpgradeModel.getInstance().setMemoryswapactive(false);
            UpgradeModel.getInstance().clearToRemoveCards();
        }
    }

    private void showAdminPrivilegeChoice(){
        adminprivvbox.setManaged(true);
        adminprivvbox.setVisible(true);
    }

    private void startTimer(){
        if (timeline != null) {
            timeline.stop();
        }

        // update timertext
        timertext.setTextFill(Color.web("#D32500"));
        timertext.setText("Timer: "+ timeSeconds.toString());
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        // KeyFrame event handler
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        (EventHandler) event -> {
                            timeSeconds--;
                            // update timerLabel
                            timertext.setText("Timer: "+ timeSeconds.toString());
                            if (timeSeconds <= 0 ||  !timerrunning) {
                                timeline.stop();
                                timertext.setTextFill(Color.BLACK);
                                draganddroppossible = false;
                                emptyHandcards();
                                ProgrammingBoardModel.getInstance().setTimerRunning(false);
                                timeSeconds = 0;
                                timertext.setText("Timer: "+ timeSeconds.toString());
                            }
                        }
                ));
        timeline.playFromStart();
        timeSeconds = STARTTIME;
    }

}
