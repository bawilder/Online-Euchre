import java.util.Scanner;

/*
 * @author Hal M. Hattis
 * 
 * @course TSP CS3141 
 * 
 * @class "EuchreGame" - Euchre headclass
 */
public class TestingEG {

	// class variables
	// boolean to control the game state
	Boolean gameOver = false;
	// Table to initialize teams and players
	Table table = new Table();
	Scanner in = new Scanner(System.in);

	/*
	 * This method will handle shuffling and dealing the deck to start each hand
	 */
	public void dealDeck() {
		table.deck.shuffle();
		table.deck.deal();
		table.players[0].hand = table.deck.hand1;
		table.players[1].hand = table.deck.hand2;
		table.players[2].hand = table.deck.hand3;
		table.players[3].hand = table.deck.hand4;
		table.topOfDiscard = table.deck.discard[0];
	}

	public char makeSuit(int suit) {
		char trumpSuit = 'X';
		if (suit == 1) {
			trumpSuit = 'H';
		}
		if (suit == 2) {
			trumpSuit = 'D';
		}
		if (suit == 3) {
			trumpSuit = 'S';
		}
		if (suit == 4) {
			trumpSuit = 'C';
		}
		return trumpSuit;
	}

	// in progress, have to add auto increment of dealer/player and actually
	// pick up card
	public void trumpRound() {

		table.playerTurn = table.playerDealing;
		table.rotateTurn();
		boolean trumpPickedUp = false;

		for (int i = 0; i < 4; i += 1) {
			System.out.println("Player " + (table.playerTurn + 1) + " do you want to set this card to trump: "
					+ table.topOfDiscard.face + table.topOfDiscard.suit + "?");
			System.out.println("This is your hand: ");
			table.players[table.playerTurn].showHand();
			int trumpCalled = in.nextInt();

			if (trumpCalled == 1) {

				if (table.playerTurn == 0 || table.playerTurn == 2) {
					table.team1.calledTrump = true;
				}

				if (table.playerTurn == 1 || table.playerTurn == 3) {
					table.team2.calledTrump = true;
				}

				table.setTrump(table.topOfDiscard.suit);
				System.out.println(
						"Dealer, you must pick this card up: " + table.topOfDiscard.face + table.topOfDiscard.suit);
				System.out.println("What card will you choose to discard from your hand?");
				table.players[table.playerDealing].showHand();
				int cardToDrop = in.nextInt() - 1;

				while (cardToDrop < 0 || cardToDrop > 4
						|| table.players[table.playerDealing].hand[cardToDrop] == null) {
					System.out.println("Invalid card, try again... (There arent that many cards in your hand)");
					cardToDrop = in.nextInt() - 1;
				}

				table.players[table.playerDealing].pickUpTrump(cardToDrop, table.topOfDiscard);
				trumpPickedUp = true;
				break;
			}
			table.rotateTurn();
		}

		// set the suit that was rejected as trump so that it cannot be selected
		// in the next round
		table.suitRejected = table.topOfDiscard.suit;

		if (trumpPickedUp == false) {
			for (int i = 0; i < 4; i += 1) {

				// this is the "Screw the dealer" functionality
				if (i == 3) {
					System.out.println("Dealer, you must set trump");
					System.out.println("What suit would you like to call it?");
					System.out.println("1=H, 2=D, 3=S, 4=C");
					System.out.println("You may not set this suit: " + table.suitRejected);
					System.out.println("This is your hand: ");
					table.players[table.playerDealing].showHand();

					int trumpSuit = in.nextInt();
					char trumpSuitChar = makeSuit(trumpSuit);

					while (trumpSuitChar == table.suitRejected) {
						System.out.println("You honestly can't set that suit... Try again.");

						trumpSuit = in.nextInt();
						trumpSuitChar = makeSuit(trumpSuit);
					}

					if (table.playerDealing % 2 == 0) {
						table.team1.calledTrump = true;
					} else {
						table.team2.calledTrump = false;
					}

					table.setTrump(trumpSuitChar);

					break;
				}

				System.out.println("Player " + (table.playerTurn + 1) + " , do you want to set trump? ");
				System.out.println("This is your hand: ");
				table.players[i].showHand();
				int trumpCalled = in.nextInt();

				if (trumpCalled == 1) {

					if (table.playerTurn == 0 || table.playerTurn == 2) {
						table.team1.calledTrump = true;
					}

					if (table.playerTurn == 1 || table.playerTurn == 3) {
						table.team2.calledTrump = true;
					}

					System.out.println("What suit would you like to call it?");
					System.out.println("1=H, 2=D, 3=S, 4=C");
					System.out.println("You may not set this suit: " + table.suitRejected);
					int trumpSuit = in.nextInt();
					char trumpSuitChar = makeSuit(trumpSuit);

					while (trumpSuitChar == table.suitRejected) {
						System.out.println("You honestly can't set that suit... Try again.");

						trumpSuit = in.nextInt();
						trumpSuitChar = makeSuit(trumpSuit);
					}

					table.setTrump(trumpSuitChar);
					break;
				}

				table.rotateTurn();
			}

			table.playerTurn = table.playerDealing;
			table.rotateTurn();

			if (table.team1.calledTrump == true) {
				System.out.println("Team 1 called trump to be: " + table.trump);
			}
			if (table.team2.calledTrump == true) {
				System.out.println("Team 2 called trump to be: " + table.trump);
			}
		}

		trumpPickedUp = false;
	}

