package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageBody;
import game.data.cards.CardName;

import java.util.ArrayList;

public class SelectedDamageMessageBody extends MessageBody {
    CardName[] cards;

    public SelectedDamageMessageBody(CardName[] cards) {
        this.cards = cards;
    }

    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(cards);
        return content;
    }
}
