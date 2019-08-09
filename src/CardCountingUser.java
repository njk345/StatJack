/**
 * Created by njk on 5/26/16.
 */

public class CardCountingUser implements Player {
    private static final int[] HI_LO_VALUES = {0, 0, 1, 1, 1, 1, 1, 0, 0, 0, -1, -1};
    private Hand hand;
    private Card dealerUpcard;
    private int runningCount;
    private Strategy strategy;
    public CardCountingUser(Strategy s) {
        hand = new Hand();
        dealerUpcard = null;
        runningCount = 0;
        strategy = s;
    }

    private int getCount(Card c) {
        switch (strategy) {
            case HI_LO:
                return HI_LO_VALUES[c.getValue()] / Simulator.NUM_DECKS;
            default:
                return HI_LO_VALUES[c.getValue()] / Simulator.NUM_DECKS;
        }
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
        //called when a game ends
        //user sees what the dealer had and adds it
        //to their running count
        for (Card c : h.getHand()) {
            runningCount += getCount(c);
        }
    }

    public void takeCard(Card c) {
        hand.addCard(c);
        runningCount += getCount(c);
    }

    public void resetHand() {
        hand.reset();
    }

    public void resetCount() {
        runningCount = 0;
    }

    public boolean busted() {
        return hand.bust();
    }

    public boolean blackjack() {
        return hand.blackjack();
    }

    public int getRunningCount() {
        return runningCount;
    }

    public enum Strategy {
        HI_LO
        /*more strategies can be implemented by simply setting
        their card values as int[] macros below
         */
    }
}
