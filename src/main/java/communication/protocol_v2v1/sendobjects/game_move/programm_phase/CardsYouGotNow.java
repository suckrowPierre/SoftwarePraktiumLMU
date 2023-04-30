package communication.protocol_v2v1.sendobjects.game_move.programm_phase;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;
import game.data.cards.CardName;

public class CardsYouGotNow extends ProtocolSendObject {
    public CardsYouGotNow(CardName[] cards) {
        this.messageType = MessageType.CardsYouGotNow;
        this.messageBody = new CardsYouGotNowMessageBody(cards);
    }

    public CardName[] getCards() {return (CardName[]) messageBody.getBodyContent().get(0);}
}


