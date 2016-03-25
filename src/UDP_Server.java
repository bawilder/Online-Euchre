import java.io.*;
import java.net.*;

/**
 * 
 * @author Tanner Howell
 * @author Brian Wilder
 */


public class UDP_Server {

	static EchoThread player1;
	static EchoThread player2;
	static EchoThread player3;
	static EchoThread player4;
	
	static int playerNo = 1;


	public static void main(String args[]) {
		int playerNo = 1;
		ServerSocket serverSocket = null;
		Socket socket = null;
		boolean endGame = false;

		try {
			serverSocket = new ServerSocket(0, 0, InetAddress.getByName(null));
			System.out.println("Using port number: " + serverSocket.getLocalPort());
			System.out.println("IP Address: " + serverSocket.getInetAddress());
		} catch (IOException e) {
			System.out.println(e);

		}
		while (playerNo < 2) {
			try {
				System.out.println("Accepting connections!");
				socket = serverSocket.accept();
				// Set the game to timeout after 167 minutes
				socket.setSoTimeout(10000000);
				System.out.println("Connection established");
			} catch (IOException e) {
				System.out.println(e);
				break;
			}
			// new thread for a client
			if(playerNo == 1)
				player1 = new EchoThread(socket, playerNo);
			else if (playerNo == 2)
				player2 = new EchoThread(socket, playerNo);
			else if (playerNo == 3)
				player3 = new EchoThread(socket, playerNo);
			else if (playerNo == 4)
				player4 = new EchoThread(socket, playerNo);

			playerNo += 1;
			
		}
		
		while(true){
			//run this shit
			// this is where game logic and server logic intercept/inteface
			Packet packit = new Packet();
			int [] hand = {1,2,3,4,5};
			int discard = 6;
			String msg = packit.initPacket(1, 1, 1, hand, discard);
			String msg2 = packit.PokeItPacket(1);
			try{
				player1.sendPacket(msg);
				for(int i = 0; i < 1000000; i++)
					i += 1;
				player1.sendPacket(msg2);
			}
			catch (Exception e){
				System.out.println(e);
				System.exit(0);
			}
			while(true){
				int count = 0;
				count += 1;
				if (count == 2)
					break;
			}
			
			if(endGame == true)
				break;
			
		}
		try{
			serverSocket.close();
		}
		catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}
	}
	
	public void sendPacket(String msg, int playerNum){
		
		//TODO: Make this work with all players
		if(playerNum == 1)
			player1.sendPacket(msg);
		else if(playerNum == 2)
			player2.sendPacket(msg);
		else if(playerNum == 3)
			player3.sendPacket(msg);
		else
			player4.sendPacket(msg);
		
		return;
	}

}