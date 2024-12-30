package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import javafx.event.Event;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;


public class Pieces {
	
	static int boxSize = 50;
	
	ArrayList<Type> pieces;
	ArrayList<Rectangle> pieceBox;
	ArrayList<Boolean> isClicked;
	ArrayList<Boolean> hasPiece;
	Boolean firstClick;
	Boolean check;
	Rectangle prevClick;
	GridPane piecePlacement;
	Label chessLabel;
	Label labelTurn;
	Boolean whiteTurn;
	
	Pieces() {
		pieces = new ArrayList<Type>(64);	
		pieceBox = new ArrayList<Rectangle>();	
		hasPiece = new ArrayList<Boolean>();
		
		piecePlacement = new GridPane();
		
		chessLabel = new Label("No Check Chess");
		labelTurn = new Label("White's Turn");
		
		chessLabel.setStyle("-fx-font-size: 50px; " +
        "-fx-text-fill: #000000; " +
        "-fx-alignment: center; " +
        "-fx-background-color: rgb(42, 54, 53);");
		
		chessLabel.setMaxWidth(Double.MAX_VALUE);
		
		labelTurn.setStyle("-fx-font-size: 20px; " +
		        "-fx-text-fill: #F0F0F0; " +
		        "-fx-alignment: center; " +
		        "-fx-background-color: rgb(42, 54, 53);");
		labelTurn.setMaxWidth(Double.MAX_VALUE);
		
		whiteTurn = true;
		firstClick = true;
		check = false;
		
		fillBoard();
	}
	
	
	public void fillBoard() {
		for (int i = 0; i < 64; i++) {
			final int num = i;
			Rectangle rect = new Rectangle();
			rect.setHeight(boxSize);
			rect.setWidth(boxSize);
			rect.setUserData(i);
			
			hasPiece.add(false);
			
			rect.setFill(Color.TRANSPARENT);
			
			piecePlacement.add(rect, i % 8, i / 8);
			pieceBox.add(rect);
			
			Type type = null;
			pieces.add(type);
			
			rect.setVisible(true);
			rect.setOnMouseClicked(this::handleClick);
			
			if (i == 0) {
				rect.setFill(createBlackRook1(i));
				hasPiece.set(i, true);
			}
			
			if (i == 1) {
				rect.setFill(createBlackKnight1(i));
				hasPiece.set(i, true);
			}
			
			if (i == 2) {
				rect.setFill(createBlackBishop1(i));
				hasPiece.set(i, true);
			}
			if (i == 3) {
				rect.setFill(createBlackKing(i));
				hasPiece.set(i, true);
			}
			
			if (i == 4) {
				rect.setFill(createBlackQueen(i));
				hasPiece.set(i, true);
			}
			
			if (i == 5) {
				rect.setFill(createBlackBishop2(i));
				hasPiece.set(i, true);
			}
			
			if (i == 6) {
				rect.setFill(createBlackKnight2(i));
				hasPiece.set(i, true);
			}
			
			if (i == 7) {
				rect.setFill(createBlackRook2(i));
				hasPiece.set(i, true);
			}
			
			if (i > 7 && i < 16) {
				rect.setFill(createBlackPawn(i));
				hasPiece.set(i, true);
			}
			
			if (i > 47 && i < 56) {
				rect.setFill(createWhitePawn(i));
				hasPiece.set(i, true);
			}
			
			if (i == 56) {
				rect.setFill(createWhiteRook1(i));
				hasPiece.set(i, true);
			}
			
			if (i == 57) {
				rect.setFill(createWhiteKnight1(i));
				hasPiece.set(i, true);
			}
			
			if (i == 58) {
				rect.setFill(createWhiteBishop1(i));
				hasPiece.set(i, true);
			}
			
			if (i == 59) {
				rect.setFill(createWhiteKing(i));
				hasPiece.set(i, true);
			}
			
			if (i == 60) {
				rect.setFill(createWhiteQueen(i));
				hasPiece.set(i, true);
			}
			
			if (i == 61) {
				rect.setFill(createWhiteBishop2(i));
				hasPiece.set(i, true);
			}
			
			if (i == 62) {
				rect.setFill(createWhiteKnight2(i));
				hasPiece.set(i, true);
			}
			
			if (i == 63) {
				rect.setFill(createWhiteRook2(i));
				hasPiece.set(i, true);
			}
			
		}
	}
	
