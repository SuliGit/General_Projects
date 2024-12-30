package application;

import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class Ball {
	Circle ball;
	double radius;
	double xVel, yVel;
	double startX, startY;
	Boolean lost;
	Label score;
	int scoreNum;
	PongGUI pong;
	
	Timeline time = new Timeline();
	
	
	
	Ball () {
		ball = new Circle();
		radius = 5;
		xVel = 0.5;
		yVel = 0.5;
		startY = 250;
		startX = 287.5;
		
		lost = true;
		
		score = new Label("0");
		scoreNum = 0;
		
		pong = new PongGUI();
	}
	
	public void setUpBall() {
		ball.setRadius(radius);
		ball.setFill(Color.WHITE);
		ball.setCenterX(startX);
		ball.setCenterY(startY);
		ball.setViewOrder(0);
		
		score.setViewOrder(0);
		score.setLayoutX(550);
		score.setLayoutY(0);
		score.setTextFill(Color.WHITE);
		score.setFont(Font.font(30));
	}
	
	public void move(double leftY, double rightY) {
			ball.setCenterX(ball.getCenterX() + xVel);
			ball.setCenterY(ball.getCenterY() + yVel);
			
			if (ball.getCenterX() < 75 || ball.getCenterX() > 500) {
				xVel = 0;
				yVel = 0;
				lost = true;
				pong.pressSpace.setText("Press Space to Restart");
			}
			
			if (ball.getCenterX() > 485 && ball.getCenterX() < 495) {
				if (ball.getCenterY() > rightY && ball.getCenterY() < rightY + 70) {
					xVel = xVel * -1;
					scoreNum++;
					score.setText("" + scoreNum);
				}
			}
			
			if (ball.getCenterX() > 80 && ball.getCenterX() < 90) {
				if (ball.getCenterY() > leftY && ball.getCenterY() < leftY + 70) {
					xVel = xVel * -1;
					scoreNum++;
					score.setText("" + scoreNum);
				}
			}
			
			if (ball.getCenterY() < 5 || ball.getCenterY() > 495) {
				yVel = yVel * -1;
				
			}
	}
	
	public Circle getBall() {
		return ball;
	}
	
	
	public Label getScore() {
		return score;
	}
	
	public void setScore(int num) {
		scoreNum = num;
		score.setText("" + scoreNum);
	}
	
	public void changeLost() {
		if (lost) {
			lost = false;
		}
		else {
			lost = true;
		}
	}
	
	public void reset() {
		ball.setCenterX(startX);
		ball.setCenterY(startY);
		setScore(0);
		
		xVel = 0.5;
		yVel = 0.5;
	}
	
}


