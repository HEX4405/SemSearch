package service;

import service.extract.Extractor;
import service.finder.URIFinder;
import service.frequency.FrequencySorter;
import service.search.SearchEngine;

import java.util.*;

public class Service {

    public static void main(String [] args) {
        String query = "communism";
        int numberOfResults = 10;
        double confidence = 0.2;

        System.out.println("Searching...");
        List<String> urls = SearchEngine.search(SearchEngine.Engine.BING, query, numberOfResults);
        System.out.println("Extracting...");
        List<String> texts = Extractor.extract(urls);
        System.out.println("Finding URI...");
        List<List<String>> urisList = URIFinder.find(texts, confidence);
        System.out.println("Done.");
        List<List<String>> sortedUrisList = FrequencySorter.processAll(urisList);

        for(List<String> sortedUris : sortedUrisList) {
            for(String uri : sortedUris) {
                System.out.println(uri);
            }
            System.out.println("============");
        }
    }
}

