package communication.protocol_v2v1.sendobjects.game_move.programm_phase;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;

public class NotYourCards extends ProtocolSendObject {
    public NotYourCards(int clientID, int cardsInHand) {
        this.messageType = MessageType.NotYourCards;
        this.messageBody = new NotYourCardsMessageBody(clientID, cardsInHand);
    }

    public int getClientID() {return (int) messageBody.getBodyContent().get(0);}
    public int getCardsInHand() {return (int) messageBody.getBodyContent().get(1);}
}


