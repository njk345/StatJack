/**
 * Created by njk on 5/24/16
 */

import java.util.*;
import java.io.*;
public class Simulator {
    public static final double DECK_REFILL_RATIO_LEFT = 0.25;
    public static final int NUM_DECKS = 1;
    public static final String OUTPUT_FILE = "blackjack.out";

    public enum Outcome {
        USER_WIN, DEALER_WIN, USER_BLACKJACK, DEALER_BLACKJACK, TIE
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Simulate How Many Games? ");
        int g = s.nextInt();
        s.nextLine();
        System.out.print("Count Cards? (y/n) ");
        String r = s.nextLine().toLowerCase();
        System.out.println("Simulating...");
        simGames(r.equals("y")? new CardCountingUser(CardCountingUser.Strategy.HI_LO) : new SimpleUser(), g);
        System.out.println("Done Simulating " + g + " Games -- Results Saved At " + OUTPUT_FILE);
    }
    public static void simGames(Player user, int numGames) {
        ArrayList<int[]> results = new ArrayList<>();
        Deck deck = new Deck(NUM_DECKS);
        for (int i = 0; i < numGames; i++) {
            results.add(simGame(user, deck));
        }
        String analysis = Analyzer.getAnalysis(results);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE));
            bw.write(analysis);
            bw.newLine();
            for (int[] r : results) {
                for (int i = 0; i < r.length-2; i++) {
                    bw.write(r[i] + ",");
                }
                if (r[r.length-2] != Integer.MAX_VALUE) {
                    //only write card count to file if user is counting cards
                    bw.write(r[r.length-2] + ",");
                }
                bw.write(Outcome.values()[r[r.length-1]].name());
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int[] simGame(Player user, Deck deck) {
        Outcome outcome;
        user.resetHand();
        Dealer.resetHand();
        if (deck.cardsLeft() < DECK_REFILL_RATIO_LEFT * (deck.numDecks() * 52)) {
            deck.refill(NUM_DECKS);
            user.resetCount(); //only for CardCountingUser
        }
        int countComingIn = user.getRunningCount();

        Dealer.takeCard(deck.dealCard());
        Dealer.takeCard(deck.dealCard());
        user.takeCard(deck.dealCard());
        user.takeCard(deck.dealCard());

        if (Dealer.blackjack() && user.blackjack()) {
            outcome = Outcome.TIE;
        } else if (Dealer.blackjack()) {
            outcome = Outcome.DEALER_BLACKJACK;
        } else if (user.blackjack()) {
            outcome = Outcome.USER_BLACKJACK;
        } else { //play out rest of game
            user.setDealerUpcard(Dealer.getUpcard());
            while (user.makeMove()) {
                user.takeCard(deck.dealCard());
                if (user.busted()) {
                    break;
                }
            }
            if (user.busted()) {
                outcome = Outcome.DEALER_WIN;
            } else {//play out Dealer's game
                while (Dealer.makeMove()) {
                    Dealer.takeCard(deck.dealCard());
                    if (Dealer.busted()) {
                        break;
                    }
                }
                if (Dealer.busted()) {
                    outcome = Outcome.USER_WIN;
                } else {//do a final total comparison
                    int userTotal = user.getHand().getTotal();
                    int dealerTotal = Dealer.getHand().getTotal();
                    outcome = userTotal > dealerTotal? Outcome.USER_WIN : userTotal == dealerTotal?
                            Outcome.TIE : Outcome.DEALER_WIN;
                }
            }
        }
        user.seeDealerHand(Dealer.getHand());
        return new int[]{user.getHand().size(), Dealer.getHand().size(),
                         user.getHand().getTotal(), Dealer.getHand().getTotal(),
                         countComingIn, outcome.ordinal()};
    }
}
