package communication.client.chatgui.boardelements;

import communication.client.chatgui.BoardGUI;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

/** @author Sarah, Tea GUI representation of energy space */
public class GUIEnergySpace extends GUIBoardElement {

    private static Image full = new Image("/BoardElements/EnergySpaceG.png");
    private static Image empty = new Image("/BoardElements/EnergySpaceR.png");
    private int count;

    public GUIEnergySpace(int x, int y, int count) {
        super(BoardGUI.TILESIZE, x, y);
        this.count = count;
        setStroke(Color.TRANSPARENT);
        setFill(new ImagePattern(full));
    }

    /** changes the lights to red when all cubes are taken */
    public void takeEnergyCube(){
        if (count>0){
            count--;
            if(count==0){
                setFill(new ImagePattern(empty));
            }
        }
    }
}
