package communication.protocol_v2v1.sendobjects.game_move.activation_phase;

import communication.protocol_v2v1.MessageBody;
import game.data.cards.CardName;

import java.util.ArrayList;

public class ReplaceCardMessageBody extends MessageBody {
    int register;
    CardName newCard;
    int clientID;

    public ReplaceCardMessageBody(int register, CardName newCard, int clientID) {
        this.register = register;
        this.newCard = newCard;
        this.clientID = clientID;
    }


    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(register);
        content.add(newCard);
        content.add(clientID);
        return content;
    }
}
