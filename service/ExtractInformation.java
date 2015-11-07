package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Selector;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.RDFS;

import modele.Concept;

public class ExtractInformation {
	
	private ExtractInformation(){
		
	}
	
	public static List<Concept> extractInformations(Model m, String mainUri)
	{
		List<Concept> result = new ArrayList<Concept>();
		
		
		String mainTitle = ExtractInformation.getName(m, mainUri);
		String mainText = ExtractInformation.getComment(m, mainUri);
		
		Concept concept = new Concept(mainTitle,mainText);
		result.add(concept);
		
		List<String> listUrisAssocies = new ArrayList<String>();
		listUrisAssocies = ExtractInformation.getAssociatedURI(m, mainUri);
		
		for(String s : listUrisAssocies)
		{		
			String title = ExtractInformation.getName(m,s);
			String text = ExtractInformation.getComment(m,s);
			
			Concept temp = new Concept(title,text);
			result.add(temp);
		}
		return result;
		
	}
	
	private static String getName(Model m, String mainUri)
	{
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
		
		
		
		String result = null;
		try
		{
			Resource mainResource = m.getResource(mainUri);
			Statement state = mainResource.getProperty(RDFS.label);
			RDFNode node = state.getObject();
			result = node.toString();
			result = result.substring(0, result.length()-3);
		}
		catch(Exception e)
		{
			result = "";
		}
		return result;
	}
	
	private static String getComment(Model m, String mainUri)
	{
		String result = null;
		
		try
		{
			Resource mainResource = m.getResource(mainUri);
			Statement state = m.getProperty(mainResource,RDFS.comment);
			RDFNode node = state.getObject();
			result = node.toString();
			result = result.substring(0, result.length()-3);
		}
		catch(Exception e)
		{
			result = "";
		}
		
		return result;
	}
	
	private static List<String> getAssociatedURI(Model m, String mainUri)
	{
		List<String> result = new ArrayList<String>();
		
		try
		{
			for(int i = 0; i<2;)
			{
				Resource mainResource = m.getResource(mainUri);
				Statement state = m.getProperty(mainResource, FOAF.knows);
				RDFNode node = state.getObject();
				if(node instanceof Resource && node.toString()!= mainUri){
					result.add(node.toString());
					i++;
				}
			}
			for(int j = 0; j<2;)
			{
				Resource mainResource = m.getResource(mainUri);
				Statement state = m.getProperty(mainResource, RDFS.seeAlso);
				RDFNode node = state.getObject();
				if(node instanceof Resource && node.toString()!= mainUri)
				{
					result.add(node.toString());
					j++;
				}
			}
		}		
		catch(Exception e)
		{
			
		}
		
		return result;
	}

}
