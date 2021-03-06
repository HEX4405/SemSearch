package thread;

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
	
	public void stop()
	{
		this.thread.interrupt();
		this.thread.stop();
	}
	
	public static void setMainApp(MainApp mainApp)
	{
		SnippetGetter.mainApp = mainApp;
	}
	
	@Override
	public void run() {
		
		Snippet snippetToDisplay = Service.identifyConcepts(similarity, url);
		
		if(snippetToDisplay != null)
		{
			synchronized(mainApp){
				mainApp.addANewSnippetToPanel(snippetToDisplay);
			}
		}
	}

}
