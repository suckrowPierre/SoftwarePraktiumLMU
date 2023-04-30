package communication.protocol_v2v1.sendobjects.game_move;

import communication.protocol_v2v1.MessageBody;

import java.util.ArrayList;

public class ActivePhaseMessageBody extends MessageBody {
    int phase;

    public ActivePhaseMessageBody(int phase) {

        this.phase = phase;
    }

    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(phase);
        return content;
    }
}
