import java.util.Scanner;

/*
 * @author Hal M. Hattis
 * 
 * @course TSP CS3141 
 * 
 * @class "EuchreGame" - Euchre headclass
 */
public class EuchreGame {

	// class variables
	// boolean to control the game state
	Boolean gameOver = false;
	// Table to initialize teams and players
	Table table = new Table();
	Scanner in = new Scanner(System.in);

	static UDP_Server server = new UDP_Server();
	
	/*
	 * This method will handle shuffling and dealing the deck to start each hand
	 */
	public void dealDeck() {
		table.deck.shuffle();
		table.deck.deal();

		table.players[0].hand= table.deck.hand1;
		table.players[1].hand= table.deck.hand2;
		table.players[2].hand= table.deck.hand3;
		table.players[3].hand= table.deck.hand4;
		table.topOfDiscard= table.deck.discard[0];
		
		int [] tempArray = {0,0,0,0,0};
		String player1init;
		String player2init;
		String player3init;
		String player4init;
		
		Packet packet = new Packet();
		table.topOfDiscard= table.deck.discard[0];
		int topofDeck = 0;
		int teamNo = 1;
		
		player1init = packet.initPacket(9, 1, teamNo, tempArray, 1);
		player2init = packet.initPacket(9, 2, teamNo, tempArray, 1);
		player3init = packet.initPacket(9, 3, teamNo, tempArray, 1);
		player4init = packet.initPacket(9, 4, teamNo, tempArray, 1);
		
<<<<<<< HEAD
		// send player 1 their hand
		for(int i = 0; i < 5; i++){
			tempArray[i] = this.cardToInt(table.players[0].hand[i]);
		}
		player1init = packet.initPacket(9, 1, "Player2", "Player3", "Player4", tempArray, this.cardToInt(table.topOfDiscard));
		server.sendPacket(player1init, 1);
		
		// send player 2 their hand
		for(int i = 0; i < 5; i++){
			tempArray[i] = this.cardToInt(table.players[1].hand[i]);
		}
		player2init = packet.initPacket(9, 2, "Player1", "Player3", "Player4", tempArray, this.cardToInt(table.topOfDiscard));
		server.sendPacket(player2init, 2);
		
		// send player 3 their hand
		for(int i = 0; i < 5; i++){
			tempArray[i] = this.cardToInt(table.players[2].hand[i]);
		}
		player3init = packet.initPacket(9, 3, "Player1", "Player2", "Player4", tempArray, this.cardToInt(table.topOfDiscard));
		server.sendPacket(player3init, 3);
		
		// send player 4 their hand
		for(int i = 0; i < 5; i++){
			tempArray[i] = this.cardToInt(table.players[3].hand[i]);
		}
		player4init = packet.initPacket(9, 4, "Player1", "Player2", "Player3", tempArray, this.cardToInt(table.topOfDiscard));
		server.sendPacket(player4init, 4);
		
=======
		

>>>>>>> master
	}

	public char makeSuit(int suit) {
		char trumpSuit = 'X';
		if (suit == 1) {
			trumpSuit = 'C';
		}
		if (suit == 2) {
			trumpSuit = 'S';
		}
		if (suit == 3) {
			trumpSuit = 'D';
		}
		if (suit == 4) {
			trumpSuit = 'H';
		}
		return trumpSuit;
	}


