package communication.protocol_v2v1.sendobjects.special;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;

public class ConnectionUpdate extends ProtocolSendObject {
    public ConnectionUpdate(int clientID, boolean isConnected, Actions action) {
        this.messageType = MessageType.ConnectionUpdate;
        this.messageBody = new ConnectionUpdateMessageBody(clientID, isConnected, action);
    }

    public int getID(){
        return (int) messageBody.getBodyContent().get(0);
    }

    public boolean isConnected(){
        return (boolean) messageBody.getBodyContent().get(1);
    }

    public Actions actions(){
        return (Actions) messageBody.getBodyContent().get(2);
    }


}


