package game.servergame;

import communication.protocol_v2v1.sendobjects.actions_events_effects.DrawDamage;
import communication.protocol_v2v1.sendobjects.actions_events_effects.Movement;
import communication.protocol_v2v1.sendobjects.actions_events_effects.PickDamage;
import communication.protocol_v2v1.sendobjects.actions_events_effects.PlayerTurning;
import communication.server.GameHandler;
import communication.server.connectedclient.ServerConnectedClient;
import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.GameMap;
import game.data.Orientations;
import game.data.Position;
import game.data.Rotations;
import game.data.boardelements.BoardElement;
import game.data.boardelements.BoardElementTypes;
import game.data.cards.Card;
import game.data.cards.CardName;
import game.data.decks.damage.SpamDeck;
import game.data.decks.damage.TrojanDeck;
import game.data.decks.damage.VirusDeck;
import game.data.decks.damage.WormDeck;
import org.tinylog.Logger;
import util.LoggingTags;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This class implements the activation of all BoardElements and the Robot lasers
 *
 * @author Simon Hümmer
 */
public class BoardElementInteraction {

    private final SpamDeck spamDeck;
    private final TrojanDeck trojanDeck;
    private final VirusDeck virusDeck;
    private final WormDeck wormDeck;

    private final GearActivation gearActivation = new GearActivation(this);
    private final LaserActivation laserActivation = new LaserActivation(this);
    private final LaserRayActivation laserRayActivation = new LaserRayActivation(this);
    private final EnergySpaceActivation energySpaceActivation = new EnergySpaceActivation();
    private final CheckPointActivation checkPointActivation = new CheckPointActivation();
    private final ConveyorBeltActivation conveyorBeltActivation = new ConveyorBeltActivation(this);
    private final PushPanelActivation pushPanelActivation = new PushPanelActivation(this);
    private final RebootRobot rebootRobot = new RebootRobot(this);
    private final RobotLaser robotLaser = new RobotLaser(this);

    public BoardElementInteraction() {
        spamDeck = new SpamDeck();
        spamDeck.initialize();

        trojanDeck = new TrojanDeck();
        trojanDeck.initialize();

        virusDeck = new VirusDeck();
        virusDeck.initialize();

        wormDeck = new WormDeck();
        wormDeck.initialize();
    }

    /**
     * This method gets activated after each round
     * If a player stands on a specific BoardElement, it gets activated
     * Then all Robots shoot their lasers and the CheckPoints rotate if needed
     */
    public void activate(int currentRound) {
        Logger.tag(LoggingTags.game.toString()).info("Activating all BoardElements");

        GameMap map = GameHandler.getInstance().getMap();
        LinkedList<ServerConnectedClient> players = GameHandler.getInstance().getPlayers();

        for (ServerConnectedClient client : players) {
            ServerConnectedPlayer player = client.getConnectedPlayer();
            int xPosition = player.getRobot().getPosition().getX();
            int yPosition = player.getRobot().getPosition().getY();

            for (BoardElement boardElement : map.getMap().get(xPosition).get(yPosition)) {
                if (boardElement.getType().equals(BoardElementTypes.ConveyorBelt)) {
                    conveyorBeltActivation.activateConveyorBelt(boardElement, player, map);
                } else if (boardElement.getType().equals(BoardElementTypes.PushPanel)) {
                    pushPanelActivation.activatePushPanel(boardElement, player, map, currentRound);
                } else if (boardElement.getType().equals(BoardElementTypes.Gear)) {
                    gearActivation.activateGear(boardElement, player);
                } else if (boardElement.getType().equals(BoardElementTypes.Laser)) {
                    laserActivation.activateLaser(boardElement, player);
                } else if (boardElement.getType().equals(BoardElementTypes.LaserRay)) {
                    laserRayActivation.activateLaserRay(boardElement, player);
                } else if (boardElement.getType().equals(BoardElementTypes.EnergySpace)) {
                    energySpaceActivation.activateEnergySpace(boardElement, player);
                } else if (boardElement.getType().equals(BoardElementTypes.CheckPoint)) {
                    checkPointActivation.activateCheckPoint(boardElement, player, map);
                } else if (boardElement.getType().equals(BoardElementTypes.Pit)) {
                    rebootRobot.rebootRobot(player, map, player.getRobot().getPosition().getX(), player.getRobot().getPosition().getY());
                }
            }
        }
        robotLaser.activateRobotLaser(map);
        GameHandler.getInstance().getMap().rotateCheckPoints();
    }

