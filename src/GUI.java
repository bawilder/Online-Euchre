import java.awt.*;        // Using AWT container and component classes
import java.awt.event.*;  // Using AWT event classes and listener interfaces
import javax.swing.JPanel;


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
	private Button bttnCreate = new Button("Create Server");
	private Button bttnCancel = new Button("Cancel");
	
	connectGUI(){
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
		
		southPanel.add(bttnCreate);                    
		bttnCreate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                //TODO create server logic
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
}

