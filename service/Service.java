package service;

import service.extract.Extractor;
import service.finder.URIFinder;
import service.frequency.FrequencySorter;
import service.search.SearchEngine;

import java.util.*;

public class Service {

    public static void main(String [] args) {
        System.out.println("Searching...");
        List<String> urls = SearchEngine.search(SearchEngine.Engine.GOOGLE, "communism", 10);
        System.out.println("Extracting...");
        List<String> texts = Extractor.extract(urls);
        System.out.println("Finding URI...");
        List<List<String>> urisList = URIFinder.find(texts, 0.5);
        System.out.println("Done.");
        List<List<String>> sortedUrisList = FrequencySorter.processAll(urisList, 1);
    }
}

