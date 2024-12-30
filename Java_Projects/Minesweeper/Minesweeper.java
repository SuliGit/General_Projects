package application;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener;
import javafx.collections.FXCollections;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;

import javafx.stage.Stage;

public class Minesweeper extends Application {
	final int boxesPerRow = 9;
	final int numMines = 12;
	final int totalNonMines = boxesPerRow * boxesPerRow - numMines;
	int count = totalNonMines;
	Boolean win = true;
	Boolean gameEnd = false;
	
	MineGrid mineGrid = new MineGrid();
	Mine[][] mineInfo = new Mine[boxesPerRow][boxesPerRow];
	
	Button reset = new Button("Reset");
	Button exit = new Button("Exit");
	
	Label title = new Label("Minesweeper\nTotal Mines: " + numMines);

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// Set all mines for mineInfo
		setMines();
		
		title.setStyle("-fx-font-family: Copperplate; -fx-font-size: 24;");
		
		// Hiding Buttons till game is over
		reset.setVisible(false);
		exit.setVisible(false);
		
		reset.setOnAction(e -> {
			reset.setVisible(false);
			exit.setVisible(false);
			
			for (int i = 0; i < boxesPerRow; i++) {
				for (int j = 0; j < boxesPerRow; j++) {
					mineInfo[i][j] = null;
					mineGrid.mineLocations[i][j].setText("");
					title.setText("Minesweeper\nTotal Mines: " + numMines);
				}
			}
			setMines();
		});
		
		exit.setOnAction(e -> {
			primaryStage.close();
		});
		
		// Creating new Scene
		HBox buttonBox = new HBox(reset, exit);
		buttonBox.setSpacing(5);
		buttonBox.setAlignment(Pos.BOTTOM_CENTER);
		buttonBox.setPadding(new Insets(5));
		
		VBox screenBox = new VBox(title, mineGrid, buttonBox);
		screenBox.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(screenBox);
		
