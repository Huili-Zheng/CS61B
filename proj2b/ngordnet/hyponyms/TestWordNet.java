package ngordnet.hyponyms;

import org.junit.Test;

import java.util.Arrays;


import static org.junit.Assert.*;

public class TestWordNet {

    public WordNet createWordNet() {
        // create a wordNet to index the data
        return new WordNet("./data/wordnet/synsets16.txt", "./data/wordnet/hyponyms16.txt");
    }
    @Test
    public void TestGetIntersection() {
        // create a wordNet to index the data
        WordNet wn = createWordNet();

        // test getIntersection()
        assertEquals(Arrays.asList("alteration", "change", "increase", "jump", "leap", "modification", "saltation", "transition"), wn.getIntersection(Arrays.asList("change", "occurrence")));
    }
}
