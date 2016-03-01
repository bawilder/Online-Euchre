package GUI;

import java.awt.EventQueue;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.*;


public class Client {
	private String playerName = ConnectUI.name;
	private int turnNo = 5;
	private ImageIcon imagep2;
	private ImageIcon imagep3;
	private ImageIcon imagep4;
	private JFrame frame;
	private final JPanel player2 = new JPanel();
	private final JPanel player3 = new JPanel();
	private final JPanel player4 = new JPanel();
	private final JPanel player1 = new JPanel();
	private JLabel lblPlayer1 = new JLabel("Player1");
	private JLabel lblPlayer2 = new JLabel("Player2");
	private JLabel lblPlayer3 = new JLabel("Player3");
	private JLabel lblPlayer4 = new JLabel("Player4");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client();
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
	public Client() {
		imagep2 = new ImageIcon(getClass().getResource("/pokeBackp2.jpg"));
		imagep3 = new ImageIcon(getClass().getResource("/pokeBackp3.jpg"));
		imagep4 = new ImageIcon(getClass().getResource("/pokeBackp4.jpg"));
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.getContentPane().setForeground(Color.GREEN);
		frame.setSize(1250, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		player2.setBackground(Color.DARK_GRAY);
		player2.setBounds(10, 130, 161, 600);
		
		frame.getContentPane().add(player2);
		player3.setBackground(Color.DARK_GRAY);
		player3.setBounds(317, 11, 634, 166);
		
		frame.getContentPane().add(player3);
		player4.setBackground(Color.DARK_GRAY);
		player4.setBounds(1071, 136, 153, 589);
		
		frame.getContentPane().add(player4);
		player1.setBackground(Color.DARK_GRAY);
		player1.setBounds(317, 684, 634, 166);
		
		frame.getContentPane().add(player1);
		
		JButton btnPass = new JButton("Pass");
		btnPass.setBounds(1135, 818, 89, 32);
		frame.getContentPane().add(btnPass);
		
		JButton btnPickUp = new JButton("Pick Up");
		btnPickUp.setBounds(1025, 818, 100, 32);
		frame.getContentPane().add(btnPickUp);
		
		JCheckBox chckbxAlone = new JCheckBox("Alone");
		chckbxAlone.setForeground(Color.WHITE);
		chckbxAlone.setBackground(Color.DARK_GRAY);
		chckbxAlone.setHorizontalAlignment(SwingConstants.CENTER);
		chckbxAlone.setBounds(1135, 788, 93, 23);
		frame.getContentPane().add(chckbxAlone);
		
		lblPlayer1.setForeground(Color.WHITE);
		lblPlayer1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPlayer1.setBounds(317, 650, 250, 23);
		frame.getContentPane().add(lblPlayer1);
		
		lblPlayer4.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPlayer4.setForeground(Color.WHITE);
		lblPlayer4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPlayer4.setBounds(811, 641, 250, 32);
		frame.getContentPane().add(lblPlayer4);
		
		lblPlayer3.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPlayer3.setForeground(Color.WHITE);
		lblPlayer3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPlayer3.setBounds(673, 188, 250, 23);
		frame.getContentPane().add(lblPlayer3);
		
		lblPlayer2.setForeground(Color.WHITE);
		lblPlayer2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPlayer2.setBounds(181, 188, 250, 23);
		frame.getContentPane().add(lblPlayer2);
		drawCards();

	}

	private void drawCards () {
		for (int i = 0; i < 4; i++) { //draw cards for each player
			switch (i) {
			case 0 : 
				player1.setLayout(new GridLayout(0, turnNo, 0, 0));
				for (int j = 0; j < turnNo; j++) { // draw the right number of cards
					JLabel temp = new JLabel("");
					temp.setIcon(imagep3);
					player1.add(temp);
				}
				break;
			case 1 :
				player2.setLayout(new GridLayout(turnNo, 0, 0, 0));
				for (int j = 0; j < turnNo; j++) { // draw the right number of cards
					JLabel temp = new JLabel("");
					temp.setIcon(imagep2);
					player2.add(temp);
				}
				break;
			case 2 :
				player3.setLayout(new GridLayout(0, turnNo, 0, 0));
				for (int j = 0; j < turnNo; j++) { // draw the right number of cards
					JLabel temp = new JLabel("");
					temp.setIcon(imagep3);
					player3.add(temp);
				}
				break;
			case 3 : 
				player4.setLayout(new GridLayout(turnNo, 0, 0, 0));
				for (int j = 0; j < turnNo; j++) { // draw the right number of cards
					JLabel temp = new JLabel("");
					temp.setIcon(imagep4);
					player4.add(temp);
				}
				break;
			}
		}
	}
}
