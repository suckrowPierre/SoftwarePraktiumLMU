package communication.server;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import communication.protocol_v2v1.ProtocolSendObject;
import communication.protocol_v2v1.sendobjects.actions_events_effects.GameFinished;
import communication.protocol_v2v1.sendobjects.game_move.ActivePhase;
import communication.protocol_v2v1.sendobjects.game_move.CurrentPlayer;
import communication.protocol_v2v1.sendobjects.game_move.activation_phase.ActiveCard;
import communication.protocol_v2v1.sendobjects.game_move.activation_phase.CurrentCards;
import communication.protocol_v2v1.sendobjects.game_move.programm_phase.*;
import communication.protocol_v2v1.sendobjects.lobby.GameStarted;
import communication.server.connectedclient.ConnectedClientsHandler;
import communication.server.connectedclient.ServerConnectedClient;
import game.data.*;
import game.data.boardelements.BoardElement;
import game.data.boardelements.BoardElementTypes;
import game.data.cards.CardName;
import game.servergame.BoardElementInteraction;
import game.servergame.Upgrade;
import org.tinylog.Logger;
import util.LoggingTags;

import java.util.*;

/**
 * Handles round logic and game setup
 * @author Pierre-Louis Suckrow
 */
public class GameHandler {

    private static GameHandler instance;
    boolean gameStarted;
    private GameMap map;
    private ServerConnectedClient currentPlayer;
    private LinkedList<ServerConnectedClient> players;
    private int phase;
    private boolean timerEnded;
    private boolean timerStarted;
    private BoardElementInteraction boardElementInteraction;
    private Upgrade upgradeShop;
    private boolean firstround;
    Timer timer = new Timer("GameTimer");
    private final long DELAY = 30000;
    private int[] activationOrder;
    private int[][] activationOrderActivationPhase;
    int playerRotationClock;
    int currentRegister;

    //TODO: BROADCAST and SENDTOALL only for players



    public GameHandler() {
        setup();
    }

    private void setup(){
        firstround = true;
        gameStarted = false;
        players = new LinkedList<>();
        phase = 0;
        timerEnded = false;
        timerStarted = false;
        activationOrder = null;
        activationOrderActivationPhase = null;
        boardElementInteraction = new BoardElementInteraction();
        this.currentRegister = 0;
        playerRotationClock = 0;
    }

    /**
     * @param integers Converts Integer List into Array
     * @return
     */
    //region util
    private static int[] convertIntegers(List<Integer> integers) {
        int[] ret = new int[integers.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = integers.get(i).intValue();
        }
        return ret;
    }

    /**
     * Singleton
     * @return Instance
     */
    public synchronized static GameHandler getInstance() {
        if (instance == null) {
            instance = new GameHandler();
        }
        return instance;
    }


    //region SetupGame
    /**
     * Load Players from ConnectedClientsHandler and add them to list players
     */
    private void loadPlayersFromConnectedClients() {
        for (ServerConnectedClient serverConnectedClient : ConnectedClientsHandler.getInstance().getConnectedClientList().getConnectedClients()) {
            if (serverConnectedClient.getConnectedPlayer().isPlayer()) {
                players.add(serverConnectedClient);
            }
        }
    }

    /**
     * Load Map and set missing lasers
     * @param mapToLoad
     */
    private void loadMap(Maps mapToLoad) {
        try {
            this.map = readJsonMap(mapToLoad);
            map.setMissingLasers();
        } catch (Exception e) {
            Logger.info(e);
        }

    }
    //endregion

    /**
     * Send loaded Map to all players
     */
    private void sendMapToALl() {
        sendToAllPlayers(new GameStarted(map));
    }

    /**
     * start game with specific map a new round
     * @param mapToLoad
     */
    public void startGame(Maps mapToLoad) {
        if (!gameStarted) {
            loadPlayersFromConnectedClients();
            activationOrderActivationPhase = new int[5][players.size()];
            activationOrder = new int[players.size()];
            fillActivationOrder();
            fillActivationOrderActivationPhase();
            loadMap(mapToLoad);
            sendMapToALl();
            upgradeShop = new Upgrade();
            gameStarted = true;
            ConnectedClientsHandler.getInstance().sendToAll(new ActivePhase(phase));
            updateCurrentPlayer();
            sendCurrentPlayer();
            firstround = true;
        }
    }


    /**
     * load the sequence in which the players play: order by first connected
     */
    private void fillActivationOrder(){
        int i = 0;
        for (ServerConnectedClient connectedClient:players){
            activationOrder[i] = connectedClient.getConnectedPlayer().getClientID();
            i++;
        }
    }

