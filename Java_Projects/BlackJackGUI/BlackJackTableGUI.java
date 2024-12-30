package application;

import java.io.FileNotFoundException;
import java.net.URL;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BlackJackTableGUI extends Application {
	
	// String for each label
	String dealerHandText = "Dealer Hand";
	String dealerValueText = "Value: ";
	String playerHandText = "Player Hand";
	String playerValueText = "Value: ";
	String playerScoreText = "Player Score: ";
	String dealerScoreText = "Dealer Score: ";
	
	// Score for players and dealers
	int playerNumScore = 0;
	int dealerNumScore = 0;
	
	// Labels for each section of the GUI
	Label dealerHand = new Label(dealerHandText);
	Label dealerValue = new Label(dealerValueText);
	Label playerHand = new Label(playerHandText);
	Label playerValue = new Label (playerValueText);
	
	Label playerScore = new Label(playerScoreText + playerNumScore);
	Label dealerScore = new Label(dealerScoreText + dealerNumScore);
	
	Label outcome = new Label("");
	
	// Three buttons
	Button start = new Button("Start");
	Button hit = new Button ("Hit");
	Button stand = new Button ("Stand");
	
	// Player and dealer objects
	Player dealer = new Player();
	Player player = new Player();
	
	// Card sections
	HBox dealerCards = new HBox();
	HBox playerCards = new HBox();

	public void start(Stage primaryStage) throws FileNotFoundException {
		
		// Gridpane to have all sections
		GridPane grid = new GridPane();
		
		// Creating variables for accessing file paths
		URL url;
		String css;
		
		// Styling all labels
		dealerHand.getStyleClass().add("labelTitle");
		dealerValue.getStyleClass().add("labelTitle");
		playerHand.getStyleClass().add("labelTitle");
		playerValue.getStyleClass().add("labelTitle");
		playerScore.getStyleClass().add("labelTitle");
		dealerScore.getStyleClass().add("labelTitle");
		outcome.getStyleClass().add("labelTitle");
		
		// HBoxes to group each section
		HBox dealerLabels = new HBox(dealerHand, dealerValue);
		dealerLabels.setSpacing(100);
		dealerLabels.setAlignment(Pos.CENTER);
		
		HBox playerLabels = new HBox(playerHand, playerValue);
		playerLabels.setSpacing(100);
		playerLabels.setAlignment(Pos.CENTER);
		
		// Setting sizes of cards
		dealerCards.setMinWidth(600);
		dealerCards.setMinHeight(150);
		playerCards.setMinWidth(600);
		playerCards.setMinHeight(150);
		
		// Adding the buttons to the same section
		HBox buttonsBox = new HBox(start, hit, stand);
		buttonsBox.setSpacing(10);
		buttonsBox.setAlignment(Pos.CENTER);
		
		// Adding scores and labels for scores
		GridPane scores = new GridPane();
		scores.add(playerScore, 0, 0);
		scores.add(dealerScore, 0, 1);
		scores.add(outcome, 1, 0);
		scores.setHgap(320);
		
		
		// VBox for all parts of the GUI
		VBox allBox = new VBox(dealerLabels, dealerCards, playerLabels, playerCards, buttonsBox, scores);
		allBox.setSpacing(10);
		allBox.setPadding(new Insets(10));
		
		// Disabling buttons to start game
		hit.setDisable(true);
		stand.setDisable(true);
		
		// Start button action
		start.setOnAction(e -> {
			startGame();
		});
		
		// Hit button action
		hit.setOnAction(e -> {
			player.hit();
			updateHand(player, playerCards, playerValue);
			if (player.valueOfHand() > 21 ) {
				endGame();
			}
		});
		
		// Stand button action
		stand.setOnAction(e -> {
			dealerTurn();
		});
		
		// Background fill
		BackgroundFill background_fill = new BackgroundFill(Color.FORESTGREEN,  
                CornerRadii.EMPTY, Insets.EMPTY); 

		// create Background 
		Background background = new Background(background_fill);
		
		allBox.setBackground(background);
		
		Scene scene = new Scene(allBox);
		
		// Style sheets for label
		url = this.getClass().getResource("application.css");
		css = url.toExternalForm();
		
		scene.getStylesheets().add(css);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}
	
	// Method to start the blackjack game when button is pressed
	public void startGame() {
		try {
			player.getDeck().Reset();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		player.clearHand();
		dealer.clearHand();
		
		dealerValue.setText(dealerValueText);
		playerValue.setText(playerValueText);
		
		dealerCards.getChildren().clear();
		playerCards.getChildren().clear();
		
		dealer.hit();
		updateHand(dealer, dealerCards, dealerValue);
		
		start.setDisable(true);
		hit.setDisable(false);
		stand.setDisable(false);
		
		outcome.setText("");
	}
	
	// Method to update the hand of each player
	public void updateHand(Player p, HBox box, Label handValue) {
		box.getChildren().add((p.getHand().get(p.getHand().size()-1)).getImage());
		handValue.setText(playerValueText + p.valueOfHand());
	}
	
	// Method to end the game when necessary
	public void endGame() {
		if (player.valueOfHand() > 21) {
			outcome.setText("Dealer Wins!");
			dealerNumScore++;
			dealerScore.setText(dealerScoreText + dealerNumScore);
		}
		else if (dealer.valueOfHand() > 21 ) {
			outcome.setText("Player Wins!");
			playerNumScore++;
			playerScore.setText(playerScoreText + playerNumScore);
		}
		else if(dealer.valueOfHand() > player.valueOfHand()) {
			outcome.setText("Dealer Wins!");
			dealerNumScore++;
			dealerScore.setText(dealerScoreText + dealerNumScore);
		}
		else if (dealer.valueOfHand() < player.valueOfHand()) {
			outcome.setText("Player Wins!");
			playerNumScore++;
			playerScore.setText(playerScoreText + playerNumScore);
		}
		else {
			outcome.setText("Push! No one Wins.");
		}
		
		start.setDisable(false);
		hit.setDisable(true);
		stand.setDisable(true);
		
	}
	
	// Method to let the dealer play when the player has stand
	public void dealerTurn() {
		while (!dealer.stand(player.valueOfHand())) {
			dealer.hit();
			updateHand(dealer, dealerCards, dealerValue);
		}
		endGame();
	}
 
}
