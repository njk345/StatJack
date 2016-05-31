/**
 * Created by njk on 5/25/16.
 */
//strategy for player who DOES NOT count cards
public class SimpleUser implements Player {
    private Hand hand;
    private Card dealerUpcard; //user keeps local version of dealer's current upcard
    public SimpleUser() {
        hand = new Hand();
        dealerUpcard = null;
    }
    public boolean makeMove() {
        int userScore = hand.getTotal();
        int dealerScore = dealerUpcard.getValue();
        if (userScore <= 11) return true; //always hit here
        if (userScore == 12) {
            return dealerScore == 2 || dealerScore == 3 || (dealerScore >= 7 && dealerScore <= 11);
        }
        if (userScore >= 13 && userScore <= 16) {
            return dealerScore >= 7 && dealerScore <= 11;
        }
        return false; //stand in every other situation
    }
    public Hand getHand() {
        return hand;
    }
    public void setDealerUpcard(Card c) {
        dealerUpcard = c;
    }
    public void seeDealerHand(Hand h) {
        //don't use this in simple strategy
    }
    public void takeCard(Card c) {
        hand.addCard(c);
    }
    public void resetHand() {
        hand.reset();
    }
    public boolean busted() {
        return hand.bust();
    }
    public boolean blackjack() {
        return hand.blackjack();
    }
    public void resetCount() {}//do nothing
    public int getRunningCount() {return Integer.MAX_VALUE;} //meaningless definition
}
