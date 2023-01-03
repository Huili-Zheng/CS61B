package ngordnet.ngrams;

import java.util.*;

/** An object for mapping a year number (e.g. 1996) to numerical data. Provides
 *  utility methods useful for data analysis.
 *  @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
        super();
    }

    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     *  inclusive of both end points. */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super(ts);
        for (Map.Entry<Integer, Double> entry : ts.entrySet()) {
            if (entry.getKey() < startYear || entry.getKey() > endYear) {
                this.remove(entry.getKey());
            }
        }

    }


    /** Returns all years for this TimeSeries (in any order). */
    public List<Integer> years() {
        List<Integer> allYears = new ArrayList<>(keySet());
        return allYears;
    }

    /** Returns all data for this TimeSeries (in any order).
     *  Must be in the same order as years(). */
    public List<Double> data() {
        List<Double> allValues =  new ArrayList<>(values());
        return allValues;
    }

    /** Returns the yearwise sum of this TimeSeries with the given TS. In other words, for
     *  each year, sum the data from this TimeSeries with the data from TS. Should return a
     *  new TimeSeries (does not modify this TimeSeries). */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries ts_copy = (TimeSeries) ts.clone();
        this.forEach(
                (key, value) -> ts_copy.merge(key, value, (v1, v2) -> v1 + v2)
        );
        return ts_copy;
    }

     /** Returns the quotient of the value for each year this TimeSeries divided by the
      *  value for the same year in TS. If TS is missing a year that exists in this TimeSeries,
      *  throw an IllegalArgumentException. If TS has a year that is not in this TimeSeries, ignore it.
      *  Should return a new TimeSeries (does not modify this TimeSeries). */
     public TimeSeries dividedBy(TimeSeries ts) {
         TimeSeries this_copy = (TimeSeries) this.clone();
         for (Map.Entry element: this.entrySet()) {
             int year = (int)element.getKey();
             if (!ts.containsKey(year)) {
                 throw new IllegalArgumentException("argument in calls dividedBy() has no such year");
             }
             else {
                 this_copy.merge(year, ts.get(year), (v1, v2) -> v1/v2);
             }
         }
        return this_copy;
    }
}
