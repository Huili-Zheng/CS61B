package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.hyponyms.WordNet;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;

import java.util.HashMap;
import java.util.List;

public class HypohistTextHandler extends NgordnetQueryHandler {
    WordNet wn;
    NGramMap ngm;

    public HypohistTextHandler(WordNet wn, NGramMap ngm) {
        this.ngm = ngm;
        this.wn = wn;
    }

    @Override
    public String handle(NgordnetQuery q) {
        StringBuilder returnString = new StringBuilder();
        List<String> intersectionHyponyms = wn.getIntersection(q.words());
        System.out.println(intersectionHyponyms);

        if (q.k() == 0) {
            for (String word : intersectionHyponyms) {
                TimeSeries wordCount = ngm.weightHistory(word, q.startYear(), q.endYear());
                returnString.append(word).append(": ").append(wordCount.toString()).append("\n");
            }
        }
        else {
            HashMap<String, Double> popularity = HyponymsHandler.getPopularity(intersectionHyponyms, q, ngm);
            List<String> kPopular = wn.sortHyponyms(HyponymsHandler.getKPopularWords(popularity, q));
            if (kPopular == null) {
                return "";
            }
            for (String word : kPopular) {
                TimeSeries wordCount = ngm.weightHistory(word, q.startYear(), q.endYear());
                returnString.append(word).append(": ").append(wordCount.toString()).append("\n");
            }
        }
        return returnString.toString();
    }
}
