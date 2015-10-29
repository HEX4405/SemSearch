package service;

import service.extract.Extractor;
import service.finder.URIFinder;
import service.search.SearchEngine;
import java.util.List;

public class Service {
    /*
    TODO : choose language
    */
    public static void main(String [] args) {
        System.out.println("Searching...");
        List<String> urls = SearchEngine.search(SearchEngine.Engine.GOOGLE, "obama", 10);
        System.out.println("Extracting...");
        List<String> texts = Extractor.extract(urls);
        System.out.println("Finding URI...");
        List<List<String>> urisLists = URIFinder.find(texts);
        System.out.println("Done.");

        for(List<String> uris : urisLists) {
            for(String uri : uris) {
                System.out.println(uri);
            }
            System.out.println("===========");
        }
    }
}
