import java.util.*;
import java.io.*;
public class UserInterface {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
		boolean quit = false, badData = true,loggedIn = false;;
		int input = -1, idx = 1; 
		double qty;
		char choice;
		User curUser = new User(); 
		System.out.println("Welcome to Gary's Gambling Garden!");
		while (!quit) {
			if (!loggedIn) {
				System.out.println("Not logged in.");
			}
			else {
				System.out.println("Logged in as " + curUser.getUsername());
			}
			System.out.println("What would you like to do?");
			System.out.println("1. Sign Up/Login");
			System.out.println("2. Account Details");
			System.out.println("3. Input Money");
			System.out.println("4. Withdraw Money");
			System.out.println("5. Play Games");
			System.out.println("6. Bet on Sports");
			System.out.println("7. Exit");
   			while(true) {
        		try {
            		System.out.print("Enter a number from 1 to 6: ");
            		input = Integer.parseInt(sc.nextLine());
					if (input < 1 || input > 6) {
						System.out.println("Please enter an integer between 1 and 6!");
						continue;
					}
            		break;
        		}
        		catch(NumberFormatException e) {
            		System.out.println("Please enter an integer");
				}
        	}
			System.out.println();
			if (input == 1) {
				if (loggedIn) {
					System.out.println("Logging out as " + curUser.getUsername()); 
				}
				curUser = login();
				loggedIn = true;
			}
			else if (input == 2) {
				if (!loggedIn) {
					System.out.println("Please login first!");
				}
				else {
					System.out.println("Current Balance: " + curUser.getWallet().getQtyUSD()); 
				}
			}
			else if (input == 3) {
				if (!loggedIn) {
					System.out.println("Please sign up/log in first!");
				}
				else {
					while(true) {
		        		try {
		            		System.out.print("Enter the amount to be added: ");
		            		qty = Double.parseDouble(sc.nextLine());
		            		break;
		        		}
		        		catch(NumberFormatException e) {
		            		System.out.println("Please enter a number!");
						}
		        	}
					curUser.getWallet().changeMoney(qty); 
					curUser.saveFile();
				}
			}
			else if (input == 4) { //user tries to withdraw money
				System.out.println("no lol");
			}
			else if (input == 5) {
				if (!loggedIn) {
					System.out.println("Please sign up/log in first!");
					continue;
				}
				System.out.println("What games would you like to play?");
				System.out.println("A. Blackjack");
				System.out.println("B. Slots");
				System.out.println("C. Roulette");
				System.out.println("D. Flip Coin");
				System.out.println("E. Quit"); 
				while (true) {
					System.out.print("Please enter A, B, C, D, or E ");
					choice = sc.nextLine().charAt(0);
					if (choice >= 'A' && choice <= 'E') {
						break; 
					}
				}
				if (choice == 'E') {
					continue;
				}
				System.out.print("What would you like to bet? ");
				double bet = Double.parseDouble(sc.nextLine());
				while (bet > curUser.getWallet().getQtyUSD()) {
					System.out.println("You do not have enough money. Please enter a bet less than " + curUser.getWallet().getQtyUSD());
					bet = Double.parseDouble(sc.nextLine());
				}
				User[] players = {curUser};
				double[] bets = {bet};
				if (choice == 'A') {
					Blackjack game = new Blackjack(players, bets, idx++);
					game.playGame();	
				}
				else if (choice == 'B') {
					SlotMachine game = new SlotMachine(players, bets, idx++);
					game.playGame();
				}
				else if (choice == 'C') {
					Roulette game = new Roulette(players, bets, idx++);
					game.playGame();
				}
				else if (choice == 'D') {
					FlipCoin game = new FlipCoin(players, bets, idx++);
					game.playGame();
				}
				curUser.saveFile();
				
			}
			else if (input == 6) {
				if (!loggedIn) {
					System.out.println("Please sign up/log in first!");
					continue;
				}
				
				//Making Players
		        Player freddyJackson = new Player("Freddy Jackson",17,38);
		        Player michealJackson = new Player("Micheal Jackson",21,38);
		        Player georgeWashingless = new Player("George Washingless ",52,21);
		        Player sierraJones = new Player("Sierra Jones",31,24);
		        Player biancaBruh = new Player("Bianca Bruh",51,26);
		        Player evan = new Player("Evan",69,21);
		        Player gary = new Player("Gary",21,69);
		        Player imRunning = new Player("Im Running",43,25);
		        Player outOf = new Player("Out Of",2,32);
		        Player namesToput = new Player("Names Toput",58,21);
				System.out.print("Would you like to play Soccer or Tennis? ");
				String sportType;
				while (true) {
					System.out.print("Type S for Soccer or T for Tennis ");
					sportType = sc.nextLine();
					if (sportType.equals("S") || sportType.equals("T")) {
						break;
					}
				}
				if (sportType.equals("S")) {
					Soccer soccer = new Soccer(freddyJackson,michealJackson,georgeWashingless,sierraJones,biancaBruh,evan,gary,imRunning,outOf,namesToput);			
					System.out.println(soccer.toString());
					System.out.print("What would you like to bet? ");
					double bet = Double.parseDouble(sc.nextLine());
					while (bet > curUser.getWallet().getQtyUSD()) {
						System.out.println("You do not have enough money. Please enter a bet less than " + curUser.getWallet().getQtyUSD());
						bet = Double.parseDouble(sc.nextLine());
					}
					System.out.println("Which team are you betting on?");
					String team;
					while (true) {
						System.out.print("Please type \"home\" for the home team or \"opposition\" for the opposing team");
						team = sc.nextLine();
						if (team.equals("home") || team.equals("opposition")) {
							break;
						}
					}
					soccer.runSoccer(bet, team, curUser);
				}
				else {
					Tennis tennis = new Tennis(freddyJackson,michealJackson,georgeWashingless,sierraJones,biancaBruh,evan,gary,imRunning,outOf,namesToput);			
					System.out.print("What would you like to bet? ");
					double bet = Double.parseDouble(sc.nextLine());
					while (bet > curUser.getWallet().getQtyUSD()) {
						System.out.println("You do not have enough money. Please enter a bet less than " + curUser.getWallet().getQtyUSD());
						bet = Double.parseDouble(sc.nextLine());
					}
					System.out.println("Which team are you betting on?");
					System.out.println(tennis.toString());
					String team;
					while (true) {
						System.out.print("Please type \"home\" for the home team or \"opposition\" for the opposing team");
						team = sc.nextLine();
						if (team.equals("home") || team.equals("opposition")) {
							break;
						}
					}
					tennis.runTennis(bet, team, curUser);
				}
				curUser.saveFile();

				
			}
			System.out.println();
    	}
	}
	public static User login() {
		System.out.print("Enter your username: ");
		String username = sc.nextLine();
		System.out.print("Enter your password: ");
		String password = sc.nextLine();
		String accountID = username+"@"+password;  
		String fileName = "User" + accountID + ".txt";
		double qtyUSD;
		User newUser; 
		try {
			Scanner fs = new Scanner(new File(fileName));
			String useless = fs.nextLine();
			useless = fs.nextLine();
			qtyUSD = Double.parseDouble(fs.nextLine()); 
			newUser = new User(username, password, accountID);
			newUser.getWallet().changeMoney(qtyUSD); 
			System.out.println("Logged in.");
		}
		catch (IOException e) {
			System.out.println("Account does not exist. Creating account... ");
			accountID = username+"@"+password;  
			newUser = new User(username, password, accountID);
			newUser.saveFile();
			System.out.println("Account created.");
		}
		return newUser; 
	}

	
}
