package communication.protocol_v2v1.sendobjects.actions_events_effects;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;

public class RegisterChosen extends ProtocolSendObject {
    public RegisterChosen(int clientID, int register) {
        this.messageType = MessageType.RegisterChosen;
        this.messageBody = new RegisterChosenMessageBody(clientID, register);
    }

    public int getClientID() {return (int) messageBody.getBodyContent().get(0);}
    public int getRegister() {return (int) messageBody.getBodyContent().get(1);}
}
