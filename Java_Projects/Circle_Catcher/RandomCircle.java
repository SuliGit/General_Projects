package application;

import application.CircleCatcher;

import javafx.util.Duration;
import java.util.Random;

import javafx.animation.Timeline;
import javafx.animation.TranslateTransition; 
import javafx.geometry.Point2D;
import javafx.scene.effect.Light.Point;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class RandomCircle extends Circle {
	//initializing variables
	final double MAX_RADIUS = 15, MIN_RADIUS = 10, MAX_X_SPEED = 5, MAX_Y_SPEED = 5;
	
	double windowWidth, windowHeight;
	double radius;
	Random rand = new Random();
	int circleCount;
	Boolean clicked = false;
	
	//Creating a translate transition    
    TranslateTransition trans = new TranslateTransition();

	RandomCircle(double width, double height) {
	    
		
		// Inputting window width and height
		windowWidth = width;
		windowHeight = height;
		
		// Setting a random radius
		setRadius();
		
		// Setting a random color
		setColor();

		// Setting movement
		setTransitionSequence();
		
		// On click action
		setOnMouseClicked(this::handleMouseClick);
	}
	
	public void setRadius() {
		// Setting a random radius between 10-25
		super.setRadius(rand.nextDouble(MAX_RADIUS) + MIN_RADIUS);
		radius = super.getRadius();
	}
	
	public void setColor() {
		// Setting random color
		Color c = new Color(rand.nextDouble(1.0), rand.nextDouble(1.0), rand.nextDouble(1.0), 1.0);
		super.setFill(c);
	}
	
	// Method to create a path to move the circle
	public void setTransitionSequence() {
		// Random point on the window within the window size
		Point2D startPoint = new Point2D(rand.nextDouble(windowWidth - 2 * radius) + radius, rand.nextDouble(windowHeight - 2 * radius) + radius);
		Point2D endPoint = new Point2D(rand.nextDouble(windowWidth - 2 * radius) + radius, rand.nextDouble(windowHeight - 2 * radius) + radius);
		
		
		trans.setDuration(Duration.millis(rand.nextDouble(300) + 1700));
		
		// Setting the speed of transition
		trans.setByX(rand.nextDouble(MAX_X_SPEED));
		trans.setByY(rand.nextDouble(MAX_Y_SPEED));
		
		// Setting start point
		trans.setFromX(startPoint.getX());
		trans.setFromY(startPoint.getY());
		
		// Setting end point
		trans.setToX(endPoint.getX());
		trans.setToY(endPoint.getY());
		
		//Setting the node for the transition 
	    trans.setNode(this);
	      
	    //Setting the cycle count for the transition 
	    trans.setCycleCount(1); 
	    trans.setOnFinished(e -> {
	    	setTransitionSequence(endPoint);
	    });
	      
	    //Setting auto reverse value to true 
	    trans.setAutoReverse(true);
	    trans.play();
	}
	
	// Overloaded method to create a new path to move the circle
	public void setTransitionSequence(Point2D prevPoint) {
		// Random point on the window within the window size
		Point2D startPoint = prevPoint; 
		Point2D endPoint = new Point2D(rand.nextDouble(windowWidth - 2 * radius) + radius, rand.nextDouble(windowHeight - 2 * radius) + radius);
		
		
		trans.setDuration(Duration.millis(rand.nextDouble(300) + 1700));
		
		// Setting the speed of transition
		trans.setByX(rand.nextDouble(MAX_X_SPEED));
		trans.setByY(rand.nextDouble(MAX_Y_SPEED));
		
		// Setting start point
		trans.setFromX(startPoint.getX());
		trans.setFromY(startPoint.getY());
		
		// Setting end point
		trans.setToX(endPoint.getX());
		trans.setToY(endPoint.getY());
		
		//Setting the node for the transition 
	    trans.setNode(this);
	      
	    //Setting the cycle count for the transition 
	    trans.setCycleCount(1); 
	    trans.setOnFinished(e -> {
	    	setTransitionSequence(endPoint);
	    });
	      
	    //Setting auto reverse value to true 
	    trans.setAutoReverse(true);
	    trans.play();
	}
	
    private void handleMouseClick(MouseEvent event) {
        // Check if the circle was clicked
        if (event.getTarget() == this && !clicked) {
            
            double xPos, yPos;

            xPos = 300 - trans.getNode().getTranslateX();
            yPos = 200 - trans.getNode().getTranslateY();
            
            // Set the circle's position to the center coordinates
            setCenterX(xPos);
            setCenterY(yPos);

            // Stop the transition if it's currently playing
            trans.stop();
            CircleCatcher.circleCount--;
            CircleCatcher.clicked++;
            clicked = true;
            
        }
    }
    
    public Boolean endGame(int num) {
    	double xPos, yPos;
    	if (!clicked) {
    	for (int i = 0; i < 3; i++) {
    		for (int j = 0; j < 7; j++) {
    			
    			if ((j + i * 7) == num) {
        			xPos = (430 + 70 * i) - trans.getFromX() - radius;
        			yPos = (25 + 55 * j) - trans.getFromY() + radius;
        			
        			setCenterX(xPos);
                    setCenterY(yPos);
        			trans.stop();
    			}

    		}
    	}
    	return false;
    	}
    	else {
    		return clicked;
    	}
    }
}