    /**
     * adminprivilege: change activationOrder for specific register in ActivationPhase
     * @param clientID
     * @param register
     */
    public void adminPriv(int clientID, int register){
        activationOrderActivationPhase[register] = pushToArray(activationOrderActivationPhase[register],clientID);
    }

    /**
     * needed for adminPriv, changes position from ID to top
     * @param arr
     * @param ID
     * @return new Array with ID on position 0
     */
    static private int[] pushToArray(int[] arr, int ID){
        int[] arrReturn = new int[arr.length];
        arrReturn[0] = ID;
        int i = 0;

        while (arr[i] != ID){
            arrReturn[i+1] = arr[i];
            i++;
        }
        i++;
        while (i < arr.length){
            arrReturn[i] = arr[i];
            i++;
        }


        return arrReturn;
    }


    /**
     * set ActivationOrder to standard
     */
    private void fillActivationOrderActivationPhase(){
        for (int i = 0; i < 5; i++){
                activationOrderActivationPhase[i] = activationOrder;
            }

        }



    //region getters + setters
    public GameMap getMap() {
        return map;
    }

    public int getPhase() {
        return phase;
    }

    public Upgrade getUpgradeShop() {
        return upgradeShop;
    }

    public ServerConnectedClient getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isTimerended() {
        return timerEnded;
    }

    public LinkedList<ServerConnectedClient> getPlayers() {
        return players;
    }

    //endregion

    //region checks

    /**
     * @return Array of clientIDs that were too slow to fill register
     */
    private int[] getTooSlows() {
        ArrayList<Integer> toslows = new ArrayList<>();
        for (ServerConnectedClient player : players) {
            if (!player.getConnectedPlayer().getPlayerCards().allCardsInRegister()) {
                toslows.add(player.getConnectedPlayer().getClientID());
            }
        }
        return convertIntegers(toslows);
    }

    public BoardElementInteraction getBoardElementInteraction() {
        return boardElementInteraction;
    }

    /**
     * @return boolean if all players rebooted in a round
     */
    private boolean allPlayersRebooted() {
        for (ServerConnectedClient connectedClient : players) {
            if (!connectedClient.getConnectedPlayer().isRebooted()) {
                return false;
            }
        }
        return true;
    }

    /**
     * activates boardElement for register, if all players played that register
     * @param register
     */
    public void checkToActiveBoardElements(int register) {
        if (allPlayersPlayedRegister(register)) {
            boardElementInteraction.activate(register);
        }
    }

    /**
     * @param register
     * @return if all players played register
     */
    private boolean allPlayersPlayedRegister(int register) {
        for (ServerConnectedClient player : players) {
            if (!(player.getConnectedPlayer().getPlayerCards().getRegisterDeckByPosition(register).getCardName() == CardName.Null)) {
                return false;
            }

        }
        return true;
    }

