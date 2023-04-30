package game.data.boardelements;

/**
 * This class implements the BoardElement EnergySpace
 *
 * @author Pierre, Simon Hümmer
 */
public class EnergySpace extends BoardElement {

    private int count;

    public EnergySpace(String BoardElement, int count) {
        super(BoardElementTypes.EnergySpace, BoardElement);
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String subClassToString() {
        return " , count = " + count;
    }
}
