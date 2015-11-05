package service.sparql;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.thrift.wire.RDF_BNode;
import org.apache.jena.query.*;

public class RDFGraphGenerator {

	private RDFGraphGenerator() {

	}

	/**
	 * 
	 * @param uris Liste d'uris dont on veut le graphe
	 * @return models. Une liste de graphes rdf contenant tout ce qui est associ� � l'URI correspondante
	 */
	public static List<Model> generateRDF(List<String> uris) {
		
		Model m = ModelFactory.createDefaultModel();
		List<Model> models = new ArrayList<Model>();

		for (String uri : uris) {
			m = ModelFactory.createDefaultModel();
			m.createResource(uri);

			//Requête permettant de récupérer tous les prédicats et objets dont l'URI donnée est le sujet
			String queryString = "select distinct ?property ?value from <http://dbpedia.org>" + "where { " + "<" + uri + "> ?property ?value }";
			Query query = QueryFactory.create(queryString);
			QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
			try {
				ResultSet results = qexec.execSelect();
				while(results.hasNext()) {
					QuerySolution sol = results.nextSolution();
					m.getResource(uri).addProperty(m.createProperty(sol.getResource("property").toString()), sol.get("value"));
				}
			} finally {
				qexec.close();
			}
			
			//Requête permettant de récupérer tous les sujet et prédicats dont l'URI donnée et l'objet
			queryString = "select distinct ?resource ?property from <http://dbpedia.org>" + "where { ?resource ?property " + "<" + uri + "> }";
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

			models.add(m);
		}
		return models;
	}
}