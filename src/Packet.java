/**
 * 
 * @author Tanner Howell
 * @emotionalSupport Brian Wilder
 * @incessantNagging Zach Dunham
 * 
 * 
 * This is the class where all of the packets are assembled. Packets are distinguished by
 * + an integer value at the start of the packet.
 * 
 * The integer values are as follows:
 * 0  - Illegal Move/Error	(host -> client)
 * 1  - Refresh Board 		(host -> client)
 * 2  - Play Card			(client -> host)
 * 3  - Choose Trump		(client -> host)
 * 4  - Poke-It Packet      (host -> client)
 * 5  - Declare Trick Winner (host -> client)
 * 6  - 
 * 7  - Set Trump Values	(host -> client)
 * 8  - Score Update/Deal	(host -> client)
 * 9  - Initialize Game		(host -> client)
 * 
 * The integer values of the cards are as follows:
 * 0  - 9 of Spades
 * 1  - 10 of Spades
 * 2  - Jack of Spades
 * 3  - Queen of Spades
 * 4  - Kind of Spades
 * 5  - Ace of Spades
 * 6  - Nine of Hearts
 * 7  - Ten of Hearts
 * 8  - Jack of Hearts
 * 9  - Queen of Hearts
 * 10 - King of Hearts
 * 11 - Ace of Hearts
 * 12 - Nine of Clubs
 * 13 - Ten of Clubs
 * 14 - Jack of Clubs
 * 15 - Queen of Clubs 
 * 16 - King of Clubs
 * 17 - Ace of Clubs
 * 18 - Nine of Diamonds
 * 19 - Ten of Diamonds
 * 20 - Jack of Diamonds
 * 21 - Card QD = Queen of Diamonds
 * 22 - Card KD = King of Diamonds
 * 23 - Card AD = Ace of Diamonds
 * -1 - Card has yet to be played
 * 
 */



public class Packet {
	/**
	 * Assembles an initialize packet, sent at start of game
	 * Packet Layout:
	 * 		9,dealFlag,playerNum,teamNo,card1,card2,card3,card4,card5,discard
	 * 
	 * @param dealFlag - A flag that is set when someone is the dealer
	 * @param playerNum - Let's the player know what number they are
	 * @param teamNo - the team number of the player
	 * @param hand - An array containing the hand dealt to the player
	 * @param discard - the current card facing up
	 * @return - an assembled packet
	 */

	public String initPacket(int dealFlag, int playerNum, int teamNo, int[] hand, int discard) {
		String packet = "9,";
		
		packet = packet.concat(Integer.toString(dealFlag) + "," + Integer.toString(playerNum) + "," + Integer.toString(teamNo) + ",");

		for(int i = 0; i < hand.length; i ++)
			packet = packet.concat(Integer.toString(hand[i]) + ",");
			
		packet = packet.concat(Integer.toString(discard));
		
		return packet;
	}
	
	/**
	 * A packet that lets the clients know who won the trick
	 * 
	 * @param trickWinner - the team that won the trick
	 * @param t1TrickScore - the number of tricks team 1 has won
	 * @param t2TrickScore - the number of tricks team 2 has won
	 * @return - the assembled packet
	 */
	public String trickUpdate(int trickWinner, int t1TrickScore, int t2TrickScore){
		String packet = "5,";
		
		packet = packet.concat(Integer.toString(trickWinner) + ",");
		packet = packet.concat(Integer.toString(t1TrickScore) + ",");
		packet = packet.concat(Integer.toString(t2TrickScore));
		
		
		return packet;
	}
	
	/**
	 * A packet where the host updates all of the clients with player names
	 * 
	 * @param p1nam - player 1 name
	 * @param p2nam - player 2 name
	 * @param p3nam - player 3 name
	 * 
	 * @return - assembled packet
	 */
	public String updateNames(String p1nam, String p2nam, String p3nam){
		String packet = "5,";
		packet = packet.concat(p1nam + ",");
		packet = packet.concat(p2nam + ",");
		packet = packet.concat(p3nam);
		
		return packet;
	}
	
