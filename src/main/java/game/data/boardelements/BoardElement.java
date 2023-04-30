package game.data.boardelements;

public abstract class BoardElement {
    // TODO: Create ResourceFolder with Images and correct this variable
    protected final transient String pathToRessourceFolder = "/blabalba/";
    protected final transient String IMGFilePath;
    protected final BoardElementTypes type;
    protected final String isOnBoard;


    public BoardElement(BoardElementTypes type, String isOnBoard) {
        this.isOnBoard = isOnBoard;
        this.type = type;
        // TODO: Make this work with ResourceFolderStructure
        IMGFilePath = pathToRessourceFolder + type + ".png";
    }

    public String getIsOnBoard() {
        return isOnBoard;
    }

    public BoardElementTypes getType() {
        return type;
    }

    @Override
    public String toString() {
        if (subClassToString() != null) {
            return "BoardElement{"
                    + "type="
                    + type
                    + ", isOnBoard="
                    + isOnBoard
                    + subClassToString()
                    + '}';
        } else {
            return "BoardElement{" + "type=" + type + ", isOnBoard=" + isOnBoard + '}';
        }
    }

    public abstract String subClassToString();
}
