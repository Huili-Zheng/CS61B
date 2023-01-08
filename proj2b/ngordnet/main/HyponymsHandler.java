package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.hyponyms.WordNet;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;

import java.util.*;
import java.util.stream.Collectors;

public class HyponymsHandler extends NgordnetQueryHandler {
    WordNet wn;
    NGramMap ngm;

    public HyponymsHandler(WordNet wn, NGramMap ngm) {
        this.wn = wn;
        this.ngm = ngm;
    }

    @Override
    public String handle(NgordnetQuery q) {
        System.out.println("Got query that looks like:");
        System.out.println("Words: " + q.words());
        System.out.println("Start Year: " + q.startYear());
        System.out.println("End Year: " + q.endYear());
        System.out.println("k: " + q.k());


        List<String> intersectionHyponyms = wn.getIntersection(q.words());

        if (q.k() == 0) {
            return intersectionHyponyms.toString();
        }
        else {
            HashMap<String, Double> popularity = getPopularity(intersectionHyponyms, q);
            List<String> kPopular = wn.sortHyponyms(getKPopularWords(popularity, q));
            if (kPopular == null) {
                return "";
            }
            return kPopular.toString();
        }
    }

    public HashMap<String, Double> getPopularity(List<String> intersectionHyponyms, NgordnetQuery q) {
        HashMap<String, Double> popularity = new LinkedHashMap<>();
        for (String word : intersectionHyponyms) {
            TimeSeries wordTS = ngm.countHistory(word, q.startYear(), q.endYear());
            Double occurrences = 0.0;
            for (Double aDouble : wordTS.data()) {
                occurrences = occurrences + aDouble;
            }
            if (occurrences > 0.0) {
                popularity.put(word, occurrences);
            }
        }
        return popularity;
    }

    public List<String> getKPopularWords(HashMap<String, Double> popularity, NgordnetQuery q) {
        if (popularity == null) {
            return null;
        }
        return popularity.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .limit(q.k())
                .collect(Collectors.toList());
    }

}
