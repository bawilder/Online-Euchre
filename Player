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
	
	//possible utility for going alone
	boolean alone;
	boolean partnerAlone;
	
	/*
	 * Method to help a player handle playing a card
	 * @return void for now
	 * @param Card card, the card to be played
	 */
	public Card playCard(int cardToPlay){
		
		//if they play an invalid card
		if(cardToPlay<1 || cardToPlay>5 || hand[cardToPlay]==null){
			//throw exception maybe?
			return null;
		}
		
		Card returnCard= hand[cardToPlay];
		hand[cardToPlay]= null;
		return returnCard;
	}
	
	/*
	 * Method to help player handle picking up trump card
	 * @return Card dropDard, the card to drop
	 * @param int cardToDrop, the position of the card to discard
	 * @param Card trumpCard, the card being picking up to call trump
	 */
	public Card pickUpTrump(int cardToDrop, Card trumpCard){
		
		if(cardToDrop<1 || cardToDrop>5 || hand[cardToDrop]==null){
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
	
	public void showHand(){
		for(int i=0; i<5; i+=1){
			System.out.println(hand[i].face + " " + hand[i].suit);
		}
	}
	
}//EOC
