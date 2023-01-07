package ngordnet.hyponyms;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class TestWordNet {
    @Test
    public void TestWordNet() {
        WordNet wn = new WordNet("./data/wordnet/synsets11.txt", "./data/wordnet/hyponyms11.txt");
        assertEquals(Set.of("antihistamine", "actifed"), wn.getSortedHyponyms("antihistamine"));
    }
}
