package ihm;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.ImageIcon;

public class PopupFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PopupFrame frame = new PopupFrame("Titre", "c:/cool/cool.png", "test");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PopupFrame(String title, String image, String description) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblImage = new JLabel();
		lblImage.setIcon(new ImageIcon(image));
		contentPane.add(lblImage, BorderLayout.WEST);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblTitle = new JLabel(title);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(lblTitle, BorderLayout.NORTH);
		
		JTextArea txtDescription = new JTextArea();
		txtDescription.setEditable(false);
		txtDescription.setText(description);
		txtDescription.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(txtDescription, BorderLayout.CENTER);
		
		}

}

