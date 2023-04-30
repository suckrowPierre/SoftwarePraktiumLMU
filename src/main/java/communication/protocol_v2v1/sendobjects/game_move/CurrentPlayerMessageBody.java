package communication.protocol_v2v1.sendobjects.game_move;

import communication.protocol_v2v1.MessageBody;

import java.util.ArrayList;

public class CurrentPlayerMessageBody extends MessageBody {
    int clientID;

    public CurrentPlayerMessageBody(int clientID) {

        this.clientID = clientID;
    }

    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(clientID);
        return content;
    }
}
