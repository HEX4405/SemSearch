package service;

import modele.Concept;
import modele.Snippet;
import org.apache.jena.rdf.model.Model;
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
        long tStart = System.currentTimeMillis();

        identifyConcepts("Karl Marx", "YAHOO", 1, 0);

        long tStop = System.currentTimeMillis();

        System.out.println((tStop - tStart) + " ms.");
    }

    public static List<Snippet> identifyConcepts(String query, String searchEngine, int numberOfResults, double similarity) {
        List<String> urls = new ArrayList<String>();
        Map<String, String> textsMap = new HashMap<String,String>();
        int currentNumberOfResults = 0;

        
        for(int i = 0; i < 10 && currentNumberOfResults != numberOfResults; i++) {
            urls = SearchEngine.search(searchEngine, query, numberOfResults);
            textsMap = Extractor.extract(urls);
            currentNumberOfResults = textsMap.size();
        }

        List<String> titles = new ArrayList<>(textsMap.keySet());
        List<String> texts = new ArrayList<>(textsMap.values());

        List<List<String>> urisList = URIFinder.find(texts, 0.2);
        List<List<String>> sortedUrisList = FrequencySorter.processAll(urisList);
        
        
        System.out.println("Sorted Uris list by URL ");
        for(List<String> i : sortedUrisList)
        {
        	System.out.println("=======================================");
        	if(i.size() > 5)
        	{
        		i = i.subList(0, 4);
        	}
        	
        	for(String j : i)
        	{
        		System.out.println(j);
        	}
        }
        List<List<Model>> modelsList = RDFGraphGenerator.generateAllRDF(sortedUrisList);
        
        
        List<Model> unifiedModels = GraphUnifier.unifyAllModels(modelsList);
        
        int i = 0;
        for(Model m : unifiedModels)
        {
        	List<Concept> listConcept = ExtractInformation.extractInformations(m, sortedUrisList.get(i).get(0));
        	for(Concept c : listConcept)
        	{
        		System.out.println(urls.get(i));
        		System.out.println(c.getTitle());
        		System.out.println(c.getDescription());
        	}
        	i++;
        }
        
        

        List<Snippet> snippets = new ArrayList<>();


        return snippets;
    }
}

