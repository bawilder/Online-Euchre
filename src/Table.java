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
	char trump;
	char trumpPartner;
	
	
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
	
	public void setTrump(char trumpSuit){
		if(trumpSuit == 'H'){
			trump= 'H';
			trumpPartner= 'D';
			return;
		}
		
		if(trumpSuit == 'D'){
			trump= 'D';
			trumpPartner= 'H';
			return;
		}
		
		if(trumpSuit == 'S'){
			trump= 'S';
			trumpPartner= 'C';
			return;
		}
		
		if(trumpSuit == 'C'){
			trump= 'C';
			trumpPartner= 'S';
			return;
		}
		
		else{
			System.out.println("Error in calling trump, program locked down");
		}
	}
	
		public int evalCards() {
			Card maxCard= new Card('B',0,'B');
			int playerOfCard=0;
			boolean containsTrump= false;
			Card tempCard;
			//check to see if this table set contains a trump card
			for(int i=0; i<4; i+=1){
				tempCard= tableCards[i];
				if (tempCard.suit== trump || (tempCard.suit==trumpPartner && tempCard.face== 'J')){
					containsTrump=true;
				}	
			}
			
			
			//so because we already know if the table contains trump, we can 
			//decide based on that which form of evaluation to use for the tables cards
			if (containsTrump){
			    for(int i=0; i<4; i+=1){
			    	tempCard= tableCards[i];
			    	//evaluate with Bowers    
			    	if(tempCard.face == 'J' || maxCard.face == 'J'){
			    		//max is right bower
			    		if(maxCard.face == 'J' && maxCard.suit == trump){
			            
			    		}
			    		//temp is right bower
			    		else if(tempCard.face == 'J' && tempCard.suit == trump){
			    			maxCard = tempCard;
			    		}
			    		//max is left and temp is not a bower
			    		else if(maxCard.face == 'J' && maxCard.suit == trumpPartner){
			    			//do nothing
			    		}
			    		//temp is left bower
			    		else if (tempCard.face == 'J' && tempCard.suit == trumpPartner){
			    			maxCard = tempCard;
			    		}
			    	}
			     
			    	else {
			    		//trump exists but no left or right bowers
			    		if (tempCard.suit == trump && maxCard.suit == trump){
			    			//do evaluation based on innate value
			    			//if temp cards base value is greater than maxCards, adjust
			    			if(tempCard.value > maxCard.value){
			    				maxCard= tempCard;
			    			}
			    			//else nothing changes
			    		}
			    		if (tempCard.suit == trump){
			    			maxCard = tempCard;
			    		}
			    		if (maxCard.suit == trump){
			    			//do nothing
			    		}
			    	} 
			    }
			}

			else {
			    //do normal evaluation
			    //already in place
				
				//note that we need to evaluate here based on the suit of the 
				//first card played, since it will now be the dominant suit
				
				//note here that we also need to make code to prevent people
				//from laying cards they shouldnt by going offsuit when thay
				//have that suit in their hand
				
				char suitToFollow= tableCards[0].suit;
				maxCard= tableCards[0];
				for(int i=0; i<4; i+=1){
					tempCard= tableCards[i];
					
					if(tempCard.suit == suitToFollow){
						//if temp cards base value is greater than maxCards, adjust
		    			if(tempCard.value > maxCard.value){
		    				maxCard= tempCard;
		    			}
					}
					//else the card isn't on suit, no eval needed
				}
			}
			
			for(int i=0; i<4; i+=1){
				tempCard= tableCards[i];
				if(tempCard.suit == maxCard.suit && tempCard.value == maxCard.value){
					//position of max card
					return i;
				}
			}
			
			//error if above loop doesnt work
			return 0;
		}
	
	public void rotateDealer(){
		if(playerDealing<3){
			playerDealing+=1;
		}
		
		else{
			playerDealing=0;
		}
	}

	public void rotateTurn(){
		if(playerTurn<3){
			playerTurn+=1;
		}
		
		else{
			playerTurn=0;
		}
	}
	
}//EOC
