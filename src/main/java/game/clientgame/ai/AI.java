package game.clientgame.ai;

import game.clientgame.clientgamehandler.AIClientGameHandler;
import game.data.BoardElementPlusPosition;
import game.data.Direction;
import game.data.Position;
import game.data.cards.CardName;

import java.util.ArrayList;

/**
 * for connecting different AIs
 * @author Pierre-Louis Suckrow
 */
public abstract class AI {

    AIClientGameHandler gameHandler;

    public AI(AIClientGameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    public abstract Position selectStartingPoint(ArrayList<BoardElementPlusPosition> boardElementPlusPositions);

    public abstract void programm();

    public abstract Direction chooseRebootDirection();

    public abstract CardName[] chooseDamageCards(int count, CardName[] cardNames);

}
