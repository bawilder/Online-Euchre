/*
 * @author Hal M. Hattis
 * 
 * @course TSP CS3141 
 * 
 * @class "Table" - Euchre component
 */
public class Table {
	
	//class variables
	Team winner;
	Player[] players;
	Card[] tableCards;
	Team team1, team2;
	int playerDealing;
	int playerTurn;
	Card topOfDiscard;
	Deck deck;
	
	
	public Table(){
		deck= new Deck();
		winner= null;
		players= new Player[4];
		players[0]= new Player();
		players[1]= new Player();
		players[2]= new Player();
		players[3]= new Player();
		tableCards= new Card[4];
		team1= new Team(players[0], players[2]);
		team2= new Team(players[1], players[3]);
	}
	
	public void rotateDealer(){
		if(playerDealing<4){
			playerDealing+=1;
		}
		
		else{
			playerDealing=1;
		}
	}
	
	public void rotateTurn(){
		if(playerTurn<4){
			playerTurn+=1;
		}
		
		else{
			playerTurn=1;
		}
	}
	
}//EOC
