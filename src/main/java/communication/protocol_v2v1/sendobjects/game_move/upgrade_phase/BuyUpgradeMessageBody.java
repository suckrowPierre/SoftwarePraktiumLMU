package communication.protocol_v2v1.sendobjects.game_move.upgrade_phase;

import communication.protocol_v2v1.MessageBody;
import game.data.cards.CardName;

import java.util.ArrayList;

public class BuyUpgradeMessageBody extends MessageBody {
    boolean isBuying;
    CardName card;

    public BuyUpgradeMessageBody(boolean isBuying, CardName card) {
        this.isBuying = isBuying;
        this.card = card;
    }


    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(isBuying);
        content.add(card);
        return content;
    }

}