		// Setting and Showing Stage
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public class MineGrid extends GridPane {
		// Creating Buttons for Mine Boxes
		Button[][] mineLocations = new Button[boxesPerRow][boxesPerRow]; {
			
			// Initializing each button and adding to gridPane
			for (int i = 0; i < boxesPerRow; i++) {
				for (int j = 0; j < boxesPerRow; j++) {
					mineLocations[i][j] = new Button();
					mineLocations[i][j].setId("button" + (i+j));
					mineLocations[i][j].setMinHeight(30);
					mineLocations[i][j].setMinWidth(30);
					add(mineLocations[i][j], i, j);
					
					final int subI = i;
					final int subJ = j;
					mineLocations[subI][subJ].setOnAction(e -> {
						mineLocations[subI][subJ].setDisable(true);
							
						// Hit a mine, game over
						if (mineInfo[subI][subJ].getIfMine()) {
							mineLocations[subI][subJ].setStyle("-fx-background-color: #DF4425; ");
							fireAllButtons();
							win = false;
							gameEnd = true;
							title.setText("You Lose HaA");
						}
						
						// Not Hit A Mine
						else if (!mineInfo[subI][subJ].getIfMine()) {
							mineLocations[subI][subJ].setStyle("-fx-background-color: #C0EF94; ");
							if (mineInfo[subI][subJ].getNumNearby() != 0) {
								mineLocations[subI][subJ].setText("" + mineInfo[subI][subJ].getNumNearby());
							}
							else {
								fireNearbyButtons(subI, subJ);
							}
							
							// Checking if won
							count--;
							if (count == 0 && win) {
								System.out.println("You Win!");
								gameEnd = true;
								title.setText("Well Done! You Win.");
							}
							
							if (gameEnd) {
								reset.setVisible(true);
								exit.setVisible(true);
							}
						}
					});
				}
			}
			
			setHgap(5);
			setVgap(5);
			setPadding(new Insets(10, 40, 5, 40));
		}
		
		void fireNearbyButtons(int i, int j) {
			// top row
			if (i == 0) {
				// Corner button
				if (j == 0) {
					mineLocations[i][j+1].fire();
					mineLocations[i+1][j].fire();
					mineLocations[i+1][j+1].fire();
				}
				
				// Other Corner Button
				else if (j == boxesPerRow - 1) {
					mineLocations[i][j-1].fire();
					mineLocations[i+1][j].fire();
					mineLocations[i+1][j-1].fire();
				}
				
				// Edge Buttons
				else {
					mineLocations[i][j-1].fire();
					mineLocations[i][j+1].fire();
					
					mineLocations[i+1][j-1].fire();
					mineLocations[i+1][j].fire();
					mineLocations[i+1][j+1].fire();
				}
			}
			// bottom row
			else if (i == boxesPerRow - 1) {
				// Corner button
				if (j == 0) {
					mineLocations[i][j+1].fire();
					mineLocations[i-1][j].fire();
					mineLocations[i-1][j+1].fire();
				}
				
				// Other Corner Button
				else if (j == boxesPerRow - 1) {
					mineLocations[i][j-1].fire();
					mineLocations[i-1][j].fire();
					mineLocations[i-1][j-1].fire();
				}
				
				// Edge Buttons
				else {
					mineLocations[i][j-1].fire();
					mineLocations[i][j+1].fire();
					
					mineLocations[i-1][j-1].fire();
					mineLocations[i-1][j].fire();
					mineLocations[i-1][j+1].fire();
				}
			}
			
			//Edge Rows
			else {
				// Edge button
				if (j == 0) {
					mineLocations[i+1][j].fire();
					mineLocations[i-1][j].fire();
					
					mineLocations[i+1][j+1].fire();
					mineLocations[i][j+1].fire();
					mineLocations[i-1][j+1].fire();
				}
				
				// Other Edge Button
				else if (j == boxesPerRow - 1) {
					mineLocations[i+1][j].fire();
					mineLocations[i-1][j].fire();
					
					mineLocations[i+1][j-1].fire();
					mineLocations[i][j-1].fire();
					mineLocations[i-1][j-1].fire();
				}
				
				// Middle Buttons
				else {
					mineLocations[i+1][j-1].fire();
					mineLocations[i+1][j].fire();
					mineLocations[i+1][j+1].fire();
					
					mineLocations[i][j-1].fire();
					mineLocations[i][j+1].fire();
					
					mineLocations[i-1][j-1].fire();
					mineLocations[i-1][j].fire();
					mineLocations[i-1][j+1].fire();
				}
			}
		}
		
		void fireAllButtons() {
			for (int i = 0; i < boxesPerRow; i++) {
				for (int j = 0; j < boxesPerRow; j++) {
					mineLocations[i][j].fire();
				}
			}
		}
		
		
		
	}

	
	// Class for Mines
	public class Mine {
		Boolean isMine;
		int minesNearby;
		
		// Default Constructor
		Mine(Boolean bool, int num) {
			isMine = bool;
			minesNearby = num;
		}
		
		// Sets if box is mine or not
		void setIfMine(Boolean bool) {
			isMine = bool;
		}
		
		// Sets number of mines nearby
		void setNumNearby(int num) {
			minesNearby = num;
		}
		
		// Sets if box is mine or not
		Boolean getIfMine() {
			return isMine;
		}
		
		// Sets number of mines nearby
		int getNumNearby() {
			return minesNearby;
		}
		
		void incrementNumMines() {
			minesNearby++;
		}
		
	}
	
