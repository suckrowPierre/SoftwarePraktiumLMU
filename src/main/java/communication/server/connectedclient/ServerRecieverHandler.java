package communication.server.connectedclient;

import communication.protocol_v2v1.ProtocolSendObject;
import communication.protocol_v2v1.sendobjects.actions_events_effects.*;
import communication.protocol_v2v1.sendobjects.cards.CardPlayed;
import communication.protocol_v2v1.sendobjects.cards.PlayCard;
import communication.protocol_v2v1.sendobjects.chat.ReceivedChat;
import communication.protocol_v2v1.sendobjects.chat.SendChat;
import communication.protocol_v2v1.sendobjects.game_move.programm_phase.CardSelected;
import communication.protocol_v2v1.sendobjects.game_move.programm_phase.SelectedCard;
import communication.protocol_v2v1.sendobjects.game_move.programm_phase.SelectionFinished;
import communication.protocol_v2v1.sendobjects.game_move.setup_phase.SetStartingPoint;
import communication.protocol_v2v1.sendobjects.game_move.setup_phase.StartingPointTaken;
import communication.protocol_v2v1.sendobjects.game_move.upgrade_phase.BuyUpgrade;
import communication.protocol_v2v1.sendobjects.game_move.upgrade_phase.UpgradeBought;
import communication.protocol_v2v1.sendobjects.lobby.*;
import communication.protocol_v2v1.sendobjects.special.ErrorMessage;
import communication.server.GameHandler;
import game.data.*;
import game.data.boardelements.Antenna;
import game.data.boardelements.BoardElement;
import game.data.boardelements.BoardElementTypes;
import game.data.cards.Card;
import game.data.cards.CardName;
import game.data.cards.upgrade.AdminPrivilege;
import game.data.cards.upgrade.MemorySwap;
import game.data.cards.upgrade.SpamBlocker;
import game.servergame.BoardElementInteraction;
import org.tinylog.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * handles recieved protocolSendObjects 
 * @author Simon Hümmer, Sarah Koch, Pierre-Louis Suckrow
 */
public class ServerRecieverHandler {

    int clientID;

    public ServerRecieverHandler(int clientID) {
        this.clientID = clientID;
    }

    public ProtocolSendObject handle(ProtocolSendObject recvSendObject) {
        if (recvSendObject != null) {
            return handleDifferentMessageTypes(recvSendObject);
        }
        //TODO: throw Error
        return null;
    }

