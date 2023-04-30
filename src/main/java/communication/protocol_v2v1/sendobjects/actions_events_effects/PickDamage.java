package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;
import game.data.cards.CardName;

public class PickDamage extends ProtocolSendObject {
    public PickDamage(int count, CardName[] availablePiles) {
        this.messageType = MessageType.PickDamage;
        this.messageBody = new PickDamageMessageBody(count, availablePiles);
    }

    public int getCount() {return (int) messageBody.getBodyContent().get(0);}
    public CardName[] getAvailablePiles() {return (CardName[]) messageBody.getBodyContent().get(1);}
}

