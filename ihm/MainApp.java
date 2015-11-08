package ihm;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JSlider;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

import modele.Concept;
import modele.Snippet;

import service.Service;

public class MainApp {

	private JFrame frame;
	private JTextField txtSearch;
	private JTextField numberResults;
	private List<JTextArea> textAreas;
	private JPanel snippetsPanel;
	
	private List<Snippet> listSnippets;
	private List<Concept> listDefinitions;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainApp window = new MainApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainApp() {
		listSnippets = new ArrayList<>();
		listDefinitions = new ArrayList<>();
		textAreas = new ArrayList<>();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 847, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		numberResults = new JTextField();
		panel.add(numberResults);
		numberResults.setColumns(2);
		
		JRadioButton Google = new JRadioButton("Yahoo");
		panel.add(Google);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Google");
		panel.add(rdbtnNewRadioButton_1);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Bing");
		panel.add(rdbtnNewRadioButton);
		
		ButtonGroup group = new ButtonGroup();
		group.add(Google);
		group.add(rdbtnNewRadioButton);
		group.add(rdbtnNewRadioButton_1);
		
		txtSearch = new JTextField();
		txtSearch.setText("Search...");
		txtSearch.setToolTipText("");
		panel.add(txtSearch);
		txtSearch.setColumns(10);
		
		JButton btnGo = new JButton("Go !");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				long tStart = System.currentTimeMillis();
				
				List<String> urls = Service.getUrls("Metal", "YAHOO", 3);
				
				for(String url : urls)
				{
					Snippet snippet = Service.identifyConcepts("Metal", "YAHOO", 0.08,url);
					listSnippets.add(snippet);
					addANewSnippetToPanel(snippet);
					listDefinitions = Snippet.getListDefinitions();
				}
		        
		        long tStop = System.currentTimeMillis();
		        System.out.println((tStop - tStart) + " ms.");
		        
		        //textArea_1.setText
			}
		});
		
		panel.add(btnGo);
		
		
		JSlider slider = new JSlider();
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setMinorTickSpacing(1);
		slider.setMaximum(1);
		slider.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(slider);
		
		JScrollBar scrollBar = new JScrollBar();
		frame.getContentPane().add(scrollBar, BorderLayout.EAST);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.CENTER);
		this.snippetsPanel = panel_1;
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		this.textAreas.add(textArea);
		

		panel_1.add(textArea);
		
		
		
		
	}
	
	private void addANewSnippetToPanel(Snippet snippet)
	{
		if(this.listSnippets.size()==1)
		{
			this.textAreas.get(0).setText(snippet.getTitle());
		}
		else
		{
			JSeparator separator = new JSeparator();
			this.snippetsPanel.add(separator);
			JTextArea textArea = new JTextArea();
			textArea.setEditable(false);
			this.textAreas.add(textArea);
			textArea.setText(snippet.getTitle());
		}
	}
	

}
