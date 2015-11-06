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
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.RDFS;

public class ExtractInformation {
	
	private ExtractInformation(){
		
	}
	
	public static void extractInformations(Model m, String mainUri){
		
		String mainTitle = ExtractInformation.getLabel(m, mainUri);
		String mainText = ExtractInformation.getComment(m, mainUri);
		
		List<String> listUrisAssocies = new ArrayList<String>();
		
		for(String s : listUrisAssocies){
			String title = ExtractInformation.getLabel(m,s);
			String text = ExtractInformation.getComment(m,s);
		}
		
	}
	
	private static String getLabel(Model m, String mainUri){
		String result = null;
		try{
			Resource mainResource = m.getResource(mainUri);
			Statement state = m.getProperty(mainResource,FOAF.name);
			RDFNode node = state.getObject();
			result = node.toString();
		}
		catch(Exception e)
		{
			result = "";
		}
		return result;
	}
	
	private static String getComment(Model m, String mainUri){
		String result = null;
		
		try{
			Resource mainResource = m.getResource(mainUri);
			Statement state = m.getProperty(mainResource,RDFS.comment);
			RDFNode node = state.getObject();
			result = node.toString();
		}
		catch(Exception e)
		{
			result = "";
		}
		
		return result;
	}
	
	private static List<String> getAssociatedURI(Model m, String mainUri){
		List<String> result = new ArrayList<String>();
		
		
		try{
			
			Resource mainResource = m.getResource(mainUri);
			NodeIterator it = m.listObjectsOfProperty(mainResource, RDFS.seeAlso);
	
			int i = 0;
			while(it.hasNext()){
				if(i==2)
				{
					break;
				}
		
				RDFNode node = it.nextNode();
				if(node instanceof Resource && node.toString()!= mainUri){
					result.add(node.toString());
					i++;	
				}
			}
		}
		catch(Exception e)
		{
			
		}
		
		try{	
			Resource mainResource = m.getResource(mainUri);
			NodeIterator it2 = m.listObjectsOfProperty(mainResource, FOAF.knows);
	
			int i = 0;
			while(it2.hasNext()){
				if(i==2)
				{
					break;
				}
		
				RDFNode node = it2.nextNode();
				if(node instanceof Resource && node.toString()!= mainUri){
					result.add(node.toString());
					i++;	
				}
			}
		}
		catch(Exception e)
		{
			
		}
		
		return result;
	}

}
