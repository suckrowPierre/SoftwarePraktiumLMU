package communication.client.chatgui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/** @author Sarah holds date for the upgrade cards and shop */
public class UpgradeModel {
    private static UpgradeModel instance;

    /**
     * Singleton
     *
     * @return instance of the model
     */
    public static UpgradeModel getInstance() {
        if (instance == null) instance = new UpgradeModel();
        return instance;
    }

    private UpgradeShopController controller = null;

    public void setController(UpgradeShopController c){
        controller = c;
    }

    private IntegerProperty energypoints = new SimpleIntegerProperty(5);

    public IntegerProperty getEnergypoints(){
        return energypoints;
    }

    public void setEnergypoints(int n){
        energypoints.set(n);
    }

    private int numberofcards = 0;

    public int getNumberofcards(){
        return numberofcards;
    }

    private ArrayList<String> upgradecards = new ArrayList<String>();

    public String getCard(int i) {
        return upgradecards.get(i);
    }

    public void setCards(ArrayList<String> cardnames){
        numberofcards = cardnames.size();
        upgradecards = cardnames;
    }

    private BooleanProperty upgradePurchaseTurn = new SimpleBooleanProperty(false);

    public BooleanProperty getUpgradePurchaseTurn(){
        return upgradePurchaseTurn;
    }

    public void setUpgradePurchaseTurn(boolean b){
        upgradePurchaseTurn.set(b);
    }

    private BoardController viewhandel = null;

    public void setViewhandel(BoardController p){
        viewhandel = p;
    }

    public  void showCardInUpgrades(String card){
        viewhandel.showCardInUpgrades(card);
        setUpgradePurchaseTurn(false);
    }

    public void removeCardfromUpgrades(String card){
        viewhandel.removeCardfromUpgrades(card);
    }

    /** Memory Swap*/

    private ObservableList<String> toremovelist = FXCollections.observableArrayList();

    public ObservableList<String> getToremovelist(){
        return toremovelist;
    }

    public void addCardtoRemoveList(String cardname){
        toremovelist.add(cardname);
    }

    public void clearToRemoveCards(){
        toremovelist.clear();
    }

    private BooleanProperty memoryswapactive = new SimpleBooleanProperty(false);

    public BooleanProperty getMemoryswapactive(){
        return memoryswapactive;
    }

    public void setMemoryswapactive (boolean b){
        memoryswapactive.set(b);
    }

    private String[] cards = new String[3];

    public void setNewCardMemoryswap(String[] newc){
        cards[0] = newc[0];
        cards[1] = newc[1];
        cards[2] = newc[2];
    }

    public String getNewCard(int i){
        return  cards[i];
    }

}
