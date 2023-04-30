package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageBody;
import game.data.cards.CardName;

import java.util.ArrayList;

public class ReturnCardsMessageBody extends MessageBody {
    CardName[] cardNames;

    public ReturnCardsMessageBody(CardName[] cardNames) {
        this.cardNames = cardNames;
    }


    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(cardNames);
        return content;
    }
}
