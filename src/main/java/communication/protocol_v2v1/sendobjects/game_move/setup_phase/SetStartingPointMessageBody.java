package communication.protocol_v2v1.sendobjects.game_move.setup_phase;

import communication.protocol_v2v1.MessageBody;

import java.util.ArrayList;

public class SetStartingPointMessageBody extends MessageBody {
    int x;
    int y;

    public SetStartingPointMessageBody(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(x);
        content.add(y);
        return content;
    }
}
