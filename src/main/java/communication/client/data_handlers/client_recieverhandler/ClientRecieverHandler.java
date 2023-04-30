package communication.client.data_handlers.client_recieverhandler;

import communication.client.data_handlers.DataHandler;
import communication.client.data_handlers.gamedata_recieverhandler.GameDateRecieverHandler;
import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;
import communication.protocol_v2v1.SendObjectJsonWrapper;
import communication.protocol_v2v1.sendobjects.special.ErrorMessage;
import org.tinylog.Logger;
import util.LoggingTags;
import util.PPLogger;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * handles received SendObjects and opens corresponding Method
 * @author Simon Naab, Sarah Koch, Pierre-Louis Suckrow
 */
public abstract class ClientRecieverHandler extends DataHandler {
    protected ProtocolSendObject current_recieved;
    protected GameDateRecieverHandler gameDateRecieverHandler;
    public WritingClientRecieverHandler overClass;

    public ClientRecieverHandler() {
        current_recieved = null;
        view_connected = false;
        game_connected = false;
    }

    public void handle(String recvjson) {
        Logger.tag(LoggingTags.communication.toString()).info(PPLogger.logCommunicationReceiving, recvjson);
        ProtocolSendObject recvSendObject = convertStringtoProtocolSendObject(recvjson);
        if (recvSendObject != null) {
            current_recieved = recvSendObject;

            if (current_recieved.getMessageType() == MessageType.Alive) {
                handleAlive(recvjson);
            } else {
                handleDifferentMessageTypes();
            }
        }
    }

    protected void handleDifferentMessageTypes() {
        String methodname = "handle" + current_recieved.getMessageType().toString();
        java.lang.reflect.Method method = null;
        try {
            method = this.getClass().getDeclaredMethod(methodname, ProtocolSendObject.class);
            // needed for private methods
            method.setAccessible(true);
        } catch (SecurityException e) {
            Logger.error(e);
        } catch (NoSuchMethodException e) {
            try {
                //Need this because of WritingClientRecieverHandler
                Method[] methods = this.getClass().getSuperclass().getDeclaredMethods();
                for (Method methodi : methods) {
                    if (methodi.getName().equals(methodname)) {
                        method = methodi;
                    }
                }
                method.setAccessible(true);
            } catch (SecurityException e2) {
                Logger.error(e);
            } catch (Exception e2) {
                Logger.error(e2);
                Logger.warn("method: " + methodname + " missing");
            }
        }
        if (method != null) {
            try {
                method.invoke(this, current_recieved);
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                Logger.error(e);
            }
        }
    }

    protected ProtocolSendObject convertStringtoProtocolSendObject(String json) {
        return SendObjectJsonWrapper.dewrapString(json);
    }

    protected abstract void sendSendObject(ProtocolSendObject tosend);

    protected abstract void handleAlive(String recvjson);

    protected abstract void handleConnectionUpdate(ProtocolSendObject recvSendObject);

    protected abstract void handleError(ProtocolSendObject recvSendObject);

    protected abstract void handleReceivedChat(ProtocolSendObject recvSendObject);

    protected abstract void handleMapSelected(ProtocolSendObject recvSendObject);

    protected abstract void handlePlayerStatus(ProtocolSendObject recvSendObject);

    protected abstract void handlePlayerAdded(ProtocolSendObject recvSendObject);

    protected abstract void handleSelectMap(ProtocolSendObject recvSendObject);

    protected abstract void handleWelcome(ProtocolSendObject recvSendObject);

    protected abstract void handleHelloClient(ProtocolSendObject recvSendObject);

    protected abstract void handleErrorCases(ErrorMessage error);

    protected abstract void handleCurrentPlayer(ProtocolSendObject recvSendObject);

    protected abstract void handleGameStarted(ProtocolSendObject recvSendObject);

    protected abstract void handleActivePhase(ProtocolSendObject recvSendObject);

    protected abstract void handleStartingPointTaken(ProtocolSendObject recvSendObject);

    protected abstract void handleYourCards(ProtocolSendObject recvSendObject);

    protected abstract void handleNotYourCards(ProtocolSendObject recvSendObject);

    protected abstract void handleCardSelected(ProtocolSendObject recvSendObject);

    protected abstract void handleSelectionFinished(ProtocolSendObject recvSendObject);

    protected abstract void handleTimerStarted(ProtocolSendObject recvSendObject);

    protected abstract void handleTimerEnded(ProtocolSendObject recvSendObject);

    protected abstract void handleCardsYouGotNow(ProtocolSendObject recvSendObject);

    protected abstract void handleCurrentCards(ProtocolSendObject recvSendObject);

    protected abstract void handleCardPlayed(ProtocolSendObject recvSendObject);

    protected abstract void handlePlayerTurning(ProtocolSendObject recvSendObject);

    protected abstract void handleMovement(ProtocolSendObject recvSendObject);

    protected abstract void handleReplaceCard(ProtocolSendObject recvSendObject);

    protected abstract void handleCheckPointReached(ProtocolSendObject recvSendObject);

    protected abstract void handleGameFinished(ProtocolSendObject recvSendObject);

    protected abstract void handleEnergy(ProtocolSendObject recvSendObject);

    protected abstract void handleReboot(ProtocolSendObject recvSendObject);

    protected abstract void handlePickDamage(ProtocolSendObject recvSendObject);

    protected abstract void handleDrawDamage(ProtocolSendObject recvSendObject);

    protected abstract void handleRefillShop(ProtocolSendObject recvSendObject);

    protected  abstract void handleExchangeShop(ProtocolSendObject recvSendObject);

    protected abstract void handleUpgradeBought(ProtocolSendObject recvSendObject);

    protected abstract void handleCheckpointMoved(ProtocolSendObject recvSendObject);

    protected abstract void handleLaserAnimation(ProtocolSendObject recvSendObject);

    protected abstract void handleRegisterChosen(ProtocolSendObject recvSendObject);

}
