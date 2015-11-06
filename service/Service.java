package service;

import service.extract.Extractor;
import service.finder.URIFinder;
import service.frequency.FrequencySorter;
import service.graphUnifier.GraphUnifier;
import service.search.SearchEngine;
import service.sparql.RDFGraphGenerator;

import java.util.*;

import org.apache.jena.rdf.model.Model;

public class Service {

    public static void main(String [] args) {

        
        List<String> urls = null;
        List<String> texts = null;
        int numberOfResults = 1;
        String query = "Karl Marx";
 
        urls = SearchEngine.search(SearchEngine.Engine.BING, query, numberOfResults);
        texts = Extractor.extract(urls);


        List<List<String>> urisList = URIFinder.find(texts, 0.2);
        List<List<String>> sortedUrisList = FrequencySorter.processAll(urisList);

        System.out.println("RDF..");
        List<Model> models = RDFGraphGenerator.generateRDF(sortedUrisList.get(0));
        System.out.println("Done");
        
        String uri = sortedUrisList.remove(0).remove(0);
        Model m = models.remove(0);
        m.write(System.out);
        
        
        String title = ExtractInformation.getTitle(m, uri);
        System.out.println(title);
        System.out.println("lol");
        
        
    }
}

