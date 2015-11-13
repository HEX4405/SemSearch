package thread;

import java.util.ArrayList;
import java.util.List;

import ihm.MainApp;
import modele.Concept;
import service.Service;

public class DefinitionsGetter implements Runnable {

	private Thread thread;
	private List<String> listUrls;
	private static MainApp mainApp;
	
	@Override
	public void run() {
		List<Concept> listConcepts = Service.getListDefinitions(listUrls);
		
		if(listConcepts != null)
		{
			synchronized (mainApp) {
				mainApp.updateDefinitionPanel(listConcepts);
			}
		}
		
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
	
	public DefinitionsGetter(List<String> urls)
	{
		this.listUrls = urls;
		this.thread = new Thread(this);
	}
	
	public static void setMainApp(MainApp mainApp)
	{
		DefinitionsGetter.mainApp = mainApp;
	}

}
