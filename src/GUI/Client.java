package GUI;

import java.awt.EventQueue;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.*;


public class Client {
	private String player1Name = ConnectUI.name;
	private String player2Name = "";
	private String player3Name = "";
	private String player4Name = "";
	
	private int turnNo = 5;
	
	private ImageIcon imagep2;
	private ImageIcon imagep3;
	private ImageIcon imagep4;
	
	private int card1Num = 1;
	private int card2Num = 2;
	private int card3Num = 3;
	private int card4Num = 4;
	private int card5Num = 5;
	
	private ImageIcon card1 = new ImageIcon ();
	private ImageIcon card2 = new ImageIcon ();
	private ImageIcon card3 = new ImageIcon ();
	private ImageIcon card4 = new ImageIcon ();
	private ImageIcon card5 = new ImageIcon ();
	
	private JFrame frame;
	
	private final JPanel player1 = new JPanel();
	private final JPanel player2 = new JPanel();
	private final JPanel player3 = new JPanel();
	private final JPanel player4 = new JPanel();
	
	private JLabel lblPlayer1 = new JLabel("");
	private JLabel lblPlayer2 = new JLabel("");
	private JLabel lblPlayer3 = new JLabel("");
	private JLabel lblPlayer4 = new JLabel("");

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
		frame.setResizable(false);
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
		
		//pickCards();
		drawCards();

	}

	private void drawCards () {
		for (int i = 0; i < 4; i++) { //draw cards for each player
			switch (i) {
			case 0 : 
				player1.setLayout(new GridLayout(0, turnNo, 0, 0));
				for (int j = 0; j < turnNo; j++) { // draw the right number of cards
					JLabel temp = new JLabel("");
					if ( j == 0 ) { 
						temp.setIcon(card1);
					} else if ( j == 1) {
						temp.setIcon(card2);
					} else if ( j == 2) {
						temp.setIcon(card3);
					} else if ( j == 3) {
						temp.setIcon(card4);
					} else if ( j == 4) {
						temp.setIcon(card5);
					}
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
	
	private void pickCards () {
		
		ImageIcon temp = null;
		int tempCardNo = -1;
		
		for(int i = 0; i < 5; i++){ //Get the images for each card
			if (i == 0){
				tempCardNo = card1Num;
			} else if (i == 1) {
				tempCardNo = card2Num;
			} else if (i == 2) {
				tempCardNo = card3Num;
			} else if (i == 3) {
				tempCardNo = card4Num;
			} else if (i == 4) {
				tempCardNo = card5Num;
			}
			
			switch (tempCardNo) {
			
			//case 0 : Image clubs = new Image(new );
				//break;
			case 1 : temp = new ImageIcon(getClass().getResource("10_of_spades.png"));
				break;
			case 2 : temp = new ImageIcon(getClass().getResource("jack_of_spades.png"));
				break;
			case 3 : temp = new ImageIcon(getClass().getResource("queen_of_spades.png"));
				break;
			case 4 : temp = new ImageIcon(getClass().getResource("king_of_spades.png"));
				break;
			case 5 : temp = new ImageIcon(getClass().getResource("ace_of_spades.png"));
				break;
			case 6 : temp = new ImageIcon(getClass().getResource("9_of_hearts.png"));
				break;
			case 7 : temp = new ImageIcon(getClass().getResource("10_of_hearts.png"));
				break;
			case 8 : temp = new ImageIcon(getClass().getResource("jack_of_hearts.png"));
				break;
			case 9 : temp = new ImageIcon(getClass().getResource("queen_of_hearts.png"));
				break;
			case 10 : temp = new ImageIcon(getClass().getResource("king_of_hearts.png"));
				break;
			case 11 : temp = new ImageIcon(getClass().getResource("ace_of_hearts.png.png"));
				break;
			case 12 : temp = new ImageIcon(getClass().getResource("9_of_clubs.png"));
				break;
			case 13 : temp = new ImageIcon(getClass().getResource("10_of_clubs.png"));
				break;
			case 14 : temp = new ImageIcon(getClass().getResource("jack_of_clubs.png"));
				break;
			case 15 : temp = new ImageIcon(getClass().getResource("queen_of_clubs.png"));
				break;
			case 16 : temp = new ImageIcon(getClass().getResource("king_of_clubs.png"));
				break;
			case 17 : temp = new ImageIcon(getClass().getResource("ace_of_clubs.png"));
				break;
			case 18 : temp = new ImageIcon(getClass().getResource("9_of_diamonds.png"));
				break;
			case 19 : temp = new ImageIcon(getClass().getResource("10_of_diamonds.png"));
				break;
			case 20 : temp = new ImageIcon(getClass().getResource("jack_of_diamonds.png"));
				break;
			case 21 : temp = new ImageIcon(getClass().getResource("queen_of_diamonds.png"));
				break;
			case 22 : temp = new ImageIcon(getClass().getResource("king_of_diamonds.png"));
				break;
			case 23 : temp = new ImageIcon(getClass().getResource("ace_of_diamonds.png"));
				break;
				default : System.out.println("Fatal Error");
			}
			
			if (i == 0){
				card1 = temp;
			} else if (i == 1) {
				card2 = temp;
			} else if (i == 2) {
				card3 = temp;
			} else if (i == 3) {
				card4 = temp;
			} else if (i == 4) {
				card5 = temp;
			}
		}
	}
}