	private void handleClick(Event event) {
		// If first click only register if there is a piece on the square
		
		if (firstClick) {
        	
        	if (hasPiece.get((int) ((Rectangle) event.getSource()).getUserData())) {
        		if (whiteTurn && pieces.get((int) ((Rectangle) event.getSource()).getUserData()).getType().substring(0, 5).equals("White")) {
        			prevClick = (Rectangle) event.getSource();
        			firstClick = false;
        		}
        		else if (!whiteTurn && pieces.get((int) ((Rectangle) event.getSource()).getUserData()).getType().substring(0, 5).equals("Black")) {
        			prevClick = (Rectangle) event.getSource();
        			firstClick = false;
        		}
        		
        	}
        	else {
        		System.out.println("First Click No Piece");
        		firstClick = true;
        	}
        }
        
        // Second click to move a piece
        else {
        	Rectangle curClick = (Rectangle) event.getSource();
        	int orgPos = (int) prevClick.getUserData();
        	int newPos = (int) curClick.getUserData();
        	
        	String orgColor = pieces.get(orgPos).getType().substring(0, 5);
        	Boolean sameColor = false;
        	
        	if (pieces.get(newPos) != null) {
            	String newColor = pieces.get(newPos).getType().substring(0, 5);
            	
            	// Check if clicked same color piece
            	if (orgColor.equals(newColor)) {
        		sameColor = true;
            	}
        	}
        	
        	if (!sameColor) {
        		String pieceType = pieces.get((int)(prevClick.getUserData())).getType();
        		
        		if (pieceType.equals("WhiteP")) {
        			checkWhitePawns(orgPos, newPos, curClick);
        		}
        		
        		else if (pieceType.equals("WhiteR")) {
        			checkAllSides(orgPos, newPos, curClick);
        		}
        		
        		else if (pieceType.equals("WhiteN")) {
        			checkKnight(orgPos, newPos, curClick);
        		}
        		
        		else if (pieceType.equals("WhiteB")) {
        			checkDiagonals(orgPos, newPos, curClick);
        		}
        		
        		else if (pieceType.equals("WhiteK")) {
        			checkKing(orgPos, newPos, curClick);
        		}
        		
        		else if (pieceType.equals("WhiteQ")) {
        			checkAllSides(orgPos, newPos, curClick);
        			checkDiagonals(orgPos, newPos, curClick);
        		}

        		else if (pieceType.equals("BlackP")) {
        			checkBlackPawns(orgPos, newPos, curClick);
        		}
        		
        		else if (pieceType.equals("BlackR")) {
        			checkAllSides(orgPos, newPos, curClick);
        		}
        		
        		else if (pieceType.equals("BlackN")) {
        			checkKnight(orgPos, newPos, curClick);
        		}
        		
        		else if (pieceType.equals("BlackB")) {
        			checkDiagonals(orgPos, newPos, curClick);
        		}
        		
        		else if (pieceType.equals("BlackK")) {
        			checkKing(orgPos, newPos, curClick);
        		}
        		
        		else if (pieceType.equals("BlackQ")) {
        			checkAllSides(orgPos, newPos, curClick);
        			checkDiagonals(orgPos, newPos, curClick);
        		}
        	}
        	
        	else {
        		System.out.print("Same Square Clicked//SameColor");
        	}
        	
        	firstClick = true;
        }
    }
	
	
	public ImagePattern createWhiteQueen(int ind) {
		Type whiteQ;
		try {
            whiteQ = new Type("WhiteQ"); // Attempt to create Type object
        } catch (FileNotFoundException e) {
            // Handle the exception here (e.g., print error message, use default values)
            System.out.println("Error loading image file for WhiteQ piece!");
            whiteQ = null; // Or set a default value for piece
        }
		pieces.set(ind, whiteQ);
		
		return whiteQ.getImg();
	}
	
	public ImagePattern createBlackQueen(int ind) {
		Type blackQ;
		try {
			blackQ = new Type("BlackQ"); // Attempt to create Type object
        } catch (FileNotFoundException e) {
            // Handle the exception here (e.g., print error message, use default values)
            System.out.println("Error loading image file for BlackQ piece!");
            blackQ = null; // Or set a default value for piece
        }
		
		pieces.set(ind, blackQ);
		
		return blackQ.getImg();
	}
	
