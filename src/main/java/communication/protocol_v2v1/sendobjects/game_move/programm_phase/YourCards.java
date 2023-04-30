package communication.protocol_v2v1.sendobjects.game_move.programm_phase;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;
import game.data.cards.CardName;

public class YourCards extends ProtocolSendObject {
    public YourCards(CardName[] cardsInHand) {
        this.messageType = MessageType.YourCards;
        this.messageBody = new YourCardsMessageBody(cardsInHand);
    }

    public CardName[] getCardsInHand() {return (CardName[]) messageBody.getBodyContent().get(0);}
}


