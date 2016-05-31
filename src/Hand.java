/**
 * Created by njk on 5/25/16.
 */
import java.util.*;
public class Hand {
    private ArrayList<Card> hand;
    public Hand() {
        hand = new ArrayList<Card>();
    }
    public int getTotal() {
        //returns the total value of cards in hand
        int tot = 0;
        int numAces = 0;
        for (Card c : hand) {
            if (!c.getRank().equals("A")) {
                tot += c.getValue();
            } else numAces++;
        }
        if (11 + tot <= 21) {
            tot += 11;
            numAces--;
        }
        tot += numAces;
        return tot;
    }
    public ArrayList<Card> getHand() {
        return hand;
    }
    public void addCard(Card c) {
        hand.add(c);
    }
    public void reset() {
        hand.clear();
    }
    public boolean bust() {
        return getTotal() > 21;
    }
    public boolean blackjack() {
        return getTotal() == 21;
    }
    public int size() {
        return hand.size();
    }
}
