package service;

import service.extract.Extractor;
import service.search.SearchEngine;

import java.io.IOException;
import java.util.List;

public class Service {

    public static void main(String [] args) throws IOException {
        SearchEngine se = new SearchEngine();
        Extractor extractor = new Extractor();

        List<String> urls = se.search("doge", 10);
        extractor.addUrls(urls);
        List<String> texts = extractor.getTexts();
    }
}
