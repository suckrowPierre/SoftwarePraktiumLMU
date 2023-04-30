package communication.client.chatgui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @author Sarah Holds data for the programming phase */
public class  ProgrammingBoardModel {

    public ProgrammingBoardModel(){
        for(int i = 0; i<9; i++){
            programmingcards.add("Empty");
        }

    }

    private static ProgrammingBoardModel instance;

    /**
     * Singleton
     *
     * @return instance of the model
     */
    public static ProgrammingBoardModel getInstance() {
        if (instance == null) instance = new ProgrammingBoardModel();
        return instance;
    }

    /** List of programmingcards available */
    private final List<String> programmingcards = new ArrayList();


    public List<String> getProgrammingcards() {
        return programmingcards;
    }

    public void addCard(String name){
        programmingcards.add(name);
    }

    public void clearProgrammingcards(){
        programmingcards.clear();
    }

    /** Hashmap to store the five programming registers */
    private final Map<Integer, String> registercontents = new HashMap();


    public Map<Integer, String> getRegistercontents() {
        return registercontents;
    }

    public void setRegistercontents(int id, String name){
        String content=null;
        if(!name.equals("Empty")){
            content = name;
        }
        registercontents.put(id,content);
        System.out.println("Register "+id+ " set to "+ content);
    }

    public void clearRegistercontents(){
        for(int i = 0; i<5; i++){
            registercontents.put(i,"Empty");
        }
    }

    public String getContentofSingleRegister(int id){
        return registercontents.get(id);
    }

    private BooleanProperty initalizeCards = new SimpleBooleanProperty(false);

    public BooleanProperty getinitializeCards(){
        return initalizeCards;
    }

    //to show cards on the view after setting them
    public void setInitalizeCards(boolean b){
        initalizeCards.setValue(b);
    }

    private BooleanProperty timerRunning = new SimpleBooleanProperty(false);


    public BooleanProperty getTimerRunning(){
        return timerRunning;
    }

    public void setTimerRunning(boolean b){
        timerRunning.setValue(b);
    }

    private BooleanProperty myturn = new SimpleBooleanProperty(false);
    public BooleanProperty getMyturn(){
        return myturn;
    }

    public void setMyturn(boolean b){
        myturn.setValue(b);
    }

    private BoardController viewhandel = null;

    public void setViewhandel(BoardController p){
        viewhandel = p;
    }

    public  void showCardInView(int register, String card){
        viewhandel.showCardInRegister(register,card);
    }



}
