package communication.protocol_v2v1.sendobjects.lobby;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;
import game.data.Maps;


//Maybe Refactoring because of same name
public class SelectMap extends ProtocolSendObject {

    public SelectMap(Maps[] availableMaps) {
        this.messageType = MessageType.SelectMap;
        this.messageBody = new SelectMapMessageBody(availableMaps);
    }

    public Maps[] getAvailableMaps(){
        return (Maps[]) messageBody.getBodyContent().get(0);
    }


}

