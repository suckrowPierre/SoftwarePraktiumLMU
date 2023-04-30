package communication.protocol_v2v1.sendobjects.game_move.activation_phase;

import game.data.cards.CardName;

public class ActiveCard {
    int clientID;
    CardName card;


    public ActiveCard(int clientID, CardName card) {
        this.clientID = clientID;
        this.card = card;
    }

    public int getClientID() {
        return clientID;
    }

    public CardName getCard() {
        return card;
    }
}
