package test;

import com.google.gson.*;
import communication.protocol_v2v1.*;
import communication.protocol_v2v1.sendobjects.actions_events_effects.*;
import communication.protocol_v2v1.sendobjects.bodycontents.Groups;
import communication.protocol_v2v1.sendobjects.bodycontents.Protocols;
import communication.protocol_v2v1.sendobjects.cards.CardPlayed;
import communication.protocol_v2v1.sendobjects.cards.PlayCard;
import communication.protocol_v2v1.sendobjects.chat.ReceivedChat;
import communication.protocol_v2v1.sendobjects.chat.SendChat;
import communication.protocol_v2v1.sendobjects.communication_setup.*;
import communication.protocol_v2v1.sendobjects.game_move.ActivePhase;
import communication.protocol_v2v1.sendobjects.game_move.CurrentPlayer;
import communication.protocol_v2v1.sendobjects.game_move.activation_phase.ReplaceCard;
import communication.protocol_v2v1.sendobjects.game_move.programm_phase.*;
import communication.protocol_v2v1.sendobjects.game_move.setup_phase.StartingPointTaken;
import communication.protocol_v2v1.sendobjects.game_move.setup_phase.SetStartingPoint;
import communication.protocol_v2v1.sendobjects.game_move.upgrade_phase.BuyUpgrade;
import communication.protocol_v2v1.sendobjects.game_move.upgrade_phase.ExchangeShop;
import communication.protocol_v2v1.sendobjects.game_move.upgrade_phase.RefillShop;
import communication.protocol_v2v1.sendobjects.game_move.upgrade_phase.UpgradeBought;
import communication.protocol_v2v1.sendobjects.lobby.*;
import communication.protocol_v2v1.sendobjects.special.Actions;
import communication.protocol_v2v1.sendobjects.special.ConnectionUpdate;
import communication.protocol_v2v1.sendobjects.special.ErrorMessage;
import game.data.Direction;

import java.util.ArrayList;

import game.data.Maps;
import game.data.Orientations;
import game.data.Rotations;
import game.data.cards.*;

import static game.data.Maps.DizzyHighway;
import static game.data.Maps.LostBearings;

public class ProtokolSendObjectWrapTest {





