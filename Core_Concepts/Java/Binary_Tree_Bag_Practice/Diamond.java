import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.*;

public class Diamond implements Serializable, Cloneable, Comparable<Diamond> {
	
	private double carats;
	private char color;
	private String stock;
	private String clarity;
	private String cut;
	
	
	public Diamond() {
		carats = -1;
		color = 'A';
		stock = "N";
		clarity = "N";
		cut = "N";
	}
	
	public Diamond(double iCarats, char iColor, String iStock, String iClarity, String iCut) {
		carats = iCarats;
		color = iColor;
		stock = iStock;
		clarity = iClarity;
		cut = iCut;
	}
	
	
	// Set methods
	public void setCarats(double c) {
		carats = c;
	}
	
	public void setcolor(char c) {
		color = c;
	}
	
	public void setClarity(String c) {
		clarity = c;
	}
	
	public void setStock(String c) {
		stock = c;
	}
	
	public void setCut(String c) {
		cut = c;
	}
	
	
	// Get functions
	public double getCarats() {
	    return carats;
	}

	public char getColor() {
	    return color;
	}

	public String getStock() {
	    return stock;
	}

	public String getClarity() {
	    return clarity;
	}

	public String getCut() {
	    return cut;
	}
	
	public int setClarityToInt() {
		int clarityInt;
		
		if (clarity.equals("FL")) {
		    clarityInt = 11;
		} 
		
		else if (clarity.equals("IF")) {
		    clarityInt = 10;
		} 
		
		else if (clarity.equals("VVS1")) {
		    clarityInt = 9;
		} 
		
		else if (clarity.equals("VVS2")) {
		    clarityInt = 8;
		} 
		
		else if (clarity.equals("VS1")) {
		    clarityInt = 7;
		} 
		
		else if (clarity.equals("VS2")) {
		    clarityInt = 6;
		} 
		
		else if (clarity.equals("SI1")) {
		    clarityInt = 5;
		} 
		
		else if (clarity.equals("SI2")) {
		    clarityInt = 4;
		} 
		
		else if (clarity.equals("I1")) {
		    clarityInt = 3;
		} 
		
		else if (clarity.equals("I2")) {
		    clarityInt = 2;
		} 
		
		else if (clarity.equals("I3")) {
		    clarityInt = 1;
		} 
		
		else {
		    clarityInt = 0;  // In case the clarity grade doesn't match any known value
		}
		
		
		return clarityInt;
	}
	
	public String toString() {
		
		String details;
		
		details = "Carats: " + carats + "\n";
		details += "Color: " + color + "\n";
		details += "Stock: " + stock + "\n";
		details += "Clarity: " + clarity + "\n";
		details += "Cut: " + cut + "\n";
		
		return details;
		
	}
	
	
	@Override
	public Diamond clone() {
		
		Diamond cloned = new Diamond();
		
		cloned.setCarats(carats);
		cloned.setcolor(color);
		
		cloned.setStock(new String(stock));
		cloned.setClarity(new String(clarity));
		cloned.setCut(new String(cut));
		
		
		return cloned;
		
	}
	
	public Boolean equals(Diamond d) {
		
		Boolean equals = true;
		
		if (d.getCarats() != carats) {
			equals = false;
		}
		
		else if (d.getColor() != color) {
	        equals = false;
	    }

		else if (!d.getStock().equals(stock)) {
	        equals = false;
	    }

		else if (!d.getClarity().equals(clarity)) {
	        equals = false;
	    }

		else if (!d.getCut().equals(cut)) {
	        equals = false;
	    }
		
		
		return equals;
		
	}
	
	
	public int compareTo(Diamond d) {
		
		int num = 0;
		int clarityIntThis;
		int clarityIntD;
		
		if (d.getCarats() > carats) {
			num = -1;
		}
		
		else if (d.getCarats() < carats) {
			num = 1;
		}
		
		else {
			clarityIntThis = setClarityToInt();
			clarityIntD = d.setClarityToInt();
			
			if (clarityIntD > clarityIntThis) {
				num = -1;
			}
			
			else if (clarityIntD < clarityIntThis) {
				num = 1;
			}
			
			else {
				if (d.getColor() < color) {
					num = -1;
				}
				else if (d.getColor() > color) {
					num = 1;
				}
			}
		}
		
		return num;
		
	}
	
	
	
}
