package communication.protocol_v2v1.sendobjects.cards;

import communication.protocol_v2v1.MessageType;
import communication.protocol_v2v1.ProtocolSendObject;;
import game.data.cards.CardName;

public class PlayCard extends ProtocolSendObject {

        public PlayCard(CardName card) {
            this.messageType = MessageType.PlayCard;
            this.messageBody = new PlayCardMessageBody(card);
        }

        public CardName getCard(){
            return (CardName) messageBody.getBodyContent().get(0);
        }

    }

