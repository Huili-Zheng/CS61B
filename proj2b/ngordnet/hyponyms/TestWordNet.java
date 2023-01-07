package ngordnet.hyponyms;

import org.junit.Test;

import java.util.Arrays;
import java.util.Set;

import static org.junit.Assert.*;

public class TestWordNet {
    @Test
    public void TestWordNet() {
        // create a wordNet to index the data
        WordNet wn = new WordNet("./data/wordnet/synsets16.txt", "./data/wordnet/hyponyms16.txt");

        // test getSortedHyponyms()
        assertEquals(Set.of("alteration", "change", "demotion", "increase", "jump", "leap", "modification", "saltation", "transition", "variation"), wn.getSortedHyponyms("change"));

        // test getIntersection()
        assertEquals(Set.of("alteration", "change", "increase", "jump", "leap", "modification", "saltation", "transition"), wn.getIntersection(Arrays.asList("change", "occurrence")));
    }
}
