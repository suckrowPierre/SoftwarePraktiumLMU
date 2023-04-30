package game.clientgame.clientgamehandler;

import communication.client.WritingClientSingelton;
import communication.client.chatgui.Model;
import communication.client.chatgui.ProgrammingBoardModel;
import communication.client.chatgui.UpgradeModel;
import game.data.cards.CardName;
import javafx.application.Platform;

/**
 * Handles Changes to ClientGame for User
 * @author Pierre-Louis Suckrow
 */
public class UserClientGameHandler extends ClientGameHandler {


    public UserClientGameHandler() {
        super();
    }


    @Override
    protected void handleStartingPoint() {
        Platform.runLater(()-> Model.getInstance().getBoard().allowToSetStartingPoint(true));
    }

    @Override
    protected void setEmptyCard(int currentregister) {
        Platform.runLater(()-> ProgrammingBoardModel.getInstance().showCardInView(currentregister, "Empty"));
    }


    protected void showCardInView(int missingregister, String name){
        Platform.runLater(()-> ProgrammingBoardModel.getInstance().showCardInView(missingregister, name));
    }

    public void setCurrentPlayer(int currentPlayerrecv) {
       super.setCurrentPlayer(currentPlayerrecv);
       if (phase==1 && currentPlayer==WritingClientSingelton.getInstance().getClientID()){
           Platform.runLater(()-> UpgradeModel.getInstance().setUpgradePurchaseTurn(true));
       }

    }

    public void chooseDamageCards(int count, CardName[] cardNames){};


}

