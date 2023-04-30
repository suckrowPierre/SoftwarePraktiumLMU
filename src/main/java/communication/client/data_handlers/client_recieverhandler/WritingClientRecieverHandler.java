package communication.client.data_handlers.client_recieverhandler;

import communication.client.AIClient;
import communication.client.WritingClient;
import communication.client.WritingClientSingelton;
import communication.client.upgradeshop.UpgradeShop;
import communication.protocol_v2v1.ProtocolSendObject;
import communication.protocol_v2v1.sendobjects.actions_events_effects.*;
import communication.protocol_v2v1.sendobjects.communication_setup.HelloServer;
import communication.protocol_v2v1.sendobjects.communication_setup.Welcome;
import communication.protocol_v2v1.sendobjects.game_move.ActivePhase;
import communication.protocol_v2v1.sendobjects.game_move.CurrentPlayer;
import communication.protocol_v2v1.sendobjects.game_move.activation_phase.CurrentCards;
import communication.protocol_v2v1.sendobjects.game_move.activation_phase.ReplaceCard;
import communication.protocol_v2v1.sendobjects.game_move.programm_phase.*;
import communication.protocol_v2v1.sendobjects.game_move.setup_phase.StartingPointTaken;
import communication.protocol_v2v1.sendobjects.game_move.upgrade_phase.ExchangeShop;
import communication.protocol_v2v1.sendobjects.game_move.upgrade_phase.RefillShop;
import communication.protocol_v2v1.sendobjects.game_move.upgrade_phase.UpgradeBought;
import communication.protocol_v2v1.sendobjects.lobby.*;
import communication.protocol_v2v1.sendobjects.special.ConnectionUpdate;
import communication.protocol_v2v1.sendobjects.special.ErrorMessage;
import org.tinylog.Logger;
import util.LoggingTags;


/**
 * handles received SendObjects that are same for AI and User
 * @author Simon Naab, Sarah Koch, Pierre-Louis Suckrow
 */
public abstract class WritingClientRecieverHandler extends ClientRecieverHandler {

    WritingClient client;
    private boolean isAI;

    public WritingClientRecieverHandler(WritingClient client) {
        overClass = this;
        this.client = client;
        if(client instanceof AIClient){
            isAI = true;
        }else{
            isAI = false;
        }
    }

    protected void handleHelloClient(ProtocolSendObject recvSendObject) {
        sendSendObject(new HelloServer(client.version, client.group, isAI));
    }

    protected void handleAlive(String recvjson) {
        client.writeOutStreamString(recvjson);
    }

    protected void handleWelcome(ProtocolSendObject recvSendObject) {
        Welcome welcome = (Welcome) recvSendObject;
        client.setClientID(welcome.getID());
        WritingClientSingelton.getInstance().setClientID(welcome.getID());
        Logger.info("ID set to: " + client.getClientID());
    }

    protected void handlePlayerAdded(ProtocolSendObject recvSendObject) {
        PlayerAdded playerAdded = (PlayerAdded) recvSendObject;
        if (playerAdded.getClientID() == client.getClientID()) {
            client.getLobbyHandler().setThisClientPlayer(playerAdded.getClientID(), playerAdded.getName(), playerAdded.getFigure());
            handlePlayerAddedSubMethod();
        } else {
            client.getLobbyHandler().addConnectedClient(playerAdded.getClientID(), playerAdded.getName(), playerAdded.getFigure());
        }
    }

    protected abstract void handlePlayerAddedSubMethod();


    protected void handlePlayerStatus(ProtocolSendObject recvSendObject) {
        PlayerStatus s = (PlayerStatus) recvSendObject;
        client.getLobbyHandler().updateStatus(s.getClientID(), s.isReady());
    }

    protected void handleError(ProtocolSendObject recvSendObject) {
        ErrorMessage e = (ErrorMessage) recvSendObject;
        handleErrorCases(e);
    }


    protected abstract void handleErrorCases(ErrorMessage error);


    protected void handleConnectionUpdate(ProtocolSendObject recvSendObject) {
        ConnectionUpdate c = (ConnectionUpdate) recvSendObject;
        if (!c.isConnected()) {
            client.getLobbyHandler().removeConnectedClient(c.getID());
        }
    }

    protected void handleCurrentPlayer(ProtocolSendObject recvSendObject) {
        CurrentPlayer currentPlayer = (CurrentPlayer) recvSendObject;
        gameDateRecieverHandler.setCurrentPlayer(currentPlayer.getClientID());
    }

    protected void handleActivePhase(ProtocolSendObject recvSendObject) {
        ActivePhase activePhase = (ActivePhase) recvSendObject;
        gameDateRecieverHandler.setActivePhase(activePhase.getPhase());
    }