    private ProtocolSendObject handleDifferentMessageTypes(ProtocolSendObject recvSendObject) {
        String methodname = "handle" + recvSendObject.getMessageType().toString();
        java.lang.reflect.Method method = null;
        try {
            method = this.getClass().getDeclaredMethod(methodname, ProtocolSendObject.class);
            // needed for private methods
            method.setAccessible(true);
        } catch (SecurityException e) {
        } catch (NoSuchMethodException e) {
            Logger.info("method: " + methodname + " missing");
        }
        if (method != null) {
            try {
                return (ProtocolSendObject) method.invoke(this, recvSendObject);
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        }
        return null;
    }

    private ProtocolSendObject handleAlive(ProtocolSendObject protocolSendObject) {
        AliveHandler.getInstance().resetAliveCounter();
        return null;
    }

    private ProtocolSendObject handlePlayerValues(ProtocolSendObject protocolSendObject) {
        PlayerValues playerValues = (PlayerValues) protocolSendObject;
        if (!checkIfFigureAlreadyTaken(playerValues.getFigure())) {
            if (!getThisConnectedPlayer().isPlayer()) {
                getThisConnectedPlayer().setFigure(playerValues.getFigure());
                getThisConnectedPlayer().setName(playerValues.getName());
                getConnectedClientsHandler().sendToAll(new PlayerAdded(clientID, playerValues.getName(), playerValues.getFigure()));
                getConnectedClientsHandler().notifyNewClientOfConnectedClients(
                        getThisConnectedClient());
                return null;
            } else {
                return new ErrorMessage("You already chose a figure.");
            }

        } else {
            return new ErrorMessage("Figure is already taken");
        }

    }

    private ProtocolSendObject handleSetStatus(ProtocolSendObject protocolSendObject) {
        SetStatus setStatus = (SetStatus) protocolSendObject;
        getThisConnectedPlayer().setReady(setStatus.isReady());
        getConnectedClientsHandler().checkIfFirstReady(clientID);
        getConnectedClientsHandler().sendToAll(new PlayerStatus(clientID, setStatus.isReady()));
        getConnectedClientsHandler().tryToLaunchGame();
        return null;
    }

    private ProtocolSendObject handleMapSelected(ProtocolSendObject protocolSendObject) {
        MapSelected mapSelected = (MapSelected) protocolSendObject;
        if (getConnectedClientsHandler().getFirstReadyClient() == clientID) {
            getConnectedClientsHandler().setMapSelected(true);
            getConnectedClientsHandler().setSelectedMap(mapSelected.getMap());
            getConnectedClientsHandler().sendToAll(mapSelected);
            getConnectedClientsHandler().tryToLaunchGame();
        } else {
            return new ErrorMessage("You cannot select a map.");
        }
        return null;
    }

    private boolean checkIfFigureAlreadyTaken(int figure) {
        for (ServerConnectedClient serverConnectedClient : getConnectedClientsHandler().getConnectedClientList().getConnectedClients()) {
            if (serverConnectedClient.getConnectedPlayer().getFigure() == figure) {
                return true;
            }
        }
        return false;
    }

    private ProtocolSendObject handleSendChat(ProtocolSendObject protocolSendObject) {
        SendChat sendChat = (SendChat) protocolSendObject;
        if (sendChat.getTo() == -1) {
            getConnectedClientsHandler().broadcast(clientID, new ReceivedChat(sendChat.getMessage(), clientID, false));
        } else {
            if (getConnectedClientsHandler().getConnectedClientList().getConnectedClientByClientID(sendChat.getTo()) != null) {
                getConnectedClientsHandler().sendTo(sendChat.getTo(), new ReceivedChat(sendChat.getMessage(), clientID, true));
            } else {
                return new ErrorMessage("You cannot send a PM to: " + sendChat.getTo() + " this player does not exist.");
            }
        }
        return null;

    }

    private ProtocolSendObject handleSetStartingPoint(ProtocolSendObject protocolSendObject) {
        if (
                getGameHandler().getPhase() == 0) {
            SetStartingPoint setStartingPoint = (SetStartingPoint) protocolSendObject;
            Position startingPoint = new Position(setStartingPoint.getX(), setStartingPoint.getY());
            if (
                    getGameHandler().isStartingPoint(startingPoint) && !
                            getGameHandler().isStartingPointTaken(startingPoint)) {
                getThisConnectedPlayer().setStartingPoint(startingPoint);
                switch (searchAntennaOrientation()) {
                    case top -> getThisConnectedPlayer().setRobot(new Robot(startingPoint, Orientations.top));
                    case bottom -> getThisConnectedPlayer().setRobot(new Robot(startingPoint, Orientations.bottom));
                    case right -> getThisConnectedPlayer().setRobot(new Robot(startingPoint, Orientations.right));
                    case left -> getThisConnectedPlayer().setRobot(new Robot(startingPoint, Orientations.left));
                    default -> new ErrorMessage("No Antenna found");
                }


                getGameHandler().sendToAllPlayers(new StartingPointTaken(clientID, startingPoint.getX(), startingPoint.getY()));

                getGameHandler().checkForNewPhase();

                getGameHandler().nextCurrentPlayer();

            } else {
                return new ErrorMessage("Invalid Starting Point");
            }
            return null;
        } else {
            return new ErrorMessage("wrong phase");
        }
    }

    private ProtocolSendObject handleSelectedCard(ProtocolSendObject protocolSendObject) {
        SelectedCard selectedCard = (SelectedCard) protocolSendObject;
        //TODO: ErrorMessage if not in Hand
        if (!
                getGameHandler().isTimerended()) {
            if (selectedCard.getCard() != CardName.Null) {
                if (getThisConnectedPlayer().getPlayerCards().putSelectedCardInRegister(selectedCard.getRegister(), selectedCard.getCard())) {
                    getConnectedClientsHandler().sendToAll(new CardSelected(clientID, selectedCard.getRegister(), true));
                    if (getThisConnectedPlayer().getPlayerCards().allCardsInRegister()) {

                        getGameHandler().sendToAllPlayers(new SelectionFinished(clientID));

                        getGameHandler().startTimer();
                        getGameHandler().endTimerPrematurely();
                    }
                } else {
                    return new ErrorMessage("Card not in your hand");
                }
            } else {
                getThisConnectedPlayer().getPlayerCards().resetCard(selectedCard.getRegister());

                getGameHandler().sendToAllPlayers(new CardSelected(clientID, selectedCard.getRegister(), false));
            }
            return null;

        } else {
            return new ErrorMessage("TimerEnded");
        }

    }

    private ProtocolSendObject handlePlayCard(ProtocolSendObject protocolSendObject) {
        PlayCard playCard = (PlayCard) protocolSendObject;

        if (getGameHandler().getPhase() == 2) {
            if (playCard.getCard().equals(CardName.SpamBlocker)) {
                ServerConnectedPlayer player = getThisConnectedPlayer();
                Card card = new SpamBlocker();
                getGameHandler().sendToAllPlayers(new CardPlayed(clientID, playCard.getCard()));
                card.activateCard(player);
            }
            if (playCard.getCard().equals(CardName.MemorySwap)) {
                ServerConnectedPlayer player = getThisConnectedPlayer();
                Card card = new MemorySwap();
                getGameHandler().sendToAllPlayers(new CardPlayed(clientID, playCard.getCard()));
                card.activateCard(player);
            }
        } else if (getGameHandler().getPhase() == 3) {
            //TODO: check if currentPlayer
            if (playCard.getCard() == getThisConnectedPlayer().getPlayerCards().getCurrentCard().getCardName()) {
                ServerConnectedPlayer player = getThisConnectedPlayer();
                int currentRegister = player.getPlayerCards().getCurrentRegister();
                Card playedCard = getThisConnectedPlayer().getPlayerCards().playCard();

                getGameHandler().sendToAllPlayers(new CardPlayed(clientID, playedCard.getCardName()));
                playedCard.activateCard(player);

                getGameHandler().checkToActiveBoardElements(currentRegister);

                getGameHandler().nextCurrentPlayer();
                if (currentRegister == 4) {
                    getGameHandler().checkForNewPhase();
                }
            } else {
                return new ErrorMessage("You cannot play this card. Card invalid.");
            }
        } else {
            return new ErrorMessage("wrong phase");
        }
        return null;
    }

    private ProtocolSendObject handleReturnCards(ProtocolSendObject protocolSendObject) {
        ReturnCards returnCards = (ReturnCards) protocolSendObject;
        CardName[] cardNames = returnCards.getReturnCards();
        ServerConnectedPlayer player = getThisConnectedPlayer();
        for (CardName cardName : cardNames) {
            for (int i = 0; i < player.getPlayerCards().getHandDeck().getHandDeck().length; i++) {
                if (player.getPlayerCards().getHandDeck().getHandDeck()[i].getCardName().equals(cardName)) {
                    player.getPlayerCards().getDiscardDeck().getDeck().add(player.getPlayerCards().getHandDeck().getHandDeck()[i]);
                    Card topCard = player.getPlayerCards().getProgrammingDeck().getTopCard();
                    player.getPlayerCards().getHandDeck().getHandDeck()[i] = topCard;
                    player.getPlayerCards().getProgrammingDeck().removeTopCard(player.getPlayerCards().getProgrammingDeck().getDeck());
                    break;
                }
            }
        }
        return null;
    }

    private ProtocolSendObject handleBuyUpgrade(ProtocolSendObject protocolSendObject) {
        BuyUpgrade buyUpgrade = (BuyUpgrade) protocolSendObject;
        if (
                getGameHandler().getPhase() == 1) {
            if (clientID ==
                    getGameHandler().getCurrentPlayer().getConnectedPlayer().getClientID()) {
                if (buyUpgrade.getIsBuying() == false) {
                    //skip
                    getThisConnectedPlayer().setUpgradeFinished(true);

                    getGameHandler().nextCurrentPlayer();
                } else {
                    if (
                            getGameHandler().getUpgradeShop().buyUpgradeCard(clientID, buyUpgrade.getCard())) {
                        getThisConnectedPlayer().setUpgradeFinished(true);

                        getGameHandler().sendToAllPlayers(new UpgradeBought(clientID, buyUpgrade.getCard()));

                        getGameHandler().sendToAllPlayers(new Energy(clientID, getThisConnectedPlayer().getEnergy(), "upgradecard"));

                        getGameHandler().nextCurrentPlayer();
                    } else {
                        return new ErrorMessage("invalid card");
                    }
                }
            } else {
                return new ErrorMessage("wrong phase");
            }

            //TODO: buy Card
        } else {
            return new ErrorMessage("Not your Turn");
        }
        return null;
    }

    private ProtocolSendObject handleRebootDirection(ProtocolSendObject protocolSendObject) {
        RebootDirection rebootDirection = (RebootDirection) protocolSendObject;
        Orientations orientation = rebootDirection.getDirection();
        ServerConnectedPlayer player = getThisConnectedPlayer();
        Orientations oldLineOfSight = player.getRobot().getLineOfSight();
        player.getRobot().setLineOfSight(orientation);
        while (oldLineOfSight != orientation) {

            getGameHandler().sendToAllPlayers(new PlayerTurning(clientID, Rotations.clockwise));
            switch (oldLineOfSight) {
                case top -> oldLineOfSight = Orientations.right;
                case right -> oldLineOfSight = Orientations.bottom;
                case bottom -> oldLineOfSight = Orientations.left;
                case left -> oldLineOfSight = Orientations.top;
            }
        }
        return null;
    }

    private ProtocolSendObject handleSelectedDamage(ProtocolSendObject protocolSendObject) {
        SelectedDamage selectedDamage = (SelectedDamage) protocolSendObject;
        CardName[] cardNames = selectedDamage.getCards();
        ArrayList<CardName> drawnCards = new ArrayList<>();
        ServerConnectedPlayer player = getThisConnectedPlayer();
        BoardElementInteraction boardElementInteraction =
                getGameHandler().getBoardElementInteraction();
        for (CardName cardName : cardNames) {
            if (cardName.equals(CardName.Trojan)) {
                Card topCard = boardElementInteraction.getTrojanDeck().getTopCard();
                player.getPlayerCards().getDiscardDeck().getDeck().add(topCard);
                boardElementInteraction.getTrojanDeck().removeTopCard(boardElementInteraction.getTrojanDeck().getDeck());
                drawnCards.add(topCard.getCardName());
            } else if (cardName.equals(CardName.Virus)) {
                Card topCard = boardElementInteraction.getVirusDeck().getTopCard();
                player.getPlayerCards().getDiscardDeck().getDeck().add(topCard);
                boardElementInteraction.getVirusDeck().removeTopCard(boardElementInteraction.getVirusDeck().getDeck());
                drawnCards.add(topCard.getCardName());
            } else if (cardName.equals(CardName.Worm)) {
                Card topCard = boardElementInteraction.getWormDeck().getTopCard();
                player.getPlayerCards().getDiscardDeck().getDeck().add(topCard);
                boardElementInteraction.getWormDeck().removeTopCard(boardElementInteraction.getWormDeck().getDeck());
                drawnCards.add(topCard.getCardName());
            }
        }

        if (!drawnCards.isEmpty()) {
            CardName[] damageCards = new CardName[drawnCards.size()];
            damageCards = drawnCards.toArray(damageCards);

            getGameHandler().sendToAllPlayers(new DrawDamage(player.getClientID(), damageCards));
        }

        return null;
    }

    private ProtocolSendObject handleChooseRegister(ProtocolSendObject protocolSendObject) {
        ChooseRegister chooseRegister = (ChooseRegister) protocolSendObject;
        int register = chooseRegister.getRegister();
        ServerConnectedPlayer player = getThisConnectedPlayer();
        Card card = new AdminPrivilege();
        card.activateCard(player);
        GameHandler.getInstance().adminPriv(clientID,register-1);
        GameHandler.getInstance().sendToAllPlayers(new RegisterChosen(player.getClientID(), register));
        player.setPriorityRegister(register-1);
        return null;
    }

    private Orientations searchAntennaOrientation() {
        GameMap map = getGameHandler().getMap();

        for (int x = 0; x < map.getMap().size(); x++) {
            for (int y = 0; y < map.getMap().get(0).size(); y++) {
                for (BoardElement boardElement : map.getMap().get(x).get(y)) {
                    if (boardElement.getType().equals(BoardElementTypes.Antenna)) {
                        Antenna antenna = (Antenna) boardElement;
                        return antenna.getOrientations()[0];
                    }
                }
            }
        }

        return null;
    }

    private ServerConnectedClient getThisConnectedClient() {
        return getConnectedClientsHandler().getConnectedClientList().getConnectedClientByClientID(clientID);
    }

    private ServerConnectedPlayer getThisConnectedPlayer() {
        return getThisConnectedClient().getConnectedPlayer();
    }

    private GameHandler getGameHandler() {
        return GameHandler.getInstance();
    }

    private ConnectedClientsHandler getConnectedClientsHandler(){
        return ConnectedClientsHandler.getInstance();
    }


}
