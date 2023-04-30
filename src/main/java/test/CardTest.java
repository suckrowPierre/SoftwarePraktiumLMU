package test;

import game.data.cards.Card;
import game.data.cards.CardGetter;
import game.data.cards.CardName;

public class CardTest {
    public static void main(String[] args) {
        CardName name = CardName.MoveII;

        Card ard1 = CardGetter.getCardByName(name);
        System.out.println(ard1.cardDescription);


    }
}