	public void runTrick() {
		// use player turn, use dealer, use read ints, use play card

		// testing play all the cards
		for (int i = 0; i < 4; i += 1) {
			System.out
					.println("Player " + (table.playerTurn + 1) + ", play a card from this hand: (1-5 are the cards)");
			table.players[table.playerTurn].showHand();
			int card = in.nextInt() - 1;

			/*
			//dont check suit of first player
			if(i==0){
				// player must play a valid card exception catch
				while(card < 0 || card > 4 || table.players[table.playerTurn].hand[card] == null) {
					System.out.println("Invalid card, try again... (There arent that many cards in your hand)");
					card = in.nextInt() - 1;
				}
			}
			
			//do check suit for rest of players
			else {
			
				// player has played an invalid card
				while ((card < 0 || card > 4 || table.players[table.playerTurn].hand[card] == null)
						|| (table.players[table.playerTurn].handContains(table.tableCards[0].suit)
								&& table.players[table.playerTurn].hand[card].suit != table.tableCards[0].suit)) {
	
					// player must play a valid card exception catch
					if (card < 0 || card > 4 || table.players[table.playerTurn].hand[card] == null) {
						System.out.println("Invalid card, try again... (There arent that many cards in your hand)");
					}
	
					// player must play on suit excpetion catch
					if (table.players[table.playerTurn].handContains(table.tableCards[0].suit)
							&& table.players[table.playerTurn].hand[card].suit != table.tableCards[0].suit) {
						System.out.println("You can't play off suit, you have a card of suit that was led...");
					}
					card = in.nextInt() - 1;
				}
			}
		*/
			table.tableCards[table.playerTurn] = table.players[table.playerTurn].playCard(card);
			table.rotateTurn();
		}

		// evaluate cards played
		int playerOfCard = 0;

		int maxCardPos = table.evalCards();
		playerOfCard = maxCardPos;

		System.out.println("Player " + (playerOfCard + 1) + " wins the trick!");

		table.players[playerOfCard].tricks += 1;

		if (playerOfCard == 0 || playerOfCard == 2) {
			table.team1.tricks += 1;
		}
		if (playerOfCard == 1 || playerOfCard == 3) {
			table.team2.tricks += 1;
		}

	}

	public void runHand() {
		dealDeck();

		trumpRound();

		for (int i = 0; i < 5; i += 1) {
			runTrick();
			table.team1.calledTrump = false;
			table.team2.calledTrump = false;
		}

		// team 1 Euchd team 2
		if (table.team1.getTricks() > 2 && table.team2.calledTrump == true) {
			table.team1.teamScored(2);
			System.out.println("Team 1 Euch'd Team 2, scored 2 points");
			System.out.println("Player 1 earned " + table.players[0].tricks + " tricks, and Player 3 earned "
					+ table.players[2].tricks + " tricks.");
			table.players[0].clearTricks();
			table.players[2].clearTricks();
			System.out.println("The Score of the game is: Team 1 has " + table.team1.score + " point(s) and Team 2 has "
					+ table.team2.score + " point(s).");
			System.out.println();
		}

		// team2 Euchd team 1
		else if (table.team2.getTricks() > 2 && table.team1.calledTrump == true) {
			table.team2.teamScored(2);
			System.out.println("Team 2 Euch'd Team 1, scored 2 points");
			System.out.println("Player 2 earned " + table.players[1].tricks + " tricks, and Player 4 earned "
					+ table.players[3].tricks + " tricks.");
			table.players[0].clearTricks();
			table.players[2].clearTricks();
			System.out.println("The Score of the game is: Team 1 has " + table.team1.score + " point(s) and Team 2 has "
					+ table.team2.score + " point(s).");
			System.out.println();
		}

		else if (table.team1.getTricks() > 2 || table.team1.getTricks() == 5) {
			// give them one point of they scored, but not 5 tricks
			if (table.team1.getTricks() > 2 && table.team1.getTricks() < 5) {
				table.team1.teamScored(1);
			}
			// give them 2 points if they get 5 tricks
			if (table.team1.getTricks() == 5) {
				table.team1.teamScored(2);
			}
			System.out.println("Team 1 scored!");
			System.out.println("Player 1 earned " + table.players[0].tricks + " tricks, and Player 3 earned "
					+ table.players[2].tricks + " tricks.");
			table.players[0].clearTricks();
			table.players[2].clearTricks();
			System.out.println("The Score of the game is: Team 1 has " + table.team1.score + " point(s) and Team 2 has "
					+ table.team2.score + " point(s).");
			System.out.println();
		}
		// team 2 scored
		else {
			// give them one point of they scored, but not 5 tricks
			if (table.team2.getTricks() > 2 && table.team2.getTricks() < 5) {
				table.team2.teamScored(1);
			}
			// give them 2 points if they get 5 tricks
			if (table.team1.getTricks() == 5) {
				table.team2.teamScored(2);
			}
			System.out.println("Team 2 scored!");
			System.out.println("Player 2 earned " + table.players[1].tricks + " tricks, and Player 4 earned "
					+ table.players[3].tricks + " tricks.");
			table.players[0].clearTricks();
			table.players[2].clearTricks();
			System.out.println("The Score of the game is: Team 1 has " + table.team1.score + " point(s) and Team 2 has "
					+ table.team2.score + " point(s).");
			System.out.println();
		}

		table.rotateDealer();

	}

	public void runGame() {
		while (table.team1.getScore() < 10 && table.team2.getScore() < 10) {
			runHand();
		}

		if (table.team1.winner == true) {
			System.out.println("Team 1 Won the Game");
		}

		else {
			System.out.println("Team 2 Won the Game");
		}
	}

	public static void main(String[] args) {
		TestingEG game = new TestingEG();
		game.runGame();
	}

}// EOC
