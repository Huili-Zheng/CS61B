package ngordnet.hyponyms;

import edu.princeton.cs.algs4.In;

import java.util.*;

import static java.util.Collections.*;


public class WordNet {
    private HyponymGraph graph; // Store the hyponyms relationship data as a graph
    private ListMap synsetsMap; // Store the synsets data as a map, id as key, words as value
    private ListMap wordsMap; // Store the synsets data as a map, word as key, ids as value



    /**
     * Create a wordNet with graph and map
     * @param synsetsFilename
     * @param hypnoymsFilename
     */
    public WordNet(String synsetsFilename, String hypnoymsFilename) {
        In synsetsFile = new In(synsetsFilename);
        In hypnoymsFile = new In(hypnoymsFilename);

        Integer V = Integer. valueOf(synsetsFilename.replaceAll("[^0-9]", ""));
        graph = new HyponymGraph(V);
        synsetsMap = new ListMap<Integer, List<String>>();
        wordsMap = new ListMap<String, List<Integer>>();

        while (!synsetsFile.isEmpty()) {
            String token = synsetsFile.readLine();
            String[] arrOfStr = token.split(",");
            int word_id = Integer.valueOf(arrOfStr[0]);
            String[] words = arrOfStr[1].split(" ");;
            synsetsMap.put(word_id, List.of(words));
            for (String word : words) {
                wordsMap.putOneValue(word, word_id);
            }
        }

        while (!hypnoymsFile.isEmpty()) {
            String token = hypnoymsFile.readString();
            String[] arrOfStr = token.split(",");
            int word_id = Integer.valueOf(arrOfStr[0]);
            int[] hypnoym_ids = new int[arrOfStr.length - 1];
            for (int i = 0; i < arrOfStr.length - 1; i++) {
                hypnoym_ids[i] = Integer.parseInt((arrOfStr[1 + i]));
            }
            for (int hypnoym_id : hypnoym_ids) {
                graph.addEdge(word_id, hypnoym_id);
            }
        }
    }

    public Set<String> getIntersection(List<String> words) {
        Set<String>[] sortedHyponyms = (HashSet<String>[]) new HashSet<?>[words.size()];
//        List<String>[] sortedHyponyms = (ArrayList<String>[]) new ArrayList<?>[words.size()];
        for (int i = 0; i < words.size(); i++) {
            sortedHyponyms[i] = getSortedHyponyms(words.get(i));
        }
        Set<String> hyponymsIntersection = sortedHyponyms[0];
        for (Set<String> hyponymsSorted : sortedHyponyms) {
            hyponymsIntersection.retainAll(hyponymsSorted);
        }
        return hyponymsIntersection;
    }

    /**
     * Return all hyponyms of the specified word given the word
     * @param s_word the word id
     * @return all hyponyms of the specified word
     */
    public Set<String> getSortedHyponyms(String s_word) {
        List<Integer> wordIds = wordsMap.get(s_word);
        List<String> hyponymsSorted = new ArrayList<>();
        for (int wordId : wordIds) {
            HashSet<Integer> hyponymIds = getHyponymIds(wordId);
            List<String> hyponyms = getHyponymsById(hyponymIds);
            hyponymsSorted.addAll(hyponyms);
        }
        return sortHyponyms(hyponymsSorted);
    }

    // Return all hyponymIDs of the specified word given the word id
    private HashSet<Integer> getHyponymIds(int s) {
        DepthFirstPaths dfs = new DepthFirstPaths(graph, s);
        HashSet<Integer> hyponymIds = dfs.connectedComponent(s);
        return hyponymIds;
    }

    // Return all hyponyms of the specified hyponymIDs
    private List<String> getHyponymsById(HashSet<Integer>hyponymIds) {
        List<String> hyponyms = new ArrayList<>();
        for (int hyponymId : hyponymIds) {
            for (String hyponym:  (List<String>)synsetsMap.get(hyponymId)) {
                hyponyms.add(hyponym);
            }
        }
        return hyponyms;
    }

    // Return the sorted set in alphabetical order
    private Set<String> sortHyponyms(List<String> hyponyms) {
        // sort the list in alphabetical order
        Collections.sort(hyponyms);
        // cast a sorted list to a set with the same order
        Set<String> hyponymsSorted = new LinkedHashSet<>(hyponyms);
        return hyponymsSorted;
    }





}
