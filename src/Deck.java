/**
 * Created by njk on 5/25/16.
 */

import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private static Random rand = new Random(System.currentTimeMillis());
    private ArrayList<Card> deck;
    private int size;

    public Deck(int numDecks) {
        refill(numDecks);
        size = numDecks;
    }

    public Card dealCard() {
        if (deck.isEmpty()) throw new RuntimeException("Can't deal from empty deck");
        return deck.remove(rand.nextInt(deck.size()));
    }

    public int numDecks() {
        return size;
    }

    public int cardsLeft() {
        return deck.size();
    }

    public void refill(int numDecks) {
        if (numDecks < 1) numDecks = 1;
        deck = new ArrayList<>();
        for (int i = 0; i < numDecks; i++) {
            for (int j = 0; j < 13; j++) {
                for (int k = 0; k < 4; k++) {
                    deck.add(new Card(j, k));
                }
            }
        }
    }
}