    public static void main(String[] args) {
        boolean alltrue = true;
        ArrayList<ProtocolSendObject> testobjects = new ArrayList<ProtocolSendObject>();


        testobjects.add(new Alive());
        testobjects.add(new Welcome(5));
        testobjects.add(new HelloServer(Protocols.V0V1, Groups.PragmatischePinguine, false));
        testobjects.add(new HelloClient(Protocols.V0V1));
        // lobby
        testobjects.add(new PlayerAdded(5, "bob", 5));
        testobjects.add(new PlayerValues("alice", 2));
        testobjects.add(new PlayerStatus(6, true));
        testobjects.add(new SetStatus(false));

        Maps[] maps = {DizzyHighway, LostBearings};
        testobjects.add(new SelectMap(maps));

        testobjects.add(new MapSelected(DizzyHighway));

        //testobjects.add(new GameStarted()

        //special
        testobjects.add(new ErrorMessage("big error"));
        testobjects.add(new ConnectionUpdate(3, false, Actions.AIControl));
        // Chat
        testobjects.add(new SendChat("hallo", 1));
        testobjects.add(new ReceivedChat("Bye", 6, true));
        // Cards
        testobjects.add(new PlayCard(CardName.MoveI));
        testobjects.add(new CardPlayed(3, CardName.MoveII));
        // Game Move
        testobjects.add(new CurrentPlayer(3));
        testobjects.add(new ActivePhase(3));
        //setup Phase
        testobjects.add(new SetStartingPoint(3,4));
        testobjects.add(new StartingPointTaken(2,6,7));
        //Upgrade Phase
        CardName[] upgradecards = {CardName.RearLaser, CardName.MemorySwap};
        testobjects.add(new RefillShop(upgradecards));
        testobjects.add(new ExchangeShop(upgradecards));
        testobjects.add(new BuyUpgrade(true, CardName.RearLaser));
        testobjects.add(new UpgradeBought(4, CardName.RearLaser));
        //Programm Phase

        CardName[] cards = {CardName.Spam,CardName.Trojan};
        testobjects.add(new YourCards(cards));
        testobjects.add(new NotYourCards(1, 9));
        testobjects.add(new ShuffleCoding(3));
        testobjects.add(new SelectedCard(CardName.Spam, 5));
        testobjects.add(new CardSelected(4, 5, true));
        testobjects.add(new SelectionFinished(9));
        testobjects.add(new TimerStarted());
        int[] clientIDs = {3,4,6};
        testobjects.add(new TimerEnded(clientIDs));
        testobjects.add(new CardsYouGotNow(cards));
        //activation Phase

        //TODO:testobjects.add(new CurrentCards(cards));

        testobjects.add(new ReplaceCard(4, CardName.MoveII, 3));
        //actions
        testobjects.add(new Movement(6, 3, 1));
        testobjects.add(new PlayerTurning(4, Rotations.counterclockwise));

        testobjects.add(new DrawDamage(4, cards));
        testobjects.add(new PickDamage(2, cards));
        testobjects.add(new SelectedDamage(cards));
        testobjects.add(new Animation("PlayerShooting"));
        testobjects.add(new Reboot(3));
        testobjects.add(new RebootDirection(Orientations.left));
        testobjects.add(new Energy(4, 1, "GUIEnergySpace"));
        testobjects.add(new CheckPointReached(4, 3));
        testobjects.add(new GameFinished(2));







        for (ProtocolSendObject testobject : testobjects) {
      alltrue &= testSendObjectWrapping(testobject);
    }

    System.out.println("\nalltrue: " + alltrue);



    /*
        String test = "{\n" +
                "\"messageType\": \"Alive\",\n" +
                "\"messageBody\": {}\n" +
                "}";

        String test2 = "{\n" +
                "\"messageType\": \"Welcome\",\n" +
                "\"messageBody\": {\n" +
                "\"clientID\": 42\n" +
                "}\n" +
                "}";

        String test3 = "{\n" +
                "\"messageType\": \"Error\",\n" +
                "\"messageBody\": {\n" +
                "\"error\": \"Whoops. That did not work. Try to adjust something.\"\n" +
                "}\n" +
                "}\n";
        String test4 = "{\n" +
                "\"messageType\": \"ConnectionUpdate\",\n" +
                "\"messageBody\": {\n" +
                "\"clientID\": 9001,\n" +
                "\"isConnected\": false,\n" +
                "\"action\": \"Remove\"\n" +
                "}\n" +
                "}";
        String test5 = "{\n" +
                "\"messageType\": \"ReceivedChat\",\n" +
                "\"messageBody\": {\n" +
                "\"message\": \"Yoh, Bob! How is your head doing after last night?\",\n" +
                "\"from\": 42,\n" +
                "\"isPrivate\": true\n" +
                "}\n" +
                "}\n";





        ProtocolSendObject data = SendObjectJsonWrapper.dewrapString(test5);
        System.out.println(new GsonBuilder().setPrettyPrinting().create().toJson(data));

     */

    }

    public static boolean testSendObjectWrapping (ProtocolSendObject sendObject){
        System.out.println("------------------------------------------------------------------------");
      System.out.println("{\nwrap: " + sendObject.toString());
      JsonObject json = SendObjectJsonWrapper.wrap(sendObject);
      System.out.println("wrapped: "  + new GsonBuilder().setPrettyPrinting().create().toJson(json));
      ProtocolSendObject sendObject2 = SendObjectJsonWrapper.dewrap(json);
      System.out.println("dewrapped wrapped: " + sendObject2.toString());
      JsonObject json2 = SendObjectJsonWrapper.wrap(sendObject2);
      System.out.println("wrapped dewrapped wrapped: " + new GsonBuilder().setPrettyPrinting().create().toJson(json2));
      System.out.println("json same: " + json.toString().equals(json2.toString()));
      System.out.println("}");
        System.out.println("------------------------------------------------------------------------");
        System.out.println("\n\n");
      return json.toString().equals(json2.toString());

    }


}
