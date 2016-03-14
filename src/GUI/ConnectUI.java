package GUI;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.*;

public class ConnectUI extends Frame{

	public static String name;
	public String serverNum = "localhost";
	private Label lblServerAddr = new Label ("Server Address:");
	private Label lblPortNum = new Label ("Port Number:");
	private Label lblName = new Label ("Name:");
	private Label lblError = new Label ("Error");
	private TextField tfName = new TextField();
	private TextField tfServAddr = new TextField();
	private TextField tfPortNum = new TextField();
	private Button bttnConnect = new Button("Connect");
	private Button bttnCreate = new Button("Create Server");
	private Button bttnCancel = new Button("Cancel");
	private int portNum = -1;
	public BufferedReader readBuff;
	public PrintWriter writeBuff;
	//private Socket socket = null;
	
	
	ConnectUI(){
		Font f = new Font (lblPortNum.getName(), Font.PLAIN, 15);
		
		setLocation(550, 400);
		setTitle("Connect to game");
		setLayout(new GridLayout (3, 1));
		setSize(500, 130);
		setVisible(true);
		
		JPanel northPanel = new JPanel (new GridLayout (1, 4));
		JPanel middlePanel = new JPanel (new GridLayout (1, 2));
		JPanel southPanel = new JPanel(new GridLayout (1, 4));
		
		lblServerAddr.setFont(f);
		northPanel.add(lblServerAddr);
		northPanel.add(tfServAddr);
		
		lblPortNum.setFont(f);
		northPanel.add(lblPortNum);
		northPanel.add(tfPortNum);
		
		lblName.setFont(f);
		middlePanel.add(lblName);
		
		lblError.setFont(f);
		lblError.setForeground(Color.RED);
		lblError.setVisible(false);
		southPanel.add(lblError);
		
		middlePanel.add(tfName);

		add(northPanel);
		add(middlePanel);
		
		southPanel.add(bttnConnect);                    
		bttnConnect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	
            	
            	if( !tfPortNum.getText().equals("") && !tfServAddr.getText().equals("") && !tfName.getText().equals("")) {
            		//serverNum = Integer.parseInt(tfPortNum.getText());
            		name = tfName.getText();
            		portNum = Integer.parseInt(tfPortNum.getText());
            		
            		//TODO Connect to the server here using portNum and serverNum
            		try {
						Socket socket = new Socket(serverNum, portNum);
						readBuff = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						writeBuff = new PrintWriter(socket.getOutputStream(), true);
					} catch (Exception err) {
						err.printStackTrace();
	            		lblError.setVisible(true);    
					}
            		
            		
            		//TODO: Close socket
            		
                	Client.main(null);
                	dispose();
            		
            	} else {
            		lblError.setVisible(true);    
            	}
            }
        });
		
		southPanel.add(bttnCreate);                    
		bttnCreate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                //TODO create server logics
            	System.out.println("Need to create server");
            }
        });
		
		southPanel.add(bttnCancel);                    
		bttnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                System.exit(0);
            }
        });
		add(southPanel);
		this.validate();
	}
	
	/**
	 * A function that enables the client to read a packet sent from the host
	 * @return - the packet received
	 */
	public String getPacket(){
		String myPacket = "";
		while(true){
			try{
				myPacket = readBuff.readLine();
			}
			catch (Exception err){
				System.out.println(err);
			}
			if(myPacket.length() > 0)
				break;
		}

		return myPacket;
	}
	
	/**
	 * A function that allows the client to send a packet to the host
	 * @param packToSend - the packet that needs to be send
	 */
	public void sendPacket(String packToSend){
		while(true){
			try{
				// Flush the buffer and send the packet
				writeBuff.flush();
				writeBuff.println(packToSend);
			}
			catch(Exception err){
				// catch any errors and print them out
				System.out.println(err);
			}
			// If the writebuffer hasn't encountered any errors, exit the loop
			//+ otherwise, try to resend the packet
			if(!writeBuff.checkError())
				break;
		}
	}
}