	public ImagePattern createWhiteKing(int ind) {
		Type whiteK;
		try {
            whiteK = new Type("WhiteK"); // Attempt to create Type object
        } catch (FileNotFoundException e) {
            // Handle the exception here (e.g., print error message, use default values)
            System.out.println("Error loading image file for WhiteK piece!");
            whiteK = null; // Or set a default value for piece
        }
		
		pieces.set(ind, whiteK);
		
		return whiteK.getImg();
	}
	
	public ImagePattern createBlackKing(int ind) {
		Type blackK;
		Rectangle boxBK = new Rectangle();
		try {
			blackK = new Type("BlackK"); // Attempt to create Type object
        } catch (FileNotFoundException e) {
            // Handle the exception here (e.g., print error message, use default values)
            System.out.println("Error loading image file for BlackK piece!");
            blackK = null; // Or set a default value for piece
        }
		pieces.set(ind, blackK);
		
		
		return blackK.getImg();
	}
	
	public ImagePattern createWhiteBishop1(int ind) {
		Type whiteB1;
		try {
			whiteB1 = new Type("WhiteB"); // Attempt to create Type object
        } catch (FileNotFoundException e) {
            // Handle the exception here (e.g., print error message, use default values)
            System.out.println("Error loading image file for WhiteB piece!");
            whiteB1 = null; // Or set a default value for piece
        }
		pieces.set(ind, whiteB1);
		
		return whiteB1.getImg();
	}
	
	public ImagePattern createWhiteBishop2(int ind) {
		Type whiteB1;
		try {
			whiteB1 = new Type("WhiteB"); // Attempt to create Type object
        } catch (FileNotFoundException e) {
            // Handle the exception here (e.g., print error message, use default values)
            System.out.println("Error loading image file for WhiteB piece!");
            whiteB1 = null; // Or set a default value for piece
        }
		pieces.set(ind, whiteB1);
		
		return whiteB1.getImg();
	}
	
	public ImagePattern createBlackBishop1(int ind) {
		Type blackB1;
		try {
			blackB1 = new Type("BlackB"); // Attempt to create Type object
        } catch (FileNotFoundException e) {
            // Handle the exception here (e.g., print error message, use default values)
            System.out.println("Error loading image file for WhiteK piece!");
            blackB1 = null; // Or set a default value for piece
        };
		pieces.set(ind, blackB1);
		
		return blackB1.getImg();
	}
	
	public ImagePattern createBlackBishop2(int ind) {
		Type blackB1;
		try {
			blackB1 = new Type("BlackB"); // Attempt to create Type object
        } catch (FileNotFoundException e) {
            // Handle the exception here (e.g., print error message, use default values)
            System.out.println("Error loading image file for WhiteK piece!");
            blackB1 = null; // Or set a default value for piece
        };
		pieces.set(ind, blackB1);
		
		return blackB1.getImg();
	}
	
	public ImagePattern createWhiteKnight1(int ind) {
		Type whiteN1;
		try {
			whiteN1 = new Type("WhiteN"); // Attempt to create Type object
        } catch (FileNotFoundException e) {
            // Handle the exception here (e.g., print error message, use default values)
            System.out.println("Error loading image file for WhiteK piece!");
            whiteN1 = null; // Or set a default value for piece
        }
		pieces.set(ind, whiteN1);
		
		return whiteN1.getImg();
	}
	
	public ImagePattern createWhiteKnight2(int ind) {
		Type whiteN1;
		try {
			whiteN1 = new Type("WhiteN"); // Attempt to create Type object
        } catch (FileNotFoundException e) {
            // Handle the exception here (e.g., print error message, use default values)
            System.out.println("Error loading image file for WhiteK piece!");
            whiteN1 = null; // Or set a default value for piece
        }
		pieces.set(ind, whiteN1);
		
		return whiteN1.getImg();
	}
	
	public ImagePattern createBlackKnight1(int ind) {
		Type blackN1;
		try {
			blackN1 = new Type("BlackN"); // Attempt to create Type object
        } catch (FileNotFoundException e) {
            // Handle the exception here (e.g., print error message, use default values)
            System.out.println("Error loading image file for WhiteK piece!");
            blackN1 = null; // Or set a default value for piece
        }
		pieces.set(ind, blackN1);
		
		return blackN1.getImg();
	}
	
