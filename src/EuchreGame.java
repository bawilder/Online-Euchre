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
		

		// send player 1 their hand
		for(int i = 0; i < 5; i++){
			tempArray[i] = this.cardToInt(table.players[0].hand[i]);
		}
		System.out.println("player 1 hand: " + tempArray[0] + "," + tempArray[1] + "," + tempArray[2] + "," + tempArray[3] + "," + tempArray[4]);
		player1init = packet.initPacket(table.playerDealing + 1, 1, 1, tempArray, this.cardToInt(table.topOfDiscard));
		server.sendPacket(player1init, 1);
		
		// send player 2 their hand
		for(int i = 0; i < 5; i++){
			tempArray[i] = this.cardToInt(table.players[1].hand[i]);
		}
		System.out.println("player 2 hand: " + tempArray[0] + "," + tempArray[1] + "," + tempArray[2] + "," + tempArray[3] + "," + tempArray[4]);
		player2init = packet.initPacket(table.playerDealing + 1, 2, 2, tempArray, this.cardToInt(table.topOfDiscard));
		server.sendPacket(player2init, 2);
		
		// send player 3 their hand
		for(int i = 0; i < 5; i++){
			tempArray[i] = this.cardToInt(table.players[2].hand[i]);
		}
		System.out.println("player 3 hand: " + tempArray[0] + "," + tempArray[1] + "," + tempArray[2] + "," + tempArray[3] + "," + tempArray[4]);
		player3init = packet.initPacket(table.playerDealing + 1, 3, 1, tempArray, this.cardToInt(table.topOfDiscard));
		server.sendPacket(player3init, 3);
		
		// send player 4 their hand
		for(int i = 0; i < 5; i++){
			tempArray[i] = this.cardToInt(table.players[3].hand[i]);
		}
		System.out.println("player 4 hand: " + tempArray[0] + "," + tempArray[1] + "," + tempArray[2] + "," + tempArray[3] + "," + tempArray[4]);
		player4init = packet.initPacket(table.playerDealing + 1, 4, 2, tempArray, this.cardToInt(table.topOfDiscard));
		server.sendPacket(player4init, 4);
		
		System.out.println("Card turned up = " + cardToInt(table.deck.discard[0]));
		
	}

	public char makeSuit(int suit) {
		char trumpSuit = 'X';
		if (suit == 1) {
			trumpSuit = 'S';
		}
		if (suit == 2) {
			trumpSuit = 'H';
		}
		if (suit == 3) {
			trumpSuit = 'C';
		}
		if (suit == 4) {
			trumpSuit = 'D';
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
		int cardToDrop;
		int trump = 0;
		table.playerTurn = table.playerDealing;
		table.rotateTurn();
		boolean trumpPickedUp = false;
		boolean trumpCalled = false;
		
		for(int i = 0; i < 4; i++){
			// Tell the clients whose turn it is
			msg = packet.PokeItPacket(table.playerTurn);
			for(int j = 0; j < 4; j++){
				server.sendPacket(msg, table.playerTurn + 1);
				table.rotateTurn();
			}
			
			// Receive a trump choice from the client
			System.out.println("waiting to receive a packet from player " + (table.playerTurn + 1));
			retMsg = server.receivePacket(table.playerTurn + 1);
			parsedMsg = retMsg.split(",");
			System.out.println("Recieved:" + retMsg);
			while(!parsedMsg[0].equals("3")){
				retMsg = server.receivePacket(table.playerTurn + 1);
				parsedMsg = retMsg.split(",");
			}
			 
			if(parsedMsg[1].equals("20")){// player is passing
				trumpCalled = false;
			} else{
				trumpCalled = true;
			}
			
			

			if (trumpCalled == true) {

				if (table.playerTurn == 0 || table.playerTurn == 2) {
					table.team1.calledTrump = true;
				}

				if (table.playerTurn == 1 || table.playerTurn == 3) {
					table.team2.calledTrump = true;
				}

				table.setTrump(table.topOfDiscard.suit);
				
				System.out.println("Trump called is" + table.topOfDiscard.suit);
				
				// get the int value of the trump suit
				if(table.topOfDiscard.suit == 'S')
					trump = 1;
				else if(table.topOfDiscard.suit == 'H')
					trump = 2;
				else if(table.topOfDiscard.suit == 'C')
					trump = 3;
				else if(table.topOfDiscard.suit == 'D')
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
				
				// receive which card is discarded by dealer
				retMsg = server.receivePacket(table.playerDealing + 1);
				parsedMsg = retMsg.split(",");
				
				// if we received a play card packet then we can move on
				while(!parsedMsg[0].equals("2")){
					retMsg = server.receivePacket(table.playerDealing + 1);
					parsedMsg = retMsg.split(",");
				}
					cardToDrop = Integer.parseInt(parsedMsg[1]);

				table.players[table.playerDealing].pickUpTrump(cardToDrop, table.topOfDiscard);
				
				trumpPickedUp = true;
				table.playerTurn = table.playerDealing;
				table.rotateTurn();
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
				retMsg = server.receivePacket(table.playerTurn + 1);
				parsedMsg = retMsg.split(",");
				while(!parsedMsg[0].equals("3")){
					retMsg = server.receivePacket(table.playerTurn + 1);
					parsedMsg = retMsg.split(",");
				}

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
					System.out.println("this is a problem");  //debug?
				} else {
					if(parsedMsg[1].equals("20")){
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

		String msg;
		String recvMsg;
		String[] parsedMsg;
		Packet packet = new Packet();
		int cardPlayed = -1;
		int card;

		// testing play all the cards
		for (int i = 0; i < 4; i += 1) {

			// Tell each client whose turn it is
			msg = packet.PokeItPacket(table.playerTurn);
			
			server.sendPacket(msg, table.playerTurn + 1);
			table.rotateTurn();
			server.sendPacket(msg, table.playerTurn + 1);
			table.rotateTurn();
			server.sendPacket(msg, table.playerTurn + 1);
			table.rotateTurn();
			server.sendPacket(msg, table.playerTurn + 1);
			table.rotateTurn();
			
			// receive a played card from the client whose turn it is
			recvMsg = server.receivePacket(table.playerTurn + 1);
			
			// parse packet
			parsedMsg = recvMsg.split(",");
			System.out.println("Message recivied: " + recvMsg);
			card = Integer.parseInt(parsedMsg[1]);
			System.out.println("Card recivied: " + card);
			while(!parsedMsg[0].equals("2")){
				recvMsg = server.receivePacket(table.playerTurn + 1);
				parsedMsg = recvMsg.split(",");
			}
			System.out.println("card Pos Played is: " + card);
			// Play card
			cardPlayed = cardToInt(table.players[table.playerTurn].hand[card]);
			table.tableCards[table.playerTurn] = table.players[table.playerTurn].playCard(card);
			
			// Tell each client what card was played
			System.out.println("cardPlayed is: " + cardPlayed);
			msg = packet.refreshPacket(cardPlayed, table.playerTurn + 1);
			
			for(int j = 0; j < 4; j++){
				server.sendPacket(msg, table.playerTurn + 1);
				table.rotateTurn();
			}
			
			table.rotateTurn();
		}

		// evaluate cards played
		int playerOfCard = 0;

		int maxCardPos = table.evalCards();
		playerOfCard = maxCardPos;

		table.players[playerOfCard].tricks += 1;
		table.playerTurn = playerOfCard;

		if (playerOfCard == 0 || playerOfCard == 2) {
			table.team1.tricks += 1;
		}
		if (playerOfCard == 1 || playerOfCard == 3) {
			table.team2.tricks += 1;
		}
		
		if(playerOfCard == 0 || playerOfCard == 2)
			msg = packet.trickUpdate(1, table.team1.tricks, table.team2.tricks);
		else
			msg = packet.trickUpdate(2, table.team1.tricks, table.team2.tricks);
		
		
		for(int i = 0; i < 4; i++){
			server.sendPacket(msg, table.playerTurn + 1);
			table.rotateTurn();
		}
	}
	
	public void runHand(){
		
		Packet myPack = new Packet();
		String msg;
		// networking should be done
		dealDeck();	
		
		// networking should be done
		trumpRound();

		for (int i = 0; i < 5; i += 1) {
			runTrick();
		}
		
		// team 1 Euchd team 2
		if (table.team1.getTricks() > 2 && table.team2.calledTrump == true) {
			table.team1.teamScored(2);

			table.players[0].clearTricks();
			table.players[2].clearTricks();

		}

		// team2 Euchd team 1
		else if (table.team2.getTricks() > 2 && table.team1.calledTrump == true) {
			table.team2.teamScored(2);
			
			table.players[0].clearTricks();
			table.players[2].clearTricks();

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

			table.players[0].clearTricks();
			table.players[2].clearTricks();

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
			table.players[0].clearTricks();
			table.players[2].clearTricks();

		}

		table.team1.calledTrump = false;
		table.team2.calledTrump = false;
		
		msg = myPack.newHandPacket(table.team1.score, table.team2.score);
		server.sendPacket(msg, table.playerTurn + 1);
		table.rotateTurn();
		server.sendPacket(msg, table.playerTurn + 1);
		table.rotateTurn();
		server.sendPacket(msg, table.playerTurn + 1);
		table.rotateTurn();
		server.sendPacket(msg, table.playerTurn + 1);
		table.rotateTurn();
		
		table.rotateDealer();

	}
	
	public void runGame(){	
		System.out.println("game is running");
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
		if (tempCard.suit == 'S' && tempCard.face == 'N'){
		    return 0;
		}

		if (tempCard.suit == 'S' && tempCard.face == 'T'){
		    return 1;
		}

		if (tempCard.suit == 'S' && tempCard.face == 'J'){
		    return 2;
		}

		if (tempCard.suit == 'S' && tempCard.face == 'Q'){
		    return 3;
		}

		if (tempCard.suit == 'S' && tempCard.face == 'K'){
		    return 4;
		}

		if (tempCard.suit == 'S' && tempCard.face == 'A'){
		    return 5;
		}
		if (tempCard.suit == 'H' && tempCard.face == 'N'){
		    return 6;
		}

		if (tempCard.suit == 'H' && tempCard.face == 'T'){
		    return 7;
		}

		if (tempCard.suit == 'H' && tempCard.face == 'J'){
		    return 8;
		}

		if (tempCard.suit == 'H' && tempCard.face == 'Q'){
		    return 9;
		}

		if (tempCard.suit == 'H' && tempCard.face == 'K'){
		    return 10;
		}

		if (tempCard.suit == 'H' && tempCard.face == 'A'){
		    return 11;
		}

		if (tempCard.suit == 'C' && tempCard.face == 'N'){
		    return 12;
		}

		if (tempCard.suit == 'C' && tempCard.face == 'T'){
		    return 13;
		}

		if (tempCard.suit == 'C' && tempCard.face == 'J'){
		    return 14;
		}

		if (tempCard.suit == 'C' && tempCard.face == 'Q'){
		    return 15;
		}

		if (tempCard.suit == 'C' && tempCard.face == 'K'){
		    return 16;
		}

		if (tempCard.suit == 'C' && tempCard.face == 'A'){
		    return 17;
		}

		if (tempCard.suit == 'D' && tempCard.face == 'N'){
		    return 18;
		}

		if (tempCard.suit == 'D' && tempCard.face == 'T'){
		    return 19;
		}

		if (tempCard.suit == 'D' && tempCard.face == 'J'){
		    return 20;
		}

		if (tempCard.suit == 'D' && tempCard.face == 'Q'){
		    return 21;
		}

		if (tempCard.suit == 'D' && tempCard.face == 'K'){
		    return 22;
		}

		if (tempCard.suit == 'D' && tempCard.face == 'A'){
		    return 23;
		}
		
		return -1;
	}
	
	public static void main(String[] args) {
		
		EuchreGame game = new EuchreGame();
		game.runGame();
		
	}

}// EOC
