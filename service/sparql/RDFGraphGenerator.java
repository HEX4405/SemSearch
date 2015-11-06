package service.sparql;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.query.*;

public class RDFGraphGenerator {

	private RDFGraphGenerator() {

	}

	/**
	 * 
	 * @param uris Liste d'uris dont on veut le graphe
	 * @return models. Une liste de graphes rdf contenant tout ce qui est associï¿½ ï¿½ l'URI correspondante
	 */
	public static List<Model> generateRDF(List<String> uris) {
		
		Model m = ModelFactory.createDefaultModel();
		List<Model> models = new ArrayList<Model>();

		for (String uri : uris) {
			m = ModelFactory.createDefaultModel();
			m.createResource(uri);

			//RequÃªte permettant de récupérer tous les prédicats et objets dont l'URI donnÃ©e est le sujet
			String queryString = "select distinct ?property ?value from <http://dbpedia.org> where { { <"+ uri +"> ?property ?value . FILTER(STRSTARTS(STR(?property), \"http://www.w3.org/2000/01/rdf-schema#\")) } UNION { <" + uri + "> ?property ?value .  FILTER(STRSTARTS(STR(?property), \"http://www.w3.org/1999/02/22-rdf-syntax-ns\")) } UNION { <" + uri + "> ?property ?value .  FILTER(STRSTARTS(STR(?property), \"http://xmlns.com/foaf/0.1/\")) } }  " ;
			Query query = QueryFactory.create(queryString);
			QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
			try {
				ResultSet results = qexec.execSelect();
				while(results.hasNext()) {
					QuerySolution sol = results.nextSolution();
					m.getResource(uri).addProperty(m.createProperty(sol.getResource("property").toString()), sol.get("value"));
					System.out.println(sol.getResource("property").toString());
				}
			} finally {
				qexec.close();
			}
			
			//RequÃªte permettant de récupérer tous les sujet et prÃ©dicats dont l'URI donnÃ©e et l'objet
			queryString = "select distinct ?resource ?property from <http://dbpedia.org> where { { ?resource ?property <"+ uri +"> . FILTER(STRSTARTS(STR(?property), \"http://www.w3.org/2000/01/rdf-schema#\")) } UNION { ?resource ?property <"+ uri +"> .  FILTER(STRSTARTS(STR(?property), \"http://www.w3.org/1999/02/22-rdf-syntax-ns\")) } UNION { ?resource ?property <"+ uri +"> .  FILTER(STRSTARTS(STR(?property), \"http://xmlns.com/foaf/0.1/\")) } }  " ;
			query = QueryFactory.create(queryString);
			qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
			try {		
				ResultSet results = qexec.execSelect();
				for (; results.hasNext();) {
					QuerySolution sol = results.nextSolution();
					m.createResource(sol.getResource("resource"));
					m.getResource(sol.getResource("resource").toString()).addProperty(m.createProperty(sol.getResource("property").toString()), uri);
				}
			} finally {
				qexec.close();
			}
		
		}
		return models;
	}

	public static List<List<Model>> generateAllRDF(List<List<String>> urisList) {
		List<List<Model>> modelsList = new ArrayList<>();

		for(List<String> uris : urisList) {
			modelsList.add(generateRDF(uris));
		}

		return modelsList;
	}
	
	public static void main(String [] args)
	{
		List<String> uris = new ArrayList<String>();
		uris.add("http://dbpedia.org/resource/Barack_Obama");
		RDFGraphGenerator.generateRDF(uris);
	}
}