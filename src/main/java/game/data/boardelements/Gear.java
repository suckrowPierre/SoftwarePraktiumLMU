package game.data.boardelements;

import game.data.Orientations;

import java.util.Arrays;

/**
 * This class implements the BoardElement Gear
 *
 * @author Pierre, Simon Hümmer
 */
public class Gear extends BoardElement {

    final private Orientations[] orientations;

    public Gear(String isOnBoard, Orientations[] orientations) {
        super(BoardElementTypes.Gear, isOnBoard);
        this.orientations = orientations;
    }

    public Orientations[] getOrientations() {
        return orientations;
    }

    @Override
    public String subClassToString() {
        return " , orientations = " + Arrays.toString(orientations);
    }
}
