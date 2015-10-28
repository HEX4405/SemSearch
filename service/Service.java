package service;

import service.extract.Extractor;
import service.finder.URIFinder;
import service.search.SearchEngine;

import java.io.IOException;
import java.util.List;

public class Service {

    public static void main(String [] args) throws IOException {
        List<String> urls = SearchEngine.search("obama", 10);
        List<String> texts = Extractor.extract(urls);
        List<List<String>> urisLists = URIFinder.find(texts);
    }
}
