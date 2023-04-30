package communication.client.data_handlers.gamedata_recieverhandler;

import communication.client.WritingClientSingelton;
import communication.client.data_handlers.GameDataOutHandeler;
import communication.protocol_v2v1.sendobjects.chat.SendChat;
import game.clientgame.clientgamehandler.AIClientGameHandler;
import game.clientgame.clientgamehandler.GameHandlerSingelton;
import game.data.Direction;
import game.data.GameMap;
import game.data.Orientations;
import game.data.Rotations;
import game.data.cards.CardName;

/**
 * To handle update to Game from ClientReceiverHandler for AI
 * @author Pierre-Louis Suckrow
 */
public class AIGameDateRecieverHandler extends GameDateRecieverHandler {


    public AIGameDateRecieverHandler(GameMap map) {
        super(map);
        GameHandlerSingelton.getInstance().setClientGameHandler(new AIClientGameHandler());
        clientGameHandler = GameHandlerSingelton.getInstance().getClientGameHandler();
        clientGameHandler.setMap(map);
    }


    public void startTimer(){
    }


    public void movePlayer(int clientID, int x, int y){
        int roboid = getRoboterbyID(clientID);
       //Save Move
    }

    public void turnPlayer(int clientID, Rotations rotation){
        int roboid = getRoboterbyID(clientID);
       //Save Turn
    }


    public void replaceCard(int register, CardName cardName, int clientID){
    }

    public void finishGame(int clientID){
        if(clientID == WritingClientSingelton.getInstance().getClientID()){
            WritingClientSingelton.getInstance().writeOutStream(new SendChat("Pragmatische Pinguine KI hat euch besiegt",-1));
        }
    }

    public void chooseRebootDirection(){
        super.chooseRebootDirection();
        Direction direction = clientGameHandler.chooseRebootDirection();
        Orientations orientation = Orientations.valueOf(direction.toString());
        GameDataOutHandeler.getInstance().sendRebootDirection(orientation);
    }

    public void pickDamage(int count, CardName[] cardNames){

    }

}
