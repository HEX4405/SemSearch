package service.graphUnifier;

import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

public class GraphUnifier {
	
	private GraphUnifier(){
		
	}
	
	public static Model unifyModels(List<Model> models){
		Model result=ModelFactory.createDefaultModel();
		if(models.size()==1)
		{	
			result = models.remove(0);
		}
		else if(models.size()==0){
			result = null;
		}
		else{
			for(Model m : models){
				result=result.union(m);
			}
		}
		return result;
	}
}
