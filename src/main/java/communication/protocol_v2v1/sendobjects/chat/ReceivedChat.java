package communication.protocol_v2v1.sendobjects.chat;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;


//Maybe Refactoring because of same name
public class ReceivedChat extends ProtocolSendObject {

    public ReceivedChat(String message, int from, boolean isPrivate) {
        this.messageType = MessageType.ReceivedChat;
        this.messageBody = new ReceivedChatMessageBody(message, from, isPrivate);
    }


    public String getMessage(){
        return (String) messageBody.getBodyContent().get(0);
    }

    public int getFrom(){
        return (int) messageBody.getBodyContent().get(1);
    }

    public boolean isPrivate(){
        return (boolean) messageBody.getBodyContent().get(2);
    }


}

