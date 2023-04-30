package communication.protocol_v2v1.sendobjects.lobby;

import communication.protocol_v2v1.MessageBody;
import game.data.GameMap;
import game.data.boardelements.BoardElement;

import java.util.ArrayList;
import java.util.List;

public class GameStartedMessageBody extends MessageBody {

    List<List<List<BoardElement>>> gameMap;

    public GameStartedMessageBody(GameMap gameMap) {
        this.gameMap = gameMap.getMap();
    }


    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(gameMap);
        ;
        return content;
    }
}