    /**
     * Gives the player an amount of Spam Cards or
     * tells the player to pick damage cards
     *
     * @param count the amount of damage the player takes
     */
    public void takeDamage(int count, ServerConnectedPlayer player) {
        ArrayList<CardName> drawnCards = new ArrayList<>();
        ArrayList<CardName> availablePiles = new ArrayList<>();
        int damageCount = 0;

        for (int i = 0; i < count; i++) {
            if (!spamDeck.getDeck().isEmpty()) {
                Card spamCard = spamDeck.getTopCard();
                player.getPlayerCards().getDiscardDeck().getDeck().add(spamCard);
                spamDeck.removeTopCard(spamDeck.getDeck());
                drawnCards.add(spamCard.getCardName());
            } else {
                damageCount++;
            }
        }

        if (spamDeck.getDeck().isEmpty() && damageCount != 0) {
            if (!trojanDeck.getDeck().isEmpty()) {
                availablePiles.add(CardName.Trojan);
            }
            if (!virusDeck.getDeck().isEmpty()) {
                availablePiles.add(CardName.Virus);
            }
            if (!wormDeck.getDeck().isEmpty()) {
                availablePiles.add(CardName.Worm);
            }
            CardName[] damageCards = new CardName[availablePiles.size()];
            damageCards = availablePiles.toArray(damageCards);

            GameHandler.getInstance().sendToAllPlayers(new PickDamage(damageCount, damageCards));
        }

        if (!drawnCards.isEmpty()) {
            CardName[] cardNames = new CardName[drawnCards.size()];
            cardNames = drawnCards.toArray(cardNames);

            GameHandler.getInstance().sendToAllPlayers(new DrawDamage(player.getClientID(), cardNames));
        }
    }

    /**
     * Checks if on the position stands a robot and
     * if so the robot gets replaced or rebooted if it falls of the map when replaced
     *
     * @param position    position to test
     * @param orientation line of sight of the robot who wants to move
     */
    public void positionOccupied(Position position, GameMap map, Orientations orientation) {
        for (ServerConnectedPlayer player : getPlayer()) {
            if (player.getRobot().getPosition().equals(position)) {
                Position newPosition;
                switch (orientation) {
                    case top -> {
                        newPosition = new Position(position.getX(), position.getY() - 1);
                        if (!fellOffMap(player, map, newPosition.getX(), newPosition.getY()) && checkPit(player, map, newPosition.getX(), newPosition.getY())) {
                            positionOccupied(newPosition, map, orientation);
                            player.getRobot().setPosition(newPosition);
                            GameHandler.getInstance().sendToAllPlayers(new Movement(player.getClientID(), player.getRobot().getPosition().getX(), player.getRobot().getPosition().getY()));
                        }
                    }
                    case bottom -> {
                        newPosition = new Position(position.getX(), position.getY() + 1);
                        if (!fellOffMap(player, map, newPosition.getX(), newPosition.getY()) && checkPit(player, map, newPosition.getX(), newPosition.getY())) {
                            positionOccupied(newPosition, map, orientation);
                            player.getRobot().setPosition(newPosition);
                            GameHandler.getInstance().sendToAllPlayers(new Movement(player.getClientID(), player.getRobot().getPosition().getX(), player.getRobot().getPosition().getY()));
                        }
                    }
                    case right -> {
                        newPosition = new Position(position.getX() + 1, position.getY());
                        if (!fellOffMap(player, map, newPosition.getX(), newPosition.getY()) && checkPit(player, map, newPosition.getX(), newPosition.getY())) {
                            positionOccupied(newPosition, map, orientation);
                            player.getRobot().setPosition(newPosition);
                            GameHandler.getInstance().sendToAllPlayers(new Movement(player.getClientID(), player.getRobot().getPosition().getX(), player.getRobot().getPosition().getY()));
                        }
                    }
                    case left -> {
                        newPosition = new Position(position.getX() - 1, position.getY());
                        if (!fellOffMap(player, map, newPosition.getX(), newPosition.getY()) && checkPit(player, map, newPosition.getX(), newPosition.getY())) {
                            positionOccupied(newPosition, map, orientation);
                            player.getRobot().setPosition(newPosition);
                            GameHandler.getInstance().sendToAllPlayers(new Movement(player.getClientID(), player.getRobot().getPosition().getX(), player.getRobot().getPosition().getY()));
                        }
                    }
                }
            }
        }
    }

