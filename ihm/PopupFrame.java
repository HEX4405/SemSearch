package ihm;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import modele.Concept;

import java.awt.Font;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class PopupFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public PopupFrame(Concept concept) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblImage = new JLabel();
		Image image_concept = concept.getImage();
		if(image_concept != null)
		{
			image_concept = image_concept.getScaledInstance(100, 100, Image.SCALE_FAST);
			Icon image_concept2 = new ImageIcon(image_concept);
			lblImage.setName(concept.getImageLink());
			lblImage.setIcon(image_concept2);
		}
		
		contentPane.add(lblImage, BorderLayout.WEST);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblTitle;
		if(concept != null)
		{
			lblTitle = new JLabel(concept.getTitle());
		}
		else
		{
			lblTitle = new JLabel("ERREUR !");
		}
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(lblTitle, BorderLayout.NORTH);
		
		JTextArea txtDescription = new JTextArea();
		txtDescription.setLineWrap(true);
		txtDescription.setEditable(false);

		if(concept != null)
		{
			txtDescription.setText(concept.getDescription());
		}
		else
		{
			txtDescription.setText("Description non trouvé !");
		}
		txtDescription.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(txtDescription, BorderLayout.CENTER);
		
		}

}

