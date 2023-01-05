package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import ngordnet.plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;

public class HistoryHandler extends NgordnetQueryHandler {
    public NGramMap ngm;

    public HistoryHandler(NGramMap map) {
        ngm = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        System.out.println("Got query that looks like:");
        System.out.println("Words: " + q.words());
        System.out.println("Start Year: " + q.startYear());
        System.out.println("End Year: " + q.endYear());

        ArrayList<TimeSeries> lts = new ArrayList<>();
        for (String word : q.words()) {
            lts.add(ngm.weightHistory(word, q.startYear(), q.endYear()));
        }

        XYChart chart = Plotter.generateTimeSeriesChart(q.words(), lts);
        String returnString = Plotter.encodeChartAsString(chart);

        return returnString;
    }
}
