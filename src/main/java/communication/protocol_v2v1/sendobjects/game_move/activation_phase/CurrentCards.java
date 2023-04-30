package communication.protocol_v2v1.sendobjects.game_move.activation_phase;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;

public class CurrentCards extends ProtocolSendObject {

    public CurrentCards(ActiveCard[] activeCards) {
        this.messageType = MessageType.CurrentCards;
        this.messageBody = new CurrentCardsMessageBody(activeCards);
    }

    public ActiveCard[] getActiveCards() {return (ActiveCard[]) messageBody.getBodyContent().get(0);}
}


