/**
 * Created by njk on 5/25/16.
 */
public class Card {
    private static final String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    private static final String[] suits = {"Spades", "Clubs", "Diamonds", "Hearts"};
    private static final int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};
    private int value;
    private String rank, suit;
    public Card(int rankIndex, int suitIndex) {
        this.rank = ranks[rankIndex];
        this.suit = suits[suitIndex];
        this.value = values[rankIndex];
    }
    public int getValue() {return this.value;}
    public String getRank() {return this.rank;};
    public String getSuit() {return this.suit;};
    public String toString() {
        return getRank() + " of " + getSuit();
    }

}
