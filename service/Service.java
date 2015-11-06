package service;

import org.apache.jena.rdf.model.Model;
import service.extract.Extractor;
import service.finder.URIFinder;
import service.frequency.FrequencySorter;
import service.search.SearchEngine;
import service.sparql.RDFGraphGenerator;

import java.util.*;

public class Service {

    public static void main(String [] args) {
        String query = "Karl Marx";
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
        //List<List<Model>> modelsList = RDFGraphGenerator.generateRDFAll(sortedUrisList);

        for(String url : urls) {
            System.out.println(url);
        }

        for(String text : texts) {
            System.out.println(text);
            System.out.println("============");
        }

        System.out.println("LOOOOOOOOOOOOOOOOL");

        for(List<String> sortedUris : sortedUrisList) {
            for(String uri : sortedUris) {
                System.out.println(uri);
            }
            System.out.println("============");
        }

//        List<String> urls = new ArrayList<>();
//        urls.add("http://www.thefreedictionary.com/communism");
//        List<String> texts = Extractor.extract(urls);
//
//        System.out.println(texts.get(0));
    }
}

