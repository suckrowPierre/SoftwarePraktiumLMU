package communication.client.chatgui.boardelements;

import communication.client.chatgui.BoardGUI;
import communication.client.chatgui.Model;
import communication.client.data_handlers.GameDataOutHandeler;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import org.tinylog.Logger;
import util.LoggingTags;

/** @author Sarah, Tea GUI representation of startpoint */
public class GUIStartPoint extends GUIBoardElement {

    private static Image fill = new Image("/BoardElements/Startpoint.png");

    public GUIStartPoint(int x, int y){
        super(BoardGUI.TILESIZE-10, x, y);
        setFill(new ImagePattern(fill));
        setStroke(Color.TRANSPARENT);

        //Creating the mouse event handlers
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(Model.getInstance().getBoard().getSetStartingPoint()){
                    GameDataOutHandeler.getInstance().sendStartPointSelection(x, y);
                    Logger.tag(LoggingTags.gui.toString()).info("Starting point " + x + ", " + y+ " chosen in GUI");
                }

            }
        };
        EventHandler<MouseEvent> highlightHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(Model.getInstance().getBoard().getSetStartingPoint()){
                    setCursor(Cursor.HAND);
                    setFill(new ImagePattern(new Image("BoardElements/Startpointhighlight.png")));
                }

            }
        };
        EventHandler<MouseEvent> dehighlightHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(Model.getInstance().getBoard().getSetStartingPoint()){
                    setFill(new ImagePattern(fill));
                }

            }
        };

        //Registering the event filters
        addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        addEventFilter(MouseEvent.MOUSE_ENTERED, highlightHandler);
        addEventFilter(MouseEvent.MOUSE_EXITED, dehighlightHandler);
    }
}