    /**
     * @return if all players finished their upgrade phase
     */
    private boolean allPlayersFinishedUpgrade() {
        for (ServerConnectedClient player : players) {
            if (!(player.getConnectedPlayer().isUpgradeFinished())) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param startingPoint
     * @return if startingPoint is taken by a player at the specified position
     */
    public boolean isStartingPointTaken(Position startingPoint) {
        for (ServerConnectedClient serverConnectedClient : players) {
            if ((serverConnectedClient.getConnectedPlayer().getStartingPoint() != null) && serverConnectedClient.getConnectedPlayer().getStartingPoint().equals(startingPoint)) {
                return true;
            }
        }
        return false;
    }

    //endregion

    //region setupPhase

    //endregion

    /**
     * @param startingPoint
     * @return if there is a starting point on specified position
     */
    public boolean isStartingPoint(Position startingPoint) {
        for (BoardElement boardElement : map.getMap().get(startingPoint.getX()).get(startingPoint.getY())) {
            if (boardElement.getType() == BoardElementTypes.StartPoint) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return if all players finished setup phase
     */
    public boolean allPlayersChoseStartingPoint() {
        if (players.size() > 0) {
            for (ServerConnectedClient serverConnectedClient : players) {
                if (serverConnectedClient.getConnectedPlayer().getStartingPoint() == null) {
                    return false;
                }

            }
            return true;
        } else {
            return false;
        }
    }
    //endregion

    /**
     * deals 9 cards to every player
     */
    //region programmingPhase
    private void dealCardsToPlayers() {
        Logger.tag(LoggingTags.game.toString()).info("dealing cards");
        for (ServerConnectedClient player : players) {
            CardName[] hand = player.getConnectedPlayer().drawHand();
            System.out.println(hand);
            player.safeWriteInOwnOutStream(new YourCards(hand));
            broadcastToPlayers(player.getConnectedPlayer().getClientID(), new NotYourCards(player.getConnectedPlayer().getClientID(), hand.length));
        }
    }
    //endregion

    /**
     * start countdown
     */
    public synchronized void startTimer() {

        if (!timerEnded && !timerStarted && phase == 2) {
            timerStarted = true;
            Logger.tag(LoggingTags.game.toString()).info("timer started");
            sendToAllPlayers(new TimerStarted());
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if(!timerEnded) {
                        endTimer();
                    }
                }
            };
            timer.schedule(task, DELAY);
        }
    }


    /**
     * ends timer if all players have filled their register
     */
    public synchronized void endTimerPrematurely(){
        int[] toSlow = getTooSlows();
        if(toSlow.length <= 0){
            timer.cancel();
            Logger.tag(LoggingTags.game.toString()).info("timer ended prematurely");
            timerEnded = true;
            sendToAllPlayers(new TimerEnded(toSlow));
            checkForNewPhase();
        }
    }


    /**
     * end Timer and deal missing cards to players which were too slow
     */
    private void endTimer(){
        Logger.tag(LoggingTags.game.toString()).info("timer ended");
        timerEnded = true;
        dealCardToTooSlow();
        checkForNewPhase();
    }

    /**
     * deal missing cards to players that where too slow with filling their register
     */
    private void dealCardToTooSlow(){
        int[] toSlow = getTooSlows();
        sendToAllPlayers(new TimerEnded(toSlow));
        if (toSlow.length >= 1) {
            for (int toSlowClientID : toSlow) {
                CardName[] cardNames = ConnectedClientsHandler.getInstance().getConnectedClientList().getConnectedClientByClientID(toSlowClientID).getConnectedPlayer().getPlayerCards().autoFillRegisters();

                ConnectedClientsHandler.getInstance().getConnectedClientList().getConnectedClientByClientID(toSlowClientID).safeWriteInOwnOutStream(new CardsYouGotNow(cardNames));
                sendToAllPlayers(new SelectionFinished(toSlowClientID));
                sendCurrentCards();
            }
        }
    }

    //region activationPhase

    /**
     * send first card of each player
     */
    private void sendCurrentCards() {
        ActiveCard[] activeCards = new ActiveCard[players.size()];
        int i = 0;
        for (ServerConnectedClient player : players) {
            activeCards[i] = new ActiveCard(player.getConnectedPlayer().getClientID(), player.getConnectedPlayer().getPlayerCards().getCurrentCard().cardName);
            i++;
        }
        sendToAllPlayers(new CurrentCards(activeCards));
    }

    //region game methods

    /**
     * remove player with specific clientID, if only one player remains he won the game
     * @param clientID
     */
    public void removePlayer(int clientID){
        if(gameStarted) {
            Logger.info("removing " + clientID + " from game");
            players.removeIf(serverConnectedClient -> serverConnectedClient.getConnectedPlayer().getClientID() == clientID);

            if (players.size() <= 1) {
                endGame(players.get(0).getConnectedPlayer().getClientID());
            } else {
                if (clientID == currentPlayer.getConnectedPlayer().getClientID()) {
                    nextCurrentPlayer();
                }
            }
        }
    }

    /**
     * end game with winner, resets GameHandler and ConnectedClientHandler for new game;
     * @param clientIDWinner
     */
    public void endGame(int clientIDWinner){
        Logger.info("ending game with winner: " + clientIDWinner);
        sendToAllPlayers(new GameFinished(clientIDWinner));
        setup();
        ConnectedClientsHandler.getInstance().reset();
    }


    /**
     * rotate Player in specified order
     */
    private void rotatePlayers() {
        Logger.tag(LoggingTags.game.toString()).info("rotating players");
        if (playerRotationClock < players.size() - 1) {
            playerRotationClock++;
        } else {
            if(phase == 3 && currentRegister < 5){
                currentRegister++;
            }
            playerRotationClock = 0;
        }
        updateCurrentPlayer();
    }


    /**
     * rotate Player and sends out the new currentPlayer
     */
    public synchronized void nextCurrentPlayer() {
        rotatePlayers();
        if (!currentPlayer.getConnectedPlayer().isRebooted()) {
            if (allPlayersFinishedUpgrade()) {
                checkForNewPhase();
            } else {
                sendCurrentPlayer();
            }
            if (phase == 3 && !allPlayersPlayedRegister(4)) {
                sendCurrentCards();
            }
        } else if (phase == 3 && (!allPlayersRebooted())) {
            nextCurrentPlayer();
        } else if (phase == 3 && allPlayersPlayedRegister(4)) {
            sendCurrentPlayer();
            checkForNewPhase();
        }
    }

    /**
     * updates currentPlayer after he has been rotated
     */
    public void updateCurrentPlayer() {
        if(phase == 3 && currentRegister < 5) {
            this.currentPlayer = getPlayerByID(activationOrderActivationPhase[currentRegister][playerRotationClock]);
        }else {
            this.currentPlayer = getPlayerByID(activationOrder[playerRotationClock]);
        }
        Logger.tag(LoggingTags.game.toString()).info("new current player:" + currentPlayer);
    }

    ServerConnectedClient getPlayerByID(int ID){
        return players.stream().filter(connectedClient -> connectedClient.getConnectedPlayer().getClientID() == ID).findFirst().orElse(null);
    }

    /**
     * send currentPlayer to all Players
     */
    public void sendCurrentPlayer() {
        sendToAllPlayers(new CurrentPlayer(currentPlayer.getConnectedPlayer().getClientID()));
    }

    /**
     * check if new phase has begun
     */
    public void checkForNewPhase() {
        switch (phase) {
            case 0 -> {
                if (allPlayersChoseStartingPoint()) {
                    setupPhase1();
                }
            }
            case 1 -> setupPhase2();
            case 2 -> setupPhase3();
            case 3 -> {
                if(allPlayersPlayedRegister(4)) {
                    clearRebooted();
                    setupPhase1();
                }else{
                    nextCurrentPlayer();
                }
            }
        }
    }

    /**
     * setups activation phase
     */
    private void setupPhase3() {
        phase = 3;
        currentRegister = 0;
        Logger.tag(LoggingTags.game.toString()).info("Phase 3 activation phase");
        sendToAllPlayers(new ActivePhase(phase));
        System.out.println(Arrays.deepToString(activationOrderActivationPhase));
        updateCurrentPlayer();
        sendCurrentPlayer();
    }


    /**
     * setup programming phase
     */
    private void setupPhase2() {
        clearUpgradeFinished();
        timerStarted = false;
        timerEnded = false;
        phase = 2;
        Logger.tag(LoggingTags.game.toString()).info("Phase 2 programming phase");
        sendToAllPlayers(new ActivePhase(phase));
        dealCardsToPlayers();
    }

    /**
     * setup new upgrade phase
     */
    private void setupPhase1() {
        fillActivationOrderActivationPhase();
        Logger.tag(LoggingTags.game.toString()).info("Beginning new Round");
        phase = 1;
        Logger.tag(LoggingTags.game.toString()).info("Phase 1 upgrade");
        sendToAllPlayers(new ActivePhase(phase));
        if (firstround) {
            upgradeShop.refillShop();
            firstround = false;
        } else {
            upgradeShop.checkShop();
            sendCurrentPlayer();
        }

    }

    //endregion


    public void clearRebooted() {
        for (ServerConnectedClient serverConnectedClient : players) {
            serverConnectedClient.getConnectedPlayer().setRebooted(false);
        }
    }

    public void clearUpgradeFinished() {
        for (ServerConnectedClient serverConnectedClient : players) {
            serverConnectedClient.getConnectedPlayer().setUpgradeFinished(false);
        }
    }


    //region communication

    /**
     * sends ProtocolSendObject to all Players
     * @param protocolSendObject
     */
    public synchronized void sendToAllPlayers(ProtocolSendObject protocolSendObject) {
        for (ServerConnectedClient serverConnectedClient : players) {
            serverConnectedClient.safeWriteInOwnOutStream(protocolSendObject);
        }
    }

    /**
     * send ProtocolSendObject to all Players except one
     * @param excludedClient
     * @param protocolSendObject
     */
    public synchronized void broadcastToPlayers(int excludedClient, ProtocolSendObject protocolSendObject) {
        for (ServerConnectedClient serverConnectedClient : players) {
            if (serverConnectedClient.getConnectedPlayer().getClientID() != excludedClient) {
                serverConnectedClient.safeWriteInOwnOutStream(protocolSendObject);
            }
        }
    }


    /**
     * @param map
     * @return map loaded from json
     * @throws Exception
     */
    private GameMap readJsonMap(Maps map) throws Exception {
        JsonParser parser = new JsonParser();
        JsonElement jsontree = parser.parse(MapStrings.getMaps(map));
        JsonObject jsonmap = jsontree.getAsJsonObject();
        JsonArray jarray = jsonmap.getAsJsonArray("gameMap");
        return GameMapJsonDeserializer.deserializeArray(jarray);
    }
    //endregion

}


