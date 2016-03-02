import java.io.*;
import java.net.Socket;

public class EchoThread extends Thread {
	protected Socket socket;
	private int playerNo;
	private BufferedReader in;
	private PrintWriter out;

	public EchoThread(Socket clientSocket, int passedNo) {
		this.socket = clientSocket;
		this.playerNo = passedNo;
		this.in = null;
		this.out = null;
	}

	//TODO: Parsing
	//TODO: Needs to know which player it is
	public void run(int playerNo) {
		//BufferedReader in = null;
		//PrintWriter out = null;
		String line;
		try {
			// Buffer for reading in
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

			//Buffer for writing
			out = new PrintWriter(this.socket.getOutputStream(), true);

		} catch (IOException e) {
			return;
		}

		while (true) {
			try {

				if(in.ready() == true){
					String pack = receivePacket();
				}
				/*line = in.readLine();
                if ((line == null) || line.equalsIgnoreCase("TERM")) {
                    socket.close();
                    return;
                } else {
                    out.write(line + "\n\r");
                    out.flush();
                	//TODO: Update board
                }*/

			} catch (IOException e) {
				System.out.println(e);
				return;
			}
		}
	}
	
	public void sendPacket(String myPacket) throws IOException{
		out.write(myPacket);
		return;
	}
	
	public String receivePacket(){
		String rcvPack = "";
		while(rcvPack.equals(null)){
			try {
				rcvPack = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rcvPack;
	}
}