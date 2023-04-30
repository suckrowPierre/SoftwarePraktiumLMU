package communication.server.connectedclient;

import communication.protocol_v2v1.sendobjects.bodycontents.Groups;
import game.data.Position;
import game.data.Robot;
import game.data.cards.CardName;

/**
 * The Player of ServerConnectedClient
 * @author Simon Hümmer, Pierre-Louis Suckrow
 */
public class ServerConnectedPlayer {
    private Groups group;
    private Boolean isAi;
    private int clientID;
    private int figure;
    private int energy;
    private int checkPointCounter;
    private String name;
    private boolean ready;
    private Position startingPoint;
    private Robot robot;
    private ServerConnectedPlayerCards playerCards;
    private boolean rebooted;
    private boolean upgradeFinished;
    private Integer priorityRegister;

    public ServerConnectedPlayer(Groups group, Boolean isAi, int clientID) {
        this.group = group;
        this.isAi = isAi;
        this.clientID = clientID;
        this.figure = -1;
        this.energy = 5;
        this.ready = false;
        this.startingPoint = null;
        this.playerCards = new ServerConnectedPlayerCards();
        this.rebooted = false;
        this.upgradeFinished = false;
        this.priorityRegister = null;
    }



    public boolean isUpgradeFinished() {
        return upgradeFinished;
    }

    public void setUpgradeFinished(boolean upgradeFinished) {
        this.upgradeFinished = upgradeFinished;
    }

    public boolean isRebooted() {
        return rebooted;
    }

    public void setRebooted(boolean rebooted) {
        this.rebooted = rebooted;
    }


    public CardName[] drawHand() {
        playerCards.drawHandCards();
        return playerCards.getHandDeckNames();
    }


    public ServerConnectedPlayerCards getPlayerCards() {
        return playerCards;
    }

    public Position getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(Position position) {
        startingPoint = position;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Groups getGroup() {
        return group;
    }

    public Boolean getAi() {
        return isAi;
    }

    public int getClientID() {
        return clientID;
    }

    public boolean isPlayer() {
        if (figure >= 0) {
            return true;
        }
        return false;
    }

    public int getFigure() {
        return figure;
    }

    public void setFigure(int figure) {
        this.figure = figure;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getCheckPointCounter() {
        return checkPointCounter;
    }

    public void setCheckPointCounter(int checkPointCounter) {
        this.checkPointCounter = checkPointCounter;
    }

    public Robot getRobot() {
        return robot;
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }

    public int getPriorityRegister() {
        return priorityRegister;
    }

    public void setPriorityRegister(Integer priorityRegister) {
        this.priorityRegister = priorityRegister;
    }
}
