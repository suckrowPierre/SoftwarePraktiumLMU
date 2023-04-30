package communication.protocol_v2v1.sendobjects.lobby;

import communication.protocol_v2v1.MessageBody;

import java.util.ArrayList;

public class PlayerAddedMessageBody extends MessageBody {
    int clientID;
    String name;
    int figure;

    public PlayerAddedMessageBody(int clientID, String name, int figure) {
        this.clientID = clientID;
        this.name = name;
        this.figure = figure;
    }

    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(clientID);
        content.add(name);
        content.add(figure);
        return content;
    }
}
