package communication.protocol_v2v1.sendobjects.game_move.activation_phase;

import communication.protocol_v2v1.MessageBody;

import java.util.ArrayList;

public class CurrentCardsMessageBody extends MessageBody {
    ActiveCard[] activeCards;

    public CurrentCardsMessageBody(ActiveCard[] activeCards) {
        this.activeCards = activeCards;
    }


    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(activeCards);
        return content;
    }
}
