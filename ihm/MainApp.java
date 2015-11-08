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
				snippetsPanel.removeAll();
				snippetsPanel.repaint();
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
		
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setOrientation(JScrollBar.HORIZONTAL);
		panel_1.add(scrollBar, BorderLayout.SOUTH);
		
		JScrollBar scrollBar_1 = new JScrollBar();
		panel_1.add(scrollBar_1, BorderLayout.EAST);
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.CENTER);
		this.snippetsPanel = panel_2;
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
			
		
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
		panelMainContent.add(lblMainTitle, BorderLayout.NORTH);
		
		JTextArea txtMainDescription = new JTextArea();
		txtMainDescription.setLineWrap(true);
		txtMainDescription.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtMainDescription.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
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
		
		JPanel panel_1 = new JPanel();
		panel_1.setForeground(new Color(245, 255, 250));
		panelConcepts.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblImg1 = new JLabel("");
		lblImg1.setHorizontalAlignment(SwingConstants.CENTER);
		
		panel_1.add(lblImg1, BorderLayout.NORTH);
		
		JLabel lblTitle1 = new JLabel("Titre");
		lblTitle1.setBackground(new Color(245, 255, 250));
		lblTitle1.setForeground(new Color(0, 0, 0));
		lblTitle1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTitle1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblTitle1, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		panelConcepts.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblImg2 = new JLabel("");
		lblImg2.setHorizontalAlignment(SwingConstants.CENTER);
		
		panel_2.add(lblImg2, BorderLayout.NORTH);
		
		JLabel lblTitle2 = new JLabel("Titre");
		lblTitle2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTitle2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblTitle2);
		
		JPanel panel_3 = new JPanel();
		panelConcepts.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JLabel lblImg3 = new JLabel("");
		lblImg3.setHorizontalAlignment(SwingConstants.CENTER);
		
		panel_3.add(lblImg3, BorderLayout.NORTH);
		
		JLabel lblTitle3 = new JLabel("Titre");
		lblTitle3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTitle3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(lblTitle3);
		
		JPanel panel_4 = new JPanel();
		panelConcepts.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JLabel lblImg4 = new JLabel("");
		lblImg4.setHorizontalAlignment(SwingConstants.CENTER);
		
		panel_4.add(lblImg4, BorderLayout.NORTH);
		
		JLabel lblTitle4 = new JLabel("Titre");
		lblTitle4.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTitle4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(lblTitle4);
		
		JPanel panel_5 = new JPanel();
		panelConcepts.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JLabel lblImg5 = new JLabel("");
		lblImg5.setHorizontalAlignment(SwingConstants.CENTER);
		
		panel_5.add(lblImg5, BorderLayout.NORTH);
		
		JLabel lblTitle5 = new JLabel("Titre");
		lblTitle5.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTitle5.setHorizontalAlignment(SwingConstants.CENTER);
		panel_5.add(lblTitle5, BorderLayout.CENTER);
		
		
		JSeparator separator = new JSeparator();
		this.snippetsPanel.add(separator);

		
	}
	

}
