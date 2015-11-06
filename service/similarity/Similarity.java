package service.similarity;

import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.StmtIterator;

public class Similarity {
	
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
	
	private static float getConcordance(Model model1, Model model2) {
		
		Model union = model1.union(model2);
		Model intersection = model1.intersection(model2);
		
		StmtIterator iter1 = union.listStatements();
		StmtIterator iter2 = intersection.listStatements();
		
		float cardinalUnion = Similarity.getNumberTripls(iter1);
		float cardinalIntersection = Similarity.getNumberTripls(iter2);
		
		return cardinalIntersection/cardinalUnion;
	}

	public static float[][] getMatrixSimilarity(List<Model> models){
		int n = models.size();
		float[][] result = new float[n][n];

		for(int i = 0; i < models.size(); i++) {
			for(int j = 0; j < models.size(); j++) {
				if(i == j) {
					result[i][j] = 1;
				}
				else {
					result[i][j] = Similarity.getConcordance(models.get(i), models.get(j));
				}
			}
		}

		return result;
	}

}
