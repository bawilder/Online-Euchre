import java.io.*;
import java.net.*;

public class UDP_Server {

	static final int PORT = 50001;

	public static void main(String args[]) {
		int playerNo = 1;
		ServerSocket serverSocket = null;
		Socket socket = null;

		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			System.out.println(e);

		}
		while (true) {
			try {
				socket = serverSocket.accept();
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