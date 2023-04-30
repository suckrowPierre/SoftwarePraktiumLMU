package communication.protocol_v2v1.sendobjects.game_move.programm_phase;

import communication.protocol_v2v1.MessageBody;

import java.util.ArrayList;

public class NotYourCardsMessageBody extends MessageBody {
    int clientID;
    int cardsInHand;

    public NotYourCardsMessageBody(int clientID, int cardsInHand) {
        this.clientID = clientID;
        this.cardsInHand = cardsInHand;
    }


    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(clientID);
        content.add(cardsInHand);
        return content;
    }
}
