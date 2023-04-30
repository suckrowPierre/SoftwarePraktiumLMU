package game.clientgame.clientgamehandler;

public class GameHandlerSingelton {

    private static GameHandlerSingelton instance;
    private ClientGameHandler clientGameHandler;

    public GameHandlerSingelton() {
    }

    public ClientGameHandler getClientGameHandler() {
        return clientGameHandler;
    }

    public void setClientGameHandler(ClientGameHandler clientGameHandler) {
        this.clientGameHandler = clientGameHandler;
    }

    public static synchronized GameHandlerSingelton getInstance() {
        if (instance == null) {
            instance = new GameHandlerSingelton();
        }
        return instance;
    }
}
