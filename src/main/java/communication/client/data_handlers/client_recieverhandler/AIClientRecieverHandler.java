package communication.client.data_handlers.client_recieverhandler;

import communication.client.AIClient;
import communication.client.chatgui.Model;
import communication.client.data_handlers.gamedata_recieverhandler.AIGameDateRecieverHandler;
import communication.client.lobby.AILobbyHandler;
import communication.protocol_v2v1.ProtocolSendObject;
import communication.protocol_v2v1.sendobjects.actions_events_effects.CheckPointReached;
import communication.protocol_v2v1.sendobjects.actions_events_effects.Energy;
import communication.protocol_v2v1.sendobjects.actions_events_effects.PickDamage;
import communication.protocol_v2v1.sendobjects.cards.CardPlayed;
import communication.protocol_v2v1.sendobjects.chat.SendChat;
import communication.protocol_v2v1.sendobjects.game_move.programm_phase.*;
import communication.protocol_v2v1.sendobjects.lobby.*;
import communication.protocol_v2v1.sendobjects.special.ErrorMessage;
import communication.protocol_v2v1.sendobjects.actions_events_effects.CheckpointMoved;
import game.data.Maps;
import javafx.application.Platform;

import java.util.Random;


/**
 * handles received SendObjects for AI
 * @author Pierre-Louis Suckrow
 */
public class AIClientRecieverHandler extends WritingClientRecieverHandler {

  int figurecounter = 0;


  public AIClientRecieverHandler(AIClient client) {
    super(client);
  }

  protected void handleWelcome(ProtocolSendObject recvSendObject) {
    super.handleWelcome(recvSendObject);
    sendSendObject(new PlayerValues("KI_PragmatischePinguine", figurecounter));
  }


  protected void handlePlayerAddedSubMethod(){
      sendSendObject(new SendChat("Hi, ich bin die KI der PragmatischenPinguine.",-1));
      sendSendObject(new SetStatus(true));
  }


  protected void handleSelectMap(ProtocolSendObject recvSendObject) {
    SelectMap s = (SelectMap) recvSendObject;
    Maps[] availableMaps = s.getAvailableMaps();
    Random random = new Random();
    int select = random.nextInt(availableMaps.length);
    Maps randomMap = availableMaps[select];
    sendSendObject(new MapSelected(randomMap));
  }

  protected void handleMapSelected(ProtocolSendObject recvSendObject) {
    MapSelected m = (MapSelected) recvSendObject;
    //Speichern für AI???
  }

  protected void handleGameStarted(ProtocolSendObject recvSendObject) {
    GameStarted gameStarted = (GameStarted) recvSendObject;
    gameDateRecieverHandler = new AIGameDateRecieverHandler(gameStarted.getGameMap());
    gameDateRecieverHandler.setLobby((AILobbyHandler) client.getLobbyHandler());
  }

  protected void handleReceivedChat(ProtocolSendObject recvSendObject){
    //DONOTHING
  }


  protected void handleErrorCases(ErrorMessage error) {
    String errorMessage = error.getMessage();
    switch (errorMessage) {
      case "Figure is already taken":
        figurecounter++;
        sendSendObject(new PlayerValues("KI_PragmatischePinguine", figurecounter));
        break;
    }
  }


  protected void handleCardPlayed(ProtocolSendObject recvSendObject){
    CardPlayed cardPlayed = (CardPlayed) recvSendObject;
   //TODO: Speichern für AI
  }

  protected void handleNotYourCards(ProtocolSendObject recvSendObject) {
    NotYourCards notYourCards = (NotYourCards) recvSendObject;
    //For now do nothing
  }

  protected void handleCardSelected(ProtocolSendObject recvSendObject) {
    CardSelected cardSelected = (CardSelected) recvSendObject;
    //For now do nothing
  }


  protected void handleSelectionFinished(ProtocolSendObject recvSendObject) {
    SelectionFinished selectionFinished = (SelectionFinished) recvSendObject;
    //For now do nothing
  }


  protected void handleTimerEnded(ProtocolSendObject recvSendObject) {
    TimerEnded timerEnded = (TimerEnded) recvSendObject;
    //for now do nothing
  }

  protected void handleCheckPointReached(ProtocolSendObject recvSendObject){
    CheckPointReached cpr = (CheckPointReached) recvSendObject;
    //for now do nothing
  }

  protected void handleEnergy(ProtocolSendObject recvSendObject){
    Energy e = (Energy) recvSendObject;
    //TODO: für das Kaufen von Upgrades merken

  }

  protected void handleCheckpointMoved(ProtocolSendObject recvSendObject){
    CheckpointMoved checkpointMoved = (CheckpointMoved) recvSendObject;

  }

  protected void handlePickDamage(ProtocolSendObject recvSendobject){
    PickDamage pickdamage = (PickDamage) recvSendobject;
   gameDateRecieverHandler.pickDamage(pickdamage.getCount(),pickdamage.getAvailablePiles());
  }

}

