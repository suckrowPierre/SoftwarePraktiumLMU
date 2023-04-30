package communication.protocol_v2v1.sendobjects.game_move.programm_phase;

import communication.protocol_v2v1.MessageBody;
import game.data.cards.CardName;

import java.util.ArrayList;

public class CardsYouGotNowMessageBody extends MessageBody {
    CardName[] cards;

    public CardsYouGotNowMessageBody(CardName[] cards) {
        this.cards = cards;
    }


    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(cards);
        return content;
    }
}