    /**
     * Rotates the player clockwise
     *
     * @param lineOfSight line fo sight of the robot
     */
    public void rotateClockwise(Orientations lineOfSight, ServerConnectedPlayer player) {
        switch (lineOfSight) {
            case top -> player.getRobot().setLineOfSight(Orientations.right);
            case right -> player.getRobot().setLineOfSight(Orientations.bottom);
            case bottom -> player.getRobot().setLineOfSight(Orientations.left);
            case left -> player.getRobot().setLineOfSight(Orientations.top);
        }
        GameHandler.getInstance().sendToAllPlayers(new PlayerTurning(player.getClientID(), Rotations.clockwise));
    }

    /**
     * Rotates the player counterclockwise
     *
     * @param lineOfSight line of sight of the robot
     */
    public void rotateCounterclockwise(Orientations lineOfSight, ServerConnectedPlayer player) {
        switch (lineOfSight) {
            case top -> player.getRobot().setLineOfSight(Orientations.left);
            case right -> player.getRobot().setLineOfSight(Orientations.top);
            case bottom -> player.getRobot().setLineOfSight(Orientations.right);
            case left -> player.getRobot().setLineOfSight(Orientations.bottom);
        }
        GameHandler.getInstance().sendToAllPlayers(new PlayerTurning(player.getClientID(), Rotations.counterclockwise));
    }

    /**
     * Checks if the player would fall of the map and
     * reboots the robot if so
     */
    public boolean fellOffMap(ServerConnectedPlayer player, GameMap map, int xPosition, int yPosition) {
        int mapXSize = map.getMap().size();
        int mapYSize = map.getMap().get(0).size();

        if (xPosition < 0 || yPosition < 0 || xPosition >= mapXSize || yPosition >= mapYSize) {
            rebootRobot.rebootRobot(player, map, xPosition, yPosition);
            return true;
        }
        return false;
    }

    /**
     * Checks if the player would fall into a Pit and
     * reboots the robot if so
     */
    public boolean checkPit(ServerConnectedPlayer player, GameMap map, int xPosition, int yPosition) {
        for (BoardElement boardElement : map.getMap().get(xPosition).get(yPosition)) {
            if (boardElement.getType().equals(BoardElementTypes.Pit)) {
                rebootRobot.rebootRobot(player, map, xPosition, yPosition);
                return false;
            }
        }
        return true;
    }

    public void rebootRobot(ServerConnectedPlayer player, GameMap map, int xPosition, int yPosition) {
        rebootRobot.rebootRobot(player, map, xPosition, yPosition);
    }

    public ArrayList<ServerConnectedPlayer> getPlayer() {
        ArrayList<ServerConnectedPlayer> player = new ArrayList<>();
        LinkedList<ServerConnectedClient> players = GameHandler.getInstance().getPlayers();

        for (ServerConnectedClient client : players) {
            player.add(client.getConnectedPlayer());
        }

        return player;
    }

    public SpamDeck getSpamDeck() {
        return spamDeck;
    }

    public TrojanDeck getTrojanDeck() {
        return trojanDeck;
    }

    public VirusDeck getVirusDeck() {
        return virusDeck;
    }

    public WormDeck getWormDeck() {
        return wormDeck;
    }
}