package ihm;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
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
import java.util.Enumeration;
import java.util.List;
import java.awt.event.ActionEvent;

import modele.Concept;
import modele.Snippet;

import service.Service;
import test.SnippetGetter;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class MainApp {

	private JFrame frame;
	private JTextField txtSearch;
	private List<JTextArea> textAreas;
	private JPanel snippetsPanel;
	private ButtonGroup buttonGroup;
	private JComboBox comboBox;
	
	private List<Snippet> listSnippets;
	private List<Concept> listDefinitions;
	private List<Concept> listAssociatedConcepts;


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
		listAssociatedConcepts = new ArrayList<>();
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
		
		String[] listP = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "20"};
		final JComboBox comboBox = new JComboBox(listP);
		
		this.comboBox = comboBox;
		panel.add(comboBox);
		
		JRadioButton Google = new JRadioButton("Google");
		
		panel.add(Google);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Yahoo");
		rdbtnNewRadioButton_1.setSelected(true);
		panel.add(rdbtnNewRadioButton_1);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Bing");
		panel.add(rdbtnNewRadioButton);
		
		ButtonGroup group = new ButtonGroup();
		group.add(Google);
		group.add(rdbtnNewRadioButton);
		group.add(rdbtnNewRadioButton_1);
		this.buttonGroup = group;
		
		txtSearch = new JTextField();
		txtSearch.setText("Search...");
		txtSearch.setToolTipText("");
		panel.add(txtSearch);
		txtSearch.setColumns(10);
		
		JButton btnGo = new JButton("Go !");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				snippetsPanel.removeAll();
				snippetsPanel.repaint();
				listAssociatedConcepts.clear();
				
				String query = txtSearch.getText();
				int nbResults = Integer.parseInt((String)comboBox.getSelectedItem()); // Since you need int use this better
		        
		        String searchEngine = "";
		        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
		            AbstractButton button = buttons.nextElement();
		            if (button.isSelected()) {
		                searchEngine = button.getText();
		            }
		        }
		        //double seuil = sldSimilarite.getValue()/100.0
				
				long tStart = System.currentTimeMillis();
				
				List<String> urls = Service.getUrls(query, searchEngine, nbResults);
				
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
		
		JLabel lblMainImg = new JLabel();
		Image image = snippet.getMainConcept().getImage();
		if(image != null)
		{
			image = image.getScaledInstance(300, 300, Image.SCALE_FAST);
			Icon image2 = new ImageIcon(image);
			
			lblMainImg.setIcon(image2);
		}
		
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
		
		List<Concept> listConceptAssocies = snippet.getAssociatedConcepts();
		if(listConceptAssocies.size()==0)
		{
			JSeparator separator = new JSeparator();
			this.snippetsPanel.add(separator);
			return;
		}
		
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
		
		
		listAssociatedConcepts.addAll(listConceptAssocies);
		
		
		for(Concept c : listConceptAssocies)
		{
			JPanel panel_for_concept = new JPanel();
			
			panel_for_concept.setBackground(new Color(60, 100, 113));
			panel_for_concept.setLayout(new BorderLayout(0, 0));
			
			JLabel lblImg1 = new JLabel();
			Image image_concept = c.getImage();
			if(image_concept != null)
			{
				image_concept = image_concept.getScaledInstance(100, 100, Image.SCALE_FAST);
				Icon image_concept2 = new ImageIcon(image_concept);
				lblImg1.setName(c.getImageLink());
				lblImg1.setIcon(image_concept2);
			}
			lblImg1.setHorizontalAlignment(SwingConstants.CENTER);
			panel_for_concept.add(lblImg1, BorderLayout.NORTH);
			
			JLabel lblTitle1 = new JLabel();
			lblTitle1.setBackground(new Color(245, 255, 250));
			lblTitle1.setForeground(new Color(0, 0, 0));
			lblTitle1.setFont(new Font("Tahoma", Font.PLAIN, 13));
			lblTitle1.setText(c.getTitle());
			lblTitle1.setHorizontalAlignment(SwingConstants.CENTER);
			panel_for_concept.add(lblTitle1, BorderLayout.CENTER);
			
			panelConcepts.add(panel_for_concept);
			
			lblImg1.addMouseListener(new java.awt.event.MouseAdapter() {
	            public void mouseClicked(java.awt.event.MouseEvent evt) {
	            	JLabel source = (JLabel)evt.getSource();
	            	
	            	Concept rightConcept = null;
	            	for(Concept concept : listAssociatedConcepts) {
	            		if(source.getName().equals(concept.getImageLink())) {
	            			rightConcept = concept;
	            		}
	            	}
	            	if(rightConcept != null) {
	            		PopupFrame frame = new PopupFrame(rightConcept);
	            		frame.setVisible(true);
	            	}
	            	else {
	            		PopupFrame frame = new PopupFrame(rightConcept);
	            		frame.setVisible(true);
	            	}
	            }
	            public void mouseEntered(java.awt.event.MouseEvent evt) {
	            	((JLabel)evt.getSource()).setCursor(new Cursor(Cursor.HAND_CURSOR));
	            }
	        });
			
			lblTitle1.addMouseListener(new java.awt.event.MouseAdapter() {
	            public void mouseClicked(java.awt.event.MouseEvent evt) {
	            	JLabel source = (JLabel)evt.getSource();
	            	
	            	Concept rightConcept = null;
	            	for(Concept concept : listAssociatedConcepts) {
	            		if(source.getText() == concept.getTitle()) {
	            			rightConcept = concept;
	            		}
	            	}
	            	if(rightConcept != null) {
	            		PopupFrame frame = new PopupFrame(rightConcept);
	            		frame.setVisible(true);
	            	}
	            	else {
	            		PopupFrame frame = new PopupFrame(rightConcept);
	            		frame.setVisible(true);
	            	}
	            }
	            public void mouseEntered(java.awt.event.MouseEvent evt) {
	            	((JLabel)evt.getSource()).setCursor(new Cursor(Cursor.HAND_CURSOR));
	            }
	        });
		}
		
		
		JSeparator separator = new JSeparator();
		this.snippetsPanel.add(separator);

		
	}
	

}
