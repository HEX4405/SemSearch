package test;

import ihm.MainApp;
import modele.Snippet;
import service.Service;

public class SnippetGetter implements Runnable {
	
	private Thread thread;
	private String url;
	private double similarity;
	private static MainApp mainApp;
	
	public SnippetGetter(String url, double similarity)
	{
		this.url = url;
		this.similarity = similarity;
		this.thread = new Thread(this);
	}
	
	public void launch()
	{
		this.thread.start();
	}
	
	public static void setMainApp(MainApp mainApp)
	{
		SnippetGetter.mainApp = mainApp;
	}
	
	@Override
	public void run() {
		
		Snippet snippetToDisplay = Service.identifyConcepts(similarity, url);
		
		
		synchronized(mainApp){
			mainApp.addANewSnippetToPanel(snippetToDisplay);
		}
	}

}
