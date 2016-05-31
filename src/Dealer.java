/**
 * Created by njk on 5/25/16.
 */
//strategy for dealer
//dealer must stand on a 17 or above of any kind (i.e. no soft-17 rule)
//everything in Dealer is static because it's not technically a Player
public class Dealer {
    private static Hand hand = new Hand();
    public static boolean makeMove() {
        //dealer must hit if total < 17
        return hand.getTotal() < 17;
    }
    public static Hand getHand() {
        return hand;
    }
    public static Card getUpcard() {
        return hand.getHand().get(0);
    }
    public static void resetHand() {
        hand.reset();
    }
    public static void takeCard(Card c) {
        hand.addCard(c);
    }
    public static boolean busted() {
        return hand.bust();
    }
    public static boolean blackjack() {
        return hand.blackjack();
    }
}
