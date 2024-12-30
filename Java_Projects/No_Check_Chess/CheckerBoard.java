package application;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class CheckerBoard extends Pane {
	
	static Color boxLightColor = Color.rgb(42, 54, 53);
	static Color boxDarkColor = Color.rgb(89, 109, 110);
	static int boxSize = 50;
	GridPane board = new GridPane();
	
	Rectangle[] rectangles = new Rectangle[64];
	Pieces piece = new Pieces();
	
	
	CheckerBoard() {
		for (int i = 0; i < 64; i++) {
			final int num = i;
			rectangles[i] = new Rectangle();
			rectangles[i].setHeight(boxSize);
			rectangles[i].setWidth(boxSize);
			rectangles[i].setUserData(i);
			
			
			if (((i % 2) + (i / 8)) % 2 == 0 ) {
				rectangles[i].setFill(boxLightColor);
			}
			else {
				rectangles[i].setFill(boxDarkColor);
			}
			
			board.add(rectangles[i], i % 8, i / 8);
			rectangles[i].setVisible(true);
		}
		
		getChildren().add(board);
		getChildren().add(piece.piecePlacement);
		
	}
	
	void setOGColors() {
		for (int i = 0; i < 64; i++) {
			if (((i % 2) + (i / 8)) % 2 == 0 ) {
				rectangles[i].setFill(boxLightColor);
			}
			else {
				rectangles[i].setFill(boxDarkColor);
			}
		}
	}
	
}
