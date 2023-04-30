package communication.client.upgradeshop;

import communication.client.WritingClientSingelton;
import communication.client.chatgui.UpgradeModel;
import communication.protocol_v2v1.sendobjects.game_move.upgrade_phase.BuyUpgrade;
import game.data.cards.Card;
import game.data.cards.CardGetter;
import game.data.cards.CardName;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Logic for UpgradeShop
 * @author Pierre-Louis Suckrow
 */
public class UpgradeShop {

    private static UpgradeShop instance;

   private ArrayList<Card> upgradeCards = new ArrayList<>();
   private int removedIndex;

    public UpgradeShop() {
    }

    public void refillShopCardNames(CardName[] cardNames){
        refillShop(CardGetter.getCardsByName(cardNames));
    }
    private void refillShop(Card[] cards){
        upgradeCards.addAll(Arrays.asList(cards));
        updateView();
    }

    public void exchangeShopCardNames(CardName[] cardNames){
        exchangeShop(CardGetter.getCardsByName(cardNames));
    }

    private void exchangeShop(Card[] cards){
        upgradeCards.clear();
        refillShop(cards);
    }

    public Card getCard(int i){
        return upgradeCards.get(i);
    }

    public void removeCard(int i){
        upgradeCards.remove(i);
        updateView();

    }

    private void removeCardByName(CardName cardName){
        for (Card card : upgradeCards) {
            if (card.getCardName().equals(cardName)) {
                upgradeCards.remove(card);
                break;
            }
        }
        updateView();
    }

    public void sendBoughtCard(int i){
        Card card = getCard(i);
        removedIndex = i;
        WritingClientSingelton.getInstance().writeOutStream(new BuyUpgrade(true,card.getCardName()));
    }

    public void skip(){
        WritingClientSingelton.getInstance().writeOutStream(new BuyUpgrade(false, CardName.Null));
    }

    public void updateSuccessfullyBoughtCard(int clientID, CardName cardName){
        if(clientID == WritingClientSingelton.getInstance().getClientID()){
            removeCard(removedIndex);
            Platform.runLater(()-> UpgradeModel.getInstance().showCardInUpgrades(cardName.toString()));
        }else {
            removeCardByName(cardName);
        }
    }


    private void updateView(){
        Platform.runLater(() -> {
            ArrayList<String> cardstrings = new ArrayList<String>();
            for(Card c: upgradeCards)
            {
                cardstrings.add(c.getCardName().toString());
            }
            UpgradeModel.getInstance().setCards(cardstrings);
        });
    }

    public static synchronized UpgradeShop getInstance() {
        if (instance == null) {
            instance = new UpgradeShop();
        }
        return instance;
    }
}
