package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageBody;
import game.data.cards.CardName;

import java.util.ArrayList;

public class PickDamageMessageBody extends MessageBody {
    int count;
    CardName[] availablePiles;

    public PickDamageMessageBody(int count, CardName[] availablePiles) {

        this.count = count;
        this.availablePiles = availablePiles;
    }



    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(count);
        content.add(availablePiles);
        return content;
    }
}
