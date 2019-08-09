/**
 * Created by njk on 5/29/16.
 */
//this class has functions which can analyze results given by the simulator
//we did not use the features of this class for our analysis (we used Excel),
//but this class provides many of the same capabilities

import java.util.ArrayList;

public class Analyzer {
    //the payout ratio for each outcome
    public static final double[] PAYOUTS = {1.0, -1.0, 2.0, -1.0, 0.0};
    public static final int BET_MINIMUM = 1, BET_MAXIMUM = 10;

    public static String getAnalysis(ArrayList<int[]> outcomes) {
        int[] of = outcomeFrequencies(outcomes);
        double[] op = outcomePercentages(of);
        String freqs = freqsToString(of);
        String percents = percentsToString(op);
        String notLost = "Percent Not Lost = " + percentNotLost(op);
        String totCash = "Total Cash Made = " + cashMade(outcomes);
        String avgCash = "Average Cash Made Per Round = " + avgCashPerRound(outcomes);
        String cashStdDev = "Cash Made Per Round Standard Deviation = " + cashPerRoundStdDev(outcomes);
        return freqs + "\n" + percents + "\n" + notLost + "\n"
                + totCash + "\n" + avgCash + "\n" + cashStdDev + "\n";
    }

    public static String freqsToString(int[] freqs) {
        String rv = "";
        for (int i = 0; i < freqs.length; i++) {
            rv += freqs[i] + " " + Simulator.Outcome.values()[i].name();
            if (i != freqs.length - 1) rv += ", ";
        }
        return rv;
    }

    public static String percentsToString(double[] freqs) {
        String rv = "";
        for (int i = 0; i < freqs.length; i++) {
            rv += (freqs[i] * 100) + "% " + Simulator.Outcome.values()[i].name();
            if (i != freqs.length - 1) rv += ", ";
        }
        return rv;
    }

    public static int[] outcomeFrequencies(ArrayList<int[]> outcomes) {
        int[] freqs = new int[5];
        for (int[] i : outcomes) {
            freqs[i[i.length - 1]]++;
        }
        return freqs;
    }

    public static double[] outcomePercentages(int[] freqs) {
        double[] percents = new double[freqs.length];
        for (int i = 0; i < freqs.length; i++) {
            percents[i] = (double) freqs[i] / arraySum(freqs);
        }
        return percents;
    }

    public static double percentNotLost(double[] percents) {
        return percents[0] + percents[2] + percents[4];
    }

    public static double payout(int[] outcome) {
        int bet = outcome[4] == Integer.MAX_VALUE || outcome[4] <= 0 ? BET_MINIMUM : BET_MAXIMUM;
        return bet * PAYOUTS[outcome[5]];
    }

    public static double cashMade(ArrayList<int[]> outcomes) {
        double cash = 0;
        for (int[] i : outcomes) {
            cash += payout(i);
        }
        return cash;
    }

    public static double avgCashPerRound(ArrayList<int[]> outcomes) {
        return cashMade(outcomes) / outcomes.size();
    }

    public static double cashPerRoundStdDev(ArrayList<int[]> outcomes) {
        double avg = avgCashPerRound(outcomes);
        double variance = 0;
        for (int[] i : outcomes) {
            variance += Math.pow(payout(i) - avg, 2);
        }
        return Math.sqrt(variance);
    }

    private static int arraySum(int[] a) {
        int sum = 0;
        for (int i : a) sum += i;
        return sum;
    }
}
