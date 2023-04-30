package game.data.cards.upgrade;

import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.cards.Card;
import game.data.cards.CardName;
import org.tinylog.Logger;
import util.LoggingTags;

/**
 * This class implements the AdminPrivilege Card
 *
 * @author Simon Hümmer
 */
public class AdminPrivilege extends Card {

    public AdminPrivilege() {
        cardName = CardName.AdminPrivilege;
        cardDescription = "Once per round, you may give your robot priority for one register.";
        cost = 3;
    }

    @Override
    public void activateCard(ServerConnectedPlayer player) {
        Logger.tag(LoggingTags.game.toString()).info("Activating Card 'AdminPrivilege' for player: " + player.getName());
    }

    @Override
    public boolean isDamageCard() {
        return false;
    }

    @Override
    public boolean isUpgradeCard() {
        return true;
    }

    @Override
    public int getCost() {
        return cost;
    }
}
