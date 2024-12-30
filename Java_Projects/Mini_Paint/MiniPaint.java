package application;

import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;

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
import javafx.stage.Stage;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.input.*;

public class MiniPaint extends Application {

	// Initializing all variables
	final int CANVAS_HEIGHT = 450, CANVAS_WIDTH = 500;
	
	// Creating grid and canvas
    public static Pane canvas = new Pane();
    GridPane grid = new GridPane();
    
    // VBox for all the controls on the left
    VBox controls = new VBox(15);
    VBox toolsBox = new VBox(10);
    VBox colorBox = new VBox(10);
    VBox canvasBox = new VBox();
    
    
    // HBox to display the color at the bottom
    HBox colorDisplay = new HBox(5);
    
    // Labels for all the controls
    Label tool = new Label("Tool");
    Label color = new Label("Color");
    Label redLabel = new Label("Red");
    Label greenLabel = new Label("Green");
    Label blueLabel = new Label("Blue");
    Label current = new Label("Current: ");
    
    // Radio buttons for mouse options
    RadioButton draw = new RadioButton("Draw");
    RadioButton move = new RadioButton("Move");
    RadioButton delete = new RadioButton("Delete");
    
    // Combobox
    ComboBox<String> shapeList = new ComboBox<String>();
    
    // Sliders
    Slider red = new Slider(0, 255, 1);
    Slider green = new Slider(0, 255, 1);
    Slider blue = new Slider(0, 255, 1);
    
    // Togglegroup for radiobuttons and sliders
    ToggleGroup toggle = new ToggleGroup();
    
    // Creating a circle for the color display
    Ellipse ellipse = new Ellipse(0, 0, 12, 8);
    ShapeCollection collection = new ShapeCollection();
    
    // Mouse Coordinates
    double endX, startX;
    double endY, startY;
    
    Boolean moving = false;
    
    Shape curShape;
    String curShapeName;
  
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        
        // Setting the styles and adding the nodes for all the groups
        setStyles();
        addNodes();
        
        // Shape control code
        EventHandler<MouseEvent>  makeShape = event -> {
        	curShapeName = shapeList.getSelectionModel().getSelectedItem();
			
			double x = event.getX();
			double y = event.getY();

			Color c = getSelectedColor();
			if (x < CANVAS_WIDTH && y < CANVAS_HEIGHT) {
			switch(curShapeName) {
				case "Line":
					Line line = new Line(x, y, x, y);
					line.setStroke(c);
					line.setStrokeWidth(4);
					collection.AddShape(line);
					canvas.getChildren().add(line);
					break;
					
				case "Rectangle":
					Rectangle rectangle = new Rectangle(x, y, 0, 0);
					rectangle.setFill(c);
					collection.AddShape(rectangle);
					canvas.getChildren().add(rectangle);
					break;
					
				case "Ellipse":
					Ellipse ellipse = new Ellipse();
					ellipse.setCenterX(x);
					ellipse.setCenterY(y);
					ellipse.setFill(c);
					collection.AddShape(ellipse);
					canvas.getChildren().add(ellipse);
					break;
			}
			}
		};

		// method to change shape
		EventHandler<MouseEvent> changeShape = event -> {
			double x = event.getX();
			double y = event.getY();
	
			if (x < CANVAS_WIDTH && y < CANVAS_HEIGHT) {
				switch(curShapeName) {
					//Get latest added shape and modify it based on the moving mouse position
					case "Line":
						Line line = collection.getLines().get(collection.getLines().size() - 1);
						line.setEndX(x);
						line.setEndY(y);
						break;
						
					case "Rectangle":
						Rectangle rectangle = collection.getRectangles().get(collection.getRectangles().size() - 1);
						rectangle.setWidth(x - rectangle.getX());
						rectangle.setHeight(y - rectangle.getY());
						break;
						
					case "Ellipse":
						Ellipse ellipse = collection.getEllipses().get(collection.getEllipses().size() - 1);
						ellipse.setRadiusX(x - ellipse.getCenterX());
						ellipse.setRadiusY(y - ellipse.getCenterY());
						break;
				}
			}
		};

		Image drawCursorImage = new Image("file:drawcursor.png");
		ImageCursor drawCursor = new ImageCursor(drawCursorImage, 40, drawCursorImage.getHeight() - 50);

		EventHandler<MouseEvent> drawEnter = event -> {
			setCursor((Node)event.getSource(), drawCursor);
		};

		EventHandler<MouseEvent> drawExit = event -> {
			setCursor((Node)event.getSource(), "default");
		};

