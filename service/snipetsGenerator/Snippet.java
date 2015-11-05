/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.snipetsGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cyril
 */
public class Snippet {

    private String url;
    private String title;
    private String content;
    private String icon;
    private List<Concept> concepts = new ArrayList<Concept>();
	
    public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		if(content.length() > 150)
		{
			content.substring(0, 150);
			content += "...";
		}
		this.content = content;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public List<Concept> getConcepts() {
		return concepts;
	}
	public void setConcepts(List<Concept> concepts) {
		this.concepts = concepts;
	}
	
	public Snippet(String url, String title, String content, String icon) {
		this.url = url;	
		this.title = title;
		if(content.length() > 150)
		{
			content.substring(0, 150);
			content += "...";
		}
		this.content = content;
		this.icon = icon;
	}
    
	public Snippet() {
		
	}
	
    
}
