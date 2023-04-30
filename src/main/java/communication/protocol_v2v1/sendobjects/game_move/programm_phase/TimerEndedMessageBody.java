package communication.protocol_v2v1.sendobjects.game_move.programm_phase;

import communication.protocol_v2v1.MessageBody;

import java.util.ArrayList;

public class TimerEndedMessageBody extends MessageBody {
    int[] clientIDs;

    public TimerEndedMessageBody(int[] clientIDs) {
        this.clientIDs = clientIDs;
    }


    @Override
    public ArrayList getBodyContent() {
        ArrayList content = new ArrayList();
        content.add(clientIDs);
        return content;
    }
}