		//Shape moving/deleting events
		EventHandler<MouseEvent> moveShape = event -> {
			
			Object source = event.getSource();

			// get coordinates
			double x = event.getSceneX();
			double y = event.getSceneY();

			// check what type of shape it is
			if (source instanceof Line){
				x = event.getX();
				y = event.getY();
				Line line = (Line)source;
				//Move the end that is closest to the mouse
				double startDistance = Math.sqrt(Math.pow(Math.abs(x - line.getStartX()), 2) + Math.pow(Math.abs(y - line.getStartY()), 2));
				double endDistance = Math.sqrt(Math.pow(Math.abs(x - line.getEndX()), 2) + Math.pow(Math.abs(y - line.getEndY()), 2));
				if (startDistance < endDistance){
					line.setStartX(x);
					line.setStartY(y);
				}
				else{
					line.setEndX(x);
					line.setEndY(y);	
				}
				
			}
			else if (source instanceof Rectangle){
				Rectangle rectangle = (Rectangle)source;
				//Translate the rectangle the distance from mouse to perform smooth moving with no jumping to mouse position
				if (!moving){
					moving = true;
					rectangle.setTranslateX(rectangle.getTranslateX() - (x - rectangle.getX()));
					rectangle.setTranslateY(rectangle.getTranslateY() - (y - rectangle.getY()));
				}
				rectangle.setX(x);
				rectangle.setY(y);
			}
			else if (source instanceof Ellipse){
				Ellipse ellipse = (Ellipse)source;
				//Translate the center of the ellipse the distance from the mouse to perform smooth moving
				if (!moving){
					moving = true;
					ellipse.setTranslateX(ellipse.getTranslateX() - (x - ellipse.getCenterX()));
					ellipse.setTranslateY(ellipse.getTranslateY() - (y - ellipse.getCenterY()));
				}
				ellipse.setCenterX(x);
				ellipse.setCenterY(y);
			}
		};

		// Entering different modes
		EventHandler<MouseEvent> moveEnter = event -> {
			setCursor((Node)event.getSource(), "move");
		};

		EventHandler<MouseEvent> moveExit = event -> {
			setCursor((Node)event.getSource(), "default");
		};

		EventHandler<MouseEvent> moveEnded = event -> {
			moving = false;
		};

		EventHandler<MouseEvent> deleteShape = event -> {
			Shape source = (Shape)event.getSource();
			canvas.getChildren().remove(source);
			collection.getShapes().remove(source);
		};

		Image deleteCursorImg = new Image("file:deletecursor.png");
		ImageCursor deleteCursor = new ImageCursor(deleteCursorImg, deleteCursorImg.getWidth() / 2, deleteCursorImg.getHeight() / 2);

		EventHandler<MouseEvent> deleteEnter = event -> {
			setCursor((Node)event.getSource(), deleteCursor);
		};

		EventHandler<MouseEvent> deleteExit = event -> {
			setCursor((Node)event.getSource(), "default");
		};
		
		//Add or remove the canvas and shape events based on the RadioButtons being clicked
		EventHandler<ActionEvent> enableDrawing = event -> {
			canvas.setOnMousePressed( makeShape);
			canvas.setOnMouseDragged(changeShape);
			canvas.setOnMouseEntered(drawEnter);
			canvas.setOnMouseExited(drawExit);
			manageShapeEvent(MouseEvent.MOUSE_ENTERED, drawEnter, false);
			shapeList.setDisable(false);
		};

		EventHandler<ActionEvent> disableDrawing = event -> {
			canvas.setOnMousePressed(null);
			canvas.setOnMouseDragged(null);
			canvas.setOnMouseEntered(null);
			canvas.setOnMouseExited(null);
			manageShapeEvent(MouseEvent.MOUSE_ENTERED, drawEnter, true);
			shapeList.setDisable(true);
		};

		EventHandler<ActionEvent> enableMoving = event -> {
			manageShapeEvent(MouseEvent.MOUSE_DRAGGED, moveShape, false);
			manageShapeEvent(MouseEvent.MOUSE_RELEASED, moveEnded, false);
			manageShapeEvent(MouseEvent.MOUSE_ENTERED, moveEnter, false);
			manageShapeEvent(MouseEvent.MOUSE_EXITED, moveExit, false);
			setCursor((Node)canvas, "default");
		};

