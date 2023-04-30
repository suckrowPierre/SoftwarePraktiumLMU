package game.data.boardelements;

import game.data.Orientations;

import java.util.Arrays;

/**
 * This class implements the BoardElement Laser
 *
 * @author Pierre, Simon Hümmer
 */
public class Laser extends BoardElement {

    final private int count;
    final private Orientations[] orientations;

    public Laser(String isOnBoard, int count, Orientations[] orientations) {
        super(BoardElementTypes.Laser, isOnBoard);
        this.count = count;
        this.orientations = orientations;
    }

    public int getCount() {
        return count;
    }

    public Orientations[] getOrientations() {
        return orientations;
    }

    @Override
    public String subClassToString() {
        return " , orientations = " + Arrays.toString(orientations) + " ,count = " + count;
    }
}
