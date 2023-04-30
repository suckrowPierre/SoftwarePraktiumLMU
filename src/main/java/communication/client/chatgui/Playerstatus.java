package communication.client.chatgui;

import java.util.ArrayList;

/**
 * @author Sarah represents entrys of reached checkpoints next to board and makes them comparable
 */
public class Playerstatus implements Comparable<Playerstatus>{
    private int id;
    private String name;
    private ArrayList<Integer> reachedCheckpoints;

    public Playerstatus(int id, String name, int checkpoint){
        this.id = id;
        this.name = name;
        reachedCheckpoints = new ArrayList<Integer>();
        reachedCheckpoints.add(checkpoint);
    }

    public void addCheckpoint(int cp){
        reachedCheckpoints.add(cp);
    }

    public int getId(){
        return id;
    }

    public String toString(){
        String checkpoints = "";
        boolean firstelem = true;
        for (int checkpoint: reachedCheckpoints){
            if(firstelem){
                checkpoints += checkpoint;
                firstelem = false;
            }
            else {
                checkpoints += ", " + checkpoint;
            }
        }
        return name + " (" + id + "): "+ checkpoints;
    }

    public ArrayList<Integer> getReachedCheckpoints(){
        return reachedCheckpoints;
    }

    public int compareTo(Playerstatus p){
        return this.reachedCheckpoints.size() - p.getReachedCheckpoints().size();
    }
}
