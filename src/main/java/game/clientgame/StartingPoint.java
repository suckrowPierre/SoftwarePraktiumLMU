package game.clientgame;

public class StartingPoint {
    private int clientID;
    private int x;
    private int y;

    public StartingPoint(int clientID, int x, int y) {
        this.clientID = clientID;
        this.x = x;
        this.y = y;
    }

    public int getClientID() {
        return clientID;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
