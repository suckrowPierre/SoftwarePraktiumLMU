package game.data.boardelements;

import game.data.Orientations;

import java.util.Arrays;

/**
 * This class implements the BoardElement Antenna
 *
 * @author Pierre, Simon Hümmer
 */
public class Antenna extends BoardElement {

    final private Orientations[] orientations;

    public Antenna(String isOnBoard, Orientations[] orientation) {
        super(BoardElementTypes.Antenna, isOnBoard);
        this.orientations = orientation;
    }

    public Orientations[] getOrientations() {
        return orientations;
    }

    @Override
    public String subClassToString() {
        return " , orientations = " + Arrays.toString(orientations);
    }
}
