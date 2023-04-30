package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;

public class Energy extends ProtocolSendObject {
    public Energy(int clientID, int count, String source) {
        this.messageType = MessageType.Energy;
        this.messageBody = new EnergyMessageBody(clientID, count, source);
    }

    public int getClientID() {return (int) messageBody.getBodyContent().get(0);}
    public int getCount() {return (int) messageBody.getBodyContent().get(1);}
    public String getSource() {return (String) messageBody.getBodyContent().get(2);}
}

