/*
 * @author Hal M. Hattis
 * 
 * @course TSP CS3141 
 * 
 * @class "Player" - Euchre component
 */
public class Player {
	
	//class variables
	int tricks;
	Card[] hand;
	Player partner;
	Boolean dealer;
	Team teamOfPlayer;
	
	//possible utility for going alone
	boolean alone;
	boolean partnerAlone;
	
	public void setTeam(Team team){
		teamOfPlayer= team;
	}
	
	/*
	 * Method to help a player handle playing a card
	 * @return void for now
	 * @param Card card, the card to be played
	 */
	public Card playCard(int cardToPlay){
		
		//if they play an invalid card
		if(cardToPlay<0 || cardToPlay>4 || hand[cardToPlay]==null){
			//throw exception maybe?
			return null;
		}
		
		Card returnCard= hand[cardToPlay];
		hand[cardToPlay]= null;
		
		for(int i = 0; i<4; i+=1){
			if(hand[i]==null){
				hand[i]= hand[i+1];
				hand[i+1] = null;
			}
		}
		
		return returnCard;
	}
	
	/*
	 * Method to help player handle picking up trump card
	 * @return Card dropDard, the card to drop
	 * @param int cardToDrop, the position of the card to discard
	 * @param Card trumpCard, the card being picking up to call trump
	 */
	public Card pickUpTrump(int cardToDrop, Card trumpCard){
		
		if(cardToDrop<0 || cardToDrop>4 || hand[cardToDrop]==null){
			//throw exception maybe?
			return null;
		}
		
		Card dropCard= hand[cardToDrop];
		hand[cardToDrop]= trumpCard;
		return dropCard;
		
	}
	
	public void landedTrick(){
		tricks+=1;
	}
	
	public void clearTricks(){
		tricks=0;
	}
	
	public void showHand(){
		for(Card card: hand){
			if(card==null){
				
			}
			else{
				System.out.println(card.face + " " + card.suit);
			}
		}
	}
	
}//EOC
