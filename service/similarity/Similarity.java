package service.similarity;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.StmtIterator;

public class Similarity {
	
	
	private static List<Model> listModelsDefinitions = new ArrayList<>();
	
	private Similarity() {
    }
	
	private static float getNumberTripls(StmtIterator it){
		float result = 0;
		while (it.hasNext()) {
		    it.nextStatement();
		    result++;
		}
		return result;
	}
	
	private static double getConcordance(Model model1, Model model2) {
		
		Model union = model1.union(model2);
		Model intersection = model1.intersection(model2);
		
		StmtIterator iter1 = union.listStatements();
		StmtIterator iter2 = intersection.listStatements();
		
		double cardinalUnion = Similarity.getNumberTripls(iter1);
		double cardinalIntersection = Similarity.getNumberTripls(iter2);
		
		return cardinalIntersection/cardinalUnion;
	}

	public static double[][] getMatrixSimilarity(List<Model> models){
		int n = models.size();
		double[][] result = new double[n][n];

		for(int i = 0; i < models.size(); i++) {
			for(int j = 0; j < models.size(); j++) {
				result[i][j] = Similarity.getConcordance(models.get(i), models.get(j));
			}
		}

		return result;
	}
	
	public static List<Model> getListModelDefinitions()
	{
		return Similarity.listModelsDefinitions;
	}
	
	public static void setModelToListModelDefinitions(Model m)
	{
		Similarity.listModelsDefinitions.add(m);
	}
}
