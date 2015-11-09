package service;

import modele.Concept;
import modele.Snippet;
import org.apache.jena.rdf.model.Model;
import service.extract.Extractor;
import service.finder.URIFinder;
import service.frequency.FrequencySorter;
import service.graphUnifier.GraphUnifier;
import service.search.SearchEngine;
import service.similarity.Similarity;
import service.sparql.RDFGraphGenerator;

import java.util.*;

import org.apache.jena.rdf.model.Model;

public class Service {

    public static void main(String [] args) {
        
    }

    public static Snippet identifyConcepts(double similarity, String url) {
        List<String> urls = new ArrayList<String>();
        Map<String, String> textsMap = new HashMap<String,String>();

        urls.add(url);
        textsMap = Extractor.extract(urls);

        List<String> titles = new ArrayList<>(textsMap.keySet());
        List<String> texts = new ArrayList<>(textsMap.values());

        List<List<String>> urisList = URIFinder.find(texts, 0.2);
        List<List<String>> sortedUrisList = FrequencySorter.processAll(urisList);
        
        
        System.out.println("Sorted Uris list by URL ");
        List<List<String>> sortedShorterUrisList = new ArrayList<>();
        for(List<String> i : sortedUrisList)
        {
        	System.out.println("=======================================");
        	if(i.size() > 5)
        	{
        		i = i.subList(0, 5);
        	}
        	sortedShorterUrisList.add(i);
        	
        	for(String j : i)
        	{
        		System.out.println(j);
        	}
        }
        List<List<Model>> modelsList = RDFGraphGenerator.generateAllRDF(sortedUrisList);
        
        
        List<Model> unifiedModels = GraphUnifier.unifyAllModels(modelsList);
        
        int i = 0;
        List<Concept> listConcept = new ArrayList<>();
        for(Model m : unifiedModels)
        {
        	System.out.println("=======================================");
        	
        	listConcept = ExtractInformation.extractInformations(m, sortedShorterUrisList.get(i));
        	
        	System.out.println(urls.get(i));
        	
        	for(Concept c : listConcept)
        	{
        		
        		System.out.println(c.getTitle());
        		System.out.println(c.getDescription());
        		System.out.println(c.getImageLink());
        	}
        	i++;
        }
        
        

        List<Snippet> snippets = new ArrayList<>();
        double seuilSimilarite = similarity;
        for(int e = 0; e < titles.size(); e++) {
            if(listConcept.size() == 0)
            {
            	return null;
            }
        	Concept mainConcept = listConcept.remove(0);
        	
        	Snippet snippet = new Snippet(titles.get(e), mainConcept, listConcept);
        	
        	snippets.add(snippet);
        	
        	List<Concept> listDef = Snippet.getListDefinitions();
        	
        	if(listDef.size() == 0)
        	{
        		Snippet.addDefinition(mainConcept);
        	}
        	else
        	{
        		List<Model> modelsExistants = Similarity.getListModelDefinitions();
        		int n = unifiedModels.size() + modelsExistants.size();
    	        double[][] similarityMatrix = new double[n][n];
    	        similarityMatrix = Similarity.getMatrixSimilarity(unifiedModels);
    	        boolean toAdd = true;
    	        for(int v =0; v<modelsExistants.size(); v++)
    	        {
    	        	if(similarityMatrix[v][modelsExistants.size()] < seuilSimilarite)
    	        	{
    	        		toAdd = false;
    	        		break;
    	        	}
    	        }
    	        if(toAdd)
    	        {
    	        	Snippet.addDefinition(mainConcept);
    	        }
        	}
            
            //snippets.add(new Snippet(title));
        }
        if(snippets.size() != 0)
        {
        	return snippets.get(0);
        }
        else
        {
        	return null;
        }
    }
    
    public static List<String> getUrls(String query, String searchEngine, int numberOfResults)
    {
    	List<String> result = SearchEngine.search(searchEngine, query, numberOfResults);
    	return result;
    }
}

