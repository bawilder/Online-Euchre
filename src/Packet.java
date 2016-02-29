public class Packet {
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
	
	public String playCard(int cardVal) {
		String packet = "2,";
		packet = packet.concat(Integer.toString(cardVal));
		
		return packet;
	}
	
	public String trumpPacket(int minTrump, int maxTrump, int leftBauer) {
		String packet = "7,";
		
		packet = packet.concat(Integer.toString(minTrump) + "," + Integer.toString(maxTrump) + "," + Integer.toString(leftBauer));
		
		return packet;
	}
	
	public String scorePacket() {
		
		return "unfinished";
	}
	
	public String refreshPacket(int[] table, int p1Count, int p2Count, int p3Count, int p4Count) {
		
		return "unfinished";
	}
	
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

