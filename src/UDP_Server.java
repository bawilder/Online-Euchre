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
		while (playerNo < 5) {
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
		
		EuchreGame game = new EuchreGame();
		game.runGame();
		while(true){
			
			if(game.gameOver){
				break;
			}

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

	public String receivePacket(int playerNum){
		String msg = "";
		while(msg == ""){
			if(playerNum == 1)
				msg = player1.receivePacket();
			else if(playerNum == 2)
				msg = player2.receivePacket();
			else if(playerNum == 3)
				msg = player3.receivePacket();
			else
				msg = player4.receivePacket();
			}
		return msg;
	}
}