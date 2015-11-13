package service.sparql;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
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

			//RequÃªte permettant de rï¿½cupï¿½rer tous les prï¿½dicats et objets dont l'URI donnÃ©e est le sujet
			String queryString = "select distinct ?property ?value from <http://dbpedia.org> where { FILTER(!isLiteral(?value) || LANG(?value) = \"en\" || LANG(?value) = \"\") { <"+ uri +"> ?property ?value . FILTER(STRSTARTS(STR(?property), \"http://www.w3.org/2000/01/rdf-schema#\")) } UNION { <" + uri + "> ?property ?value .  FILTER(STRSTARTS(STR(?property), \"http://www.w3.org/1999/02/22-rdf-syntax-ns\")) } UNION { <" + uri + "> ?property ?value .  FILTER(STRSTARTS(STR(?property), \"http://xmlns.com/foaf/0.1/\")) } }  " ;
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
			
			/*//Requête permettant de rï¿½cupï¿½rer tous les sujet et prÃ©dicats dont l'URI donnÃ©e est l'objet
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
			}*/
			models.add(m);
			
		}
		
		
		return models;
	}
	
	public static Model generateRDFDefinitions(String uri)
	{
		Model result = ModelFactory.createDefaultModel();
		
		String queryString = "select distinct ?resource ?property from <http://dbpedia.org> where { { ?resource ?property <"+ uri +"> . FILTER(STRSTARTS(STR(?property), \"http://dbpedia.org/ontology/\")) } }  ";
		Query query = QueryFactory.create(queryString);
		QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
		try {
			ResultSet results = qexec.execSelect();
			if(results.hasNext())
			{
				
				String tmp = "";
				while(results.hasNext())
				{
					QuerySolution sol =results.nextSolution();
					String p = sol.get("property").toString();
					if(sol.get("property").toString().equals("http://dbpedia.org/ontology/wikiPageDisambiguates"))
					{
						tmp= sol.get("resource").toString();
						result.createResource(tmp);
					}
				}
				String uri2 = tmp;
				if(uri2 != "")
				{
					String queryString2 = "select distinct ?property ?value from <http://dbpedia.org> where {{ <"+ uri2 +"> ?property ?value . FILTER(STRSTARTS(STR(?property), \"http://dbpedia.org/ontology/\")) } }  ";
					Query query2 = QueryFactory.create(queryString2);
					QueryExecution qexec2 = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query2);
					try {
						ResultSet results2 = qexec2.execSelect();
						while(results2.hasNext()) {
							QuerySolution sol2 = results2.nextSolution();
							result.getResource(uri2).addProperty(result.createProperty(sol2.getResource("property").toString()), sol2.get("value"));
						}
					} finally {
						qexec.close();
					}
				}
				else
				{
					return null;
				}
			}
			
		}
		catch(Exception e)
		{
			//System.out.println("[RDFGraphGenerator] "+ e.getMessage());
			e.printStackTrace();
			return null;
		}
		finally
		{
			qexec.close();
		}
		
		return result;
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
		String uri="http://dbpedia.org/resource/Doge";
		Model m = RDFGraphGenerator.generateRDFDefinitions(uri);
		
		StmtIterator iter = m.listStatements();

		// affiche l'objet, le prédicat et le sujet de chaque déclaration
		while (iter.hasNext()) {
		    Statement stmt      = iter.nextStatement();  // obtenir la prochaine déclaration
		    Resource  subject   = stmt.getSubject();     // obtenir le sujet
		    Property  predicate = stmt.getPredicate();   // obtenir le prédicat
		    RDFNode   object    = stmt.getObject();      // obtenir l'objet

		    System.out.print(subject.toString());
		    System.out.print(" " + predicate.toString() + " ");
		    if (object instanceof Resource) {
		       System.out.print(object.toString());
		    } else {
		        // l'objet est un littéral
		        System.out.print(" \"" + object.toString() + "\"");
		    }

		    System.out.println(" .");
		}
	}
}