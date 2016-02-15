import java.awt.*;        // Using AWT container and component classes
import java.awt.event.*;  // Using AWT event classes and listener interfaces


class GUI extends Frame {

	private String playerName = connectGUI.name;
	private Label lblScoreWord;    
	private Label lblScore;
	private TextArea tfsend;
	private TextArea tfChat;
	private Button btnSend; 
	
	/** Constructor to setup GUI components and event handling */
	public GUI () {
		setLocation(250, 0);
		setLayout(null);
		setBackground(Color.LIGHT_GRAY);
		
		lblScoreWord = new Label("Score");  
		add(lblScoreWord);                    

		lblScore = new Label("0");  
		add(lblScore);
		
		tfsend = new TextArea("Send a Message",0, 0, TextArea.SCROLLBARS_NONE); 
		tfsend.setBounds(1250, 790, 250, 75);
		add(tfsend);     

		tfChat = new TextArea("",0, 0, TextArea.SCROLLBARS_NONE); 
		tfChat.setBounds(1250, 30, 250, 750);
		tfChat.setEditable(false); 
		add(tfChat);

		btnSend = new Button("Send");  
		btnSend.setBounds(1425, 868, 75, 30);
		add(btnSend);                    
		btnSend.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
            	
            	//TODO send the typed message to server to send to the rest of the clients
            	
                System.out.println("You clicked the button");
            }
        });

		setTitle("Online Euchre Client");  
		setSize(1500, 900);        
		
		setVisible(true);         
	}

	/** The entry main() method */
	public static void main(String[] args) {
		connectGUI p = new connectGUI();
	}
} 

class connectGUI extends Frame {
	public static String name;
	private Label lblServerAddr = new Label ("Server Address:");
	private Label lblPortNum = new Label ("Port Number:");
	private Label lblName = new Label ("Name:");
	private Label lblError = new Label ("Error");
	private TextField tfName = new TextField();
	private TextField tfServAddr = new TextField();
	private TextField tfPortNum = new TextField();
	private Button bttnConnect = new Button("Connect");
	private Button btnCancel = new Button("Cancel");
	
	connectGUI(){
		Font f = new Font (lblPortNum.getName(), Font.PLAIN, 15);
		
		setResizable(false);
		setLocation(550, 400);
		setTitle("Connect to game");
		setBackground(Color.GRAY);
		setLayout(null);
		setSize(500, 130);
		setVisible(true);
		
		lblServerAddr.setBounds(15, 40, 110, 15);
		lblServerAddr.setFont(f);
		lblServerAddr.setForeground(Color.WHITE);
		add(lblServerAddr);
		
		lblPortNum.setBounds(285, 40, 90, 15);
		lblPortNum.setFont(f);
		lblPortNum.setForeground(Color.WHITE);
		add(lblPortNum);
		
		lblName.setBounds(15, 70, 50, 15);
		lblName.setFont(f);
		lblName.setForeground(Color.WHITE);
		add(lblName);
		
		lblError.setBounds(15, 100, 90, 15);
		lblError.setFont(f);
		lblError.setForeground(Color.RED);
		lblError.setVisible(false);
		add(lblError);
		
		tfServAddr.setBounds(125, 37, 150, 21);
		add(tfServAddr);
		
		tfPortNum.setBounds(380, 37, 100, 21);
		add(tfPortNum);
		
		tfName.setBounds(67, 67, 208, 21);
		add(tfName);
		
		bttnConnect.setBounds(380, 67, 100, 30);
		add(bttnConnect);                    
		bttnConnect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	int portNum;
            	int serverNum;
            	
            	if( !tfPortNum.getText().equals("") && !tfServAddr.getText().equals("") && !tfName.getText().equals("")) {
            		serverNum = Integer.parseInt(tfPortNum.getText());
            		name = tfName.getText();
            		portNum = Integer.parseInt(tfPortNum.getText());
            		
            		//TODO Connect to the server here using portNum and serverNum
            		
                	GUI app = new GUI();
                	app.setResizable(false);
                	dispose();
            		
            	} else {
            		lblError.setVisible(true);    
            	}
            }
        });
		
		btnCancel.setBounds(279, 67, 100, 30);
		add(btnCancel);                    
		btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });
	}
}

