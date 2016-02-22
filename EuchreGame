import java.util.Scanner;
/*
 * @author Hal M. Hattis
 * 
 * @course TSP CS3141 
 * 
 * @class "EuchreGame" - Euchre headclass
 */
public class EuchreGame {
	
	//class variables
	//boolean to control the game state
	Boolean gameOver= false;
	//Table to initialize teams and players
	Table table= new Table();
	Scanner in = new Scanner(System.in);
	
	/*
	 * This method will handle shuffling and dealing
	 * the deck to start each hand
	 */
	public void dealDeck(){
		table.deck.shuffle();
		table.deck.deal();
		table.players[0].hand= table.deck.hand1;
		table.players[1].hand= table.deck.hand2;
		table.players[2].hand= table.deck.hand3;
		table.players[3].hand= table.deck.hand4;
		table.topOfDiscard= table.deck.discard[0];
	}
	
	public void trumpRound(){
		
	}
	
	public void runTrick(){
		//use player turn, use dealer, use read ins, use play card
		
		//play all the cards for the trick
		System.out.println("Player 1, play a card from this hand: (0-4 are the viable inputs)");
		table.players[0].showHand();
		int card1 = in.nextInt();
		table.tableCards[0]= table.players[0].playCard(card1);
		
		
		System.out.println("Player 2, play a card from this hand: (0-4 are the viable inputs)");
		table.players[1].showHand();
		int card2 = in.nextInt();
		table.tableCards[1]= table.players[1].playCard(card2);
		
		System.out.println("Player 3, play a card from this hand: (0-4 are the viable inputs)");
		table.players[2].showHand();
		int card3 = in.nextInt();
		table.tableCards[2]= table.players[2].playCard(card3);
		
		System.out.println("Player 4, play a card from this hand: (0-4 are the viable inputs)");
		table.players[3].showHand();
		int card4 = in.nextInt();
		table.tableCards[3]= table.players[3].playCard(card4);
		
		//evaluate cards played
		Card maxCard= new Card('A',0,'J');
		int playerOfCard=0;
		for(int i=0; i<4; i+=1){
			if(maxCard.value < table.tableCards[i].value){
				maxCard=table.tableCards[i];
				playerOfCard=i;
			}
		}
		
		System.out.println("Player " + (playerOfCard+1) +  " wins the trick!");
		
		table.players[playerOfCard].tricks+=1;
		
		if(playerOfCard<2){
			table.team1.tricks+=1;
		}
		if(playerOfCard>1){
			table.team2.tricks+=1;
		}
		
	}
	
	public void runHand(){
		dealDeck();
		
		for(int i=0; i<5; i+=1){
			runTrick();
		}
		
		if(table.team1.getTricks()>2){
			table.team1.teamScored(1);
			System.out.println("Team 1 scored!");
		}
		else{
			table.team2.teamScored(1);
			System.out.println("Team 2 scored!");
		}
		
	}
	
	public void runGame(){
		while(table.team1.getScore()<10 && table.team2.getScore()<10){
			runHand();
		}
		
		if(table.team1.winner == true){
			System.out.println("Team 1 Won the Game");
		}
		
		else{
			System.out.println("Team 2 Won the Game");
		}
	}
	
	
	
	public static void main(String[] args) {
		EuchreGame game= new EuchreGame();
		game.runGame();
	}
	
}//EOC
