import java.io.*;
import java.net.*;

/**
 * 
 * @author Tanner Howell
 * @author Brian Wilder
 */

public class UDP_Server {

	public static void main(String args[]) {
		int playerNo = 1;
		ServerSocket serverSocket = null;
		Socket socket = null;

		try {
			serverSocket = new ServerSocket(0, 0, InetAddress.getByName(null));
			System.out.println("Using port number: " + serverSocket.getLocalPort());
			System.out.println("IP Address: " + serverSocket.getInetAddress());
		} catch (IOException e) {
			System.out.println(e);

		}
		while (true) {
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
			new EchoThread(socket, playerNo).start();
			playerNo += 1;
			
		}
		try{
			serverSocket.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
}