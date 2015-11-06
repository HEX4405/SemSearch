package service;

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

        long startTime = System.currentTimeMillis();

        searchConcepts("communism", "BING", 5, 0);

        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Execution time: " + elapsedTime + " ms.");

    }

    public static void searchConcepts(String query, String searchEngine, int numberOfResults, double similarity) {
        List<String> urls = null;
        List<String> texts = null;
        int currentNumberOfResults = 0;

        for(int i = 0; i < 10 && currentNumberOfResults != numberOfResults; i++) {
            urls = SearchEngine.search(searchEngine, query, numberOfResults);
            texts = Extractor.extract(urls);
            currentNumberOfResults = texts.size();
        }

        System.out.println("Annotating...");
        List<List<String>> urisList = URIFinder.find(texts, 0.2);
        System.out.println("Frequency sorting...");
        List<List<String>> sortedUrisList = FrequencySorter.processAll(urisList);
        System.out.println("Graph building...");
        List<List<Model>> modelsList = RDFGraphGenerator.generateAllRDF(sortedUrisList);
        //System.out.println("Unifying models...");
        //List<Model> unifiedModels = GraphUnifier.unifyAllModels(modelsList);
        System.out.println("Done !");
    }
}

