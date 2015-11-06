package service;

import java.util.HashMap;
import java.util.Map;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

public class ExtractInformation {
	
	private ExtractInformation(){
		
	}
	
	public static Map<String,String> extractInformations(Model m, String mainUri){
		Map<String,String> result = new HashMap<String,String>();
		
		
		
		return result;
	}
	
	private static String getTitle(Model m, String mainUri){
		String result = null;
		
		Property property = m.getProperty("dbo", "alias");
		Resource mainResource = m.getResource(mainUri);
		
		NodeIterator it = m.listObjectsOfProperty(mainResource, property);
		RDFNode node = it.nextNode();
		
		result = node.toString();
			
		
		return result;
	}
	
	private static String getDescription(Model m, String mainUri){
		String result = null;
		
		return result;
	}

}
