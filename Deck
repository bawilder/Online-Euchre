/*
 * @author Hal M. Hattis
 * 
 * @course TSP CS3141 
 * 
 * @class "Deck" - Euchre component
 */
public class Deck {

	//class variables
	Card[] cards;  
	Card[] discard;
	Card[] hand1;
	Card[] hand2;
	Card[] hand3;
	Card[] hand4;
	
	//constructor for deck
	public Deck(){
		cards = new Card[24];
		
		//cards in deck
		Card NS = new Card('S', 1, 'N');
		Card TS = new Card('S', 2, 'T');
		Card JS = new Card('S', 3, 'J');
		Card QS = new Card('S', 4, 'Q');
		Card KS = new Card('S', 5, 'K');
		Card AS = new Card('S', 6, 'A');
		Card NH = new Card('H', 1, 'N');
		Card TH = new Card('H', 2, 'T');
		Card JH = new Card('H', 3, 'J');
		Card QH = new Card('H', 4, 'Q');
		Card KH = new Card('H', 5, 'K');
		Card AH = new Card('H', 6, 'A');
		Card NC = new Card('C', 1, 'N');
		Card TC = new Card('C', 2, 'T');
		Card JC = new Card('C', 3, 'J');
		Card QC = new Card('C', 4, 'Q');
		Card KC = new Card('C', 5, 'K');
		Card AC = new Card('C', 6, 'A');
		Card ND = new Card('D', 1, 'N');
		Card TD = new Card('D', 2, 'T');
		Card JD = new Card('D', 3, 'J');
		Card QD = new Card('D', 4, 'Q');
		Card KD = new Card('D', 5, 'K');
		Card AD = new Card('D', 6, 'A');
	
		cards[0] = NS;
		cards[1] = TS;
		cards[2] = JS;
		cards[3] = QS;
		cards[4] = KS;
		cards[5] = AS;
		cards[6] = NH;
		cards[7] = TH;
		cards[8] = JH;
		cards[9] = QH;
		cards[10] = KH;
		cards[11] = AH;
		cards[12] = NC;
		cards[13] = TC;
		cards[14] = JC;
		cards[15] = QC;
		cards[16] = KC;
		cards[17] = AC;
		cards[18] = ND;
		cards[19] = TD;
		cards[20] = JD;
		cards[21] = QD;
		cards[22] = KD;
		cards[23] = AD;
	}
	
	/*
	 * Method to generate hands and discard from the deck
	 * @return void
	 */
	public void deal(){
		hand1= new Card[5];
		hand2= new Card[5];
		hand3= new Card[5];
		hand4= new Card[5];
		discard = new Card[4];
		int j=0; 
		
		//loop to fill hands with cards
		for(int i=0; i<=16; i+=4){
			hand1[(j)]= cards[i];
			hand2[(j)]= cards[i+1];
			hand3[(j)]= cards[i+2];
			hand4[(j)]= cards[i+3];
			j+=1;
		}
		
		discard[0]= cards[20];
		discard[1]= cards[21];
		discard[2]= cards[22];
		discard[3]= cards[23];
	}
	
	/*
	 * Method to randomize deck for dealing
	 * @return void
	 */
	public void shuffle(){
		
		//randomly pull cards out of the array to swap around the deck
		for(int i=0; i<50; i+=1){
			//create a dummy card to work for swapping
			Card tempCard;
			
			//bubble swap the cards at random
			int randomNum = ((int)(23*(Math.random())));
			tempCard= cards[randomNum];
			int randomNum2 = ((int)(23*(Math.random())));
			cards[randomNum] = cards[randomNum2];
			cards[randomNum2] = tempCard;
			
		}
	}
	
	public static void main(String[] args) {
		Deck theDeck= new Deck();
		
		//print deck
		for(int i=0; i<24; i+=1){
			System.out.println("Card " + (i+1) + ": " + theDeck.cards[i].face + theDeck.cards[i].suit);
		}
		
		//shuffle
		System.out.println("Every day Im shufflin...");
		theDeck.shuffle();
		
		//print deck after shuffle
		for(int i=0; i<24; i+=1){
			System.out.println("Card " + (i+1) + ": " + theDeck.cards[i].face + theDeck.cards[i].suit);
		}
		
		//deal cards
		System.out.println("Lets Make A Deal");
		theDeck.deal();
		
		//print hands after the deal
		System.out.println("Raise Them Hands High");
		for(int i=0; i<5; i+=1){
			System.out.println("Hand 1 Card " + (i+1) + ": " + theDeck.hand1[i].face + theDeck.hand1[i].suit);
		}
		for(int i=0; i<5; i+=1){
			System.out.println("Hand 2 Card " + (i+1) + ": " + theDeck.hand2[i].face + theDeck.hand2[i].suit);
		}
		for(int i=0; i<5; i+=1){
			System.out.println("Hand 3 Card " + (i+1) + ": " + theDeck.hand3[i].face + theDeck.hand3[i].suit);
		}
		for(int i=0; i<5; i+=1){
			System.out.println("Hand 4 Card " + (i+1) + ": " + theDeck.hand4[i].face + theDeck.hand4[i].suit);
		}
		
		//print the discard pile after the deal
		System.out.println("And the crescendo");
		for(int i=0; i<4; i+=1){
			System.out.println("Discard Card " + (i+1) + ": " + theDeck.discard[i].face + theDeck.discard[i].suit);
		}
		
	}//EOM
	
}//EOC
