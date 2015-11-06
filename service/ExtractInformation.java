package service;

import java.util.HashMap;
import java.util.Map;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.vocabulary.RDFS;

public class ExtractInformation {
	
	private ExtractInformation(){
		
	}
	
	public static Map<String,String> extractInformations(Model m, String mainUri){
		Map<String,String> result = new HashMap<String,String>();
		
		result.put("Label", ExtractInformation.getLabel(m, mainUri));
		result.put("Comment", ExtractInformation.getComment(m, mainUri));
		
		return result;
	}
	
	private static String getLabel(Model m, String mainUri){
		String result = null;
		try{
			Resource mainResource = m.getResource(mainUri);
			Statement state = m.getProperty(mainResource,RDFS.label);
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

}
