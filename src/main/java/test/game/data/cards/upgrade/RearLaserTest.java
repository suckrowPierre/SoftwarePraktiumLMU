package test.game.data.cards.upgrade;

import communication.protocol_v2v1.sendobjects.bodycontents.Groups;
import communication.server.connectedclient.ServerConnectedPlayer;
import game.data.Robot;
import game.data.cards.CardName;
import game.data.cards.upgrade.RearLaser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RearLaserTest {

    @Test
    public void constructorInitialization() {
        RearLaser rearLaser = new RearLaser();

        assertEquals(CardName.RearLaser, rearLaser.getCardName());
        assertEquals(2, rearLaser.getCost());
    }

    @Test
    void activateCard_rearLaserIsActivated() {
        ServerConnectedPlayer player = new ServerConnectedPlayer(Groups.PragmatischePinguine, false, 0);
        Robot robot = new Robot(null, null);
        player.setRobot(robot);

        assertFalse(player.getRobot().isRearLaser(), "Expected rear laser to be inactive");

        RearLaser rearLaserCard = new RearLaser();
        rearLaserCard.activateCard(player);

        assertTrue(player.getRobot().isRearLaser(), "Expected rear laser to be activated");
    }

    @Test
    void rearLaserIsNoDamageCard() {
        RearLaser rearLaser = new RearLaser();
        assertFalse(rearLaser.isDamageCard());
    }

    @Test
    void rearLaserIsUpgradeCard() {
        RearLaser rearLaser = new RearLaser();
        assertTrue(rearLaser.isUpgradeCard());
    }
}