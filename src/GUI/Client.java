package GUI;


/**
 * Comments for Zach to add:
 * Trump display, shows players what is trump
 * When player pick ups card
 * 
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.*;


public class Client {
	private String player1Name = "Player1"; //ConnectUI.name;
	private String player2Name = "Player2";
	private String player3Name = "Player3";
	private String player4Name = "Player4";
	
	private int numPlayers = 0;
	
	private int turnNo = 5;
	private int oppoTricks = 0;
	private int oppoScore = 0;
	private int yourTricks = 0;
	private int yourScore = 0;
	
	private ImageIcon imagep2;
	private ImageIcon imagep3;
	private ImageIcon imagep4;
	
	private int card1Num = 21;
	private int card2Num = 22;
	private int card3Num = 23;
	private int card4Num = 1;
	private int card5Num = 9;
	
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
	
	private JLabel lblPlayer1 = new JLabel(player1Name);
	private JLabel lblPlayer2 = new JLabel(player2Name);
	private JLabel lblPlayer3 = new JLabel(player3Name);
	private JLabel lblPlayer4 = new JLabel(player4Name);

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
		//TODO Draw the sit down screen
		
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
		
		//TODO Wait for the server to send out the first hand
		
		//Draw the board for the first hand
		JButton btnPass = new JButton("Pass");
		btnPass.setBounds(1135, 818, 89, 32);
		frame.getContentPane().add(btnPass);
		
		JButton btnPickUp = new JButton("Pick Up");
		btnPickUp.setBounds(1025, 818, 100, 32);
		frame.getContentPane().add(btnPickUp);
		lblPlayer1.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblPlayer1.setForeground(Color.WHITE);
		lblPlayer1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPlayer1.setBounds(497, 650, 250, 23);
		frame.getContentPane().add(lblPlayer1);
		
		lblPlayer4.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPlayer4.setForeground(Color.WHITE);
		lblPlayer4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPlayer4.setBounds(811, 419, 250, 32);
		frame.getContentPane().add(lblPlayer4);
		
		lblPlayer3.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayer3.setForeground(Color.WHITE);
		lblPlayer3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPlayer3.setBounds(497, 188, 250, 23);
		frame.getContentPane().add(lblPlayer3);
		
		lblPlayer2.setForeground(Color.WHITE);
		lblPlayer2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPlayer2.setBounds(181, 424, 250, 23);
		frame.getContentPane().add(lblPlayer2);
		
		JLabel lblYourTeam = new JLabel("Your Team:");
		lblYourTeam.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblYourTeam.setForeground(Color.WHITE);
		lblYourTeam.setBounds(10, 789, 100, 23);
		frame.getContentPane().add(lblYourTeam);
		
		JLabel lblOpponents = new JLabel("Opponents:");
		lblOpponents.setForeground(Color.WHITE);
		lblOpponents.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblOpponents.setBounds(10, 827, 100, 23);
		frame.getContentPane().add(lblOpponents);
		
		JLabel lblTricks = new JLabel("Tricks");
		lblTricks.setHorizontalAlignment(SwingConstants.CENTER);
		lblTricks.setForeground(Color.WHITE);
		lblTricks.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblTricks.setBounds(99, 754, 100, 23);
		frame.getContentPane().add(lblTricks);
		
		JLabel lblScore = new JLabel("Score");
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		lblScore.setForeground(Color.WHITE);
		lblScore.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblScore.setBounds(194, 754, 100, 23);
		frame.getContentPane().add(lblScore);
		
		JLabel yourTeamTricks = new JLabel("0");
		yourTeamTricks.setHorizontalAlignment(SwingConstants.CENTER);
		yourTeamTricks.setForeground(Color.WHITE);
		yourTeamTricks.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		yourTeamTricks.setBounds(99, 788, 100, 23);
		frame.getContentPane().add(yourTeamTricks);
		
		JLabel yourTeamScore = new JLabel("0");
		yourTeamScore.setHorizontalAlignment(SwingConstants.CENTER);
		yourTeamScore.setForeground(Color.WHITE);
		yourTeamScore.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		yourTeamScore.setBounds(194, 788, 100, 23);
		frame.getContentPane().add(yourTeamScore);
		
		JLabel oppoTricks = new JLabel("0");
		oppoTricks.setHorizontalAlignment(SwingConstants.CENTER);
		oppoTricks.setForeground(Color.WHITE);
		oppoTricks.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		oppoTricks.setBounds(99, 827, 100, 23);
		frame.getContentPane().add(oppoTricks);
		
		JLabel oppoScore = new JLabel("0");
		oppoScore.setHorizontalAlignment(SwingConstants.CENTER);
		oppoScore.setForeground(Color.WHITE);
		oppoScore.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		oppoScore.setBounds(194, 827, 100, 23);
		frame.getContentPane().add(oppoScore);
		
		JButton btnPickupAndGo = new JButton("Pick-Up and Go Alone");
		btnPickupAndGo.setBounds(1025, 789, 200, 23);
		frame.getContentPane().add(btnPickupAndGo);
		
		pickCards();
		drawCards();

		
		//TODO update the board as turns go
		
		//TODO deal with next hand
	}

	private void drawCards () {
		for (int i = 0; i < 4; i++) { //draw cards for each player
			switch (i) {
			case 0 : 
				player1.setLayout(new GridLayout(0, turnNo, 0, 0));
				for (int j = 0; j < turnNo; j++) { // draw the right number of card
					if ( j == 0 ) { 
						JButton card1butt = new JButton ();
						card1butt.setBackground(Color.darkGray);
						card1butt.setBorderPainted(false);
						card1butt.setIcon(card1);
						player1.add(card1butt);
						card1butt.addActionListener(new ActionListener() {
				            @Override
				            public void actionPerformed(ActionEvent e) {
				                System.out.println("Playerclicked button1");
				            }
						});
					} else if ( j == 1) {
						JButton card2butt = new JButton ();
						card2butt.setBackground(Color.darkGray);
						card2butt.setBorderPainted(false);
						card2butt.setIcon(card2);
						player1.add(card2butt);
						card2butt.addActionListener(new ActionListener() {
				            @Override
				            public void actionPerformed(ActionEvent e) {
				                System.out.println("Player clicked button2");
				            }
						});
					} else if ( j == 2) {
						JButton card3butt = new JButton ();
						card3butt.setBackground(Color.darkGray);
						card3butt.setBorderPainted(false);
						card3butt.setIcon(card3);
						player1.add(card3butt);
						card3butt.addActionListener(new ActionListener() {
				            @Override
				            public void actionPerformed(ActionEvent e) {
				                System.out.println("Player clicked button3");
				            }
						});
					} else if ( j == 3) {
						JButton card4butt = new JButton ();
						card4butt.setBackground(Color.darkGray);
						card4butt.setBorderPainted(false);
						card4butt.setIcon(card4);
						player1.add(card4butt);
						card4butt.addActionListener(new ActionListener() {
				            @Override
				            public void actionPerformed(ActionEvent e) {
				                System.out.println("Player clicked button4");
				            }
						});
					} else if ( j == 4) {
						JButton card5butt = new JButton ();
						card5butt.setBackground(Color.darkGray);
						card5butt.setBorderPainted(false);
						card5butt.setIcon(card5);
						player1.add(card5butt);
						card5butt.addActionListener(new ActionListener() {
				            @Override
				            public void actionPerformed(ActionEvent e) {
				                System.out.println("Player clicked button5");
				            }
						});
					}
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
	
	/**
	 * After client recieves intial packet with all players cards, this is load the correct images into the 
	 * players hand
	 * 
	 */
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
			
			case 0 : temp = new ImageIcon(getClass().getResource("/9_of_spades.png"));;
				break;
			case 1 : temp = new ImageIcon(getClass().getResource("/10_of_spades.png"));
				break;
			case 2 : temp = new ImageIcon(getClass().getResource("/jack_of_spades.png"));
				break;
			case 3 : temp = new ImageIcon(getClass().getResource("/queen_of_spades.png"));
				break;
			case 4 : temp = new ImageIcon(getClass().getResource("/king_of_spades.png"));
				break;
			case 5 : temp = new ImageIcon(getClass().getResource("/ace_of_spades.png"));
				break;
			case 6 : temp = new ImageIcon(getClass().getResource("/9_of_hearts.png"));
				break;
			case 7 : temp = new ImageIcon(getClass().getResource("/10_of_hearts.png"));
				break;
			case 8 : temp = new ImageIcon(getClass().getResource("/jack_of_hearts.png"));
				break;
			case 9 : temp = new ImageIcon(getClass().getResource("/queen_of_hearts.png"));
				break;
			case 10 : temp = new ImageIcon(getClass().getResource("/king_of_hearts.png"));
				break;
			case 11 : temp = new ImageIcon(getClass().getResource("/ace_of_hearts.png"));
				break;
			case 12 : temp = new ImageIcon(getClass().getResource("/9_of_clubs.png"));
				break;
			case 13 : temp = new ImageIcon(getClass().getResource("/10_of_clubs.png"));
				break;
			case 14 : temp = new ImageIcon(getClass().getResource("/jack_of_clubs.png"));
				break;
			case 15 : temp = new ImageIcon(getClass().getResource("/queen_of_clubs.png"));
				break;
			case 16 : temp = new ImageIcon(getClass().getResource("/king_of_clubs.png"));
				break;
			case 17 : temp = new ImageIcon(getClass().getResource("/ace_of_clubs.png"));
				break;
			case 18 : temp = new ImageIcon(getClass().getResource("/9_of_diamonds.png"));
				break;
			case 19 : temp = new ImageIcon(getClass().getResource("/10_of_diamonds.png"));
				break;
			case 20 : temp = new ImageIcon(getClass().getResource("/jack_of_diamonds.png"));
				break;
			case 21 : temp = new ImageIcon(getClass().getResource("/queen_of_diamonds.png"));
				break;
			case 22 : temp = new ImageIcon(getClass().getResource("/king_of_diamonds.png"));
				break;
			case 23 : temp = new ImageIcon(getClass().getResource("/ace_of_diamonds.png"));
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
