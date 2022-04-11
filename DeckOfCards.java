import java.util.Random;
import javax.swing.*;
import java.awt.*;

public class DeckOfCards {

	protected String [] deck = new String[52];
	protected int currentPosition = -1;
	protected boolean seenDeck = false;		//cannot proceed backwards through deck (unless seen once)
	
	JButton currCard = new JButton();		//update the picture of this button on prev/next
	JButton prevCard = new JButton();		//update # cards seen
	JButton nextCard = new JButton();		// "     #   "    left

	public DeckOfCards () {
		fillDeck();
	}
	
	private void fillDeck () {				//fills deck in order
		for (int i = 0; i < 52; i++) {
			deck [i] = rankSwitch(i);
			deck [i] = deck[i] + suitSwitch(i);
		}
	}
	
	public void showDeck () {				//prints all cards in terminal in same order as deck
		for (int i = 0; i < 13; i++) {
			System.out.print(deck[i] + " ");
		}
		System.out.println();
		for (int i = 13; i < 26; i++) {
			System.out.print(deck[i] + " ");
		}
		System.out.println();
		for (int i = 26; i < 39; i++) {
			System.out.print(deck[i] + " ");
		}
		System.out.println();
		for (int i = 39; i < 52; i++) {
			System.out.print(deck[i] + " ");
		}
		System.out.println();
	}
	
	public void goBack () {
		if (currentPosition > 0) {		//standard case
			currentPosition--;
			currCard.setIcon(new ImageIcon("Cards\\" + deck[currentPosition] + ".jpg"));
			nextCard.setText(Integer.toString(51 - currentPosition) + " >");
			prevCard.setText("< " + Integer.toString(currentPosition + 1));
			printCard();
		}
		else if (currentPosition == 0) {	//looking at first card -> go to face design
			currentPosition--;
			currCard.setIcon(new ImageIcon("Cards\\face.jpg"));
			nextCard.setText("52 >");
			prevCard.setText("< 0");
		}
		else {
			if (seenDeck) {		//if already looking at face design, and flipped through once
				currentPosition = 51;
				currCard.setIcon(new ImageIcon("Cards\\" + deck[currentPosition] + ".jpg"));
				printCard();
				nextCard.setText("0 >");
				prevCard.setText("< 52");
			}
			else {
				System.out.println("Proceed through deck first!");
			}
		}
	}
	
	public void drawOne () {
		if (currentPosition < 51) {		//standard case
			currentPosition++;
			currCard.setIcon(new ImageIcon("Cards\\" + deck[currentPosition] + ".jpg"));
			nextCard.setText(Integer.toString(51 - currentPosition) + " >");
			prevCard.setText("< " + Integer.toString(currentPosition + 1));
			printCard();
		}
		else {			//drawing one while on last card (deck[51])
			seenDeck = true;
			currentPosition = -1;
			currCard.setIcon(new ImageIcon("Cards\\last.jpg"));
			nextCard.setText("52 >");
			prevCard.setText("< 0");
		}
	}
	
	public void shuffleDeck() {
		currentPosition = -1;			//reset global variables and JFrame UI
		currCard.setIcon(new ImageIcon("Cards\\face.jpg"));
		seenDeck = false;
		nextCard.setText("52 >");
		prevCard.setText("< 0");
		
		Random pick = new Random();
		for (int s = 0; s < 4; s++) {
			int selection = pick.nextInt(52);
			for (int b = 51; b > 0; b--) {
				swapCards(selection, b);		//sends randomized card to back of deck
				selection = pick.nextInt(b);
			}
		}
	}
	
	public void resetDeck () {			//fills deck in order
		seenDeck = false;
		for (int i = 0; i < 52; i++) {
			deck[i] = "";
		}
		fillDeck();
		currentPosition = -1;
		currCard.setIcon(new ImageIcon("Cards\\face.jpg"));
		nextCard.setText("52 >");
		prevCard.setText("< 0");
	}
	
	public void initializeFresh () {		//initializes new deck in order, despite changes initiated from terminal**
		for (int i = 0; i < 52; i++) {
			deck[i] = "";
		}
		fillDeck();
		currentPosition = -1;
		initializeFrame();
	}
	
