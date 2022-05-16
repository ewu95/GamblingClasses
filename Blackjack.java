import java.util.*;
import java.math.*;

public class Blackjack extends Game {
    //global constants
    static final int CARDS = 52, CARD_TYPES = 13,  GOAL = 21;
    static final int ACE = 1, JACK = 11, QUEEN = 12, KING = 13;

    /*
    Constructors
    @param players[] players in the game
    @param bet players' bets on the game
    @param gameID identification number of the game
    */
    public Blackjack(User[] players, double[] bets, int gameID) {
	super(players, bets, gameID);
	takeMoney();
    }

	
    //Accessors 
	
    /**
    Converts numeric values of face cards into letters 
    @param cardVal the numerical value of the card
    @return the display value of the card
    */
    public static String numericToDisplay(int cardVal) {
        //this method given the numeric identity of a card returns its display value(11 returns J, 12 returns Q, etc.)
        if (cardVal >= 2 && cardVal <= 10) {
            return Integer.toString(cardVal);
        }
        else {
            switch (cardVal) {
                case JACK:
                    return "J";
                case QUEEN:
                    return "Q";
                case KING:
                    return "K";
                case ACE:
                    return "A";
                default:
                    //not possible just needs to return some value in order for compliation
                    return "";
            }
        }
    }

    //Mutators

    /**
    Runs a game of Blackjack
    */
    public void playGame() {
        //variables and array declaration
        final int COMP_DRAW_THRESHOLD = 16, START_BAL = 100;
        String move;
        int deckPos =0 , curSum = 0, compSum = 0, aceVal, val = 0, curBal = START_BAL, wager;
        int[] suits = {72, 83, 67, 68};//ascii values for H, S, C, D which represents hearts, spades, clubs, diamonds respectively;
        int[][] deck = new int[CARDS][2];
        Scanner sc = new Scanner(System.in);
		double[] wagers = getBets(); 
		
        //title and rules disply
        System.out.println("Blackjack");
        System.out.println("==============================================================================================================");//no .repeat in this version
        System.out.println("Welcome to Blackjack! Try to get to a value as close to " + GOAL + " as possible but make sure not to go over!");
        System.out.println("The dealer will try and beat you! Choose wisely!");
        System.out.println("Cards will be displayed along the format of cardofsuit(e.g Jack of Diamonds == JofD)");
        System.out.println("==============================================================================================================");

    	//in case player has run out of money
        if (curBal == 0) {
            System.out.println("You have ran out of money! Resetting balance now.");
            curBal = START_BAL;
		}

		//title and rules display
		System.out.println("Game Started!");
		System.out.println("========================================");

		//variable resetting
		deckPos = 0; curSum = 0; compSum = 0;

		//the loop below fills the deck array with "cards" represented by numbers from 1 to 13
		int curIdx = 0;
		for (int i = 1; i <= CARD_TYPES; i++) {
			for (int j = 0; j < suits.length; j++) {
				deck[curIdx][1] = suits[j];
				deck[curIdx++][0] = i;
			}
		}

		//the loop below randomizes the order of the deck, thus "shuffling" it
		for (int i = 0; i < CARDS; i++) {
			int newIndex = (int) (Math.random() * (CARDS - 1)); //generates a random integer and swaps it with i
			int temp = deck[i][0];
			deck[i][0] = deck[newIndex][0];
			deck[newIndex][0] = temp;
			temp = deck[i][1];
			deck[i][1] = deck[newIndex][1];
			deck[newIndex][1] = temp;
		}

		//player turn
		do {
			//hand display
			System.out.print("Your Hand: ");
			for (int i = 0; i <= deckPos; i++) {
				//prints hand by looping through all cards already "drawn"
				System.out.print(numericToDisplay(deck[i][0]) + "of" + ((char) deck[i][1]) + " ");
			}
			System.out.println();

			//drawing and adding a card to the hand sum
			if (deck[0][0] == ACE && curSum + 11 <= GOAL) {
				//if drawed card is an ace, prompt for the ace's chosen value(1 or 11)
				//don't give user a choice if curSum + 11 exceeds 21
				System.out.print("You got an ace! What value do you want this card to hold? ");
				aceVal = sc.nextInt();
				while (aceVal != 1 && aceVal != 11) {
					System.out.println("Invalid input! Please enter 1 or 11!");
					aceVal = sc.nextInt();
					//value entered is not 1 or 11
				}
				sc.nextLine(); //removes the newline entered by the player
				curSum += aceVal;
			}
			else {
				//max value for non ace cards is 10
				curSum += Math.min(10, deck[deckPos][0]);
			}

			deckPos++;//"removing" top card from the "deck"
			System.out.println("Your current value: " + curSum);
			if (curSum >= GOAL) {
				//sum >= GOAL, no more cards can be drawn, break
				break;
			}

			//prompt for user move(stop drawing or continue drawing)
			System.out.print("Would you like to stop drawing? Type yes or no: ");
			move = sc.nextLine();
			while (!move.toLowerCase().equals("yes") && !move.toLowerCase().equals("no")) { //case insensitivity by converting string to lowercase.
				//invalid input entered
				System.out.print("Invalid input! Please enter yes or no: ");
				move = sc.nextLine();
			}
		} while (move.equals("no"));

		//if structure to determine result of game
		if (curSum == GOAL) {
			System.out.println("You won!");
			wagers[0] *= 2.0;
			returnMoney();
		}
		else if (curSum > GOAL) {
			System.out.println("You went over the limit and thus lost!");
			wagers[0] = 0.0;
			returnMoney();
		}
		else {
			//Dealer turn
			System.out.print("Dealer Turn: ");
			//the following code simulates through the computer turn by looping through the deck array and continuosly drawing cards
			for (; deckPos<CARDS && compSum <= COMP_DRAW_THRESHOLD; deckPos++) {
				System.out.print(numericToDisplay(deck[deckPos][0]) + "of" + ((char) deck[deckPos][1]) + " ");
				if (deck[deckPos][0] == ACE) {
					if (deckPos + 11 > GOAL) {
						//computer can't pick 11 as it will lose, and thus picks 1
						compSum += 1;
					}
					else {
						compSum += 11;
					}
				}
				else {
					compSum += Math.min(10, deck[deckPos][0]);//max val for non-ace cards is 10
				}
			}
			System.out.println("\nDealer value: " + compSum);

			//result processing
			System.out.println();
			if (compSum > GOAL || curSum > compSum) {
				System.out.println("You won!");
				wagers[0] *= 2.0; //player has won, earn money from wager
				returnMoney();
			}
			else {
				System.out.println("The dealer wins! You lose!");
				wagers[0] = 0.0; //player has lost, lose money from wager
				returnMoney();
			}
		}
	}

	/**
	Main method to test functionality
	@param String[] args command-line arguments
	*/
	public static void main(String[] args) {
		User evan = new User("evan1", "asdf", "evan1@asdf");
		User gary = new User("gary1", "qwer", "gary1@qwer");
		User matthew = new User("matthew1", "zxcv", "matthew1@zxcv");
		User kevin = new User("kevin1", "sdfg", "kevin1@sdfg");
		User[] players = {evan, gary, matthew, kevin};
		double[] bets = {100.0, 50.0};
		Blackjack bjgame = new Blackjack(players, bets, 600);
		bjgame.playGame();
	}
}
