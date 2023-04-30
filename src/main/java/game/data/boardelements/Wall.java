package game.data.boardelements;

import game.data.Orientations;

import java.util.Arrays;

/**
 * This class implements the BoardElement Wall
 *
 * @author Pierre, Simon Hümmer
 */
public class Wall extends BoardElement {

    final private Orientations[] orientations;

    public Wall(String isOnBoard, Orientations[] orientations) {
        super(BoardElementTypes.Wall, isOnBoard);
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
