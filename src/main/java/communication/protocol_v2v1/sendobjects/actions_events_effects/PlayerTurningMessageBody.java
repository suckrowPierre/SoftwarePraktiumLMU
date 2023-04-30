package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageBody;
import game.data.Rotations;

import java.util.ArrayList;

public class PlayerTurningMessageBody extends MessageBody {

    int clientID;
    Rotations rotation;

    public PlayerTurningMessageBody(int clientID, Rotations rotation) {
        this.clientID = clientID;
        this.rotation = rotation;
    }


    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(clientID);
        content.add(rotation);
        return content;
    }

}
