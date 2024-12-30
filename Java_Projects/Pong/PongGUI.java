package application;

import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.*;
import javafx.scene.Scene;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.ImageCursor;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.input.*;

public class PongGUI extends Application {

	final int SCREEN_HEIGHT = 500;
	final int SCREEN_WIDTH = 500;
	static Label pressSpace = new Label("Press Space to Start");
	double loopTime = 0.001;
	
  
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
    	Pane pane = new Pane();
    	
    	Rectangle background = new Rectangle();
    	Rectangle rightWall = new Rectangle();
    	Rectangle leftWall = new Rectangle();
    	
    	pressSpace.setViewOrder(0);
    	pressSpace.setLayoutX(150);
    	pressSpace.setLayoutY(0);
    	pressSpace.setTextFill(Color.WHITE);
    	pressSpace.setFont(Font.font(30));
    	
    	pane.setMinHeight(SCREEN_HEIGHT);
    	pane.setMinWidth(SCREEN_WIDTH);
    	
    	background.setFill(Color.BLACK);
    	background.setHeight(SCREEN_HEIGHT);
    	background.setWidth(SCREEN_WIDTH + 75);
    	background.setViewOrder(4);
    	
    	rightWall.setFill(Color.WHITE);
    	rightWall.setX(500);
    	rightWall.setHeight(SCREEN_HEIGHT);
    	rightWall.setWidth(25);
    	
    	leftWall.setFill(Color.WHITE);
    	leftWall.setX(50);
    	leftWall.setHeight(SCREEN_HEIGHT);
    	leftWall.setWidth(25);
    	
    	Walls rackets = new Walls();
    	rackets.setUpWalls();
    	
    	Ball ball = new Ball();
    	ball.setUpBall();
    	
    	pane.getChildren().addAll(pressSpace, rightWall, leftWall, background,
    			rackets.getLeftWall(), rackets.getRightWall(), ball.getBall(), ball.getScore());
        
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		
		
		// Keyboard Listeners
	    scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
		      if (ball.lost && key.getCode() == KeyCode.SPACE) {
			  	changeStart(ball);
			  	ball.reset();
			  	
		    	// Setting up animation of ball
		  		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(loopTime), e -> ball.move(rackets.getLeftY(), rackets.getRightY())));
		  	    timeline.setCycleCount(timeline.INDEFINITE);
		  	    timeline.playFromStart();
		  	    
		      }
		});
		
		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
		      if(key.getCode()==KeyCode.W) {
		    	  rackets.moveLeftUp();
		      }
		      
		      else if (key.getCode()==KeyCode.S) {
		    	  rackets.moveLeftDown();
		      }
		});
		
		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
		      if (key.getCode()==KeyCode.UP) {
		    	  rackets.moveRightUp();
		      }

		      else if (key.getCode()==KeyCode.DOWN) {
		    	  rackets.moveRightDown();
		      }
		});
		
	}

	public void changeStart(Ball ball) {
		ball.changeLost();
		pressSpace.setText("");
		loopTime *= 10;
	}
	
    
    
    
}
