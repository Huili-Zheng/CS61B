package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.hyponyms.WordNet;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import ngordnet.plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HypohistHandler extends NgordnetQueryHandler {
    WordNet wn;
    NGramMap ngm;

    public HypohistHandler(WordNet wn, NGramMap ngm) {
        this.ngm = ngm;
        this.wn = wn;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> intersectionHyponyms = wn.getIntersection(q.words());

        if (q.k() == 0) {
            return "";
        }
        else {
            HashMap<String, Double> popularity = HyponymsHandler.getPopularity(intersectionHyponyms, q, ngm);
            List<String> kPopular = wn.sortHyponyms(HyponymsHandler.getKPopularWords(popularity, q));
            if (kPopular == null) {
                return "";
            }
            ArrayList<TimeSeries> lts = new ArrayList<>();
            for (String word : kPopular) {
                lts.add(ngm.weightHistory(word, q.startYear(), q.endYear()));
            }
            XYChart chart = Plotter.generateTimeSeriesChart(kPopular, lts);
            return Plotter.encodeChartAsString(chart);
        }
    }
}
