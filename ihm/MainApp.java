package ihm;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JSlider;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

import modele.Concept;
import modele.Snippet;

import service.Service;
import test.SnippetGetter;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

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
		SnippetGetter.setMainApp(this);
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
				//snippetsPanel.removeAll();
				//snippetsPanel.repaint();
				long tStart = System.currentTimeMillis();
				
				List<String> urls = Service.getUrls("Metal", "YAHOO", 3);
				
				for(String url : urls)
				{
					SnippetGetter thread = new SnippetGetter(url, 0.08);
					thread.launch();
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
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		this.snippetsPanel = panel_1;
		
		
		JPanel panel_2 = new JPanel();
		this.snippetsPanel = panel_2;
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		JScrollPane scroller = new JScrollPane(panel_2);
		panel_1.add(scroller, BorderLayout.CENTER);
		
			
		
	}
	
	public void addANewSnippetToPanel(Snippet snippet)
	{
		
		JPanel panel = new JPanel();
		this.snippetsPanel.add(panel);
		panel.setBackground(new Color(60, 179, 113));
		
		JLabel lblSiteName = new JLabel(snippet.getTitle());
		panel.add(lblSiteName);
		lblSiteName.setBackground(new Color(47, 79, 79));
		lblSiteName.setForeground(Color.WHITE);
		lblSiteName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSiteName.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panelContent = new JPanel();
		this.snippetsPanel.add(panelContent);
		panelContent.setLayout(new BorderLayout(0, 0));
		
		JPanel panelMainConcept = new JPanel();
		panelMainConcept.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		panelContent.add(panelMainConcept, BorderLayout.NORTH);
		panelMainConcept.setLayout(new BorderLayout(0, 0));
		
		JPanel panelMainImg = new JPanel();
		panelMainImg.setBackground(Color.WHITE);
		panelMainConcept.add(panelMainImg, BorderLayout.WEST);
		
		JLabel lblMainImg = new JLabel("");
		
		panelMainImg.add(lblMainImg);
		
		JPanel panelMainContent = new JPanel();
		panelMainContent.setBackground(Color.WHITE);
		panelMainConcept.add(panelMainContent, BorderLayout.CENTER);
		panelMainContent.setLayout(new BorderLayout(0, 0));
		
		JLabel lblMainTitle = new JLabel("Titre");
		lblMainTitle.setForeground(new Color(46, 139, 87));
		lblMainTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblMainTitle.setHorizontalAlignment(SwingConstants.LEFT);
		lblMainTitle.setText(snippet.getMainConcept().getTitle());
		panelMainContent.add(lblMainTitle, BorderLayout.NORTH);
		
		JTextArea txtMainDescription = new JTextArea();
		txtMainDescription.setLineWrap(true);
		txtMainDescription.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtMainDescription.setText(snippet.getMainConcept().getDescription());
		panelMainContent.add(txtMainDescription, BorderLayout.CENTER);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(new Color(245, 255, 250));
		panelContent.add(panel_6, BorderLayout.CENTER);
		
		JLabel lblVoirAussi = new JLabel("Voir aussi");
		lblVoirAussi.setForeground(new Color(0, 0, 0));
		panel_6.add(lblVoirAussi);
		lblVoirAussi.setBackground(new Color(60, 179, 113));
		lblVoirAussi.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblVoirAussi.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panelConcepts = new JPanel();
		panelConcepts.setForeground(new Color(245, 255, 250));
		panelConcepts.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		panelContent.add(panelConcepts, BorderLayout.SOUTH);
		panelConcepts.setLayout(new BoxLayout(panelConcepts, BoxLayout.X_AXIS));
		
		
		
	
		
		List<Concept> listConceptAssocies = snippet.getAssociatedConcepts();
		
		
		for(Concept c : listConceptAssocies)
		{
			JPanel panel_for_concept = new JPanel();
			
			panel_for_concept.setBackground(new Color(60, 179, 113));
			panel_for_concept.setLayout(new BorderLayout(0, 0));
			
			JLabel lblImg1 = new JLabel("");
			lblImg1.setHorizontalAlignment(SwingConstants.CENTER);
			
			panel_for_concept.add(lblImg1, BorderLayout.NORTH);
			
			JLabel lblTitle1 = new JLabel("Titre");
			lblTitle1.setBackground(new Color(245, 255, 250));
			lblTitle1.setForeground(new Color(0, 0, 0));
			lblTitle1.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblTitle1.setText(c.getTitle());
			lblTitle1.setHorizontalAlignment(SwingConstants.CENTER);
			panel_for_concept.add(lblTitle1, BorderLayout.CENTER);
			
			panelConcepts.add(panel_for_concept);
		}
		
		
		
		JSeparator separator = new JSeparator();
		this.snippetsPanel.add(separator);

		
	}
	

}
