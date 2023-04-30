package game.data.boardelements;

/**
 * This class implements the BoardElement StartPoint
 *
 * @author Pierre, Simon Hümmer
 */
public class StartPoint extends BoardElement {

    public StartPoint(String isOnBoard) {
        super(BoardElementTypes.StartPoint, isOnBoard);
    }

    @Override
    public String subClassToString() {
        return null;
    }
}
