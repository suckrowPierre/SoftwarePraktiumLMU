package game.servergame;

import communication.protocol_v2v1.sendobjects.game_move.upgrade_phase.ExchangeShop;
import communication.protocol_v2v1.sendobjects.game_move.upgrade_phase.RefillShop;
import communication.server.GameHandler;
import communication.server.connectedclient.ConnectedClientsHandler;
import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.cards.Card;
import game.data.cards.CardName;
import game.data.cards.programming.Null;
import game.data.cards.upgrade.RearLaser;
import game.data.decks.upgrade.UpgradeDeck;
import game.data.decks.upgrade.UpgradeDiscardDeck;
import game.data.decks.upgrade.UpgradeShop;
import org.tinylog.Logger;
import util.LoggingTags;

import java.util.ArrayList;

/**
 * This class implements the UpgradeShop for the Server
 *
 * @author Simon Hümmer
 */
public class Upgrade {

    private final UpgradeDeck upgradeDeck;
    private final UpgradeShop upgradeShop;
    private final UpgradeDiscardDeck upgradeDiscardDeck;

    public Upgrade() {
        this.upgradeDeck = new UpgradeDeck();
        upgradeDeck.initialize();
        upgradeDeck.shuffle();

        this.upgradeShop = new UpgradeShop();
        upgradeShop.initialize();

        this.upgradeDiscardDeck = new UpgradeDiscardDeck();
        upgradeDiscardDeck.initialize();
    }

    /**
     * Checks if the shop needs to refill or to exchange
     */
    public void checkShop() {
        if (GameHandler.getInstance().getPhase() == 1) {
            ArrayList<Card> check = new ArrayList<>();

            for (int i = 0; i < upgradeShop.getUpgradeShop().length; i++) {
                if (upgradeShop.getUpgradeShop()[i].getCardName() != CardName.Null) {
                    check.add(upgradeShop.getUpgradeShop()[i]);
                }
            }

            if (check.size() != upgradeShop.getUpgradeShop().length) {
                refillShop();
            } else {
                exchangeShop();
            }
        }
    }

    /**
     * Refills the UpgradeShop with cards from the upgradeDeck
     */
    public void refillShop() {
        if (GameHandler.getInstance().getPhase() == 1) {
            Logger.tag(LoggingTags.game.toString()).info("Refill Shop");
            ArrayList<CardName> refill = new ArrayList<>();

            for (int i = 0; i < upgradeShop.getUpgradeShop().length; i++) {
                if (upgradeShop.getUpgradeShop()[i].getCardName().equals(CardName.Null)) {
                    Card card = upgradeDeck.getTopCard();
                    upgradeShop.getUpgradeShop()[i] = card;
                    refill.add(card.getCardName());
                    upgradeDeck.removeTopCard(upgradeDeck.getDeck());
                }
            }

            CardName[] cardNames = new CardName[refill.size()];
            cardNames = refill.toArray(cardNames);

            GameHandler.getInstance().sendToAllPlayers(new RefillShop(cardNames));
        }
    }

    /**
     * Exchanges all Cards of the UpgradeShop with new cards from the upgradeDeck
     */
    private void exchangeShop() {
        Logger.tag(LoggingTags.game.toString()).info("Exchange Shop");
        ArrayList<CardName> exchange = new ArrayList<>();

        for (int i = 0; i < upgradeShop.getUpgradeShop().length; i++) {
            Card card = upgradeShop.getUpgradeShop()[i];
            upgradeDiscardDeck.getDeck().add(card);

            Card topCard = upgradeDeck.getTopCard();
            upgradeShop.getUpgradeShop()[i] = topCard;
            exchange.add(topCard.getCardName());
            upgradeDeck.removeTopCard(upgradeDeck.getDeck());
        }

        CardName[] cardNames = new CardName[exchange.size()];
        cardNames = exchange.toArray(cardNames);

        GameHandler.getInstance().sendToAllPlayers(new ExchangeShop(cardNames));
    }

    /**
     * Checks if the player is able to purchase an upgrade and
     * adds the upgrade card to his upgradeRegisterDeck if so
     */
    public boolean buyUpgradeCard(int clientID, CardName cardName) {
        if (GameHandler.getInstance().getPhase() == 1) {
            for (int i = 0; i < upgradeShop.getUpgradeShop().length; i++) {
                if (upgradeShop.getUpgradeShop()[i].getCardName().equals(cardName)) {
                    Card card = upgradeShop.getUpgradeShop()[i];
                    ServerConnectedPlayer player = ConnectedClientsHandler.getInstance().getConnectedClientList().getConnectedClientByClientID(clientID).getConnectedPlayer();
                    if (card.getCost() <= player.getEnergy() && checkUpgradeRegister(player, card.getCardName())) {
                        upgradeShop.getUpgradeShop()[i] = new Null();
                        for (int y = 0; y < player.getPlayerCards().getUpgradeRegisterDeck().getUpgradeRegisterDeck().length; y++) {
                            if (player.getPlayerCards().getUpgradeRegisterDeck().getUpgradeRegisterDeck()[y].getCardName().equals(CardName.Null)) {
                                player.getPlayerCards().getUpgradeRegisterDeck().getUpgradeRegisterDeck()[y] = card;
                            }
                        }
                        int currentEnergy = player.getEnergy();
                        currentEnergy = currentEnergy - card.getCost();
                        player.setEnergy(currentEnergy);
                        if (card.getCardName().equals(CardName.RearLaser)) {
                            RearLaser rearLaser = (RearLaser) card;
                            rearLaser.activateCard(player);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if the player already has the same permanent upgrade card he wants to buy in his hand
     */
    private boolean checkUpgradeRegister(ServerConnectedPlayer player, CardName cardName) {
        for (Card card : player.getPlayerCards().getUpgradeRegisterDeck().getUpgradeRegisterDeck()) {
            if ((card.getCardName().equals(CardName.AdminPrivilege) && cardName.equals(CardName.AdminPrivilege))
                    || card.getCardName().equals(CardName.RearLaser) && cardName.equals(CardName.RearLaser)) {
                return false;
            }
        }
        return true;
    }
}
