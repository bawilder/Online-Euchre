import java.io.*;
import java.net.Socket;

public class EchoThread extends Thread {
    protected Socket socket;
    private int playerNo;

    public EchoThread(Socket clientSocket, int passedNo) {
        this.socket = clientSocket;
        this.playerNo = passedNo;
    }
    
//TODO: Parsing
//TODO: Needs to know which player it is
    public void run(int playerNo) {
        BufferedReader in = null;
        PrintWriter out = null;
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
                line = in.readLine();
                if ((line == null) || line.equalsIgnoreCase("TERM")) {
                    socket.close();
                    return;
                } else {
                    out.write(line + "\n\r");
                    out.flush();
                	//TODO: Update board
                }
            } catch (IOException e) {
            	System.out.println(e);
                return;
            }
        }
    }
}