	public ImagePattern createBlackKnight2(int ind) {
		Type blackN1;
		try {
			blackN1 = new Type("BlackN"); // Attempt to create Type object
        } catch (FileNotFoundException e) {
            // Handle the exception here (e.g., print error message, use default values)
            System.out.println("Error loading image file for WhiteK piece!");
            blackN1 = null; // Or set a default value for piece
        }
		pieces.set(ind, blackN1);
		
		return blackN1.getImg();
	}
	
	public ImagePattern createWhiteRook1(int ind) {
		Type whiteR1;
		try {
			whiteR1 = new Type("WhiteR"); // Attempt to create Type object
        } catch (FileNotFoundException e) {
            // Handle the exception here (e.g., print error message, use default values)
            System.out.println("Error loading image file for WhiteK piece!");
            whiteR1 = null; // Or set a default value for piece
        }
		pieces.set(ind, whiteR1);
		
		return whiteR1.getImg();
	}
	
	public ImagePattern createWhiteRook2(int ind) {
		Type whiteR1;
		try {
			whiteR1 = new Type("WhiteR"); // Attempt to create Type object
        } catch (FileNotFoundException e) {
            // Handle the exception here (e.g., print error message, use default values)
            System.out.println("Error loading image file for WhiteK piece!");
            whiteR1 = null; // Or set a default value for piece
        }
		pieces.set(ind, whiteR1);
		
		return whiteR1.getImg();
	}
	
	public ImagePattern createBlackRook1(int ind) {
		Type blackR1;
		try {
			blackR1 = new Type("BlackR"); // Attempt to create Type object
        } catch (FileNotFoundException e) {
            // Handle the exception here (e.g., print error message, use default values)
            System.out.println("Error loading image file for WhiteK piece!");
            blackR1 = null; // Or set a default value for piece
        }
		pieces.set(ind, blackR1);
		
		return blackR1.getImg();
	}
	
	public ImagePattern createBlackRook2(int ind) {
		Type blackR1;
		try {
			blackR1 = new Type("BlackR"); // Attempt to create Type object
        } catch (FileNotFoundException e) {
            // Handle the exception here (e.g., print error message, use default values)
            System.out.println("Error loading image file for WhiteK piece!");
            blackR1 = null; // Or set a default value for piece
        }
		pieces.set(ind, blackR1);
		
		return blackR1.getImg();
	}
	
	public ImagePattern createWhitePawn(int ind) {
		Type pawn;
		
		try {
			pawn = new Type("WhiteP"); // Attempt to create Type object
        } catch (FileNotFoundException e) {
            // Handle the exception here (e.g., print error message, use default values)
            System.out.println("Error loading image file for WhiteP piece!");
            pawn = null; // Or set a default value for piece
        }
		pieces.set(ind, pawn);
		
		return pawn.getImg();
	}
	
	public ImagePattern createBlackPawn(int ind) {

		Type pawn;
		
		try {
			pawn = new Type("BlackP"); // Attempt to create Type object
        } catch (FileNotFoundException e) {
            // Handle the exception here (e.g., print error message, use default values)
            System.out.println("Error loading image file for BlackP piece!");
            pawn = null; // Or set a default value for piece
        }
		pieces.set(ind, pawn);
		
		return pawn.getImg();
	}
	
	
	public void checkAllSides(int orgPos, int newPos, Rectangle curClick) {
		int counter = 1;
		Boolean pieceBlocked = false;
		Boolean alrMoved = false;
		
		while ((orgPos - (8 * counter) > 0) && !pieceBlocked && !alrMoved) {
			if (hasPiece.get(orgPos - (8 * counter))) {
				pieceBlocked = true;
			}
			
			if (newPos == (orgPos - (8 * counter))) {
				changePiece(orgPos, newPos, curClick);
				alrMoved = true;
			}
			
			counter++;
		}
		
		counter = 1;
		pieceBlocked = false;
		while ((orgPos + (8 * counter) < 64) && !pieceBlocked && !alrMoved) {
			if (hasPiece.get(orgPos + (8 * counter))) {
				pieceBlocked = true;
			}
			
			if (newPos == (orgPos + (8 * counter))) {
				changePiece(orgPos, newPos, curClick);
				alrMoved = true;
			}
			
			counter++;
		}
		
		counter = 1;
		pieceBlocked = false;
		while (((orgPos + counter) % 8 != 0) && !pieceBlocked && !alrMoved) {
			if (hasPiece.get(orgPos + counter)) {
				pieceBlocked = true;
			}
			
			if (newPos == (orgPos + counter)) {
				changePiece(orgPos, newPos, curClick);
				alrMoved = true;
			}
			
			counter++;
		}
		
		counter = 1;
		pieceBlocked = false;
		while (((orgPos - counter) % 8 != 7) && !pieceBlocked && !alrMoved) {
			if (hasPiece.get(orgPos - counter)) {
				pieceBlocked = true;
			}
			
			if (newPos == (orgPos - counter)) {
				changePiece(orgPos, newPos, curClick);
				alrMoved = true;
			}
			
			counter++;
		}
	}
	
