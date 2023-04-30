package game.data.cards.upgrade;

import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.cards.Card;
import game.data.cards.CardName;
import org.tinylog.Logger;
import util.LoggingTags;

/**
 * This class implements the RearLaser Card
 *
 * @author Simon Hümmer
 */
public class RearLaser extends Card {

    public RearLaser() {
        cardName = CardName.RearLaser;
        cardDescription = "Your robot may shoot backward as well as forward.";
        cost = 2;
    }

    /**
     * This sets the rearLaser of the Robot true, so it shoots backward as well as forward
     */
    @Override
    public void activateCard(ServerConnectedPlayer player) {
        Logger.tag(LoggingTags.game.toString()).info("Activating Card 'RearLaser' for player: " + player.getName());

        player.getRobot().setRearLaser(true);
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
