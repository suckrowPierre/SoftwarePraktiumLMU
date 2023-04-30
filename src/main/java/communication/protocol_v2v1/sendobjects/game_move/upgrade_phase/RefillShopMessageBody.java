package communication.protocol_v2v1.sendobjects.game_move.upgrade_phase;

import communication.protocol_v2v1.MessageBody;
import game.data.cards.CardName;

import java.util.ArrayList;

public class RefillShopMessageBody extends MessageBody {
    CardName[] cards;

    public RefillShopMessageBody(CardName[] card) {
        this.cards = card;
    }


    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(cards);
        return content;
    }

}
