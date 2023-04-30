package game.servergame;

import communication.protocol_v2v1.sendobjects.actions_events_effects.CheckPointReached;
import communication.server.GameHandler;
import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.GameMap;
import game.data.boardelements.BoardElement;
import game.data.boardelements.BoardElementTypes;
import game.data.boardelements.CheckPoint;
import org.tinylog.Logger;
import util.LoggingTags;

/**
 * This class implements the activation of the BoardElement CheckPoint
 *
 * @author Simon Hümmer
 */
public class CheckPointActivation {

    public CheckPointActivation() {
    }

    /**
     * If the player reaches a CheckPoint, the method checks if it is the right order and
     * ends the game a player reached a CheckPoints
     */
    public void activateCheckPoint(BoardElement boardElement, ServerConnectedPlayer player, GameMap map) {
        Logger.tag(LoggingTags.game.toString()).info("Activating CheckPoint for player: " + player.getName());

        CheckPoint checkPoint = (CheckPoint) boardElement;

        if (checkPoint.getCount() == player.getCheckPointCounter() + 1) {
            player.setCheckPointCounter(player.getCheckPointCounter() + 1);
            Logger.tag(LoggingTags.game.toString()).info(player.getName() + " CheckPointCounter: " + player.getCheckPointCounter());
            GameHandler.getInstance().sendToAllPlayers(new CheckPointReached(player.getClientID(), player.getCheckPointCounter()));
        }

        if (player.getCheckPointCounter() == countCheckPoints(map)) {
            GameHandler.getInstance().endGame(player.getClientID());
        }
    }

    /**
     * Counts how many CheckPoints there are on the map
     */
    private int countCheckPoints(GameMap map) {
        int counter = 0;
        for (int x = 0; x < map.getMap().size(); x++) {
            for (int y = 0; y < map.getMap().get(0).size(); y++) {
                for (BoardElement boardElement : map.getMap().get(x).get(y)) {
                    if (boardElement.getType().equals(BoardElementTypes.CheckPoint)) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }
}