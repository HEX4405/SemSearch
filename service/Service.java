package service;

import modele.Snippet;
import service.extract.Extractor;
import service.finder.URIFinder;
import service.frequency.FrequencySorter;
import service.search.SearchEngine;

import java.util.*;

public class Service {

    public static List<Snippet> identifyConcepts(String query, String searchEngine, int numberOfResults, double similarity) {
        List<String> urls = null;
        Map<String, String> textsMap = null;
        int currentNumberOfResults = 0;

        for(int i = 0; i < 10 && currentNumberOfResults != numberOfResults; i++) {
            urls = SearchEngine.search(searchEngine, query, numberOfResults);
            textsMap = Extractor.extract(urls);
            currentNumberOfResults = textsMap.size();
        }

        List<String> titles = new ArrayList<>(textsMap.keySet());
        List<String> texts = new ArrayList<>(textsMap.values());

        List<List<String>> urisList = URIFinder.find(texts, 0.2);
        List<List<String>> sortedUrisList = FrequencySorter.processAll(urisList);

        //List<List<Model>> modelsList = RDFGraphGenerator.generateAllRDF(sortedUrisList);
        //List<Model> unifiedModels = GraphUnifier.unifyAllModels(modelsList);

        List<Snippet> snippets = new ArrayList<>();

        for(int i = 0; i < numberOfResults; i++) {
            String title = titles.get(i);
            snippets.add(new Snippet(title));
        }

        return snippets;
    }
}