	public void checkDiagonals(int orgPos, int newPos, Rectangle curClick) {
		int counter = 1;
		Boolean pieceBlocked = false;
		Boolean alrMoved = false;
		
		int checkPos = orgPos - 7;
		
		while (checkPos > 0 && (checkPos % 8) != 0 && !pieceBlocked && !alrMoved) {
			if (hasPiece.get(checkPos)) {
				pieceBlocked = true;
			}
			
			if (newPos == (checkPos)) {
				changePiece(orgPos, newPos, curClick);
				alrMoved = true;
			}

			counter++;
			checkPos = orgPos - (7 * counter);
		}
		
		pieceBlocked = false;
		counter = 1;
		checkPos = orgPos - 9;
		while (checkPos > 0 && (checkPos % 8) != 7 && !pieceBlocked && !alrMoved) {
			if (hasPiece.get(checkPos)) {
				pieceBlocked = true;
			}
			
			if (newPos == (checkPos)) {
				changePiece(orgPos, newPos, curClick);
				alrMoved = true;
			}

			counter++;
			checkPos = orgPos - (9 * counter);
		}
		
		pieceBlocked = false;
		counter = 1;
		checkPos = orgPos + 7;
		while (checkPos < 64 && (checkPos % 8) != 7 && !pieceBlocked && !alrMoved) {
			if (hasPiece.get(checkPos)) {
				pieceBlocked = true;
			}
			
			if (newPos == (checkPos)) {
				changePiece(orgPos, newPos, curClick);
				alrMoved = true;
			}

			counter++;
			checkPos = orgPos + (7 * counter);
		}
		
		pieceBlocked = false;
		counter = 1;
		checkPos = orgPos + 9;
		while (checkPos < 64 && (checkPos % 8) != 0 && !pieceBlocked && !alrMoved) {
			if (hasPiece.get(checkPos)) {
				pieceBlocked = true;
			}
			
			if (newPos == (checkPos)) {
				changePiece(orgPos, newPos, curClick);
				alrMoved = true;
			}

			counter++;
			checkPos = orgPos + (9 * counter);
		}
		
	}
	
	public void checkWhitePawns(int orgPos, int newPos, Rectangle curClick) {
		Boolean curHasPiece = hasPiece.get(newPos);
		
		if (orgPos >= 48 && orgPos < 56) {
			if (newPos == (orgPos - 8)) {
				if (!curHasPiece) {
					changePiece(orgPos, newPos, curClick);
				}
			}
			else if (newPos == (orgPos - 16)) {
				if (!curHasPiece) {
					changePiece(orgPos, newPos, curClick);
				}
			}
			else if (orgPos % 8 != 0) {
				if (newPos == (orgPos - 9)) {
					if (curHasPiece) {
						changePiece(orgPos, newPos, curClick);
					}
				}
			}
			else if (orgPos % 8 != 7) {
				if (newPos == (orgPos - 7)) {
					if (curHasPiece) {
						changePiece(orgPos, newPos, curClick);
					}
				}
			}
	}

		else if (orgPos >= 8) {
			if (newPos == (orgPos - 8)) {
				if (!curHasPiece) {
					changePiece(orgPos, newPos, curClick);
				}
			}
			else if (orgPos % 8 != 0 && newPos == (orgPos - 9)) {
					if (curHasPiece) {
						changePiece(orgPos, newPos, curClick);
					}
			}
			else if (orgPos % 8 != 7) {
				if (newPos == (orgPos - 7)) {
					if (curHasPiece) {
						changePiece(orgPos, newPos, curClick);
					}
				}
			}
		}
	}
	
