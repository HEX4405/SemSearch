package service;

import service.extract.Extractor;
import service.finder.URIFinder;
import service.search.SearchEngine;
import java.util.List;

public class Service {

    public static void main(String [] args) {
        List<String> urls = SearchEngine.search("obama", 10);
        List<String> texts = Extractor.extract(urls);
        List<List<String>> urisLists = URIFinder.find(texts);
    }
}
