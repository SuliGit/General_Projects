package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Walls {
	Rectangle leftWall, rightWall;
	int height, width;
	
	Walls() {
		leftWall = new Rectangle();
		rightWall = new Rectangle();
		height = 70;
		width = 10;
	}
	
	public void setUpWalls() {
		leftWall.setHeight(height);
		leftWall.setWidth(width);
		leftWall.setX(75);
		leftWall.setY(225);
		leftWall.setFill(Color.GREY);
		leftWall.setViewOrder(1);
		
		rightWall.setHeight(height);
		rightWall.setWidth(width);
		rightWall.setX(490);
		rightWall.setY(225);
		rightWall.setFill(Color.GREY);
		rightWall.setViewOrder(1);
	}
	
	public Rectangle getLeftWall() {
		return leftWall;
	}
	
	public Rectangle getRightWall() {
		return rightWall;
	}
	
	public void moveLeftDown() {
		if (leftWall.getY() < 430) {
			leftWall.setY(leftWall.getY() + 5);
		}
		
	}
	
	public void moveLeftUp() {
		if (leftWall.getY() > 0) {
			leftWall.setY(leftWall.getY() - 5);
		}
		
	}
	
	public void moveRightDown() {
		if (rightWall.getY() < 430) {
			rightWall.setY(rightWall.getY() + 5);
		}
		
	}
	
	public void moveRightUp() {
		if (rightWall.getY() > 0) {
			rightWall.setY(rightWall.getY() - 5);
		}
		
	}
	
	public double getLeftY() {
		return leftWall.getY();
	}
	
	public double getRightY() {
		return rightWall.getY();
	}
}
