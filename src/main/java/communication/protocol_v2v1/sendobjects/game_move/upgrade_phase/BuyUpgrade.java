package communication.protocol_v2v1.sendobjects.game_move.upgrade_phase;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;
import game.data.cards.CardName;

public class BuyUpgrade extends ProtocolSendObject {
    public BuyUpgrade (boolean isBuying, CardName card) {
        this.messageType = MessageType.BuyUpgrade;
        this.messageBody = new BuyUpgradeMessageBody(isBuying, card);
    }

    public boolean getIsBuying() {return (boolean) messageBody.getBodyContent().get(0);}
    public CardName getCard() {return (CardName) messageBody.getBodyContent().get(1);}

}
