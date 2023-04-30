package communication.protocol_v2v1.sendobjects.game_move.upgrade_phase;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;
import game.data.cards.CardName;

public class RefillShop extends ProtocolSendObject {
    public RefillShop (CardName[] cards) {
        this.messageType = MessageType.RefillShop;
        this.messageBody = new RefillShopMessageBody(cards);
    }

    public CardName[] getCards() {return (CardName[]) messageBody.getBodyContent().get(0);}

    }

