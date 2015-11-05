package service.frequency;


import java.util.*;

public class FrequencySorter {

    private FrequencySorter() {

    }

    // TODO: seuil en dessous duquel le resultat n'est pas ajoute
    public static List<String> process(List<String> uris) {
        Map<String, Integer> frequencies = new HashMap<>();
        for(String uri : uris) {
            if(frequencies.containsKey(uri)) {
                frequencies.put(uri, frequencies.get(uri) + 1);
            }
            else {
                frequencies.put(uri, 1);
            }
        }

        List<Map.Entry<String, Integer>> sortedFrequencies = new LinkedList<Map.Entry<String, Integer>>(frequencies.entrySet());
        Collections.sort(sortedFrequencies, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return -(o1.getValue()).compareTo(o2.getValue());
            }
        });

        List<String> sortedUris = new LinkedList<>();
        for (Iterator<Map.Entry<String, Integer>> it = sortedFrequencies.iterator(); it.hasNext();) {
            Map.Entry<String, Integer> entry = it.next();
            sortedUris.add(entry.getKey());
        }

        return sortedUris;
    }
}
