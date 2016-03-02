public class Packet {
	/**
	 * Assembles an initialize packet, sent at start of game
	 * @param dealFlag - A flag that is set when someone is the dealer
	 * @param p2Nam - Player 2 name
	 * @param p3Nam - Player 3 name
	 * @param p4Nam - Player 4 name
	 * @param hand - An array containing the hand dealt to the player
	 * @return - an assembled packet
	 */
	public String initPacket(int dealFlag, String p2Nam, String p3Nam, String p4Nam, int[] hand) {
		String packet = "9,";
		
		packet = packet.concat(Integer.toString(dealFlag) + "," + p2Nam + "," + p3Nam + ","  + p4Nam + ",");
		for(int i = 0; i < hand.length; i ++)
			packet = packet.concat(Integer.toString(hand[i]) + ",");
		
		return packet;
	}
	/*
	public String legalPacket() {
		
		return "unfinished";
	}
	*/
	
	/**
	 * A packet that is used when a card is played
	 * @param cardVal - the value of the card being played
	 * @return - an assembled packet
	 */
	public String playCard(int cardVal) {
		String packet = "2,";
		packet = packet.concat(Integer.toString(cardVal));
		
		return packet;
	}
	
	/**
	 * A packet that is used to set trump values
	 * @param minTrump - the minimum value of trump
	 * @param maxTrump
	 * @param leftBauer
	 * @return
	 */
	public String trumpPacket(int minTrump, int maxTrump, int leftBauer) {
		String packet = "7,";
		
		packet = packet.concat(Integer.toString(minTrump) + "," + Integer.toString(maxTrump) + "," + Integer.toString(leftBauer));
		
		return packet;
	}
	
	/**
	 * A packet that is sent to update the scores
	 * @return - Packet
	 */
	//TODO: write this packet function
	public String scorePacket() {
		
		return "unfinished";
	}
	
	/**
	 * A packet that is sent by the host to update the board to all of the clients
	 * @param table - The cards currently on the table
	 * @param p1Count - The number of cards in Player 1's hand
	 * @param p2Count - The number of cards in Player 2's hand
	 * @param p3Count - The number of cards in Player 3's hand
	 * @param p4Count - The number of cards in Player 4's hand
	 * @return - An assembled packet
	 */
	//TODO
	public String refreshPacket(int[] table, int p1Count, int p2Count, int p3Count, int p4Count) {
		
		return "unfinished";
	}
	
	/**
	 * A packet sent from the host to the client when the client makes an illegal move
	 * (playing out of turn, playing the wrong suit)
	 * @param message - A string to let player know what they did wrong
	 * @return - packet
	 */
	public String illegalPacket(String message) {
		String packet = "0,";
		
		packet = packet.concat(message + ",");
		
		return packet;
	}
	
	
	//TODO
	public int[] initHand(String packet)
	{
		int[] hand = new int[3];
		return hand;
	}
	//TODO
	public String initPackNames(String packet)
	{
		String playerNames = "";
		
		return playerNames;
	}
}

