package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;
import game.data.cards.CardName;

public class SelectedDamage extends ProtocolSendObject {
    public SelectedDamage(CardName[] cards) {
        this.messageType = MessageType.SelectedDamage;
        this.messageBody = new SelectedDamageMessageBody(cards);
    }

    public CardName[] getCards() {return (CardName[]) messageBody.getBodyContent().get(0);}
}

