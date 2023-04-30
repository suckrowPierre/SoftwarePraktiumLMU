package communication.protocol_v2v1.sendobjects.game_move.activation_phase;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;
import game.data.cards.CardName;

public class ReplaceCard extends ProtocolSendObject {
    public ReplaceCard(int register, CardName newCard, int clientID) {
        this.messageType = MessageType.ReplaceCard;
        this.messageBody = new ReplaceCardMessageBody(register, newCard, clientID);
    }

    public int getRegister() {return (int) messageBody.getBodyContent().get(0);}
    public CardName getNewCard() {return (CardName) messageBody.getBodyContent().get(1);}
    public int getClientID() {return (int) messageBody.getBodyContent().get(2);}
}


