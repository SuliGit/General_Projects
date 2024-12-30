package application;

import javafx.util.Duration;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

public class CircleCatcher extends Application {
	// Initializing variables
	static final int MAX_CIRCLES = 20;
	static final double MAX_WINDOW_HEIGHT = 600, MAX_WINDOW_WIDTH = 400;
	static final double TIMER = 5;
	
	static double rate = 5;
	static int circleCount = 0;
	static int clicked = 0;
	static int totalCircles = 0;
	
	static ArrayList<RandomCircle> circles = new ArrayList<RandomCircle>();
	static Group group = new Group();
	static Text captureText = new Text();
	static Text missedCircles = new Text();
	static Text score = new Text();
	static Timeline timeline;

	@Override
	public void start(Stage primaryStage) {
		
		// Load circles onto the screen function
		circleLoader();
		
		// Setting the text for the screen
		captureText.getStyleClass().add("text-style"); // Add Style Class
        captureText.setLayoutX(5); // Set position
        captureText.setLayoutY(20);
        
        missedCircles.getStyleClass().add("missed-circles-style");
        missedCircles.setLayoutX(400);
        missedCircles.setLayoutY(10);
        
        score.getStyleClass().add("score-style");
        score.setLayoutX(5);
        score.setLayoutY(350);
        
    	group.getChildren().addAll(captureText, missedCircles, score);
		
        Scene scene = new Scene(group, MAX_WINDOW_HEIGHT, MAX_WINDOW_WIDTH);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		primaryStage.setScene(scene);
		primaryStage.show();
		

	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	// Function to load circles
	public static void circleLoader() {
		
		//Creating a circle
		circles.add(new RandomCircle(MAX_WINDOW_HEIGHT, MAX_WINDOW_WIDTH));
		circleCount++;
		
		group.getChildren().add(circles.get(circles.size() - 1));
		
		// Creating a ticking timer for the window to spawn circles at
		timeline = new Timeline(new KeyFrame(
                javafx.util.Duration.seconds(TIMER),
                event -> {
                	
                    // Creating a circle
                    if (circleCount < MAX_CIRCLES) {
                    	circles.add(new RandomCircle(MAX_WINDOW_HEIGHT, MAX_WINDOW_WIDTH));
                    	circleCount++;
                    	totalCircles++;
                    	
                        group.getChildren().add(circles.get(circles.size() - 1));
                        checkScore();
                        
                    }
                 // Game has ended
                    else {
                    	// Update total circles to final score
                    	totalCircles++;
                    	
                    	// Creat the text
                    	captureText.setText("Circles have outnumbered you \n(Reached More than 20 at one time).");
                    	captureText.setVisible(true);
                    	missedCircles.setText("Here are the circles you missed.. \n(You can still click them if you like)");
                    	missedCircles.setVisible(true);
                    	score.setText("Your total score is " + clicked + "/" + totalCircles + 
                    			".\nYou were able to keep up with"
                    			+ " the circles \nuntil they were spawning every " + rate + " seconds!");
                    	score.setVisible(true);
                    	
                    	// Stop timeline and put all circles in the position to be collected
                    	timeline.stop();
                    	clicked = 0;
                    	for (int i = 0; i < circles.size(); i++) {
                    		Boolean wasClicked = circles.get(i).endGame(i - clicked);
                    		if (wasClicked) {
                    			clicked++;
                    		}
                    		
                    		
                    	}
                    }
                }
        ));
		
		// Let the window keep refreshing
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
	}
	
	// Check score to update the rate of the circles that spawn
	public static void checkScore() {
		
		if (clicked >= 3) {
			timeline.setRate(1.6666);
			rate = 3;
		}
		if (clicked >= 5) {
			timeline.setRate(2.5);
			rate = 2;
		}
		if (clicked >= 12) {
			timeline.setRate(5);
			rate = 1;
		}
		if (clicked >= 20) {
			timeline.setRate(6.6666);
			rate = 0.75;
		}
		if (clicked >= 30) {
			timeline.setRate(10);
			rate = 0.5;
		}
		if (clicked >= 40) {
			timeline.setRate(25);
			rate = 0.2;
		
		}
    }
	


}