	public void initializeFrame () {		//launches deck with current position
		JPanel cardPanel = new JPanel();
		JFrame cardFrame = new JFrame();
		cardFrame.setLayout(null);
		
		cardFrame.setBounds(0,0,286,489);	//roughly 3:5 (3:4 card panel, 3:1 control panel)
		cardFrame.setResizable(false);
		cardFrame.setTitle("Deck o' Cards");
		cardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel controlPanel = new JPanel();
		JButton shuffleButton = new JButton("SHUFFLE");
		JButton resetButton = new JButton("RESET");
		
		resetButton.addActionListener(e -> resetDeck());
		shuffleButton.addActionListener(e -> shuffleDeck());
		
		nextCard.setText(Integer.toString(51 - currentPosition) + " >");		//**
		nextCard.addActionListener(e -> drawOne());
		prevCard.setText("< " + Integer.toString(currentPosition + 1));
		prevCard.addActionListener(e -> goBack());
		
		if (currentPosition == -1) {
			currCard.setIcon(new ImageIcon("Cards\\face.jpg"));			//face is not part of deck[]
		} else {
			currCard.setIcon(new ImageIcon("Cards\\" + deck[currentPosition] + ".jpg"));
		}
		currCard.setBounds(1,1,270,360);
		currCard.addActionListener(e -> drawOne());
		
		controlPanel.setLayout(new BorderLayout());
		controlPanel.setBackground(Color.lightGray);
		controlPanel.setBounds(1,361,270,90);
		controlPanel.add(prevCard, BorderLayout.WEST);
		controlPanel.add(shuffleButton, BorderLayout.CENTER);
		controlPanel.add(nextCard, BorderLayout.EAST);
		controlPanel.add(resetButton, BorderLayout.SOUTH);
		
		cardPanel.setLayout(null);
		cardPanel.setBackground(Color.gray);
		cardPanel.setBounds(0, 0, 270, 360);
		cardPanel.add(currCard);

		cardFrame.add(controlPanel);
		cardFrame.add(cardPanel);
		cardFrame.setVisible(true);

		Image icon = Toolkit.getDefaultToolkit().getImage("Cards\\icon.jpg");
		cardFrame.setIconImage(icon);
	}
	
	// helper methods ______________________________________________________________________________________________________ **

	private void swapCards (int a, int b) {
		String temp = deck[b];
		deck[b] = deck[a];
		deck[a] = temp;
	}
	
	private String suitSwitch (int index) {		//used in fillDeck()
		String suit = "N";
		switch (index) {
			case 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12: suit = "C"; break;
			case 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25: suit = "D"; break;
			case 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38: suit = "H"; break;
			case 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51: suit = "S"; break;
		}
		return suit;
	}
	
	private String rankSwitch (int index) {		//used in fillDeck()
		String rank = "0";
		switch (index) {
			case 0, 13, 26, 39: rank = "A"; break;
			case 1, 14, 27, 40: rank = "2"; break;
			case 2, 15, 28, 41: rank = "3"; break;
			case 3, 16, 29, 42: rank = "4"; break;
			case 4, 17, 30, 43: rank = "5"; break;
			case 5, 18, 31, 44: rank = "6"; break;
			case 6, 19, 32, 45: rank = "7"; break;
			case 7, 20, 33, 46: rank = "8"; break;
			case 8, 21, 34, 47: rank = "9"; break;
			case 9, 22, 35, 48: rank = "0"; break;
			case 10, 23, 36, 49: rank = "J"; break;
			case 11, 24, 37, 50: rank = "Q"; break;
			case 12, 25, 38, 51: rank = "K"; break;
		}
		return rank;
	}
	
	private void printCard () {			//to display cards in terminal more nicely
		String card = deck[currentPosition];
		char rank = card.charAt(0);
		char suit = card.charAt(1);
		
		if (Character.compare(rank,'2') == 0) {
			System.out.print("Two of ");
		} else if (Character.compare(rank,'3') == 0) {
			System.out.print("Three of ");
		} else if (Character.compare(rank,'4') == 0) {
			System.out.print("Four of ");
		} else if (Character.compare(rank,'5') == 0) {
			System.out.print("Five of ");
		} else if (Character.compare(rank,'6') == 0) {
			System.out.print("Six of ");
		} else if (Character.compare(rank,'7') == 0) {
			System.out.print("Seven of ");
		} else if (Character.compare(rank,'8') == 0) {
			System.out.print("Eight of ");
		} else if (Character.compare(rank,'9') == 0) {
			System.out.print("Nine of ");
		} else if (Character.compare(rank,'0') == 0) {
			System.out.print("Ten of ");
		} else if (Character.compare(rank,'J') == 0) {
			System.out.print("Jack of ");
		} else if (Character.compare(rank,'Q') == 0) {
			System.out.print("Queen of ");
		} else if (Character.compare(rank,'K') == 0) {
			System.out.print("King of ");
		} else {
			System.out.print("Ace of ");
		}
		
		if (Character.compare(suit,'C') == 0) {
			System.out.println("Clubs");
		} else if (Character.compare(suit,'D') == 0) {
			System.out.println("Diamonds");
		} else if (Character.compare(suit,'H') == 0) {
			System.out.println("Hearts");
		} else {
			System.out.println("Spades");
		}
	}
}