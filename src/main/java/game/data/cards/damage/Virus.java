package game.data.cards.damage;

import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.cards.Card;
import game.data.cards.CardName;
import game.servergame.BoardElementInteraction;
import org.tinylog.Logger;
import util.LoggingTags;

/**
 * This class implements the Virus card.
 *
 * @author Simon Hümmer
 */
public class Virus extends Card {

    public Virus() {
        cardName = CardName.Virus;
        cardDescription = "";
    }

    /**
     * This makes all robots within a 6-tile radius take a Spam card.
     * The player plays the top card of his programming deck.
     */
    @Override
    public void activateCard(ServerConnectedPlayer player) {
        Logger.tag(LoggingTags.game.toString()).info("Activating Card 'Virus' for player: " + player.getName());

        BoardElementInteraction boardElementInteraction = new BoardElementInteraction();

        boardElementInteraction.getVirusDeck().getDeck().add(this);

        Card topCard = player.getPlayerCards().getProgrammingDeck().getTopCard();
        player.getPlayerCards().getProgrammingDeck().removeTopCard(player.getPlayerCards().getProgrammingDeck().getDeck());

        topCard.activateCard(player);

        int xPosition = player.getRobot().getPosition().getX();
        int yPosition = player.getRobot().getPosition().getY();

        for (ServerConnectedPlayer connectedPlayer : boardElementInteraction.getPlayer()) {
            int connectedPlayerXPosition = connectedPlayer.getRobot().getPosition().getX();
            int connectedPlayerYPosition = connectedPlayer.getRobot().getPosition().getY();

            if (connectedPlayerXPosition <= xPosition + 6 || connectedPlayerXPosition >= xPosition - 6
                    || connectedPlayerYPosition <= yPosition + 6 || connectedPlayerYPosition >= yPosition - 6) {
                Card spamCard = boardElementInteraction.getSpamDeck().getTopCard();
                boardElementInteraction.getSpamDeck().removeTopCard(boardElementInteraction.getSpamDeck().getDeck());
                player.getPlayerCards().getDiscardDeck().getDeck().add(spamCard);
            }
        }
    }

    @Override
    public boolean isDamageCard() {
        return true;
    }
}
