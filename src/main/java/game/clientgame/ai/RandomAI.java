package game.clientgame.ai;

import game.clientgame.StartingPoint;
import game.clientgame.clientgamehandler.AIClientGameHandler;
import game.data.BoardElementPlusPosition;
import game.data.Direction;
import game.data.Position;
import game.data.cards.Card;
import game.data.cards.CardName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Ai that plays randomly
 * @author Sarah Koch, Pierre-Louis Suckrow
 */
public class RandomAI extends AI {

    public RandomAI(AIClientGameHandler gameHandler) {
        super(gameHandler);
    }

    public Position selectStartingPoint(ArrayList<BoardElementPlusPosition> boardElementPlusPositions) {
        ArrayList<StartingPoint> takenstartingPoints = gameHandler.getTakenstartingPoints();
        ArrayList<BoardElementPlusPosition> availablestartingpoints = removeTakenStartingPoints(boardElementPlusPositions,takenstartingPoints);
        Random random = new Random();
        int select = random.nextInt(availablestartingpoints.size());
        return availablestartingpoints.get(select).getPosition();
    }

    private ArrayList<BoardElementPlusPosition> removeTakenStartingPoints(ArrayList<BoardElementPlusPosition> allstartingpoints, ArrayList<StartingPoint> takenstartingPoints) {
        ArrayList<BoardElementPlusPosition> startingpointspluspsotion = allstartingpoints;
        ArrayList<BoardElementPlusPosition> toRemove = new ArrayList<BoardElementPlusPosition>();
        if (takenstartingPoints == null) {
            return startingpointspluspsotion;
        } else {
            for (StartingPoint startingPoint : takenstartingPoints) {
                for (BoardElementPlusPosition startingpointplusposition : startingpointspluspsotion) {
                    if (startingPoint.getX() == startingpointplusposition.getPosition().getX() && startingPoint.getY() == startingpointplusposition.getPosition().getY()) {
                        toRemove.add(startingpointplusposition);
                    }
                }

            }
            startingpointspluspsotion.removeAll(toRemove);
            return startingpointspluspsotion;
        }
    }

    @Override
    public void programm() {
        ArrayList<Card> hand = gameHandler.getProgrammingBoard().getRecievedProgrammingCardsArrayList();
        for (int i = 0; i < 5; i++) {
            Random random = new Random();
            int select = random.nextInt(hand.size());
            Card randomCard = hand.get(select);
            while(i==0 && randomCard.getCardName().equals(CardName.Again)){
                select = random.nextInt(hand.size());
                randomCard = hand.get(select);
            }
            hand.remove(select);
            gameHandler.setCardInProgrammingDeck(randomCard, i);
        }
    }

    @Override
    public Direction chooseRebootDirection() {
        return Direction.top;
    }

    public CardName[] chooseDamageCards(int count, CardName[] cardNames){
        ArrayList<CardName> cardlist = new ArrayList<>(Arrays.asList(cardNames));
        ArrayList<CardName> returnList = new ArrayList<>();
        for (int i = 0; i < count;i++){
            Random random = new Random();
            int select = random.nextInt(cardlist.size());
            CardName cardName = cardlist.get(select);
            returnList.add(cardName);
            cardlist.remove(cardName);
        }
        CardName[] cardNamesReturn = new CardName[returnList.size()];
        cardNamesReturn = returnList.toArray(cardNamesReturn);
        return cardNamesReturn;
    }

}