	public void checkBlackPawns(int orgPos, int newPos, Rectangle curClick) {
		Boolean curHasPiece = hasPiece.get(newPos);
		
		if (orgPos >= 8 && orgPos < 16) {
			if (newPos == (orgPos + 8)) {
				if (!curHasPiece) {
					changePiece(orgPos, newPos, curClick);
				}
			}
			else if (newPos == (orgPos + 16)) {
				if (!curHasPiece) {
					changePiece(orgPos, newPos, curClick);
				}
			}
			else if (orgPos % 8 != 0 && newPos == (orgPos + 9)) {
					if (curHasPiece) {
						changePiece(orgPos, newPos, curClick);
					}
			}
			else if (orgPos % 8 != 7) {
				if (newPos == (orgPos + 7)) {
					if (curHasPiece) {
						changePiece(orgPos, newPos, curClick);
					}
				}
			}
	}

		else if (orgPos >= 8) {
			if (newPos == (orgPos + 8)) {
				if (!curHasPiece) {
					changePiece(orgPos, newPos, curClick);
				}
			}
			else if (orgPos % 8 != 7 && newPos == (orgPos + 9)) {
					if (curHasPiece) {
						changePiece(orgPos, newPos, curClick);
					}
			}
			else if (orgPos % 8 != 0) {
				if (newPos == (orgPos + 7)) {
					if (curHasPiece) {
						changePiece(orgPos, newPos, curClick);
					}
				}
			}
		}
	}

	public void checkKing(int orgPos, int newPos, Rectangle curClick) {

		System.out.println("OrgPos: " + orgPos);
		
		// If king is on left wall
		if (orgPos % 8 == 0) {
			if (orgPos == 56) {
				if (newPos == orgPos - 8 || newPos == orgPos - 7 || newPos == orgPos + 1) {
					changePiece(orgPos, newPos, curClick);
				}
			}
			
			else if (orgPos == 0) {
				if (newPos == orgPos + 8 || newPos == orgPos + 9 || newPos == orgPos + 1) {
					changePiece(orgPos, newPos, curClick);
				}
			}
			
			else {
				if (newPos == orgPos - 8 || newPos == orgPos - 7 || newPos == orgPos + 1
						|| newPos == orgPos + 8 || newPos == orgPos + 9) {
					changePiece(orgPos, newPos, curClick);
				}
			}
		}
		
		// If king is on right wall
		else if (orgPos % 8 == 7) {
			if (orgPos == 56) {
				if (newPos == orgPos - 8 || newPos == orgPos - 9 || newPos == orgPos - 1) {
					changePiece(orgPos, newPos, curClick);
				}
			}
			
			else if (orgPos == 0) {
				if (newPos == orgPos + 8 || newPos == orgPos + 7 || newPos == orgPos - 1) {
					changePiece(orgPos, newPos, curClick);
				}
			}
			
			else {
				if (newPos == orgPos - 8 || newPos == orgPos - 9 || newPos == orgPos - 1
						|| newPos == orgPos + 8 || newPos == orgPos + 7) {
					changePiece(orgPos, newPos, curClick);
				}
			}
		}
		
		// If king is on bottom wall
		else if (orgPos > 56 && orgPos < 63) {
			if (newPos == orgPos - 1 || newPos == orgPos + 1 || newPos == orgPos - 9
					|| newPos == orgPos - 8 || newPos == orgPos - 7) {
				changePiece(orgPos, newPos, curClick);
			}
		}
		
		// If king is on top wall
		else if (orgPos > 0 && orgPos < 7) {
			if (newPos == orgPos - 1 || newPos == orgPos + 1 || newPos == orgPos + 9
					|| newPos == orgPos + 8 || newPos == orgPos + 7) {
				changePiece(orgPos, newPos, curClick);
			}
		}
		
		else {
			if (newPos == orgPos - 1 || newPos == orgPos + 1 || newPos == orgPos + 9
					|| newPos == orgPos + 8 || newPos == orgPos + 7
					|| newPos == orgPos - 8 || newPos == orgPos - 7
					|| newPos == orgPos - 9) {
				changePiece(orgPos, newPos, curClick);
			}
		}
	}
	