    protected void handleStartingPointTaken(ProtocolSendObject recvSendObject) {
        StartingPointTaken startingPointTaken = (StartingPointTaken) recvSendObject;
        gameDateRecieverHandler.addNewStartingPoint(
                startingPointTaken.getClientID(), startingPointTaken.getX(), startingPointTaken.getY());

    }

    protected void handleYourCards(ProtocolSendObject recvSendObject) {
        YourCards yourCards = (YourCards) recvSendObject;
        gameDateRecieverHandler.drawCards(yourCards.getCardsInHand());
    }


    protected void handleTimerStarted(ProtocolSendObject recvSendObject) {
        gameDateRecieverHandler.startTimer();
    }


    protected void handleCardsYouGotNow(ProtocolSendObject recvSendObject) {
        CardsYouGotNow cardsYouGotNow = (CardsYouGotNow) recvSendObject;
        gameDateRecieverHandler.fillMissingProgrammCards(cardsYouGotNow.getCards());
    }

    protected void handleCurrentCards(ProtocolSendObject recvSendObject) {
        CurrentCards currentCards = (CurrentCards) recvSendObject;
        gameDateRecieverHandler.handleCurrentCards(currentCards.getActiveCards());
    }

    protected void handleMovement(ProtocolSendObject recvSendObject){
        Movement movement = (Movement) recvSendObject;
        gameDateRecieverHandler.movePlayer(movement.getClientID(),movement.getX(),movement.getY());
    }

    protected void handlePlayerTurning(ProtocolSendObject recvSendObject){
        PlayerTurning playerTurning = (PlayerTurning) recvSendObject;
        gameDateRecieverHandler.turnPlayer(playerTurning.getClientID(), playerTurning.getRotation());
    }

    protected void handleReplaceCard(ProtocolSendObject recvSendObject){
        ReplaceCard replaceCard = (ReplaceCard) recvSendObject;
        gameDateRecieverHandler.replaceCard(replaceCard.getRegister(),replaceCard.getNewCard(),replaceCard.getClientID());
    }

    protected void handleGameFinished(ProtocolSendObject recvSendObject){
        GameFinished gameFinished = (GameFinished) recvSendObject;
        gameDateRecieverHandler.finishGame(gameFinished.getClientID());
        sendSendObject(new SetStatus(false));
    }

    //TODO:

    protected void handleEnergy(ProtocolSendObject recvSendObject){
        Energy energy = (Energy) recvSendObject;
    }

    protected void handleReboot(ProtocolSendObject recvSendObject){
        Reboot reboot = (Reboot) recvSendObject;
        Logger.info("Player :" + reboot.getClientID() + " rebooted");
        if(reboot.getClientID() == WritingClientSingelton.getInstance().getClientID()) {
            gameDateRecieverHandler.chooseRebootDirection();
        }
    }


    protected void handleSelectedDamage(ProtocolSendObject recvSendObject){
        SelectedDamage selectedDamage = (SelectedDamage) recvSendObject;
    }

    protected abstract void handlePickDamage(ProtocolSendObject recvSendObject);

    protected  void handleDrawDamage(ProtocolSendObject recvSendObject){
        DrawDamage drawDamage = (DrawDamage) recvSendObject;
    }

    protected void handleRefillShop(ProtocolSendObject recvSendObject){
        RefillShop refillShop = (RefillShop) recvSendObject;
        UpgradeShop.getInstance().refillShopCardNames(refillShop.getCards());
    }

    protected void handleExchangeShop(ProtocolSendObject recvSendObject){
        ExchangeShop exchangeShop = (ExchangeShop) recvSendObject;
        UpgradeShop.getInstance().exchangeShopCardNames(exchangeShop.getCards());
    }

    protected void handleUpgradeBought(ProtocolSendObject recvSendObject){
        UpgradeBought upgradeBought = (UpgradeBought) recvSendObject;
        UpgradeShop.getInstance().updateSuccessfullyBoughtCard(upgradeBought.getClientID(),upgradeBought.getCard());
    }

    protected void handleLaserAnimation(ProtocolSendObject recvSendObject){
        LaserAnimation la = (LaserAnimation) recvSendObject;
    }

    //TODO: handleGameFinished fehlt, außerdem fehlt Handling für Energypoints, Damage auswählen und das neue Protokoll


    //TODO  Model.getInstance().getBoard().showTextBelowMap("Starting point already taken, please choose a new one"); anzeigen, falls StartingPoint belegt


    protected void sendSendObject(ProtocolSendObject tosend) {
        client.writeOutStream(tosend);
    }

    protected void handleRegisterChosen(ProtocolSendObject recvSendObject){

    }


}
