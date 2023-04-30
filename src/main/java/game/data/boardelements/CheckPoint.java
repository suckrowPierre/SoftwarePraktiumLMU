package game.data.boardelements;

/**
 * This class implements the class BoardElement CheckPoint
 *
 * @author Pierre, Simon Hümmer
 */
public class CheckPoint extends BoardElement {

    final private int count;

    public CheckPoint(String isOnBoard, int count) {
        super(BoardElementTypes.CheckPoint, isOnBoard);
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String subClassToString() {
        return " , count = " + count;
    }
}
