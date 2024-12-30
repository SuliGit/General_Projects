package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class ChessGUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		VBox labelBox = new VBox();
		CheckerBoard board = new CheckerBoard();
		board.setOGColors();
		
		labelBox.setAlignment(Pos.CENTER);
		
		labelBox.getChildren().addAll(board.piece.chessLabel, board.piece.labelTurn, board);
		
		Scene scene = new Scene(labelBox);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	

}
