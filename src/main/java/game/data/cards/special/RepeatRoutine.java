package game.data.cards.special;

import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.cards.Card;
import game.data.cards.CardName;
import org.tinylog.Logger;
import util.LoggingTags;

/**
 * This class implements the RepeatRoutine Card
 *
 * @author Simon Hümmer
 */
public class RepeatRoutine extends Card {

    public RepeatRoutine() {
        cardName = CardName.RepeatRoutine;
        cardDescription = "";
    }

    @Override
    public void activateCard(ServerConnectedPlayer player) {
        Logger.tag(LoggingTags.game.toString()).info("Activating Card 'RepeatRoutine' for player: " + player.getName());

        //TODO: Repeat the programming
        // in your previous register.
        // If your previous register
        // was a damage card, draw
        // a card from the top of your
        // deck, and play that card
        // this register. If you used an
        // upgrade in your previous
        // register that allowed you to
        // play multiple programming
        // cards, re-execute the second
        // card.
    }

    @Override
    public boolean isDamageCard() {
        return false;
    }
}
