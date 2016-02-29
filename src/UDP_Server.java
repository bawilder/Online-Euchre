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





/*public class UDP_Server {
	public static DatagramSocket mySocket;

	public static void createServer(int sockNo){
		try {
			mySocket = new DatagramSocket(sockNo);
		}
		catch (Exception err){
			System.out.println(err);
		}
	}

	public void disconnect(){
		mySocket.close();
		return;
	}

	public static void main(String args[]){
		try {
			int myPort = Integer.parseInt(args[0]); // get port number
			boolean gameIsDone = false;
			//DatagramSocket mySocket = new DatagramSocket(myPort);
			createServer(myPort);


			System.out.println("Server is now ready");
			System.out.println("IP Address is: " + InetAddress.getLocalHost().getHostAddress());
			System.out.println("Listening port is: " + mySocket.getLocalPort());

			while (true){
				byte[] receive = new byte[512];
				byte[] send = new byte[512];




				if(gameIsDone == true)
					break;
			}

			//mySocket.close();
		}
		catch ( Exception err ){
			System.out.println(err);
		}

	}
}
 */