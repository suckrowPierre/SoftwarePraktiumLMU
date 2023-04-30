package communication.protocol_v2v1.sendobjects.lobby;

import communication.protocol_v2v1.MessageBody;

import java.util.ArrayList;

public class PlayerValuesMessageBody extends MessageBody {
    String name;
    int figure;

    public PlayerValuesMessageBody(String name, int figure) {
        this.name = name;
        this.figure = figure;
    }

    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(name);
        content.add(figure);
        return content;
    }
}
