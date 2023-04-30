package communication.protocol_v2v1.sendobjects.game_move.upgrade_phase;

import communication.protocol_v2v1.MessageBody;
import game.data.cards.CardName;

import java.util.ArrayList;

public class UpgradeBoughtMessageBody extends MessageBody {
    int clientID;
    CardName card;

    public UpgradeBoughtMessageBody(int clientID, CardName card) {
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