	public void checkKnight (int orgPos, int newPos, Rectangle curClick) {
		// Left Wall
		if (orgPos % 8 == 0) {
			if (orgPos == 56) {
				if (newPos == orgPos - 15) {
					changePiece(orgPos, newPos, curClick);
				}
				else if (newPos == orgPos - 6) {
					changePiece(orgPos, newPos, curClick);
				}
			}
			else if (orgPos == 0) {
				if (newPos == orgPos + 17) {
					changePiece(orgPos, newPos, curClick);
				}
				else if (newPos == orgPos + 10) {
					changePiece(orgPos, newPos, curClick);
				}
			}
			else if (orgPos == 48) {
				if (newPos == orgPos - 15 || newPos == orgPos - 6 || newPos == orgPos + 10) {
					changePiece(orgPos, newPos, curClick);
				}
			}
			
			else if (orgPos == 8) {
				if (newPos == orgPos + 17 || newPos == orgPos + 10 || newPos == orgPos - 6) {
					changePiece(orgPos, newPos, curClick);
				}
			}
			
			else {
				if (newPos == orgPos + 17 ||newPos == orgPos - 15 || newPos == orgPos + 10 || newPos == orgPos - 6) {
					changePiece(orgPos, newPos, curClick);
				}
			}
		}
		
		// Right Wall
		else if (orgPos % 8 == 7) {
			if (orgPos == 63) {
				if (newPos == orgPos - 17) {
					changePiece(orgPos, newPos, curClick);
				}
				else if (newPos == orgPos - 10) {
					changePiece(orgPos, newPos, curClick);
				}
			}
			else if (orgPos == 0) {
				if (newPos == orgPos + 15) {
					changePiece(orgPos, newPos, curClick);
				}
				else if (newPos == orgPos + 6) {
					changePiece(orgPos, newPos, curClick);
				}
			}
			else if (orgPos == 48) {
				if (newPos == orgPos - 17 || newPos == orgPos - 10 || newPos == orgPos + 6) {
					changePiece(orgPos, newPos, curClick);
				}
			}
			
			else if (orgPos == 8) {
				if (newPos == orgPos + 15 || newPos == orgPos + 6 || newPos == orgPos - 10) {
					changePiece(orgPos, newPos, curClick);
				}
			}
			
			else {
				if (newPos == orgPos + 15 ||newPos == orgPos - 17 || newPos == orgPos + 6 || newPos == orgPos - 10) {
					changePiece(orgPos, newPos, curClick);
				}
			}
		}
		
		// Second left wall
		else if (orgPos % 8 == 1) {
			if (orgPos == 57) {
				if (newPos == orgPos - 15) {
					changePiece(orgPos, newPos, curClick);
				}
				else if (newPos == orgPos - 6) {
					changePiece(orgPos, newPos, curClick);
				}
				else if (newPos == orgPos - 17) {
					changePiece(orgPos, newPos, curClick);
				}
			}
			else if (orgPos == 1) {
				if (newPos == orgPos + 17) {
					changePiece(orgPos, newPos, curClick);
				}
				else if (newPos == orgPos + 10) {
					changePiece(orgPos, newPos, curClick);
				}
				else if (newPos == orgPos + 15) {
					changePiece(orgPos, newPos, curClick);
				}
			}
			else if (orgPos == 49) {
				if (newPos == orgPos - 15 || newPos == orgPos - 17 || newPos == orgPos - 6 || newPos == orgPos + 10) {
					changePiece(orgPos, newPos, curClick);
				}
			}
			
			else if (orgPos == 9) {
				if (newPos == orgPos + 17 || newPos == orgPos + 15 || newPos == orgPos + 10 || newPos == orgPos - 6) {
					changePiece(orgPos, newPos, curClick);
				}
			}
			
			else {
				if (newPos == orgPos + 17 || newPos == orgPos + 15 || newPos == orgPos - 15 
						|| newPos == orgPos - 17 || newPos == orgPos + 10 || newPos == orgPos - 6) {
					changePiece(orgPos, newPos, curClick);
				}
			}
		}
		
		// Second right wall
		else if (orgPos % 8 == 6) {
			if (orgPos == 62) {
				if (newPos == orgPos - 15) {
					changePiece(orgPos, newPos, curClick);
				}
				else if (newPos == orgPos - 10) {
					changePiece(orgPos, newPos, curClick);
				}
				else if (newPos == orgPos - 17) {
					changePiece(orgPos, newPos, curClick);
				}
				
			}
			else if (orgPos == 6) {
				if (newPos == orgPos + 17) {
					changePiece(orgPos, newPos, curClick);
				}
				else if (newPos == orgPos + 6) {
					changePiece(orgPos, newPos, curClick);
				}
				else if (newPos == orgPos + 15) {
					changePiece(orgPos, newPos, curClick);
				}
			}
			else if (orgPos == 54) {
				if (newPos == orgPos - 15) {
					changePiece(orgPos, newPos, curClick);
				}
				else if (newPos == orgPos - 10) {
					changePiece(orgPos, newPos, curClick);
				}
				else if (newPos == orgPos - 17) {
					changePiece(orgPos, newPos, curClick);
				}
				else if (newPos == orgPos + 6) {
					changePiece(orgPos, newPos, curClick);
				}
			}
			
			else if (orgPos == 14) {
				if (newPos == orgPos + 15) {
					changePiece(orgPos, newPos, curClick);
				}
				else if (newPos == orgPos + 6) {
					changePiece(orgPos, newPos, curClick);
				}
				else if (newPos == orgPos + 17) {
					changePiece(orgPos, newPos, curClick);
				}
				else if (newPos == orgPos - 10) {
					changePiece(orgPos, newPos, curClick);
				}
			}
			
			else {
				if (newPos == orgPos + 15) {
					changePiece(orgPos, newPos, curClick);
				}
				else if (newPos == orgPos + 6) {
					changePiece(orgPos, newPos, curClick);
				}
				else if (newPos == orgPos + 17) {
					changePiece(orgPos, newPos, curClick);
				}
				else if (newPos == orgPos - 10) {
					changePiece(orgPos, newPos, curClick);
				}
				else if (newPos == orgPos - 17) {
					changePiece(orgPos, newPos, curClick);
				}
				else if (newPos == orgPos - 15) {
					changePiece(orgPos, newPos, curClick);
				}
			}
		}
		
		// Middle section baby
		else {
			if (newPos == orgPos + 15) {
				changePiece(orgPos, newPos, curClick);
			}
			else if (newPos == orgPos + 6) {
				changePiece(orgPos, newPos, curClick);
			}
			else if (newPos == orgPos + 17) {
				changePiece(orgPos, newPos, curClick);
			}
			else if (newPos == orgPos - 10) {
				changePiece(orgPos, newPos, curClick);
			}
			else if (newPos == orgPos - 17) {
				changePiece(orgPos, newPos, curClick);
			}
			else if (newPos == orgPos - 15) {
				changePiece(orgPos, newPos, curClick);
			}
			else if (newPos == orgPos - 6) {
				changePiece(orgPos, newPos, curClick);
			}
			else if (newPos == orgPos + 10) {
				changePiece(orgPos, newPos, curClick);
			}
		}
	}
	
