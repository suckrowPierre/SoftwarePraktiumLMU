package game.clientgame.clientgamehandler;

import communication.client.WritingClientSingelton;
import communication.client.chatgui.UpgradeModel;
import communication.client.data_handlers.GameDataOutHandeler;
import communication.protocol_v2v1.sendobjects.game_move.activation_phase.ActiveCard;
import game.clientgame.ProgrammingBoard;
import game.clientgame.StartingPoint;
import game.data.Direction;
import game.data.GameMap;
import game.data.cards.Card;
import game.data.cards.CardGetter;
import game.data.cards.CardName;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Handles Changes to ClientGame that are same for AI and User
 * @author Pierre-Louis Suckrow
 */
public abstract class ClientGameHandler {

    protected GameMap map;
    protected int currentPlayer;
    protected int phase;
    protected ArrayList<StartingPoint> takenstartingPoints;
    protected ProgrammingBoard programmingBoard;

    public ClientGameHandler() {
        this.programmingBoard = new ProgrammingBoard();
        this.takenstartingPoints = new ArrayList<>();

    }

    public void setCurrentPlayer(int currentPlayerrecv) {
        this.currentPlayer = currentPlayerrecv;
        if (phase == 0 && currentPlayer == WritingClientSingelton.getInstance().getClientID()) {
            handleStartingPoint();
        }else  if(phase == 3 && currentPlayer == WritingClientSingelton.getInstance().getClientID()){
            playCard();
        }
    }

    //only used by AI, GUIClient will choose over GUI
    public Direction chooseRebootDirection(){
        return null;
    }

    public void playCard(){
        try {
            TimeUnit.MILLISECONDS.sleep(1500);
        } catch (InterruptedException e) {}

        System.out.println("playing card");
        if(currentPlayer == WritingClientSingelton.getInstance().getClientID()){
            int currentregister = programmingBoard.getRegisterPosition();
            if(currentregister != -1) {
                System.out.println("playing card in register:" + currentregister);
                CardName cardName = programmingBoard.playActiveCard();
                GameDataOutHandeler.getInstance().sendCardtoPlay(cardName);
                setEmptyCard(currentregister);
            }

        }else{
            System.out.println("NOTYOURTURN");
        }
    }

    public void setMissingCards(CardName[] cardNames){
        ArrayList<Integer> missingcardslots = programmingBoard.getEmptyIndicesProgrammingDeck();
        if(missingcardslots.size() == cardNames.length){
            for (int i = 0; i < cardNames.length; i++){
                CardName name = cardNames[i];
                int missingregister = missingcardslots.get(i).intValue();
                programmingBoard.setCardInProgrammingDeckwithName(name, missingregister);
                showCardInView(missingregister, name.toString());
            }
        }else{
            System.out.println("Error missing cards in Server and Client not the same");
        }
    }

    protected void showCardInView(int missingregister, String name){
    }

    protected abstract void handleStartingPoint();

    protected void setEmptyCard(int currentregister){

    }

    public ArrayList<StartingPoint> getTakenstartingPoints() {
        return takenstartingPoints;
    }

    public void setMap(GameMap map){
        this.map = map;
    }

    public GameMap getMap() {
        return map;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    public void addStartingPoint(int clientID, int x, int y) {
        int index = getStartingPointIndexbyClientID(clientID);
        if (index == -1) {
            takenstartingPoints.add(new StartingPoint(clientID, x, y));
        } else {
            takenstartingPoints.set(index, new StartingPoint(clientID, x, y));
        }
    }

    public int getStartingPointIndexbyClientID(int clientID) {
        int i = 0;
        for (StartingPoint startingPoint : takenstartingPoints) {
            if (startingPoint.getClientID() == clientID) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public void recieveCards(CardName[] cardNames){
        if(cardNames.length == 3){
            Platform.runLater(()->{
                String[] cardstrings = new String[3];
                int i = 0;
                for(CardName card: cardNames){
                    cardstrings[i] = card.toString();
                    i++;
                }
                UpgradeModel.getInstance().setNewCardMemoryswap(cardstrings);
                UpgradeModel.getInstance().setMemoryswapactive(true);
            });
        }
        else {
            programmingBoard.setRecievedProgrammingCards(getCardsbyCardName(cardNames));
        }
    }


    public Card[] getCardsbyCardName(CardName[] cardNames){
        Card[] cards = new Card[cardNames.length];
        int i = 0;
        for (CardName cardName:cardNames){
            cards[i] = CardGetter.getCardByName(cardName);
            i++;
        }
        return cards;
    }

    public void setCardInProgrammingDeckwithName(String cardName, int index ){
        GameDataOutHandeler.getInstance().sendSelectedCard(CardName.valueOf(cardName), index+1);
        programmingBoard.setCardInProgrammingDeckwithName(CardName.valueOf(cardName), index);
    }

    public void setCardInProgrammingDeck(Card card, int index ){
        GameDataOutHandeler.getInstance().sendSelectedCard(card.getCardName(), index+1);
        programmingBoard.setCardInProgrammingDeck(card, index);
    }

    public void clearCardInProgrammingDeckatIndex(int index){
        GameDataOutHandeler.getInstance().sendSelectedCard(CardName.Null, index+1);
        programmingBoard.clearCardInProgrammingDeckatIndex(index);
    }

    public void recieveActiveCard(ActiveCard[] activeCards){
        programmingBoard.setActiveCards(activeCards);
    }


    public ProgrammingBoard getProgrammingBoard() {
        return programmingBoard;
    }

    public void replaceCard(int register, CardName cardName){
        programmingBoard.setCardInProgrammingDeckwithName(cardName,register);
    }

    public abstract void chooseDamageCards(int count, CardName[] cardNames);
}
