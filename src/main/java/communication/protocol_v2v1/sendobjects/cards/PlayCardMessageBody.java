package communication.protocol_v2v1.sendobjects.cards;

import communication.protocol_v2v1.MessageBody;
import game.data.cards.CardName;

import java.util.ArrayList;

public class PlayCardMessageBody extends MessageBody {
    CardName card;

    public PlayCardMessageBody(CardName card) {
        this.card = card;
    }

    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(card);
        return content;
    }
}
