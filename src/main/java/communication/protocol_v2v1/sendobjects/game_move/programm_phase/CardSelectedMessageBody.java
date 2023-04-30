package communication.protocol_v2v1.sendobjects.game_move.programm_phase;

import communication.protocol_v2v1.MessageBody;

import java.util.ArrayList;

public class CardSelectedMessageBody extends MessageBody {
    int clientID;
    int register;
    boolean filled;

    public CardSelectedMessageBody(int clientID, int register, boolean filled) {
        this.clientID = clientID;
        this.register = register;
        this.filled = filled;
    }


    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(clientID);
        content.add(register);
        content.add(filled);
        return content;
    }
}
