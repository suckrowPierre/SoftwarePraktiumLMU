package communication.client.data_handlers;

import communication.client.WritingClientSingelton;
import communication.protocol_v2v1.ProtocolSendObject;
import communication.protocol_v2v1.sendobjects.actions_events_effects.ChooseRegister;
import communication.protocol_v2v1.sendobjects.actions_events_effects.RebootDirection;
import communication.protocol_v2v1.sendobjects.actions_events_effects.ReturnCards;
import communication.protocol_v2v1.sendobjects.actions_events_effects.SelectedDamage;
import communication.protocol_v2v1.sendobjects.cards.PlayCard;
import communication.protocol_v2v1.sendobjects.game_move.programm_phase.SelectedCard;
import communication.protocol_v2v1.sendobjects.game_move.setup_phase.SetStartingPoint;
import communication.protocol_v2v1.sendobjects.lobby.MapSelected;
import communication.protocol_v2v1.sendobjects.lobby.PlayerValues;
import communication.protocol_v2v1.sendobjects.lobby.SetStatus;
import game.data.Maps;
import game.data.Orientations;
import game.data.cards.CardName;

/**
 * Send GameData to OutStream
 * @author Pierre-Louis Suckrow
 */
public class GameDataOutHandeler {
    private static GameDataOutHandeler instance;

    public GameDataOutHandeler() {
    }

    private void send(ProtocolSendObject send){
        WritingClientSingelton.getInstance().writeOutStream(send);
    }

    public void sendUsernameAndRobot(String username, int robotid) {
        ProtocolSendObject tosend = new PlayerValues(username, robotid);
        send(tosend);
    }

    public void setReady(boolean isready) {
        ProtocolSendObject tosend = new SetStatus(isready);
        send(tosend);
    }

    public void sendMapselection(String mapname) {
        ProtocolSendObject tosend = new MapSelected(Maps.valueOf(mapname));
        send(tosend);
    }

    public void sendStartPointSelection(int x, int y){send(new SetStartingPoint(x,y));
    }



    public void sendSelectedCard(CardName name, int register) {
        send(new SelectedCard(name, register));
    }

    public static synchronized GameDataOutHandeler getInstance() {
        if (instance == null) {
            instance = new GameDataOutHandeler();
        }
        return instance;
    }

    public void sendCardtoPlay(CardName cardName){
        send(new PlayCard(cardName));
    }

    public void sendRebootDirection(Orientations orientation){
        send(new RebootDirection(orientation));
    }

    public void sendSelectedDamage(CardName[] cardNames){
        send(new SelectedDamage(cardNames));
    }

    public  void sendChooseRegister(int register){
        send(new ChooseRegister(register));

    }

    public void sendReturnCards(CardName[] cardNames){
        send(new ReturnCards(cardNames));
    }
}