	// in progress, have to add auto increment of dealer/player and actually
	// pick up card
	public void trumpRound() {

		Packet packet = new Packet();
		String msg;
		String retMsg;
		String[] parsedMsg;
		int trump = 0;
		table.playerTurn = table.playerDealing;
		table.rotateTurn();
		boolean trumpPickedUp = false;
		boolean trumpCalled = false;
		
		for(int i = 0; i < 4; i++){
			// Tell the clients whose turn it is
			msg = packet.PokeItPacket(table.playerTurn);
			server.sendPacket(msg, table.playerTurn + 1);
			table.rotateTurn();
			server.sendPacket(msg, table.playerTurn + 1);
			table.rotateTurn();
			server.sendPacket(msg, table.playerTurn + 1);
			table.rotateTurn();
			server.sendPacket(msg, table.playerTurn + 1);
			table.rotateTurn();
			
			// Receive a trump choice from the client
			retMsg = server.threads[table.playerTurn + 1].receivePacket();
			parsedMsg = retMsg.split(",");
			
			if(!parsedMsg[0].equals("3")){
				System.out.println("this is a problem");
			} else {
				if(parsedMsg[1].equals("5")){
					trumpCalled = false;
				} else{
					trumpCalled = true;
				}
			}
			

			if (trumpCalled == true) {

				if (table.playerTurn == 0 || table.playerTurn == 2) {
					table.team1.calledTrump = true;
				}

				if (table.playerTurn == 1 || table.playerTurn == 3) {
					table.team2.calledTrump = true;
				}

				table.setTrump(table.topOfDiscard.suit);
				// TODO get this to actually have the dealer pick up trump card
				/*
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
				*/
				// get the int value of the trump suit
				if(table.topOfDiscard.suit == 'C')
					trump = 1;
				else if(table.topOfDiscard.suit == 'S')
					trump = 2;
				else if(table.topOfDiscard.suit == 'D')
					trump = 3;
				else if(table.topOfDiscard.suit == 'H')
					trump = 4;
				msg = packet.trumpPacket(trump);
				
				server.sendPacket(msg, table.playerTurn + 1);
				table.rotateTurn();
				server.sendPacket(msg, table.playerTurn + 1);
				table.rotateTurn();
				server.sendPacket(msg, table.playerTurn + 1);
				table.rotateTurn();
				server.sendPacket(msg, table.playerTurn + 1);
				table.rotateTurn();
				
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
				// Tell each client who is choosing trump &
				// wait for a response
				msg = packet.PokeItPacket(table.playerTurn);
				server.sendPacket(msg, table.playerTurn + 1);
				table.rotateTurn();
				server.sendPacket(msg, table.playerTurn + 1);
				table.rotateTurn();
				server.sendPacket(msg, table.playerTurn + 1);
				table.rotateTurn();
				server.sendPacket(msg, table.playerTurn + 1);
				table.rotateTurn();
				
				// receive and parse reponse
				retMsg = server.threads[table.playerTurn + 1].receivePacket();
				parsedMsg = retMsg.split(",");

				// this is the "Screw the dealer" functionality
				if (i == 3) {
					

					int trumpSuit = Integer.parseInt(parsedMsg[1]);
					char trumpSuitChar = makeSuit(trumpSuit);

					if (table.playerDealing % 2 == 0) {
						table.team1.calledTrump = true;
					} else {
						table.team2.calledTrump = false;
					}

					table.setTrump(trumpSuitChar);

					break;
				}
				
				if(!parsedMsg[0].equals("3")){
					System.out.println("this is a problem");
				} else {
					if(parsedMsg[1].equals("5")){
						trumpCalled = false;
					} else{
						trumpCalled = true;
					}
				}

				// Set trump
				if (trumpCalled) {

					if (table.playerTurn == 0 || table.playerTurn == 2) {
						table.team1.calledTrump = true;
					}

					if (table.playerTurn == 1 || table.playerTurn == 3) {
						table.team2.calledTrump = true;
					}

					trump = Integer.parseInt(parsedMsg[1]);
					char trumpSuitChar = makeSuit(trump);
					table.setTrump(trumpSuitChar);
					
					break;
				}

				table.rotateTurn();
			}

			table.playerTurn = table.playerDealing;
			table.rotateTurn();
			
			msg = packet.PokeItPacket(table.playerTurn);
			server.sendPacket(msg, table.playerTurn + 1);
			table.rotateTurn();
			server.sendPacket(msg, table.playerTurn + 1);
			table.rotateTurn();
			server.sendPacket(msg, table.playerTurn + 1);
			table.rotateTurn();
			server.sendPacket(msg, table.playerTurn + 1);
			table.rotateTurn();

		}

		// Send trump to each client
		msg = packet.trumpPacket(trump);
		
		server.sendPacket(msg, table.playerTurn + 1);
		table.rotateTurn();
		server.sendPacket(msg, table.playerTurn + 1);
		table.rotateTurn();
		server.sendPacket(msg, table.playerTurn + 1);
		table.rotateTurn();
		server.sendPacket(msg, table.playerTurn + 1);
		table.rotateTurn();

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
	
	public void runHand(){
		
		// should be done
		dealDeck();
		
		//TODO networking
		trumpRound();

		for (int i = 0; i < 5; i += 1) {
			runTrick();
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

		table.team1.calledTrump = false;
		table.team2.calledTrump = false;
		table.rotateDealer();

	}
	
	public void runGame(){
		
		// wait for four players to connect
		while(server.playerNo <= 4){
			
		}		
		
		while(table.team1.getScore()<10 && table.team2.getScore()<10){
			runHand();
		}

		if (table.team1.winner == true) {
			System.out.println("Team 1 Won the Game");
		}

		else {
			System.out.println("Team 2 Won the Game");
		}
	}
	
	public int cardToInt(Card tempCard){
		if (tempCard.suit == 'C' && tempCard.face == 'N'){
		    return 0;
		}

		if (tempCard.suit == 'C' && tempCard.face == 'T'){
		    return 1;
		}

		if (tempCard.suit == 'C' && tempCard.face == 'J'){
		    return 2;
		}

		if (tempCard.suit == 'C' && tempCard.face == 'Q'){
		    return 3;
		}

		if (tempCard.suit == 'C' && tempCard.face == 'K'){
		    return 4;
		}

		if (tempCard.suit == 'C' && tempCard.face == 'A'){
		    return 5;
		}
		if (tempCard.suit == 'S' && tempCard.face == 'N'){
		    return 6;
		}

		if (tempCard.suit == 'S' && tempCard.face == 'T'){
		    return 7;
		}

		if (tempCard.suit == 'S' && tempCard.face == 'J'){
		    return 8;
		}

		if (tempCard.suit == 'S' && tempCard.face == 'Q'){
		    return 9;
		}

		if (tempCard.suit == 'S' && tempCard.face == 'K'){
		    return 10;
		}

		if (tempCard.suit == 'S' && tempCard.face == 'A'){
		    return 11;
		}

		if (tempCard.suit == 'D' && tempCard.face == 'N'){
		    return 12;
		}

		if (tempCard.suit == 'D' && tempCard.face == 'T'){
		    return 13;
		}

		if (tempCard.suit == 'D' && tempCard.face == 'J'){
		    return 14;
		}

		if (tempCard.suit == 'D' && tempCard.face == 'Q'){
		    return 15;
		}

		if (tempCard.suit == 'D' && tempCard.face == 'K'){
		    return 16;
		}

		if (tempCard.suit == 'D' && tempCard.face == 'A'){
		    return 17;
		}

		if (tempCard.suit == 'H' && tempCard.face == 'N'){
		    return 18;
		}

		if (tempCard.suit == 'H' && tempCard.face == 'T'){
		    return 19;
		}

		if (tempCard.suit == 'H' && tempCard.face == 'J'){
		    return 20;
		}

		if (tempCard.suit == 'H' && tempCard.face == 'Q'){
		    return 21;
		}

		if (tempCard.suit == 'H' && tempCard.face == 'K'){
		    return 22;
		}

		if (tempCard.suit == 'H' && tempCard.face == 'A'){
		    return 23;
		}
		
		return -1;
	}
	
	public static void main(String[] args) {
		EuchreGame game = new EuchreGame();
		game.runGame();
	}

}// EOC
