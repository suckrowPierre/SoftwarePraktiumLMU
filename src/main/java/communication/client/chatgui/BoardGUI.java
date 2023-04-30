package communication.client.chatgui;


import communication.client.chatgui.boardelements.*;
import game.data.Orientations;
import game.data.Rotations;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.Group;
import org.tinylog.Logger;
import util.LoggingTags;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sarah class for dynamically displaying the board and all its elements
 */
public class BoardGUI extends Pane {
    public static final int TILESIZE = 60;
    private int x;
    private int y;
    private GUITile[][] board;
    private boolean setstartingpoint = false;

    private Group tileGroup = new Group();
    private Group robotGroup = new Group();
    private Group boardelementgroup = new Group();
    private Group checkpointgroup = new Group();
    private Group laserGroup = new Group();
    private BoardController mycontroller;

    public BoardGUI(int x, int y, BoardController controller) {
        Logger.tag(LoggingTags.gui.toString()).debug("Creating board inside scrollpane.");
        this.x = x;
        this.y = y;
        mycontroller = controller;
        board = new GUITile[x][y];

        setPrefSize(x*TILESIZE, y*TILESIZE);
        getChildren().addAll(tileGroup, boardelementgroup, checkpointgroup, robotGroup, laserGroup);

        //create all Tiles of the board
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {

                GUITile tile = new GUITile(i, j);
                board[i][j] = tile;
                tileGroup.getChildren().add(tile);
            }
        }

        // create all robots
        for (int i = 0; i<6; i++) {
            Robot robot = new Robot(i, 0, 0);
            robotGroup.getChildren().add(robot);
        }
        Model.getInstance().setBoard(this);

        //create all robotlasers
        laserGroup.getChildren().add(new GUIRobotLaser(0, 0, Orientations.left));
        laserGroup.getChildren().add(new GUIRobotLaser(0, 0, Orientations.right));
        laserGroup.getChildren().add(new GUIRobotLaser(0, 0, Orientations.top));
        laserGroup.getChildren().add(new GUIRobotLaser(0, 0, Orientations.bottom));
    }

    /** Method to move Elements in back layers to the front and to turn robots correctly */
    public void finishBoard(){
        List<Node> tomove = new ArrayList<>();
        Orientations antennadirection = null;
        //move lasers to the front and get Antenna direction
        for(Node node: boardelementgroup.getChildren()){
            if(node instanceof GUILaser){
                tomove.add(node);
            }
            if(node instanceof GUIAntenna){
                antennadirection = ((GUIAntenna) node).getOrientation();
            }
        }
        for(Node node: tomove){
            node.toFront();
        }
        for(Node node: robotGroup.getChildren()){
            switch(antennadirection){
                case top -> ((Robot) node).turn(Rotations.counterclockwise);
                case bottom -> ((Robot) node).turn(Rotations.clockwise);
                case left -> {
                    ((Robot) node).turn(Rotations.clockwise);
                    ((Robot) node).turn(Rotations.clockwise);
                }
            }
        }
    }

    /** Method to place GUIBoardElements on the board */
    public void setBoardElement(GUIBoardElement element){
        if (element instanceof GUICheckPoint){
            checkpointgroup.getChildren().add(element);
        }
        else {
            boardelementgroup.getChildren().add(element);
        }
    }

    /** Get a specific robot on the board to tell it how to move */
    public Robot getRobot(int id){
        for(Node node: robotGroup.getChildren()){
            Robot robot = (Robot) node;
            if(robot.getRobotid() == id){
                return robot;
            }
        }
        return null;
    }

    /** Get a specific checkpoint on the board to tell it how to move */
    public GUICheckPoint getCheckpoint(int id){
        for(Node node: checkpointgroup.getChildren()){
            GUICheckPoint checkpoint = (GUICheckPoint) node;
            if(checkpoint.getCheckpointId() == id){
                return checkpoint;
            }
        }
        return null;
    }

    /** Get a specific energyspace on the board */
    public GUIEnergySpace getEnergySpace(int x, int y){
        for(Node node: boardelementgroup.getChildren()){
            if(node instanceof GUIEnergySpace){
                if(((GUIEnergySpace) node).getxPos() == x && ((GUIEnergySpace) node).getYPos() == y){
                    return (GUIEnergySpace) node;
                }
            }
        }
        return null;
    }

    /** Get a specific robotlaser on the board to tell it how to move */
    public GUIRobotLaser getRobotLaser(Orientations orientation){
        for(Node node: laserGroup.getChildren()){
            GUIRobotLaser laser = (GUIRobotLaser) node;
            if(laser.getOrientation() == orientation){
                System.out.println("getting laser");
                return laser;
            }
        }
        return null;
    }


    public boolean getSetStartingPoint(){
        return setstartingpoint;
    }

    /** Make the start points on the board clickable or not */
    public void allowToSetStartingPoint(boolean b){
        setstartingpoint = b;
        if(b){
            showTextBelowMap("Please choose a starting point by clicking it.");
        }
        else{
            showTextBelowMap("");
        }
    }

    public void showTextBelowMap(String t){
        mycontroller.showText(t);
    }

    public int getX(){
        return x*TILESIZE;
    }

    public  int getY(){
        return y*TILESIZE;
    }


}
