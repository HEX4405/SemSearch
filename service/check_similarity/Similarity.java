package service.check_similarity;

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
	
	
	private static float getConcordance(Model model1, Model model2)
	{
		// liste des déclarations dans le modèle
		StmtIterator iter1 = model1.listStatements();
		StmtIterator iter2 = model2.listStatements();
		
		float cardinalUnion = 0;
		float cardinalIntersection = 0;

		// affiche l'objet, le prédicat et le sujet de chaque déclaration
		while (iter1.hasNext()) 
		{
			cardinalUnion++;
			
		    Statement stmt1      = iter1.nextStatement();  // obtenir la prochaine déclaration
		    Resource  subject1   = stmt1.getSubject();     // obtenir le sujet
		    Property  predicate1 = stmt1.getPredicate();   // obtenir le prédicat
		    RDFNode   object1    = stmt1.getObject();      // obtenir l'objet
		    while(iter2.hasNext())
		    {
		    	cardinalUnion++;
		    	
		    	Statement stmt2      = iter2.nextStatement();  // obtenir la prochaine déclaration
			    Resource  subject2   = stmt2.getSubject();     // obtenir le sujet
			    Property  predicate2 = stmt2.getPredicate();   // obtenir le prédicat
			    RDFNode   object2    = stmt2.getObject();      // obtenir l'objet
			    
			    
			    if(subject1.equals(subject2) && predicate1.equals(predicate2) && object1.equals(object2))
			    {
			    	cardinalUnion--;
			    	cardinalIntersection++;
			    }
		    }
		    
		}
		return cardinalIntersection/cardinalUnion;
	}
	
	public static float[][] getMatrixSimilarity(List<Model> modelesAssocies){
		
		Integer N = modelesAssocies.size();
		float[][] result = new float[N][N];
		
		int i = 0;
		int j = 0;
		for(Model it1 : modelesAssocies)
		{
			for(Model it2 : modelesAssocies)
			{
				if(i==j)
				{
					result[i][j] = 1;
				}
				else
				{
					result[i][j] = Similarity.getConcordance(it1, it2);
				}
				j++;
			}
			i++;
		}
		return result;
		
	}

}
