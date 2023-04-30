package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageBody;
import game.data.cards.CardName;

import java.util.ArrayList;

public class DrawDamageMessageBody extends MessageBody {
    int clientID;
    CardName[] cards;

    public DrawDamageMessageBody(int clientID, CardName[] cards) {
        this.clientID = clientID;
        this.cards = cards;
    }


    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(clientID);
        content.add(cards);
        return content;
    }
}
