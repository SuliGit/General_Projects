package application;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.shape.*;

public class ShapeCollection implements Serializable {
	private ArrayList<Line> lines;
	private ArrayList<Rectangle> rectangles;
	private ArrayList<Ellipse> ellipses;
	
	ShapeCollection() {
		lines = new ArrayList<Line>();
		rectangles = new ArrayList<Rectangle>();
		ellipses = new ArrayList<Ellipse>();

	}
	
	public void AddShape(Shape shape) {
			
		if (shape instanceof Line) {
			lines.add((Line) shape);
		}
		
		else if (shape instanceof Rectangle) {
			rectangles.add((Rectangle) shape);
		}
		
		else if (shape instanceof Ellipse) {
			ellipses.add((Ellipse) shape);
		}
		else {
			System.out.println("Invalid Shape Inputted");
		}
	}
	
	public void removeShape(Shape shape){
		if (shape instanceof Line){
			lines.remove((Line)shape);
		}
		else if (shape instanceof Rectangle){
			rectangles.remove((Rectangle)shape);
		}
		else if (shape instanceof Ellipse){
			ellipses.remove((Ellipse)shape);
		}
	}
	
	public ArrayList<Line> getLines() {
		return lines;
	}
	
	public ArrayList<Rectangle> getRectangles() {
		return rectangles;
	}
	
	public ArrayList<Ellipse> getEllipses() {
		return ellipses;
	}
	
	public ArrayList<Shape> getShapes() {
		ArrayList<Shape> shapes = new ArrayList<>();
		
		shapes.addAll(lines);
		shapes.addAll(rectangles);
		shapes.addAll(ellipses);
		
		return shapes;
	}
	

}
