package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 * <p>
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
    public TimeSeries total; // Timeseries of total corpus of data
    public List<String> NGrams; // List of the specified word
    public List<TimeSeries> NGramTS; // List of Timeseries of the specified word

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     * @param wordsFilename
     * @param countsFilename
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        In wordsFile = new In(wordsFilename);
        In countsFile = new In(countsFilename);

        total = new TimeSeries();
        NGrams = new ArrayList<>();
        NGramTS = new ArrayList<>();

        while (!countsFile.isEmpty()) {
            String token = countsFile.readString();
            String[] arrOfStr = token.split(",");
            int countsYear = Integer.valueOf(arrOfStr[0]);
            double counts = Double.parseDouble(arrOfStr[1]);
            total.put(countsYear, counts);
        }

        while (!wordsFile.isEmpty()) {
            String word = wordsFile.readString();
            int year = wordsFile.readInt();
            int count = wordsFile.readInt();
            wordsFile.readInt();

            if (NGrams.isEmpty() || !NGrams.get(NGrams.size() - 1).equals(word)) {
                TimeSeries timeSeries = new TimeSeries();
                timeSeries.put(year, (double)count);
                NGrams.add(word);
                NGramTS.add(timeSeries);
            }
            else {
                TimeSeries timeSeries = NGramTS.get(NGrams.size() - 1);
                timeSeries.put(year, (double)count);
            }
        }
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy,
     * not a link to this NGramMap's TimeSeries. In other words, changes made
     * to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word) {
        TimeSeries history = (TimeSeries)countHistoryHelper(word).clone();
        return history;
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other words,
     * changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries history = countHistoryHelper(word);
        TimeSeries historyLimited = new TimeSeries(history, startYear, endYear);
        return historyLimited;
    }

    private TimeSeries countHistoryHelper(String word) {
        if (!NGrams.contains(word)) throw new IllegalArgumentException("calls countHistory() has a null word.");
        int ind = NGrams.indexOf(word);
        TimeSeries history = NGramTS.get(ind);
        return history;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return (TimeSeries)total.clone();
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to
     * all words recorded in that year.
     */
    public TimeSeries weightHistory(String word) {
        TimeSeries countHistory = countHistory(word);
        TimeSeries weightHistory = countHistory.dividedBy(total);
        return weightHistory;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries countHistoryLimited = countHistory(word, startYear, endYear);
        TimeSeries weightHistoryLimited = countHistoryLimited.dividedBy(total);
        return weightHistoryLimited;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries summedWeightHistory = new TimeSeries();
        for (String word: words) {
            TimeSeries weightHistory = weightHistory(word);
            summedWeightHistory.plus(weightHistory);
        }
        return summedWeightHistory;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS
     * between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     * this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries summedWeightHistory = new TimeSeries();
        for (String word: words) {
            TimeSeries weightHistoryLimited = weightHistory(word, startYear, endYear);
            summedWeightHistory = summedWeightHistory.plus(weightHistoryLimited);
        }
        return summedWeightHistory;
    }


}
