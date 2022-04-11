import java.util.Scanner;

public class PlayCards {
	DeckOfCards newDeck = new DeckOfCards();
	
	public static void main(String [] args) {
		new PlayCards();
	}
	
	public void promptUser () {
		Scanner scanner = new Scanner(System.in);
		System.out.print("User> ");
		String arg = scanner.nextLine();
		interpretInput(arg);
		scanner.close();
	}
	
	public PlayCards () {
		System.out.println("-------------Deck O' Cards-------------");
		System.out.println();
		System.out.println("Press \'Enter\' to launch with fresh deck");
		System.out.println();
		System.out.println("Or type: ");
		System.out.println("\'q\' ..................... exits program");
		System.out.println("\'w\' ..... launches UI with current deck");
		System.out.println("\'a\' .................. go back one card");
		System.out.println("\'s\' ................. shuffles the deck");
		System.out.println("\'d\' .................... draws one card");
		System.out.println("\'e\' ................. reveals all cards");
		System.out.println("\'r\' ................... resets the deck");
		System.out.println("\'f\' .................. list of commands");
		System.out.println();
		System.out.println("---------------By: Zane K--------------");
		System.out.println();
		promptUser();
	}
	
	public void interpretInput (String line) {
		if (line.equals("q")) {
			System.exit(0);
		} else if (line.equals("w")) {
			newDeck.initializeFrame();
		} else if (line.equals("e")) {
			newDeck.showDeck();
			System.out.println("Deck displayed!");
			promptUser();
		} else if (line.equals("r")) {
			newDeck.resetDeck();
			System.out.println("Deck reset!");
			promptUser();
		} else if (line.equals("a")) {
			newDeck.goBack();
			promptUser();
		} else if (line.equals("s")) {
			newDeck.shuffleDeck();
			System.out.println("Deck shuffled!");
			promptUser();
		} else if (line.equals("d")) {
			newDeck.drawOne();
			promptUser();
		} else if (line.equals("f")) {
			listCommands();
			promptUser();
		} else if (!(line.equals(""))) {
			System.out.println("Command not recognized (try \'f\' for full list)");
			promptUser();
		} else {
			newDeck.initializeFresh();
		}
	}
	
	public void listCommands () {
		System.out.println("Press \'Enter\' to launch with fresh deck");
		System.out.println();
		System.out.println("Or type: ");
		System.out.println("\'q\' ..................... exits program");
		System.out.println("\'w\' ..... launches UI with current deck");
		System.out.println("\'a\' .................. go back one card");
		System.out.println("\'s\' ................. shuffles the deck");
		System.out.println("\'d\' .................... draws one card");
		System.out.println("\'e\' ................. reveals all cards");
		System.out.println("\'r\' ................... resets the deck");
		System.out.println("\'f\' .................. list of commands");
		System.out.println();
	}
}