	/**
	 * Allows the client to let the host know his/her name
	 * @param playerPosition - the location of the player
	 * @param name - the name the client wants
	 * @return - an assembled packet
	 */
	public String playerName(int playerPosition, String name){
		String packet = "6,";
		packet = packet.concat(Integer.toString(playerPosition) + ",");
		packet = packet.concat(name);
		
		return packet;
	}

	/**
	 * A packet that is used when a card is played
	 * Packet Layout:
	 * 		2,cardVal
	 * @param cardVal - the value of the card being played
	 * @return - an assembled packet
	 */
	public String playCard(int cardVal) {
		String packet = "2,";
		packet = packet.concat(Integer.toString(cardVal));
		
		return packet;
	}
	
	/**
	 * A packet the client uses to choose the next trump suit
	 * @param trump - the value of the suit being chosen
	 * 		1 = Clubs
	 * 		2 = Spades
	 * 		3 = Diamonds
	 * 		4 = Hearts
	 * 		-1 = Pass
	 * @return - an assembled packet
	 */
	public String chooseTrump(int trump){
 
		String packet = "3,";

		System.out.println("got this as trump: " + trump);
		packet = packet.concat(Integer.toString(trump));
		System.out.println("Packet: " + packet);
		return packet;
	}
	
	/**
	 * A packet to tell the client when it is their
	 * turn to do something
	 */
	public String PokeItPacket(int playerTurn){
		String packet = "4,";
		
		//Why was there a plus 1?
		packet = packet.concat(Integer.toString(playerTurn + 1));
		return packet;
	}
	
	/**
	 * A packet that is used to set trump values
	 * Packet Layout:
	 * 		7,minTrump,maxTrump,leftBaur
	 * 
	 * @param minTrump - the minimum value of trump
	 * @param maxTrump
	 * @param leftBauer
	 * @return
	 */
	public String trumpPacket(int suit) {
		String packet = "7,";
		
		//convert the integers to strings and append them to the packet
		packet = packet.concat(Integer.toString(suit));

		return packet;
	}
	
	/**
	 * A packet that is sent to update the scores
	 * PacketLayout:
	 * 		8,team1Score,team2Score,card1,card2,card3,card4,card5,discard
	 * 
	 * @param team1Score - the current score of team1
	 * @param team2Score - the current score of team2
	 * @param newHand - an array of new cards for the player
	 * @param discard - the card currently facing up
	 * @param dealerFlag - let's the board know who's team it is
	 * @return - Packet
	 */


	public String newHandPacket(int team1Score, int team2Score) {
		String packet = "8,";
		
		//append team 1's score
		packet = packet.concat(Integer.toString(team1Score) + ",");
		//append team 2's score
		packet = packet.concat(Integer.toString(team2Score) + ",");
		
		//return the packet for sending
		return packet;
	}
	
	/**
	 * A packet that is sent by the host to update the board to all of the clients
	 * Packet Layout;
	 * 		1,card1||*,card2||*,card3||*,card4||*,p1Count,p2Count,p3Count,p4Count
	 * 
	 * @param table - The cards currently on the table
	 * @param p1Count - The number of cards in Player 1's hand
	 * @param p2Count - The number of cards in Player 2's hand
	 * @param p3Count - The number of cards in Player 3's hand
	 * @param p4Count - The number of cards in Player 4's hand
	 * @return - An assembled packet
	 */
	public String refreshPacket(int lastCardPlayed, int whoPlayedIt) {
		String packet = "1,";
			
		packet = packet.concat(Integer.toString(lastCardPlayed) + ",");
		packet = packet.concat(Integer.toString(whoPlayedIt));
		
		//return the packet
		return packet;
	}
	
	/**
	 * A packet sent from the host to the client when the client makes an illegal move
	 * (playing out of turn, playing the wrong suit)
	 * Packet Layout:
	 * 		0,message
	 * 
	 * @param message - A string to let player know what they did wrong
	 * @return - packet 
	 */
	public String illegalPacket(String message) {
		String packet = "0,";
		
		packet = packet.concat(message + ",");
		
		return packet;
	}
}

