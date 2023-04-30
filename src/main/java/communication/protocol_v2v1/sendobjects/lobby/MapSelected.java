package communication.protocol_v2v1.sendobjects.lobby;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;
import game.data.Maps;

// Maybe Refactoring because of same name
public class MapSelected extends ProtocolSendObject {

  public MapSelected(Maps map) {
    this.messageType = MessageType.MapSelected;
    this.messageBody = new MapSelectedMessageBody(map);
  }

  public Maps getMap() {
    return (Maps) messageBody.getBodyContent().get(0);
  }
}

