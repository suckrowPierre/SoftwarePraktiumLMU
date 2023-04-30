package communication.protocol_v2v1.sendobjects.lobby;

import communication.protocol_v2v1.MessageBody;
import game.data.Maps;

import java.util.ArrayList;

public class SelectMapMessageBody extends MessageBody {

    Maps[] availableMaps;

    public SelectMapMessageBody(Maps[] availableMaps) {
        this.availableMaps = availableMaps;
    }

    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(availableMaps);
        ;
        return content;
    }
}
