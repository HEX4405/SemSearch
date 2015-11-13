package service.extract;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.alchemyapi.api.AlchemyAPI;

public class Extractor {

    private Extractor() {

    }

    public static Map<String, String> extract(List<String> urls) {
        Map<String, String> textsMap = new HashMap<>();

        for(String url : urls) {
        	// Create an AlchemyAPI object.
        	String text = "";
        	String title = "";
        	try
        	{
	            AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromFile("api_key.txt");
	
	            // Extract page text from a web URL. (ignoring ads, navigation links,
	            // and other content).
	            Document doc = alchemyObj.URLGetText(url);
	           text = getStringFromDocument(doc);
	           DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	           DocumentBuilder builder = factory.newDocumentBuilder();
	           Document document = builder.parse(new InputSource(new StringReader(text)));
	           NodeList textNodeList = document.getElementsByTagName("text");
	           Node nodeText = textNodeList.item(0);
	           text = nodeText.getTextContent();
        	}
	         catch(Exception e)
	       	{
	       		System.out.println("[EXTRACTOR] "+e.getMessage());
	       		
	       	}
        	try
        	{
	            // Extract a title from a web URL.
        		AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromFile("api_key.txt");
	            Document doc = alchemyObj.URLGetTitle(url);
	            title = getStringFromDocument(doc);
	            DocumentBuilderFactory factoryTitle = DocumentBuilderFactory.newInstance();
	            DocumentBuilder builderTitle = factoryTitle.newDocumentBuilder();
	            Document documentTitle = builderTitle.parse(new InputSource(new StringReader(title)));
	            NodeList titleNodeList = documentTitle.getElementsByTagName("title");
	            Node nodeTextTitle = titleNodeList.item(0);
	            title = nodeTextTitle.getTextContent();
	            
	            
        	}
        	catch(Exception e)
        	{
        		System.out.println("[EXTRACTOR] "+e.getMessage());
        	}
        	textsMap.put(title, text);

        }

        return textsMap;
    }
   
        // utility method
        private static String getStringFromDocument(Document doc) {
            try {
                DOMSource domSource = new DOMSource(doc);
                StringWriter writer = new StringWriter();
                StreamResult result = new StreamResult(writer);

                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer transformer = tf.newTransformer();
                transformer.transform(domSource, result);

                return writer.toString();
            } catch (TransformerException ex) {
                ex.printStackTrace();
                return null;
            }
        }
}
