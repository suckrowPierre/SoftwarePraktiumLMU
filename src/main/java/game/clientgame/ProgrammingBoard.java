package game.clientgame;

import communication.client.chatgui.ProgrammingBoardModel;
import communication.protocol_v2v1.sendobjects.game_move.activation_phase.ActiveCard;
import game.data.cards.Card;
import game.data.cards.CardGetter;
import game.data.cards.CardName;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * logic for ProgrammingBoard
 * @author Pierre-Louis Suckrow
 */
public class ProgrammingBoard {

    private ArrayList<Card> recievedProgrammingCards = new ArrayList<>();
    private Card[] programmableDeck = new Card[5];
    private ActiveCard[] activeCards;
    private boolean timerstopped = false;


    public ProgrammingBoard() {
        activeCards = null;

    }

    public int getRegisterPosition(){
        for (int i = 0; i < programmableDeck.length; i++){
            if(!(programmableDeck[i] == null)){
                return i;
            }
        }
        return -1;
    }

    public void setActiveCards(ActiveCard[] activeCards) {
        this.activeCards = activeCards;
        System.out.println(Arrays.toString(activeCards));
    }

    public void clearprogrammableCardsInProgrammingDeck(){
        programmableDeck = new Card[5];
    }


    public Card pullfromProgrammableDeck(){
       for(int i = 0; i < programmableDeck.length;i++){
           if(programmableDeck[i] != null){
               return programmableDeck[i];
           }
       }
       return null;
    }

    public void setCardInProgrammingDeckwithName(CardName cardName, int index ){
        programmableDeck[index] = CardGetter.getCardByName(cardName);
    }

    public ArrayList<Integer> getEmptyIndicesProgrammingDeck(){
        ArrayList<Integer> emptyIndices = new ArrayList<>();
        for (int i = 0; i < programmableDeck.length; i++){
            if(programmableDeck[i] == null){
                emptyIndices.add(i);
            }
        }
        return emptyIndices;
    }

    public void setCardInProgrammingDeck(Card card, int index ){
        programmableDeck[index] = card;
    }

    public void clearCardInProgrammingDeckatIndex(int index){
        programmableDeck[index] = null;
    }



    public Card[] getRecievedProgrammingCards() {
        return recievedProgrammingCards.toArray(new Card[recievedProgrammingCards.size()]);
    }

    public ArrayList<Card> getRecievedProgrammingCardsArrayList() {
        return recievedProgrammingCards;
    }

    public boolean isTimerstopped() {
        return timerstopped;
    }

    public void setTimerstopped(boolean timerstopped) {
        this.timerstopped = timerstopped;



    }

    public void setRecievedProgrammingCards(Card[] recievedProgrammingCards) {
        this.recievedProgrammingCards.clear();
        for (Card card:recievedProgrammingCards){
            this.recievedProgrammingCards.add(card);
        }
        updateViewRecvCards();

    }

    private void updateViewRecvCards(){
        Platform.runLater(() ->  ProgrammingBoardModel.getInstance().clearProgrammingcards());
        for(Card card:recievedProgrammingCards){
            Platform.runLater(() ->  ProgrammingBoardModel.getInstance().addCard(card.getCardName().toString()));
        }
        Platform.runLater(() ->  ProgrammingBoardModel.getInstance().setInitalizeCards(true));
    }


    public CardName playActiveCard(){
        return getfromActiveCards();
        //TODO: überprüfen ob ActiveCard gliech ist mit der aus der Que
    }

    private CardName getfromActiveCards(){
        int activeregister = getRegisterPosition();
        CardName activecard = programmableDeck[activeregister].cardName;
        programmableDeck[activeregister] = null;
        return activecard;
    }

    public Card[] getProgrammableDeck() {
        return programmableDeck;
    }
}
