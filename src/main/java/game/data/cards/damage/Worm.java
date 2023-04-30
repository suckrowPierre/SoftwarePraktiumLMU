package game.data.cards.damage;

import communication.server.GameHandler;
import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.GameMap;
import game.data.cards.Card;
import game.data.cards.CardName;
import game.servergame.BoardElementInteraction;
import org.tinylog.Logger;
import util.LoggingTags;

/**
 * This class implements the Worm card.
 *
 * @author Simon Hümmer
 */
public class Worm extends Card {

    public Worm() {
        cardName = CardName.Worm;
        cardDescription = "";
    }

    /**
     * This makes the robot reboot.
     */
    @Override
    public void activateCard(ServerConnectedPlayer player) {
        Logger.tag(LoggingTags.game.toString()).info("Activating Card 'Worm' for player: " + player.getName());

        GameMap map = GameHandler.getInstance().getMap();
        BoardElementInteraction boardElementInteraction = new BoardElementInteraction();

        boardElementInteraction.getWormDeck().getDeck().add(this);

        boardElementInteraction.rebootRobot(player, map, player.getRobot().getPosition().getX(), player.getRobot().getPosition().getY());
    }

    @Override
    public boolean isDamageCard() {
        return true;
    }
}
