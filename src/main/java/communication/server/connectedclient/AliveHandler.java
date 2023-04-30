package communication.server.connectedclient;

/**
 * Singelton for checking alive
 * @author Pierre-Louis Suckrow
 */
public class AliveHandler {

    private static final int MAXALIVEREQUEST = 5;
    private int aliveCounter;
    private static AliveHandler instance;

    public AliveHandler() {
        aliveCounter = 0;
    }

    /**
     * times the Alive message has been send, if the counter reaches MAXALIVEREQUEST Server will disconnect client
     */
    public void incrementAliveCounter(){
        aliveCounter++;
    }

    public boolean isStillAlive(){
        if(aliveCounter > MAXALIVEREQUEST){
            return false;
        }
        return true;
    }

    public void resetAliveCounter(){
        aliveCounter = 0;
    }

    public static synchronized AliveHandler getInstance() {
        if (instance == null) {
            instance = new AliveHandler();
        }
        return instance;
    }
}
