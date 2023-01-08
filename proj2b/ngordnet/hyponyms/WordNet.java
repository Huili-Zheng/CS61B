package ngordnet.hyponyms;

import edu.princeton.cs.algs4.In;

import java.util.*;
import java.util.stream.Collectors;

public class WordNet {
    private HyponymGraph graph; // Store the hyponyms relationship data as a graph
    private ListMap synsetsMap; // Store the synsets data as a map, id as key, words as value
    private ListMap wordsMap; // Store the synsets data as a map, word as key, ids as value



    /**
     * Create a wordNet with graph and map
     * @param synsetsFilename the name of synsetsFile
     * @param hypnoymsFilename the name of hypnoymsFile
     */
    public WordNet(String synsetsFilename, String hypnoymsFilename) {
        In synsetsFile = new In(synsetsFilename);
        In hypnoymsFile = new In(hypnoymsFilename);

        graph = new HyponymGraph();
        // graph gets the node goes with that index given an integer
        synsetsMap = new ListMap<Integer, List<String>>();
        // synsetsMap gets the words given a word id
        wordsMap = new ListMap<String, List<Integer>>();
        // wordsMap gets the word ids point that word given a word

        structureSynsets(synsetsFile);
        structureHyponyms(hypnoymsFile);
    }

    private void structureSynsets(In synsetsFile) {
        while (!synsetsFile.isEmpty()) {
            String token = synsetsFile.readLine();
            String[] arrOfStr = token.split(",");
            int word_id = Integer.parseInt(arrOfStr[0]); // word id
            String[] words = arrOfStr[1].split(" "); // one or multiple words

            synsetsMap.put(word_id, List.of(words));
            // synsetsMap store word id as key, store wordList that contains one or multiple word as value
            for (String word : words) {
                wordsMap.putOneValue(word, word_id);
            }
            // wordsMap store word as key, store wordList that contains one or multiple word id as value
        }
    }

    private void structureHyponyms(In hypnoymsFile) {
        while (!hypnoymsFile.isEmpty()) {
            String token = hypnoymsFile.readString();
            String[] arrOfStr = token.split(",");
            int word_id = Integer.parseInt(arrOfStr[0]);
            int[] hypnoym_ids = new int[arrOfStr.length - 1];
            for (int i = 0; i < arrOfStr.length - 1; i++) {
                hypnoym_ids[i] = Integer.parseInt((arrOfStr[1 + i]));
            }

            graph.addVertex(word_id);
            for (int hypnoym_id : hypnoym_ids) {
                graph.addVertex(hypnoym_id);
                graph.addEdge(word_id, hypnoym_id);
            }
            // graph store the wordId as vertex, store their relation as edge
        }
    }

    /**
     * Return the sorted intersection of the hyponyms given the words
     * @param words the words
     * @return the intersection of the sorted hyponyms
     */
    public List<String> getIntersection(List<String> words) {
        List<String>[] hyponymsSynsets = (ArrayList<String>[]) new ArrayList<?>[words.size()];
        // hyponymsSynsets store the list of hyponyms of each words

        for (int i = 0; i < words.size(); i++) {
            hyponymsSynsets[i] = getHyponymsSynsets(words.get(i));
        }
        // return all hyponyms of the synsets

        List<String> hyponymsIntersection = hyponymsSynsets[0];
        // hyponymsIntersection store the sorted intersection of the list of hyponyms

        for (List<String> hyponymsSynset : hyponymsSynsets) {
            hyponymsIntersection.retainAll(hyponymsSynset);
        }
        return sortHyponyms(hyponymsIntersection);
    }

    // Return all hyponyms of the synsets given the word
    private List<String> getHyponymsSynsets(String s_word) {
        List<Integer> wordIds = wordsMap.get(s_word); // get the lists of hyponymIds of one synset
        List<String> hyponymsSynsets = new ArrayList<>();

        for (int wordId : wordIds) {
            HashSet<Integer> hyponymIds = getHyponymIds(wordId); // get the hyponymIds for a word
            List<String> hyponyms = getHyponymsById(hyponymIds); // get the hyponyms of one synset
            hyponymsSynsets.addAll(hyponyms); // get all the hyponyms of synsets
        }
        return hyponymsSynsets;
    }

    // Return all hyponymIDs of the word given the word id
    private HashSet<Integer> getHyponymIds(int s) {
        DepthFirstPaths dfs = new DepthFirstPaths(graph, s);
        return dfs.connectedComponent(s);
    }

    // Return all hyponyms of the specified hyponymIDs
    private List<String> getHyponymsById(HashSet<Integer>hyponymIds) {
        List<String> hyponyms = new ArrayList<>();

        for (int hyponymId : hyponymIds) {
            hyponyms.addAll((List<String>) synsetsMap.get(hyponymId));
        }
        return hyponyms;
    }

    // Return the sorted set in alphabetical order
    public List<String> sortHyponyms(List<String> hyponyms) {
        // remove the redundancy items in the hyponyms. sort the list in alphabetical order
        return hyponyms.stream().distinct().sorted().collect(Collectors.toList());
    }





}
