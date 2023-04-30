package communication.protocol_v2v1.sendobjects.lobby;

import communication.protocol_v2v1.MessageBody;
import game.data.Maps;

import java.util.ArrayList;

public class MapSelectedMessageBody extends MessageBody {

    Maps map;

    public MapSelectedMessageBody(Maps map) {
        this.map = map;
    }

    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(map);
        ;
        return content;
    }
}
