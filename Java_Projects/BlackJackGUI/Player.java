package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


import java.io.File;

import javafx.stage.FileChooser;

// Creation of the player class
public class Player {
	// Variable declaration
	ArrayList<Card> hand = new ArrayList<Card>();
	Deck playerDeck = new Deck();
	
	// Default Constructor
	Player() {
		
	}
	
	// Return hand of player method
	ArrayList<Card> getHand() {
		return hand;
	}
	
	// Return value of hand of player method
	int valueOfHand() {
		int totalValue = 0;
		int aceCount = 0;
		
		for (int i = 0; i < hand.size(); i++) {
			totalValue += hand.get(i).valueOf();
			if (hand.get(i).getFace().equals("A")) {
				aceCount++;
			}
			while (totalValue > 21 && aceCount != 0) {
				aceCount--;
				totalValue -= 10;
			}
		}
		
		return totalValue;
	}
	
	// Method to Remove all cards from player's hand
	void clearHand() {
		hand.clear();
	}
	
	// Method to Check if the dealer should stand
	boolean stand(int otherPlayersValue) {
		boolean toStand = false;
		int myValue = valueOfHand();
		
		
		if (myValue > otherPlayersValue || myValue > 18) {
			toStand = true;
		}
		
		if (myValue > 16) {
			int randNum = (int) (Math.random() * 100) % 2;
			if (randNum == 0) {
				toStand = true;
			}
		}
		
		
		return toStand;
	}
	
	// Method to add a card to the hand
	void hit() {
		Card cardToAdd;
		cardToAdd = playerDeck.dealCard();
		if (cardToAdd.getFace().equals("A")) {
			
		}
		hand.add(cardToAdd);
	}
	
	// Method to check if the hand has bust
	boolean bust() {
		boolean bust =  false;
		
		if (valueOfHand() > 21) {
			bust = true;
		}
		
		return bust;
	}
	
	// Method to return deck
	Deck getDeck() {
		return playerDeck;
	}
}

// Class for a deck of cards
class Deck {
	
	// Variable initialization
	static ArrayList<Card> cards = new ArrayList<Card>();
	static int deckSize = 0;
	
	// Default Constructor
	Deck() {
		try {
			Reset();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Method to reset deck with every card
	void Reset() throws FileNotFoundException {
		
		cards.clear();
		
		deckSize = 0;
		
		for (int i = 0; i < 4; i++) {
			for (int j = 2; j < 11; j++) {
				Card card = new Card(j + "");
				cards.add(card);
				deckSize++;
			}
			Card cardA = new Card("A");
			Card cardK = new Card("K");
			Card cardQ = new Card("Q");
			Card cardJ = new Card("J");
			cards.add(cardA);
			cards.add(cardK);
			cards.add(cardQ);
			cards.add(cardJ);
			
			deckSize += 4;
		}
	}
	
	// Method to Deal a card to hand
	Card dealCard() {
		int randNum = (int) (Math.random() * 100) % deckSize;
		Card cardToRemove = cards.get(randNum);
		cards.remove(randNum);
		deckSize--;
		
		return cardToRemove;
	}
}


// Class for each card
class Card {
	
	// Variable initializtion
	static String[] FACES;
	static int height = 130;
	String face;
	ImageView cardImage;
	
	// Default Constructor
	Card(String faceInput) throws FileNotFoundException {
		face = faceInput;
		
		//creating the image object
	    InputStream stream = new FileInputStream("/Users/suli/eclipse-workspace/BlackJackTableGUI/cards/" + faceInput + ".png");
	    Image image = new Image(stream);
	    //Creating the image view
	    ImageView imageView = new ImageView();
	    
	    imageView.setImage(image);
	    imageView.setFitHeight(height);
	    imageView.setPreserveRatio(true);
	    cardImage = imageView;
	}
	
	// Method to return the face of a card
	public String getFace() {
		return face;
	}
	
	// Method to return the image of a card
	public ImageView getImage() {
		return cardImage;
	}
	
	// Method to return the value of the card
	public int valueOf() {
		int value;
		
		if (face.equals("A")) {
			value = 11;
		}
		else if (face.equals("K") || face.equals("Q") || face.equals("J")) {
			value = 10;
		}
		else {
			value = Integer.valueOf(face);
		}
		
		return value;
	}
}
