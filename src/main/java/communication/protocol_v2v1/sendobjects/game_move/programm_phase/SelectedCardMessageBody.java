package communication.protocol_v2v1.sendobjects.game_move.programm_phase;

import communication.protocol_v2v1.MessageBody;
import game.data.cards.CardName;

import java.util.ArrayList;

public class SelectedCardMessageBody extends MessageBody {
    CardName card;
    int register;

    public SelectedCardMessageBody(CardName card, int register) {
        this.card = card;
        this.register = register;
    }


    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(card);
        content.add(register);
        return content;
    }

}
