package communication.client.lobby;

public class ConnectedClient {
    private final int ID;
    private final String name;
    private final int figure;
    private boolean ready;

    public ConnectedClient(int ID, String name, int figure) {
        this.ID = ID;
        this.name = name;
        this.figure = figure;
        this.ready = false;

    }

    public boolean isPlayer(){
        return !(figure == -1);
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getFigure() {
        return figure;
    }





}
