package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;
import game.data.cards.CardName;

public class ReturnCards extends ProtocolSendObject {

    public ReturnCards(CardName[] cardNames) {
        this.messageType = MessageType.ReturnCards;
        this.messageBody = new ReturnCardsMessageBody(cardNames);
    }

    public CardName[] getReturnCards() {
        return (CardName[]) messageBody.getBodyContent().get(0);
    }

}