	// Sets the mines on the grid
	void setMines() {
		int minesLeft = numMines;
		
		for (int i = 0; i < boxesPerRow; i++) {
			for (int j = 0; j < boxesPerRow; j++) {
				// Creating random number and finding the number of boxes still left
				Random random = new Random();
				int randNum = random.nextInt();
				//System.out.println(randNum);
				int numBoxesLeft = (boxesPerRow - i - 1) * (boxesPerRow) + (boxesPerRow - j);
				
				// Setting num of mines to the grid
				if (numBoxesLeft == minesLeft) {
					mineInfo[i][j] = new Mine(true, 0);
					minesLeft--;
				}
				else if (randNum % 7 == 0 && minesLeft != 0) {
					mineInfo[i][j] = new Mine(true, 0);
					minesLeft--;
				}
				
				
				else {
					mineInfo[i][j] = new Mine(false, 0);
					
				}
				
				//Styling buttons
				if ((i + j) % 2 == 0) {
					mineGrid.mineLocations[i][j].setStyle("-fx-background-color: #B7B4B3; ");
				}
				else {
					mineGrid.mineLocations[i][j].setStyle("-fx-background-color: #D2CFCE; ");
				}
			}
		}
		
		setNumMines();
		
	}
	
	
	// Set the number of mines next to each box
	void setNumMines() {
		
		for (int i = 0; i < boxesPerRow; i++) {
			for (int j = 0; j < boxesPerRow; j++) {
				
				
				// First Row
				if(i == 0) {
					
					// Corner Box
					if (j == 0) {
						 if (mineInfo[i][j+1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 if (mineInfo[i+1][j].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 if (mineInfo[i+1][j+1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 
					}
					
					// Corner Box
					else if (j == boxesPerRow - 1) {
						 if (mineInfo[i][j-1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 if (mineInfo[i+1][j].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 if (mineInfo[i+1][j-1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
					}
					
					// Middle Boxes
					else {
						 if (mineInfo[i][j-1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 if (mineInfo[i][j+1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 if (mineInfo[i+1][j-1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 if (mineInfo[i+1][j].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 if (mineInfo[i+1][j+1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
					}
				}
				
				
				// Last Row
				else if (i == boxesPerRow - 1) {
					// Corner Box
					if (j == 0) {
						 if (mineInfo[i][j+1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 if (mineInfo[i-1][j].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 if (mineInfo[i-1][j+1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 
					}
					
					// Corner Box
					else if (j == boxesPerRow - 1) {
						 if (mineInfo[i][j-1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 if (mineInfo[i-1][j].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 if (mineInfo[i-1][j-1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
					}
					
					// Middle Boxes
					else {
						 if (mineInfo[i][j-1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 if (mineInfo[i][j+1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 if (mineInfo[i-1][j-1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 if (mineInfo[i-1][j].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 if (mineInfo[i-1][j+1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
					}
				}
				
				// Middle Rows
				else {
					// Edge Box
					if (j == 0) {
						// Checking Above and Below the box first
						 if (mineInfo[i-1][j].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 if (mineInfo[i+1][j].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 } 
						 
						 // Checking all boxes to the right
						 if (mineInfo[i-1][j+1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 if (mineInfo[i][j+1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 if (mineInfo[i+1][j+1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 
					}
					
					// Corner Box
					else if (j == boxesPerRow - 1) {
						// Checking Above and Below the box first
						 if (mineInfo[i-1][j].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 if (mineInfo[i+1][j].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 } 
						 
						 // Checking all boxes to the left
						 if (mineInfo[i-1][j-1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 if (mineInfo[i][j-1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 if (mineInfo[i+1][j-1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
					}
					
					// Middle Boxes
					else {
						// All boxes above
						 if (mineInfo[i-1][j-1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 if (mineInfo[i-1][j].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 if (mineInfo[i-1][j+1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 
						 // Boxes to right and left
						 if (mineInfo[i][j-1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 if (mineInfo[i][j+1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 
						 // All boxes below
						 if (mineInfo[i+1][j-1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 if (mineInfo[i+1][j].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 if (mineInfo[i+1][j+1].getIfMine()) {
							 mineInfo[i][j].incrementNumMines();
						 }
						 

					}
				}
				
			}
		}
		
	}
	

}




