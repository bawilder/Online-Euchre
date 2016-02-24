/*
 * @author Hal M. Hattis
 * 
 * @course TSP CS3141 
 * 
 * @class "Team" - Euchre component
 * 
 * @SuperClass to Player
 */
public class Team {
	
	//class variables
	Player[] players;
	boolean winner;
	int score;
	int tricks;
	boolean gotEuchd;
	boolean calledTrump;
	
	//constructor for Team
	public Team(Player teammate1, Player teammate2){
		players= new Player[2];
		players[0]= teammate1;
		players[1]= teammate2;
		winner=false;
		score=0;
		tricks=0;
		gotEuchd= false;
		calledTrump=false;
	}
	
	/*
	 * method to increment score for team
	 * method also detemines if team won on score
	 * @param int points: points scored
	 */
	public void teamScored(int points){
		score+= points;
		if(score>=10){
			winner=true;
		}
	}
	
	public int getScore(){
		return score;
	}
	
	public int getTricks(){
		return (players[0].tricks + players[1].tricks);
	}
	
}//EOC
