package communication.protocol_v2v1.sendobjects.cards;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;
import game.data.cards.CardName;

public class CardPlayed  extends ProtocolSendObject {
    public CardPlayed (int clientID, CardName card) {
        this.messageType = MessageType.CardPlayed;
        this.messageBody = new CardPlayedMessageBody(clientID, card);
    }

    public int getClientID() {return (int) messageBody.getBodyContent().get(0);}
    public CardName getCard(){
        return (CardName) messageBody.getBodyContent().get(1);
    }


}

