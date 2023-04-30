package game.data.cards;

import game.data.Parameter;

import java.lang.reflect.Constructor;

public class CardGetter {

    //Is there a way to get the name of the packages automatically ?
    private static final String[] packageNames = {"damage.", "programming.", "special.", "upgrade."};

    public static Card getCardByName(CardName name) {
        try {
            Class<Card> cardClass = getClassWithSubStructure(name.toString());
            Constructor constructor = cardClass.getConstructor();

            Object instanceOfMyClass = constructor.newInstance();
            return (Card) instanceOfMyClass;
        } catch (Exception e) {

        }
        return null;

    }

    public static Card[] getCardsByName(CardName[] cardNames) {
        Card[] cards = new Card[cardNames.length];
        int i = 0;
        for (CardName cardName : cardNames) {
            cards[i] = getCardByName(cardName);
            i++;
        }
        return cards;
    }


    public static Class getClassWithSubStructure(String name) throws ClassNotFoundException {
        Class<?> act = null;
        for (String packageName : packageNames) {
            try {
                act = Class.forName(Parameter.GAME_ROBORALLY_DATA_CARDS + packageName + name);
            } catch (ClassNotFoundException e) {
                //DON'T Refactor this. Must be ignored to check if class is in other packages
            }
        }
        if (act == null) {
            //if class is not in any of the packages throw exception
            throw new ClassNotFoundException("class: " + name + "does not exist");
        }
        return act;
    }

}
