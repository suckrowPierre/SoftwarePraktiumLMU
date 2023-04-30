package communication.client.chatgui;

import game.data.cards.CardName;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import communication.client.*;
import javafx.collections.ObservableMap;

import java.util.ArrayList;
import java.util.Collections;

/** @author Sarah, Tea holds data of the user interface */
public class Model {

  private static Model instance;

  /**
   * Singleton
   *
   * @return instance of the model
   */
  public static Model getInstance() {
    if (instance == null) instance = new Model();
    return instance;
  }

  /** list of messages we want to show on screen */
  private final ObservableList<String> messagelist = FXCollections.observableArrayList("Welcome to Roborally!",
          "How to send a direct message:", "$playerid$ message", "Send \"bye\" to leave the game.");

  public ObservableList<String> messagelistProperty() {
    return messagelist;
  }

  // function to add a string to the list
  public void saveMessage(String s) {
    messagelist.add(s);
  }

  // function to remove a string from list
  public void remove(int index) {
    messagelist.remove(index);
  }

  /** saves the players robotid */
  private int myrobotid;

  public void setMyrobotid(int id){
    myrobotid = id;
  }

  public int getMyrobotid(){
    return myrobotid;
  }

  /** reference to the client */
  private GuiUserClient myClient;

  public void setMyClient(GuiUserClient c) {
    myClient = c;
  }

  public GuiUserClient getMyClient() {
    return myClient;
  }

  /** this property holds the string that will be added */
  private final StringProperty textFieldContent = new SimpleStringProperty("");

  public StringProperty getTextFieldContent() {
    return textFieldContent;
  }

  /** these properties hold ipaddress, portnumber and username on the login page */
  private final StringProperty ipaddress = new SimpleStringProperty("localhost");

  public StringProperty getip() {
    return ipaddress;
  }

  private final StringProperty portnumber = new SimpleStringProperty("12345");

  public StringProperty getport() {
    return portnumber;
  }

  private final StringProperty username = new SimpleStringProperty("");

  public StringProperty getusername() {
    return username;
  }

  /** map of playerid to playername */
  private final ObservableMap<Integer, String> idtoname = FXCollections.observableHashMap();

  public ObservableMap<Integer, String> getidtoname() {
    return idtoname;
  }

  public void setIdtoname(int userid, String name){
    idtoname.put(userid,name);
  }

  public void getnamefromid(int userid){
    idtoname.get(userid);
  }

  /** List of available Maps */
  private final ObservableList<String> maplist = FXCollections.observableArrayList();

  public ObservableList<String> getMaplist() {
    return maplist;
  }

  public void addMap(String mapname){
    maplist.add(mapname);
  }

  /** Saves if the player should choose the map, triggers a window to choose the map */
  private BooleanProperty loadmapselection = new SimpleBooleanProperty(false);

  public BooleanProperty getLoadmapselection(){
    return loadmapselection;
  }

  public void setChooseMap(boolean choose){
    loadmapselection.set(choose);
  }

  /** Triggers the loading of the map and saves initial map parameters*/
  private int x;
  private int y;

  public int getX(){
    return x;
  }

  public int getY() {
    return y;
  }

  private BooleanProperty loadmap = new SimpleBooleanProperty(false);

  public BooleanProperty getLoadMap(){
    return loadmap;
  }

  public void setLoadmap(int x, int y){
    this.x = x;
    this.y = y;
    loadmap.set(true);
  }

  private String mapname;
  public void setMapname(String name){
    mapname = name;
  }
  public String getMapname(){
    return mapname;
  }

  private BoardGUI board;

  public void setBoard(BoardGUI b){
    board = b;
  }
  public BoardGUI getBoard(){
    return board;
  }

  //List to show checkpoint of players
  private final ObservableList<Playerstatus> playerstatuslist = FXCollections.observableArrayList();

  public ObservableList<Playerstatus> playerstatuslistProperty() {
    return playerstatuslist;
  }

  // function to add a string to the list
  public void reachCheckpoint(int id, String name, int checkpoint) {
    boolean found = false;
    for (Playerstatus p : playerstatuslist) {
      if (p.getId() == id) {
        p.addCheckpoint(checkpoint);
        found = true;
      }
    }
    if (!found) {
      playerstatuslist.add(new Playerstatus(id, name, checkpoint));
    }
    playerstatuslist.sort(Collections.reverseOrder());
  }

  private boolean isAI = false;
  public void setAI(){isAI=true;}
  public boolean isAI(){
    return isAI;
  }

  //handles player rebooting
  private BooleanProperty rebooting = new SimpleBooleanProperty(false);

  public BooleanProperty getRebooting(){
    return rebooting;
  }

  public void setRebooting(boolean b){
    rebooting.set(b);
  }

  //handles choosing damage type
  private BooleanProperty choosedamage = new SimpleBooleanProperty(false);

  private int numberofcards;

  private ObservableList<CardName> piles = FXCollections.observableArrayList();

  public BooleanProperty getChoosedamage(){
    return choosedamage;
  }

  public int getNumberofcards(){
    return  numberofcards;
  }

  public ObservableList<CardName> getAvailablePiles(){
    return piles;
  }

  public void setChoosedamage(boolean b, int numberofcards, CardName[] piles){
    this.numberofcards = numberofcards;
    if (piles != null) {
      this.piles.addAll(piles);
    }
    choosedamage.set(b);
  }

  //handles the end of the game
  private BooleanProperty gameFinished = new SimpleBooleanProperty(false);

  public BooleanProperty getGameFinished(){
    return gameFinished;
  }

  private int winnerid;

  public void setGameFinished(int winner){
    winnerid = winner;
    gameFinished.set(true);
  }

  public int getWinnerid(){
    return winnerid;
  }



}