	public void changePiece(int orgPos, int newPos, Rectangle curClick) {
		curClick.setFill(prevClick.getFill());
		prevClick.setFill(Color.TRANSPARENT);
		pieces.set(newPos, pieces.get(orgPos));
		pieces.set(orgPos, null);
		
		hasPiece.set(newPos, true);
		hasPiece.set(orgPos, false);
		
		changeTurn();
	}
	
	public void changeTurn() {
		
		if (whiteTurn) {
			whiteTurn = false;
			labelTurn.setText("Black's Turn");
			labelTurn.setStyle("-fx-font-size: 20px; " +
			        "-fx-text-fill: #000000; " +
			        "-fx-alignment: center; " +
			        "-fx-background-color: rgb(42, 54, 53);");
		}
		else {
			whiteTurn = true;
			labelTurn.setText("White's Turn");
			labelTurn.setStyle("-fx-font-size: 20px; " +
			        "-fx-text-fill: #F0F0F0; " +
			        "-fx-alignment: center; " +
			        "-fx-background-color: rgb(42, 54, 53);");
		}
		
	}
	
	public Type get(int num) {
		return pieces.get(num);
	}
}

class Type {
	static String[] types;
	String type;
	ImagePattern img;
	
	public Type(String typeInput) throws FileNotFoundException {
		type = typeInput;
		InputStream stream = new FileInputStream("/Users/suli/eclipse-workspace/Chess/ChessPieces/" + type + ".png");
		Image image = new Image(stream);
	    ImagePattern imageView = new ImagePattern(image);
	    
	    img = imageView;
	}
	
	public ImagePattern getImg() {
		return img;
	}
	
	public String getType() {
		return type;
	}
	
}
