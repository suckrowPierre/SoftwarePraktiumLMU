package game.data.cards.programming;

import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.cards.Card;
import game.data.cards.CardName;
import org.tinylog.Logger;
import util.LoggingTags;

/**
 * This class implements the Again card.
 *
 * @author Simon Hümmer
 */
public class Again extends Card {

    public Again() {
        cardName = CardName.Again;
        cardDescription = """
                Repeat the programming in your previous register.\s
                If your previous register was a damage card, draw a card from the\s
                top of your deck, and play that card this register.\s
                If you used an upgrade in your previous register that allowed you to play\s
                multiple programming cards, re-execute the second card.\s
                This card cannot be played in the first register.""";
    }

    /**
     * This will repeat the action that was performed in the previous register.
     */
    @Override
    public void activateCard(ServerConnectedPlayer player) {
        Logger.tag(LoggingTags.game.toString()).info("Activating Card 'Worm' for player: " + player.getName());
    }

    @Override
    public boolean isDamageCard() {
        return false;
    }
}
