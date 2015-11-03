package sparqlMaker;

import java.io.File;
import java.io.FileReader;
import java.util.List;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.shared.PrefixMapping;

public class SparqlQuery {
	
	public OntModel createModel(String[] uris){
		
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		
		for(int i=0 ; i < uris.length; i++){
			
			model.createResource(uris[i]);
			
		}
		
		return null;
	}
	
	public static void printResults(String fileName)
	{
	OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
	//String fileName = "../travel.owl";
		try {
			
			//read file into a model
			
			File file = new File(fileName);
			FileReader reader = new FileReader(file);
			model.read(reader,null);
	
		} catch(Exception e){
			e.printStackTrace();
		}
		
		
		String sparqlQuery;
		//put the query as a string
		sparqlQuery = "";
		System.out.println(sparqlQuery);
		
		
		Query query = QueryFactory.create(sparqlQuery);
		QueryExecution qe = QueryExecutionFactory.create(query,model);
		ResultSet results = qe.execSelect();
		List<String> vars = results.getResultVars();
		
		while(results.hasNext()){
			QuerySolution qs = results.nextSolution();
			System.out.println("------solutions-------");
			PrefixMapping pm = query.getPrefixMapping();
			
			for(int i = 0; i < vars.size(); i++){
				String var = vars.get(i).toString();
				RDFNode node = qs.get(var);
				//System.out.println(var + "\t" + node.toString());
				String text ="";
				if(node.isURIResource()){
					text = pm.shortForm(node.asNode().getURI());
				}else{
					text = node.toString();
				}
				System.out.println(var + "\t" + text);
			}
		}
		
		ResultSetFormatter.outputAsXML(System.out, results);
	}	
}
