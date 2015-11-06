package service;

import service.extract.Extractor;
import service.finder.URIFinder;
import service.frequency.FrequencySorter;
import service.search.SearchEngine;
import service.similarity.Similarity;
import service.sparql.RDFGraphGenerator;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;

import java.util.*;

public class Service {

    public static void main(String [] args) {
        /*System.out.println("Searching...");
        List<String> urls = SearchEngine.search(SearchEngine.Engine.GOOGLE, "communism", 10);
        System.out.println("Extracting...");
        List<String> texts = Extractor.extract(urls);
        System.out.println("Finding URI...");
        List<List<String>> urisLists = URIFinder.find(texts, 0.5);
        System.out.println("Done.");
        
        
        List<List<Model>> modelsByUri = new ArrayList<>();*/
        
        Model m = ModelFactory.createDefaultModel();
		Model m1 = ModelFactory.createDefaultModel();
		List<String> uris = new ArrayList<String>();
		uris.add("http://dbpedia.org/resource/Medium-security");
		uris.add("http://dbpedia.org/resource/Medium-security");
		Property property = m.createProperty("vCard:FN");
		m.createResource("http://somewhere/JohnSmith").addProperty(property, "John Smith");
		m1.createResource("http://somewhere/JohnSmith").addProperty(property, "John Smith");
		m1.createResource("http://somewhere/JohnWick").addProperty(property, "John Wick");
		List<Model> models = new ArrayList<Model>();
		//models.add(m);
		//models.add(m1);
		models = RDFGraphGenerator.generateRDF(uris);
		int n = models.size();
		float[][] result = new float[n][n];
		result = Similarity.getMatrixSimilarity(models);
		for(int i = 0; i<n;i++){
			for(int j = 0; j<n;j++){
				System.out.print(result[i][j]+" ");
			}
			System.out.print("\n");
		}
    }
}

