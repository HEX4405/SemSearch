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
	
	public static List<Concept> extractInformations(Model m, List<String> uris)
	{
		List<Concept> result = new ArrayList<Concept>();
		
		for(String s : uris)
		{		
			String title = ExtractInformation.getName(m,s);
			String text = ExtractInformation.getComment(m,s);
			String imageLink = ExtractInformation.getImageLink(m, s);
			
			Concept temp = new Concept(title,text,imageLink);
			result.add(temp);
		}
		return result;
		
	}
	
	private static String getName(Model m, String mainUri)
	{		
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
	
	private static String getImageLink(Model m, String mainUri)
	{
		String result = null;
		
		try
		{
			Resource mainResource = m.getResource(mainUri);
			Statement state = m.getProperty(mainResource,FOAF.depiction);
			RDFNode node = state.getObject();
			result = node.toString();
		}
		catch(Exception e)
		{
			result = "";
		}
		
		return result;
	}

}
