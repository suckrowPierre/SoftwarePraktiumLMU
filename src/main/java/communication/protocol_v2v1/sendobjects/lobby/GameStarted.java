package communication.protocol_v2v1.sendobjects.lobby;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;
import game.data.GameMap;
import game.data.boardelements.BoardElement;

import java.util.List;


//Maybe Refactoring because of same name
public class GameStarted extends ProtocolSendObject {

    public GameStarted(GameMap gameMap) {
        this.messageType = MessageType.GameStarted;
        this.messageBody = new GameStartedMessageBody(gameMap);
    }

    public GameMap getGameMap(){
        List<List<List<BoardElement>>> map = (List<List<List<BoardElement>>>) messageBody.getBodyContent().get(0);
        return new GameMap(map);
    }


}

