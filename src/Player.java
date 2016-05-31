/**
 * Created by njk on 5/25/16.
 */
public interface Player {
    boolean makeMove(); //should return true if player hitting, false if standing
    Hand getHand(); //return the player's hand object
    void setDealerUpcard(Card c); //sets player's local account of dealer's current upcard
    void seeDealerHand(Hand h); //to be called after a game finishes so user can see cards dealer had
    void takeCard(Card c); //pushes a card to user's Hand
    void resetHand(); //clears user hand
    boolean busted(); //checks if user's hand is a bust
    boolean blackjack(); //checks if user's hand is a blackjack
    void resetCount();//only for card counting users
    int getRunningCount();//only for card counting users
}
