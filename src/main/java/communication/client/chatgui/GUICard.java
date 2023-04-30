package communication.client.chatgui;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.util.Duration;

/** @author Sarah Representation of Card in View */
public class GUICard extends Image{

    private String name;
    private Tooltip tooltip;

    public GUICard(String name){
        super("/Cards/"+name+".png");
        this.name = name;
        tooltip = new Tooltip();
        tooltip.setShowDelay(Duration.seconds(0.5));
        switch (name) {
            case "AdminPrivilege" -> tooltip.setText("Permanent Upgrade\nOnce per round, you may\ngive your robot priority\nfor one register.");
            case "MemorySwap" -> tooltip.setText("Temporary Upgrade\nDraw three cards.\nThen choose three from your hand\nto put on top of your deck.");
            case "SpamBlocker" -> tooltip.setText("Temporary Upgrade\nReplace each SPAM card in your\nhand with a card from\nthe top of your deck.");
            case "RearLaser" -> tooltip.setText("Permanent Upgrade\nYour robot may shoot\nbackward as well as\nforward.");
            default -> tooltip = null;
        }
    }

    public String getName(){
        return name;
    }
    public Tooltip getTooltip(){
        return tooltip;
    }
}
