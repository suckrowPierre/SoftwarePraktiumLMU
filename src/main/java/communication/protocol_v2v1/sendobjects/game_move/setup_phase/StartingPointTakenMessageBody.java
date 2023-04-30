package communication.protocol_v2v1.sendobjects.game_move.setup_phase;

import communication.protocol_v2v1.MessageBody;

import java.util.ArrayList;

public class StartingPointTakenMessageBody extends MessageBody {
    int x;
    int y;
    int clientID;

    public StartingPointTakenMessageBody(int clientID,int x, int y) {
        this.x = x;
        this.y = y;
        this.clientID = clientID;
    }


    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(x);
        content.add(y);
        content.add(clientID);
        return content;
    }


    @Override
    public String toString() {
        return "StartingPointTakenMessageBody{" +
                "x=" + x +
                ", y=" + y +
                ", clientID=" + clientID +
                '}';
    }
}