		EventHandler<ActionEvent> disableMoving = event -> {
			manageShapeEvent(MouseEvent.MOUSE_DRAGGED, moveShape, true);
			manageShapeEvent(MouseEvent.MOUSE_RELEASED, moveEnded, true);
			manageShapeEvent(MouseEvent.MOUSE_ENTERED, moveEnter, true);
			manageShapeEvent(MouseEvent.MOUSE_EXITED, moveExit, true);
		};

		EventHandler<ActionEvent> enableDelete = event -> {
			manageShapeEvent(MouseEvent.MOUSE_CLICKED, deleteShape, false);
			manageShapeEvent(MouseEvent.MOUSE_ENTERED, deleteEnter, false);
			manageShapeEvent(MouseEvent.MOUSE_EXITED, deleteExit, false);
			setCursor((Node)canvas, "default");
		};

		EventHandler<ActionEvent> disableDelete = event -> {
			manageShapeEvent(MouseEvent.MOUSE_CLICKED, deleteShape, true);
			manageShapeEvent(MouseEvent.MOUSE_ENTERED, deleteEnter, true);
			manageShapeEvent(MouseEvent.MOUSE_EXITED, deleteExit, true);
		};
		
		// Setting color of ellipse
		red.valueProperty().addListener((observable, oldValue, newValue) -> {
		    ellipse.setFill(getSelectedColor());
		});
		
		green.valueProperty().addListener((observable, oldValue, newValue) -> {
			ellipse.setFill(getSelectedColor());
		});
		
		blue.valueProperty().addListener((observable, oldValue, newValue) -> {
			ellipse.setFill(getSelectedColor());
		});




		//Draw RadioButton must activate the canvas but disable the shape events
		draw.addEventHandler(ActionEvent.ACTION, enableDrawing);
		draw.addEventHandler(ActionEvent.ACTION, disableMoving);
		draw.addEventHandler(ActionEvent.ACTION, disableDelete);
		
		//Move should disable canvas and delete but enable moving
		move.addEventHandler(ActionEvent.ACTION, disableDrawing);
		move.addEventHandler(ActionEvent.ACTION, disableDelete);
		move.addEventHandler(ActionEvent.ACTION, enableMoving);

		//Delete should disable canvas and move but enable deleting
		delete.addEventHandler(ActionEvent.ACTION, disableDrawing);
		delete.addEventHandler(ActionEvent.ACTION, disableMoving);
		delete.addEventHandler(ActionEvent.ACTION, enableDelete);

		//Manually fire drawRdo to activate "drawing mode" upon launch
		draw.fire();

		Scene scene = new Scene(grid);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
	}
	
    // Get color of sliders
	public Color getSelectedColor(){
		return Color.rgb((int)red.getValue(), (int)green.getValue(), (int)blue.getValue());
	}
	
	// Manage shape method to remove or not
	public void manageShapeEvent(EventType<MouseEvent> type, EventHandler<MouseEvent> event, boolean remove){
		for (Shape shape: collection.getShapes()){
			if (!remove){
				shape.addEventHandler(type, event);
			}
			else{
				shape.removeEventHandler(type, event);
			}
		}
	}
	
	public void setCursor(Node source, String cursorType){
		source.setStyle("-fx-cursor: " + cursorType + ";");
	}

	public void setCursor(Node source, ImageCursor cursor){
		source.setCursor(cursor);
		}
	
	// Setting styles of text and boxes
	public void setStyles() {
		tool.getStyleClass().add("text-style");
		color.getStyleClass().add("text-style");
		
		canvas.getStyleClass().add("borderedCanvas");
		controls.getStyleClass().add("borderedTools");
		grid.getStyleClass().add("borderedGrid");
	}
	
	// Add  nodes to boxes and grid. setting togglegroups and combobox
	public void addNodes() {
		canvas.setPrefSize(CANVAS_WIDTH, CANVAS_HEIGHT);
		toolsBox.getChildren().addAll(tool, draw, move, delete);
		colorBox.getChildren().addAll(color, redLabel, red, greenLabel, green, blueLabel, blue);
		colorDisplay.getChildren().addAll(current, ellipse);
		
		
		controls.getChildren().addAll(toolsBox, shapeList, colorBox, colorDisplay);
		grid.add(controls, 0, 0);
		grid.add(canvas, 1, 0);
		
		shapeList.getItems().addAll("Line", "Rectangle", "Ellipse");
		shapeList.getSelectionModel().selectFirst();
		
		draw.setToggleGroup(toggle);
		move.setToggleGroup(toggle);
		delete.setToggleGroup(toggle);
	}
    
}
