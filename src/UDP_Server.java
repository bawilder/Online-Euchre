import java.io.*;
import java.net.*;

public class UDP_Server {

	static final int PORT = 50001;

	public static void main(String args[]) {
		int playerNo = 1;
		ServerSocket serverSocket = null;
		Socket socket = null;

		try {
			//serverSocket = new ServerSocket(PORT);
			serverSocket = new ServerSocket(PORT, 0, InetAddress.getByName(null));
		} catch (IOException e) {
			System.out.println(e);

		}
		while (true) {
			try {
				System.out.println("Accepting connections!");
				socket = serverSocket.accept();
				//TODO: Find a better way to end the game than a 167 minute timeout 
				socket.setSoTimeout(10000000);
				System.out.println("Connection established");
			} catch (IOException e) {
				System.out.println(e);
			}
			// new thread for a client
			new EchoThread(socket, playerNo).start();
			playerNo += 1;
			
			//TODO: Make actual break condition
			if(playerNo > 4)
				break;
		}
		try{
			serverSocket.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
}