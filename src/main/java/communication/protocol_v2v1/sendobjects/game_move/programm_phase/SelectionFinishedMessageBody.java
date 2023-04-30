package communication.protocol_v2v1.sendobjects.game_move.programm_phase;

import communication.protocol_v2v1.MessageBody;

import java.util.ArrayList;

public class SelectionFinishedMessageBody extends MessageBody {
    int clientID;

    public SelectionFinishedMessageBody(int clientID) {
        this.clientID = clientID;
    }


    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(clientID);
        return content;
    }
}
