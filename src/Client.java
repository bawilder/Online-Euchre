//Need dealer to be identified in initPacket player1 is dealer, player2 is dealer, player3 is dealer, or player4 is dealer
//Need poke-it-packet to send every client client whose turn it is
//need a packet to send each client who they are in init packet. Which player they are.

/**
 * Comments for Zach to add:
 * Trump display, shows players what is trump
 * When player pick ups card
 * 
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import javax.swing.*;

public class Client {

	private String name;
	public String serverNum = "localhost";
	private Label lblServerAddr = new Label ("Server Address:");
	private Label lblPortNum = new Label ("Port Number:");
	private Label lblName = new Label ("Name:");
	private Label lblError = new Label ("Error");
	private TextField tfName = new TextField();
	private TextField tfServAddr = new TextField();
	private TextField tfPortNum = new TextField();
	private Button bttnConnect = new Button("Connect");
	private Button bttnCreate = new Button("Create Server");
	private Button bttnCancel = new Button("Cancel");
	private int portNum = -1;
	public BufferedReader readBuff;
	public static PrintWriter writeBuff;

	private String player1Name = "Player1";
	private String player2Name = "Player2";
	private String player3Name = "Player3";
	private String player4Name = "Player4";

	private boolean myTurn = false;
	
	private int turnNum = 0;
	private int dealer = -1;
	private int whoAmI = 1;
	private int whoseTurn = 0;

	private boolean firstRound = false;
	private boolean passed = false;

	private int turnNo = 5;
	private int oppoTricks = 0;
	private int oppoScore = 0;
	private int yourTricks = 0;
	private int yourScore = 0;

	private ImageIcon imagep2;
	private ImageIcon imagep3;
	private ImageIcon imagep4;

	private int card1Num = -1;
	private int card2Num = -1;
	private int card3Num = -1;
	private int card4Num = -1;
	private int card5Num = -1;
	private int trumpCardNum = -1;

	private JLabel ply1CardPlayed = new JLabel("");
	private JLabel ply2CardPlayed = new JLabel("");
	private JLabel ply3CardPlayed = new JLabel("");
	private JLabel ply4CardPlayed = new JLabel("");
	private JLabel trumpCard = new JLabel("");

	private JLabel yourTeamTricks = new JLabel("0");
	private JLabel yourTeamScore = new JLabel("0");
	private JLabel oppoTrickslbl = new JLabel("0");
	private JLabel oppoScorelbl = new JLabel("0");

	private JLabel ply1Deal = new JLabel("Dealer");
	private JLabel ply4Deal = new JLabel("Dealer");
	private JLabel ply3Deal = new JLabel("Dealer");
	private JLabel ply2Deal = new JLabel("Dealer");

	private JLabel lblPickTrump = new JLabel("Pick Trump");

	private JPanel player1Turn = new JPanel();
	private JPanel player3Turn = new JPanel();
	private JPanel player2Turn = new JPanel();
	private JPanel player4Turn = new JPanel();

	private JPanel tSelect = new JPanel();

	private JLabel trumpLbl = new JLabel("");

	/**
	 * 0 - Invalid
	 * 1 - Club
	 * 2 - Spade
	 * 3 - Diamond
	 * 4 - Heart
	 */
	private int cardLead = 0;
	private int trump = 0;

	private ImageIcon card1 = new ImageIcon ();
	private ImageIcon card2 = new ImageIcon ();
	private ImageIcon card3 = new ImageIcon ();
	private ImageIcon card4 = new ImageIcon ();
	private ImageIcon card5 = new ImageIcon ();

	private static JFrame frame;

	private final JPanel player1 = new JPanel();
	private final JPanel player2 = new JPanel();
	private final JPanel player3 = new JPanel();
	private final JPanel player4 = new JPanel();

	private JLabel lblPlayer1 = new JLabel(player1Name);
	private JLabel lblPlayer2 = new JLabel(player2Name);
	private JLabel lblPlayer3 = new JLabel(player3Name);
	private JLabel lblPlayer4 = new JLabel(player4Name);

	private final JLabel discardLbl = new JLabel("Discard");
	
	private Packet myPacket = new Packet();
	String msg;

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
		start();
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void start() {
		frame = new JFrame();
		ConnectUI();
	}

	private void initialize() {

		Runnable r = new Runnable() {
			public void run() {
				while(true){
					String [] parsedPacket;
					String rcvdInit = "";
					rcvdInit = getPacket();
					//Packet Layout:
					// 		9,dealFlag,p2Nam,p3Name,p4Name,card1,card2,card3,card4,card5,trump

					/*
					 * The integer values are as follows:
					 * 0  - Illegal Move/Error	(host -> client)
					 * 1  - Refresh Board 		(host -> client)
					 * 2  - Play Card			(client -> host)
					 * 3  - Choose Trump		(client -> host)
					 * 4  - Poke-It Packet      (host -> client)
					 * 5  - 
					 * 6  - 
					 * 7  - Set Trump Values	(host -> client)
					 * 8  - Score Update/Deal	(host -> client)
					 * 9  - Initialize Game		(host -> client)
					 */
					parsedPacket = rcvdInit.split(",");
					if(Integer.parseInt(parsedPacket[0]) == 9) {
						dealer = Integer.parseInt(parsedPacket[1]);
						card1Num = Integer.parseInt(parsedPacket[4]);
						card2Num = Integer.parseInt(parsedPacket[5]);
						card3Num = Integer.parseInt(parsedPacket[6]);
						card4Num = Integer.parseInt(parsedPacket[7]);
						card5Num = Integer.parseInt(parsedPacket[8]);
						trumpCardNum = Integer.parseInt(parsedPacket[9]);

						System.out.println("Card currently in hand: " + card1Num + ", " + card2Num + ", " + card3Num + ", " + card4Num + ", " + card5Num);
						System.out.println("Card currently displayed up: " + trumpCardNum);

						initRecv();
						checkDealer();
						
						turnNum = 0;
						passed = false;
					}

					else if (Integer.parseInt(parsedPacket[0]) == 8){
						//score update
						yourScore = Integer.parseInt(parsedPacket[1]);
						oppoScore = Integer.parseInt(parsedPacket[2]);
						card1Num = Integer.parseInt(parsedPacket[3]);
						card2Num = Integer.parseInt(parsedPacket[4]);
						card3Num = Integer.parseInt(parsedPacket[5]);
						card4Num = Integer.parseInt(parsedPacket[6]);
						card5Num = Integer.parseInt(parsedPacket[7]);
						trumpCardNum = Integer.parseInt(parsedPacket[8]);
					}

					else if (Integer.parseInt(parsedPacket[0]) == 7){
						trump = Integer.parseInt(parsedPacket[1]);
						firstRound = true;
						if(whoAmI == dealer) {
							discardLbl.setVisible(true);
						}
						
						if (trump == 1) {
							trumpLbl.setText("Clubs");
						} else if (trump == 2) {
							trumpLbl.setText("Spades");
						} if (trump == 3) {
							trumpLbl.setText("Diamonds");
						} if (trump == 4){
							trumpLbl.setText("Hearts");
						}
					}
					
					else if(Integer.parseInt(parsedPacket[0]) == 4){
						//get poked (play card)
						turnNum = turnNum + 1;
						synchronized(frame){
							frame.notify();
						}
						whoseTurn = Integer.parseInt(parsedPacket[1]);
						checkPlayerTurn();
						if(whoseTurn == whoAmI) {
							if(trump == 0 && passed) { //trump has not been chosen yet
								lblPickTrump.setVisible(true);
								tSelect.setVisible(true);
							}
							myTurn = true;
						} 
					}

					//TODO: this.finish()
					else if(Integer.parseInt(parsedPacket[0]) == 1){
						// refresh board
					}

					else{
						System.out.println("Either no packet received, or error parsing packet");
					}

					rcvdInit = "";
					Arrays.fill(parsedPacket, null);

					// This will redraw the gui so all
					// labels and buttons show up properly
					frame.repaint();
				}
			}
		};
		new Thread(r).start();
	}

	private void initRecv () {
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

		System.out.println();
		player1.setBackground(Color.DARK_GRAY);
		player1.setBounds(317, 684, 634, 166);

		frame.getContentPane().add(player1);
		System.out.println("player 1 added");

		//Draw the board for the first hand
		JButton btnPass = new JButton("Pass");
		btnPass.setBounds(1135, 818, 89, 32);
		frame.getContentPane().add(btnPass);
		btnPass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (myTurn && turnNum == 1) { 
					if(passed == true && whoAmI == dealer) {
						System.out.println("you must pick Trump");
					} else {
						passed = true;
						msg = myPacket.chooseTrump(-1);
						sendPacket(msg);
						myTurn = false;
					}
				}
			}
		});

		JButton btnPickUp = new JButton("Pick Up");
		btnPickUp.setBounds(1025, 818, 100, 32);
		frame.getContentPane().add(btnPickUp);
		lblPlayer1.setHorizontalAlignment(SwingConstants.CENTER);
		btnPickUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: pick-up logic
				if(myTurn && turnNum == 1) {
					msg = myPacket.chooseTrump(trumpCardNum);
					sendPacket(msg);
					myTurn = false;
				}
			}
		});

		lblPlayer1.setForeground(Color.WHITE);
		lblPlayer1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPlayer1.setBounds(497, 650, 250, 23);
		frame.getContentPane().add(lblPlayer1);

		lblPlayer4.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPlayer4.setForeground(Color.WHITE);
		lblPlayer4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPlayer4.setBounds(950, 419, 111, 32);
		frame.getContentPane().add(lblPlayer4);

		lblPlayer3.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayer3.setForeground(Color.WHITE);
		lblPlayer3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPlayer3.setBounds(497, 188, 250, 23);
		frame.getContentPane().add(lblPlayer3);

		lblPlayer2.setForeground(Color.WHITE);
		lblPlayer2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPlayer2.setBounds(181, 424, 136, 23);
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

		yourTeamTricks.setHorizontalAlignment(SwingConstants.CENTER);
		yourTeamTricks.setForeground(Color.WHITE);
		yourTeamTricks.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		yourTeamTricks.setBounds(99, 788, 100, 23);
		frame.getContentPane().add(yourTeamTricks);

		yourTeamScore.setHorizontalAlignment(SwingConstants.CENTER);
		yourTeamScore.setForeground(Color.WHITE);
		yourTeamScore.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		yourTeamScore.setBounds(194, 788, 100, 23);
		frame.getContentPane().add(yourTeamScore);

		oppoTrickslbl.setHorizontalAlignment(SwingConstants.CENTER);
		oppoTrickslbl.setForeground(Color.WHITE);
		oppoTrickslbl.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		oppoTrickslbl.setBounds(99, 827, 100, 23);
		frame.getContentPane().add(oppoTrickslbl);

		oppoScorelbl.setHorizontalAlignment(SwingConstants.CENTER);
		oppoScorelbl.setForeground(Color.WHITE);
		oppoScorelbl.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		oppoScorelbl.setBounds(194, 827, 100, 23);
		frame.getContentPane().add(oppoScorelbl);

		JButton btnPickupAndGo = new JButton("Pick-Up and Go Alone");
		btnPickupAndGo.setBounds(1025, 789, 200, 23);
		frame.getContentPane().add(btnPickupAndGo);

		JPanel table = new JPanel();
		table.setBounds(305, 222, 662, 427);
		table.setBackground(Color.DARK_GRAY);
		frame.getContentPane().add(table);
		table.setLayout(null);

		ply1CardPlayed.setBounds(244, 224, 165, 192);
		ply1CardPlayed.setBackground(Color.DARK_GRAY);
		table.add(ply1CardPlayed);

		ply2CardPlayed.setBounds(10, 149, 214, 129);
		ply2CardPlayed.setBackground(Color.DARK_GRAY);
		table.add(ply2CardPlayed);

		ply3CardPlayed.setBounds(244, 11, 165, 202);
		ply3CardPlayed.setBackground(Color.DARK_GRAY);
		table.add(ply3CardPlayed);

		ply4CardPlayed.setBounds(389, 149, 235, 129);
		ply4CardPlayed.setBackground(Color.DARK_GRAY);
		table.add(ply4CardPlayed);

		trumpCard.setBounds(244, 112, 165, 202);
		trumpCard.setBackground(Color.DARK_GRAY);
		trumpCard.setIcon(cardToDrawVert(trumpCardNum));
		table.add(trumpCard);

		player1Turn.setBackground(Color.YELLOW);
		player1Turn.setBounds(0, 417, 634, 10);
		player1Turn.setVisible(false);
		table.add(player1Turn);

		player3Turn.setBackground(Color.YELLOW);
		player3Turn.setBounds(0, 0, 634, 10);
		player3Turn.setVisible(false);
		table.add(player3Turn);

		player2Turn.setBackground(Color.YELLOW);
		player2Turn.setBounds(0, 0, 10, 427);
		player2Turn.setVisible(false);
		table.add(player2Turn);

		player4Turn.setBackground(Color.YELLOW);
		player4Turn.setBounds(624, 0, 10, 427);
		player4Turn.setVisible(false);
		table.add(player4Turn);

		tSelect.setBounds(1025, 754, 199, 32);
		frame.getContentPane().add(tSelect);
		tSelect.setVisible(false);
		tSelect.setLayout(new GridLayout(1, 0, 0, 0));

		JButton tSelectSpades = new JButton("");
		tSelectSpades.setIcon(new ImageIcon (getClass().getResource("/spade.png")));
		tSelectSpades.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tSelect.isVisible()) {
					msg = myPacket.chooseTrump(2);
					sendPacket(msg);
					tSelect.setVisible(false);
					lblPickTrump.setVisible(false);
					myTurn = false;
				}
			}
		});
		tSelect.add(tSelectSpades);

		JButton tSelectClubs = new JButton("");
		tSelectClubs.setIcon(new ImageIcon (getClass().getResource("/club.png")));
		tSelectClubs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tSelect.isVisible()) {
					msg = myPacket.chooseTrump(1);
					sendPacket(msg);
					tSelect.setVisible(false);
					lblPickTrump.setVisible(false);
					myTurn = false;
				}
			}
		});
		tSelect.add(tSelectClubs);

		JButton tSelectDiamonds = new JButton("");
		tSelectDiamonds.setIcon(new ImageIcon (getClass().getResource("/diamond (2).png")));
		tSelectDiamonds.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tSelect.isVisible()) {
					msg = myPacket.chooseTrump(3);
					sendPacket(msg);
					tSelect.setVisible(false);
					lblPickTrump.setVisible(false);
					myTurn = false;
				}
			}
		});
		tSelect.add(tSelectDiamonds);
		JButton tSelectHearts = new JButton("");
		tSelectHearts.setIcon(new ImageIcon (getClass().getResource("/heart.png")));
		tSelectHearts.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tSelect.isVisible()) {
					msg = myPacket.chooseTrump(4);
					sendPacket(msg);
					tSelect.setVisible(false);
					lblPickTrump.setVisible(false);
					myTurn = false;
				}
			}
		});
		tSelect.add(tSelectHearts);
		trumpLbl.setForeground(Color.WHITE);
		trumpLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));

		trumpLbl.setBounds(1082, 736, 143, 19);
		frame.getContentPane().add(trumpLbl);

		JLabel lblTrump = new JLabel("Trump:");
		lblTrump.setForeground(Color.WHITE);
		lblTrump.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTrump.setBounds(1025, 733, 111, 23);
		frame.getContentPane().add(lblTrump);

		ply1Deal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		ply1Deal.setHorizontalAlignment(SwingConstants.TRAILING);
		ply1Deal.setForeground(Color.WHITE);
		ply1Deal.setBounds(840, 654, 111, 23);
		ply1Deal.setVisible(false);
		frame.getContentPane().add(ply1Deal);

		ply4Deal.setHorizontalAlignment(SwingConstants.TRAILING);
		ply4Deal.setForeground(Color.WHITE);
		ply4Deal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		ply4Deal.setBounds(950, 449, 111, 23);
		ply4Deal.setVisible(false);
		frame.getContentPane().add(ply4Deal);

		ply3Deal.setForeground(Color.WHITE);
		ply3Deal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		ply3Deal.setBounds(317, 188, 111, 23);
		ply3Deal.setVisible(false);
		frame.getContentPane().add(ply3Deal);

		ply2Deal.setForeground(Color.WHITE);
		ply2Deal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		ply2Deal.setBounds(181, 455, 111, 23);
		ply2Deal.setVisible(false);
		frame.getContentPane().add(ply2Deal);

		lblPickTrump.setForeground(Color.WHITE);
		lblPickTrump.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPickTrump.setBounds(950, 750, 124, 32);
		lblPickTrump.setVisible(false);
		frame.getContentPane().add(lblPickTrump);

		discardLbl.setForeground(Color.WHITE);
		discardLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		discardLbl.setBounds(317, 652, 89, 23);
		discardLbl.setVisible(false);
		frame.getContentPane().add(discardLbl);

		pickCards();
		drawCards();
		frame.validate();
	}
	
	private void drawCards () {
		trumpCard.setVisible(true);
		for (int i = 0; i < 4; i++) { //draw cards for each player
			switch (i) {
			case 0 : 
				player1.setLayout(new GridLayout(0, turnNo, 0, 0));
				for (int j = 0; j < turnNo; j++) { // draw the right number of card
					if ( j == 0 ) { 
						final JButton card1butt = new JButton ();
						card1butt.setBackground(Color.darkGray);
						card1butt.setBorderPainted(false);
						card1butt.setIcon(card1);
						card1butt.setVisible(true);
						player1.add(card1butt);
						card1butt.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								//if i am pickingup get ride of this card and replace with
								if(firstRound && (whoAmI == dealer)) {
									card1Num = trumpCardNum;
									trumpCard.setVisible(false);
									discardLbl.setVisible(false);
									firstRound = false;
									card1butt.setIcon(cardToDrawVert(card1Num));
									msg = myPacket.playCard(0);
									sendPacket(msg);
								} else if (isValidMove(card1Num) && myTurn) {
									myTurn = false;
									ply1CardPlayed.setIcon(card1);
									playCard(card1Num);
									msg = myPacket.playCard(0);
									sendPacket(msg);
									card1butt.setVisible(false);
								}
							}
						});
					} else if ( j == 1) {
						final JButton card2butt = new JButton ();
						card2butt.setBackground(Color.darkGray);
						card2butt.setBorderPainted(false);
						card2butt.setVisible(true);
						card2butt.setIcon(card2);
						player1.add(card2butt);
						card2butt.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								if(firstRound && (whoAmI == dealer)) {
									card2Num = trumpCardNum;
									trumpCard.setVisible(false);
									firstRound = false;
									discardLbl.setVisible(false);
									card2butt.setIcon(cardToDrawVert(card2Num));
									msg = myPacket.playCard(1);
									sendPacket(msg);
								} else if (isValidMove(card2Num) && myTurn) {
									myTurn = false;
									playCard(card2Num);
									ply1CardPlayed.setIcon(card2);
									msg = myPacket.playCard(1);
									sendPacket(msg);
									card2butt.setVisible(false);
								}
							}
						});
					} else if ( j == 2) {
						final JButton card3butt = new JButton ();
						card3butt.setBackground(Color.darkGray);
						card3butt.setBorderPainted(false);
						card3butt.setVisible(true);
						card3butt.setIcon(card3);
						player1.add(card3butt);
						card3butt.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								if(firstRound && (whoAmI == dealer)) {
									card3Num = trumpCardNum;
									trumpCard.setVisible(false);
									discardLbl.setVisible(false);
									firstRound = false;
									card3butt.setIcon(cardToDrawVert(card3Num));
									msg = myPacket.playCard(2);
									sendPacket(msg);
								} else if (isValidMove(card3Num) && myTurn) {
									myTurn = false;
									ply1CardPlayed.setIcon(card3);
									playCard(card3Num);
									msg = myPacket.playCard(2);
									sendPacket(msg);
									card3butt.setVisible(false);
								}
							}
						});
					} else if ( j == 3) {
						final JButton card4butt = new JButton ();
						card4butt.setBackground(Color.darkGray);
						card4butt.setBorderPainted(false);
						card4butt.setVisible(true);
						card4butt.setIcon(card4);
						player1.add(card4butt);
						card4butt.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								if(firstRound && (whoAmI == dealer)) {
									card4Num = trumpCardNum;
									trumpCard.setVisible(false);
									discardLbl.setVisible(false);
									firstRound = false;
									card4butt.setIcon(cardToDrawVert(card4Num));
									msg = myPacket.playCard(3);
									sendPacket(msg);
								} else if (isValidMove(card4Num) && myTurn) {
									myTurn = false;
									ply1CardPlayed.setIcon(card4);
									playCard(card4Num);
									msg = myPacket.playCard(3);
									sendPacket(msg);
									card4butt.setVisible(false);
								}
							}
						});
					} else if ( j == 4) {
						final JButton card5butt = new JButton ();
						card5butt.setBackground(Color.darkGray);
						card5butt.setBorderPainted(false);
						card5butt.setVisible(true);
						card5butt.setIcon(card5);
						player1.add(card5butt);
						card5butt.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								if(firstRound && (whoAmI == dealer)) {
									card5Num = trumpCardNum;
									trumpCard.setVisible(false);
									discardLbl.setVisible(false);
									firstRound = false;
									card5butt.setIcon(cardToDrawVert(card5Num));
									msg = myPacket.playCard(4);
									sendPacket(msg);
								} else if (isValidMove(card5Num) && myTurn) {
									myTurn = false;
									ply1CardPlayed.setIcon(card5);
									playCard(card5Num);
									msg = myPacket.playCard(4);
									sendPacket(msg);
									card5butt.setVisible(false);
								}
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

	private ImageIcon cardToDrawVert (int carVal) {
		ImageIcon temp = null;

		switch (carVal) {

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

		return temp;
	}
	
	private ImageIcon cardToDrawHorz (int carVal) {
		ImageIcon temp = null;

		switch (carVal) {

		case 0 : temp = new ImageIcon(getClass().getResource("/9_of_spades (2).png"));;
		break;
		case 1 : temp = new ImageIcon(getClass().getResource("/10_of_spades (2).png"));
		break;
		case 2 : temp = new ImageIcon(getClass().getResource("/jack_of_spades (2).png"));
		break;
		case 3 : temp = new ImageIcon(getClass().getResource("/queen_of_spades (2).png"));
		break;
		case 4 : temp = new ImageIcon(getClass().getResource("/king_of_spades (2).png"));
		break;
		case 5 : temp = new ImageIcon(getClass().getResource("/ace_of_spades (2).png"));
		break;
		case 6 : temp = new ImageIcon(getClass().getResource("/9_of_hearts (2).png"));
		break;
		case 7 : temp = new ImageIcon(getClass().getResource("/10_of_hearts (2).png"));
		break;
		case 8 : temp = new ImageIcon(getClass().getResource("/jack_of_hearts (2).png"));
		break;
		case 9 : temp = new ImageIcon(getClass().getResource("/queen_of_hearts (2).png"));
		break;
		case 10 : temp = new ImageIcon(getClass().getResource("/king_of_hearts (2).png"));
		break;
		case 11 : temp = new ImageIcon(getClass().getResource("/ace_of_hearts (2).png"));
		break;
		case 12 : temp = new ImageIcon(getClass().getResource("/9_of_clubs (2).png"));
		break;
		case 13 : temp = new ImageIcon(getClass().getResource("/10_of_clubs (2).png"));
		break;
		case 14 : temp = new ImageIcon(getClass().getResource("/jack_of_clubs (2).png"));
		break;
		case 15 : temp = new ImageIcon(getClass().getResource("/queen_of_clubs (2).png"));
		break;
		case 16 : temp = new ImageIcon(getClass().getResource("/king_of_clubs (2).png"));
		break;
		case 17 : temp = new ImageIcon(getClass().getResource("/ace_of_clubs (2).png"));
		break;
		case 18 : temp = new ImageIcon(getClass().getResource("/9_of_diamonds (2).png"));
		break;
		case 19 : temp = new ImageIcon(getClass().getResource("/10_of_diamonds (2).png"));
		break;
		case 20 : temp = new ImageIcon(getClass().getResource("/jack_of_diamonds (2).png"));
		break;
		case 21 : temp = new ImageIcon(getClass().getResource("/queen_of_diamonds (2).png"));
		break;
		case 22 : temp = new ImageIcon(getClass().getResource("/king_of_diamonds (2).png"));
		break;
		case 23 : temp = new ImageIcon(getClass().getResource("/ace_of_diamonds (2).png"));
		break;
		default : System.out.println("Fatal Error");
		}

		return temp;
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


	private boolean isValidMove (int cardPlayed) {

		if(cardLead == 0) { //Player has the lead, they can play anything they want
			return true;
		} else if (!canFollowSuit()) { // if they cannot follow suit, any card is valid
			return true;
		} else if(cardLead == 1 && ((cardPlayed <= 17) && (cardPlayed >= 12)) || (trump == 1 && cardPlayed == 2)) {
			if(trump == 2 && cardPlayed == 14) {
				return false;
			}
			return true;
		} else if (cardLead == 2 && ((cardPlayed <= 5) && (cardPlayed >= 0)) || (trump == 2 && cardPlayed == 14)) {
			if(trump == 1 && cardPlayed == 2) {
				return false;
			}
			return true;
		} else if (cardLead == 3 && ((cardPlayed <= 23) && (cardPlayed >= 18)) || (trump == 3 && cardPlayed == 8)) {
			if(trump == 4 && cardPlayed == 20) {
				return false;
			}
			return true;
		} else if (cardLead == 4 && ((cardPlayed <= 11) && (cardPlayed >= 6)) || (trump == 4 && cardPlayed == 20)) {
			if(trump == 3 && cardPlayed == 8) {
				return false;
			}
			return true;
		}
		return false;
	}


	/**
	 * 0 - Invalid
	 * 1 - Club
	 * 2 - Spade
	 * 3 - Diamond
	 * 4 - Heart
	 */
	private boolean canFollowSuit () {
		if(cardLead == 1) {
			if ((card1Num <= 17) && (card1Num >= 12) || (trump == 1 && card1Num == 2)) {
				return true;
			} else if (((card2Num <= 17) && (card1Num >= 12) || (trump == 1 && card2Num == 2))) {
				return true;
			} else if (((card3Num <= 17) && (card1Num >= 12) || (trump == 1 && card3Num == 2))) {
				return true;
			} else if (((card4Num <= 17) && (card1Num >= 12) || (trump == 1 && card4Num == 2))) {
				return true;
			} else if (((card5Num <= 17) && (card1Num >= 12) || (trump == 1 && card5Num == 2))) {
				return true;
			}
			return false;
		} else if (cardLead == 2) {
			if ((card1Num <= 5) && (card1Num >= 0) || (trump == 2 && card1Num == 14)) {
				return true;
			} else if (((card2Num <= 5) && (card1Num >= 0) || (trump == 2 && card2Num == 14))) {
				return true;
			} else if (((card3Num <= 5) && (card1Num >= 0) || (trump == 2 && card3Num == 14))) {
				return true;
			} else if (((card4Num <= 5) && (card1Num >= 0) || (trump == 2 && card4Num == 14))) {
				return true;
			} else if (((card5Num <= 5) && (card1Num >= 0) || (trump == 2 && card5Num == 14))) {
				return true;
			}
			return false;
		} else if (cardLead == 3) {
			if ((card1Num <= 23) && (card1Num >= 18) || (trump == 3 && card1Num == 8)) {
				return true;
			} else if ((card2Num <= 23) && (card2Num >= 18) || (trump == 3 && card2Num == 8)) {
				return true;
			} else if ((card3Num <= 23) && (card3Num >= 18) || (trump == 3 && card3Num == 8)) {
				return true;
			} else if ((card4Num <= 23) && (card4Num >= 18) || (trump == 3 && card4Num == 8)) {
				return true;
			} else if ((card5Num <= 23) && (card5Num >= 18) || (trump == 3 && card5Num == 8)) {
				return true;
			}
			return false;
		} else if (cardLead == 4) {
			if ((card1Num <= 11) && (card1Num >= 6) || (trump == 4 && card1Num == 20)) {
				return true;
			} else if ((card2Num <= 11) && (card2Num >= 6) || (trump == 4 && card2Num == 20)) {
				return true;
			} else if ((card3Num <= 11) && (card3Num >= 6) || (trump == 4 && card3Num == 20)) {
				return true;
			} else if ((card4Num <= 11) && (card4Num >= 6) || (trump == 4 && card4Num == 20)) {
				return true;
			} else if ((card5Num <= 11) && (card5Num >= 6) || (trump == 4 && card5Num == 20)) {
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * A helper function that packs a card into a packet and sends it
	 * 
	 * @param cardPos - The position of the card being played
	 */
	private static void playCard(int cardPos){
		String packToSend;

		packToSend = "2,";
		packToSend = packToSend.concat(Integer.toString(cardPos));

		sendPacket(packToSend);
	}



	public void ConnectUI(){
		final JFrame ConnectUI = new JFrame();
		Font f = new Font (lblPortNum.getName(), Font.PLAIN, 15);

		ConnectUI.setLocation(550, 400);
		ConnectUI.setTitle("Connect to game");
		ConnectUI.getContentPane().setLayout(new GridLayout (3, 1));
		ConnectUI.setSize(500, 130);
		ConnectUI.setVisible(true);

		JPanel northPanel = new JPanel (new GridLayout (1, 4));
		JPanel middlePanel = new JPanel (new GridLayout (1, 2));
		JPanel southPanel = new JPanel(new GridLayout (1, 4));

		lblServerAddr.setFont(f);
		northPanel.add(lblServerAddr);
		northPanel.add(tfServAddr);

		lblPortNum.setFont(f);
		northPanel.add(lblPortNum);
		northPanel.add(tfPortNum);

		lblName.setFont(f);
		middlePanel.add(lblName);

		lblError.setFont(f);
		lblError.setForeground(Color.RED);
		lblError.setVisible(false);
		southPanel.add(lblError);

		middlePanel.add(tfName);

		ConnectUI.getContentPane().add(northPanel);
		ConnectUI.getContentPane().add(middlePanel);

		southPanel.add(bttnConnect);                    
		bttnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if( !tfPortNum.getText().equals("") && !tfServAddr.getText().equals("") && !tfName.getText().equals("")) {
					//serverNum = Integer.parseInt(tfPortNum.getText());
					name = tfName.getText();
					portNum = Integer.parseInt(tfPortNum.getText());

					try {
						Socket socket = new Socket(serverNum, portNum);
						readBuff = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						writeBuff = new PrintWriter(socket.getOutputStream(), true);
					} catch (Exception err) {
						err.printStackTrace();
						lblError.setVisible(true);
						System.exit(0);
					}

					ConnectUI.dispose();
					initialize();
				} else {
					lblError.setVisible(true);    
				}
			}
		});

		southPanel.add(bttnCreate);                    
		bttnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				//TODO create server logic
				System.out.println("Need to create server");
			}
		});

		southPanel.add(bttnCancel);                    
		bttnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				ConnectUI.dispose();
				System.exit(0);
			}
		});
		ConnectUI.getContentPane().add(southPanel);
		ConnectUI.validate();
	}

	public String getPacket(){
		String myPacket = "";
		System.out.println("Waiting to receive a packet");
		//while(true){
		try{
			//if(readBuff.ready() == true)
			myPacket = readBuff.readLine();
		}
		catch (Exception err){
			System.out.println(err);
		}
		//if(myPacket.length() > 0)
		//break;
		//}

		return myPacket;
	}

	/**
	 * A function that allows the client to send a packet to the host
	 * @param packToSend - the packet that needs to be send
	 */
	public static void sendPacket(String packToSend){
		while(true){
			try{
				// Flush the buffer and send the packet
				writeBuff.flush();
				writeBuff.println(packToSend);
			}
			catch(Exception err){
				// catch any errors and print them out
				System.out.println(err);
			}
			// If the writebuffer hasn't encountered any errors, exit the loop
			//+ otherwise, try to resend the packet
			if(!writeBuff.checkError())
				break;
		}
	}

	private void checkDealer() {
		ply1Deal.setVisible(false);
		ply2Deal.setVisible(false);
		ply3Deal.setVisible(false);
		ply4Deal.setVisible(false);
		if (whoAmI == 1) {
			if (dealer == 1) {
				ply1Deal.setVisible(true);
			} else if (dealer == 2) {
				ply2Deal.setVisible(true);
			} else if (dealer == 3) {
				ply3Deal.setVisible(true);
			} else if (dealer == 4) {
				ply4Deal.setVisible(true);
			}	
		} else if (whoAmI == 2) {
			if (dealer == 1) {
				ply4Deal.setVisible(true);
			} else if (dealer == 2) {
				ply1Deal.setVisible(true);
			} else if (dealer == 3) {
				ply2Deal.setVisible(true);
			} else if (dealer == 4) {
				ply3Deal.setVisible(true);
			}

		} else if (whoAmI == 3) {
			if (dealer == 1) {
				ply3Deal.setVisible(true);
			} else if (dealer == 2) {
				ply4Deal.setVisible(true);
			} else if (dealer == 3) {
				ply1Deal.setVisible(true);
			} else if (dealer == 4) {
				ply2Deal.setVisible(true);
			}

		} else if (whoAmI == 4) {
			if (dealer == 1) {
				ply2Deal.setVisible(true);
			} else if (dealer == 2) {
				ply3Deal.setVisible(true);
			} else if (dealer == 3) {
				ply4Deal.setVisible(true);
			} else if (dealer == 4) {
				ply1Deal.setVisible(true);
			}
		}
	}

	private void checkPlayerTurn () {
		player1Turn.setVisible(false);
		player2Turn.setVisible(false);
		player3Turn.setVisible(false);
		player4Turn.setVisible(false);
		if (whoAmI == 1) {
			if (whoseTurn == 1) {
				player1Turn.setVisible(true);
			} else if (whoseTurn == 2) {
				player2Turn.setVisible(true);
			} else if (whoseTurn == 3) {
				player3Turn.setVisible(true);
			} else if (whoseTurn == 4) {
				player4Turn.setVisible(true);
			}	
		} else if (whoAmI == 2) {
			if (whoseTurn == 1) {
				player4Turn.setVisible(true);
			} else if (whoseTurn == 2) {
				player1Turn.setVisible(true);
			} else if (whoseTurn == 3) {
				player2Turn.setVisible(true);
			} else if (whoseTurn == 4) {
				player3Turn.setVisible(true);
			}

		} else if (whoAmI == 3) {
			if (whoseTurn == 1) {
				player3Turn.setVisible(true);
			} else if (whoseTurn == 2) {
				player4Turn.setVisible(true);
			} else if (whoseTurn == 3) {
				player1Turn.setVisible(true);
			} else if (whoseTurn == 4) {
				player2Turn.setVisible(true);
			}

		} else if (whoAmI == 4) {
			if (whoseTurn == 1) {
				player2Turn.setVisible(true);
			} else if (whoseTurn == 2) {
				player3Turn.setVisible(true);
			} else if (whoseTurn == 3) {
				player4Turn.setVisible(true);
			} else if (whoseTurn == 4) {
				player1Turn.setVisible(true);
			}
		}
	}
}

