package communication.protocol_v2v1.sendobjects.game_move.programm_phase;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;
import game.data.cards.CardName;

public class SelectedCard extends ProtocolSendObject {
    public SelectedCard(CardName card, int register) {
        this.messageType = MessageType.SelectedCard;
        this.messageBody = new SelectedCardMessageBody(card, register);
    }

    public CardName getCard() {return (CardName) messageBody.getBodyContent().get(0);}

    public int getRegister() {return (int) messageBody.getBodyContent().get(1);}
}


