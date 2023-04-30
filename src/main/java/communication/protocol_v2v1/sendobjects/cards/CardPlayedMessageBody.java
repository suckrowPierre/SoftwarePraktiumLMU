package communication.protocol_v2v1.sendobjects.cards;

import communication.protocol_v2v1.MessageBody;
import game.data.cards.CardName;

import java.util.ArrayList;

public class CardPlayedMessageBody extends MessageBody {
    CardName card;
    int clientID;

    public CardPlayedMessageBody(int clientID, CardName card) {

        this.clientID = clientID;
        this.card = card;
    }

    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(clientID);
        content.add(card);
        return content;
    }
}
