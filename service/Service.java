package service;

import modele.Snippet;
import org.apache.jena.rdf.model.Model;
import service.extract.Extractor;
import service.finder.URIFinder;
import service.frequency.FrequencySorter;
import service.graphUnifier.GraphUnifier;
import service.search.SearchEngine;
import service.sparql.RDFGraphGenerator;

import java.util.*;

public class Service {

    public static void main(String [] args) {
        long tStart = System.currentTimeMillis();

        identifyConcepts("communism", "BING", 5, 0);

        long tStop = System.currentTimeMillis();

        System.out.println((tStop - tStart) + " ms.");
    }

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

        List<List<Model>> modelsList = RDFGraphGenerator.generateAllRDF(sortedUrisList);
        List<Model> unifiedModels = GraphUnifier.unifyAllModels(modelsList);

        List<Snippet> snippets = new ArrayList<>();

        for(int i = 0; i < titles.size(); i++) {
            String title = titles.get(i);
        }

        return snippets;
    }
}

