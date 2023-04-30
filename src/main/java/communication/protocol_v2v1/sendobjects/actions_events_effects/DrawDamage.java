package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;
import game.data.cards.CardName;

public class DrawDamage extends ProtocolSendObject {
    public DrawDamage(int clientID, CardName[] cards) {
        this.messageType = MessageType.DrawDamage;
        this.messageBody = new DrawDamageMessageBody(clientID, cards);
    }

    public int getClientID() {return (int) messageBody.getBodyContent().get(0);}
    public CardName[] getCards() {return (CardName[]) messageBody.getBodyContent().get(1);}

}

