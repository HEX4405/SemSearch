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
        List<List<String>> urisLists = URIFinder.find(texts, 0.5);
        System.out.println("Done.");

        for(List<String> uris : urisLists) {
           List<String> sortedUris = FrequencySorter.process(uris);
           for(String uri : sortedUris) {
               System.out.println(uri);
           }
           System.out.println("===========");
        }
    }
}

