package game.clientgame.clientgamehandler;

import communication.client.WritingClientSingelton;
import communication.client.upgradeshop.UpgradeShop;
import communication.protocol_v2v1.sendobjects.actions_events_effects.SelectedDamage;
import communication.protocol_v2v1.sendobjects.game_move.setup_phase.SetStartingPoint;
import game.clientgame.ai.AI;
import game.clientgame.ai.RandomAI;
import game.data.Direction;
import game.data.Position;
import game.data.boardelements.BoardElementTypes;
import game.data.cards.CardName;

/**
 * Handles Changes to ClientGame for AI
 * @author Pierre-Louis Suckrow
 */
public class AIClientGameHandler extends ClientGameHandler {

    private AI ai;

    public AIClientGameHandler() {
        super();
        ai = new RandomAI(this);
    }

    public void setCurrentPlayer(int currentPlayerrecv) {
        super.setCurrentPlayer(currentPlayerrecv);
        if(currentPlayerrecv == WritingClientSingelton.getInstance().getClientID() && phase == 1){
            UpgradeShop.getInstance().skip();
        }
    }

    @Override
    protected void handleStartingPoint() {
        Position position = ai.selectStartingPoint(map.getAllBoardElementPlusPositionByType(BoardElementTypes.StartPoint));
        System.out.println(position.getX() + position.getY());
        WritingClientSingelton.getInstance().writeOutStream(new SetStartingPoint(position.getX(), position.getY()));
    }

    public void recieveCards(CardName[] cardNames) {
        super.recieveCards(cardNames);
        ai.programm();
    }

    public Direction chooseRebootDirection(){
        return ai.chooseRebootDirection();
    }

    public void chooseDamageCards(int count, CardName[] cardNames){
        CardName[] cards = ai.chooseDamageCards(count,cardNames);
        WritingClientSingelton.getInstance().writeOutStream(new SelectedDamage(cards));
    }

}
