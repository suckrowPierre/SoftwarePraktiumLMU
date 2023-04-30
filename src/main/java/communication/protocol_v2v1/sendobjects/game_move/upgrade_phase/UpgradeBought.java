package communication.protocol_v2v1.sendobjects.game_move.upgrade_phase;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;
import game.data.cards.CardName;

public class UpgradeBought extends ProtocolSendObject {
    public UpgradeBought (int clientID, CardName card) {
        this.messageType = MessageType.UpgradeBought;
        this.messageBody = new UpgradeBoughtMessageBody(clientID, card);
    }

    public int getClientID() {return (int) messageBody.getBodyContent().get(0);}
    public CardName getCard() {return (CardName) messageBody.getBodyContent().get(1);}
}
