package communication.protocol_v2v1.sendobjects.game_move.programm_phase;

import communication.protocol_v2v1.MessageBody;
import game.data.cards.CardName;

import java.util.ArrayList;

public class YourCardsMessageBody extends MessageBody {
    CardName[] cardsInHand;

    public YourCardsMessageBody(CardName[] cardsInHand) {
        this.cardsInHand = cardsInHand;
    }


    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(cardsInHand);
        return content;
    }

}
