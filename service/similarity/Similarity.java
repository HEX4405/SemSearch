package service.similarity;

import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

public class Similarity {
	
	private Similarity() {
    }
	
	
	private static float getConcordance(Model model1, Model model2) {
		StmtIterator iter1 = model1.listStatements();
		StmtIterator iter2 = model2.listStatements();
		
		float cardinalUnion = 0;
		float cardinalIntersection = 0;

		while (iter1.hasNext()) {
			cardinalUnion++;
			
		    Statement stmt1      = iter1.nextStatement();
		    Resource  subject1   = stmt1.getSubject();
		    Property  predicate1 = stmt1.getPredicate();
		    RDFNode   object1    = stmt1.getObject();
		    while(iter2.hasNext()) {
		    	cardinalUnion++;
		    	
		    	Statement stmt2      = iter2.nextStatement();
			    Resource  subject2   = stmt2.getSubject();
			    Property  predicate2 = stmt2.getPredicate();
			    RDFNode   object2    = stmt2.getObject();
			    
			    
			    if(subject1.equals(subject2) && predicate1.equals(predicate2) && object1.equals(object2)) {
			    	cardinalUnion--;
			    	cardinalIntersection++;
			    }
		    }
		    
		}
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
