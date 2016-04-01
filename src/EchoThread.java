import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Tanner Howell
 * @author Brian Wilder
 * 
 * This is a thread that runs the actual print and write buffers for each
 * +client and server
 *
 */
public class EchoThread extends Thread {
	protected Socket socket;
	private int playerNo;
	private BufferedReader in;
	private PrintWriter out;

	public EchoThread(Socket clientSocket, int passedNo) {
		this.socket = clientSocket;
		this.playerNo = passedNo;
		try {
			// Buffer for reading in
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

			//Buffer for writing
			out = new PrintWriter(this.socket.getOutputStream(), true);

		} catch (IOException e) {
			return;
		}
	}

	public void run(int playerNo) {
		try {
			// Buffer for reading in
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

			//Buffer for writing
			out = new PrintWriter(this.socket.getOutputStream(), true);

		} catch (IOException e) {
			return;
		}
	}

	/**
	 * A function to parse a packet to get the player location when a name packet is received
	 * @param packet - the packet received
	 * @return  - player location
	 */
	public int parseNamePacket_loc(String packet){
		int playerLocation = -1;
		
		// parse the packet into an arraylist
		ArrayList<String> list = new ArrayList<String>(Arrays.asList(packet.split(",")));
		playerLocation = Integer.parseInt(list.get(1));
		
		return playerLocation;
	}
	
	/**
	 * A function to parse a packet to get the player name when a name packet is received
	 * @param packet - the packet recieved
	 * @return - the player name
	 */
	public String parseNamePacket_name(String packet){
		String playerName = "";
		// parse the packet into an arraylist
		ArrayList<String> list = new ArrayList<String>(Arrays.asList(packet.split(",")));
		playerName = list.get(2);
			
		return playerName;
	}
	/**
	 * A function that sends a packet over the printbuffer
	 * @param myPacket - the packet to be send
	 */
	
	//TODO: Something is wrong in this peice of code or in server
	public void sendPacket(String myPacket){
		boolean errInWrite = true;
		while(errInWrite == true)
		{
			try{
				//out.flush();
				//TODO: Notify the client might not be needed, check into this
				//out.notify();    //Might not be needed 
				out.println(myPacket);
				errInWrite = out.checkError();
			}
			catch(Exception e){
				System.out.println(e);
				System.exit(0);
			}
		}
		return;
	}

	/**
	 * A function that listens for a packet
	 * @return
	 */
	public String receivePacket(){
		String rcvPack = "";
		
		try {
			rcvPack = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return rcvPack;
	}

	/**
	 * A parser to return the card that is played by the client
	 *  
	 * @param packet - a packet that is received
	 * @return - integer value corresponding to a card
	 */
	public int parseCardPacket(String packet){
		int cardNo = -1;
		ArrayList<String> list = new ArrayList<String>(Arrays.asList(packet.split(",")));
		
		cardNo = Integer.parseInt(list.get(1));
		
		return cardNo;
	}
}