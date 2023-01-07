package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.hyponyms.WordNet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HyponymsHandler extends NgordnetQueryHandler {
    WordNet wn;
//    public HyponymsHandler() {
//    }
//
//    @Override
//    public String handle(NgordnetQuery q) {
//        return "Hello";
//    }
    public HyponymsHandler(WordNet wordNet) {
        this.wn = wordNet;
    }
    @Override
    public String handle(NgordnetQuery q) {
        System.out.println("Got query that looks like:");
        System.out.println("Words: " + q.words());

        String returnString = new String();
        List<String> intersectionHyponyms = wn.getIntersection(q.words());

        returnString = String.join(" ", intersectionHyponyms);
    return returnString;
    }


}
