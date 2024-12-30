import java.io.*;
import java.util.Random;

public class DiamondArrayBag implements Serializable, Cloneable {
	private Diamond[] diamonds;
	private int size;
	private static final int DEFAULT_CAPACITY = 100;
	
	public DiamondArrayBag() {
		diamonds = new Diamond[DEFAULT_CAPACITY];
		int size = 0;
	}
	
	public DiamondArrayBag(int capacity) {
        diamonds = new Diamond[capacity];
        size = 0;
    }
	
	public DiamondArrayBag(DiamondArrayBag d) {
		diamonds = new Diamond[d.getSize()];
		size = d.getSize();
		for(int i = 0; i < d.getSize(); i++) {
			diamonds[i] = (Diamond) d.getPosition(i).clone();
		}
	}
    
    public boolean add(Diamond d) {
        if (size >= diamonds.length) {
            this.ensureCapacity(size * 2);
        }
        
        diamonds[size++] = d;
        
        return true;
    }
    
    public Diamond removeRandom() {
        if (size == 0) {
            return null;
        }
        
        Random random = new Random();
        int index = random.nextInt(size);
        
        Diamond removedPosition = diamonds[index];
        diamonds[index] = diamonds[--size];
        diamonds[size] = null;
        return removedPosition;
    }
    
    public Diamond remove(Diamond d) {
        if (size == 0) {
            return null;
        }
        
        int index = -1;
        
        for (int i = 0; i < size; i++) {
        	if (diamonds[i].equals(d)) {
        		index = i;
        	}
        }
        
        
        if (index != -1) {
        	Diamond removedPosition = diamonds[index].clone();
        	
            diamonds[index] = diamonds[size - 1].clone();
            diamonds[size - 1] = null;
            
            size--;
            
            return removedPosition;
        }
        
        	return null;
        
    }
    
    public void trimToSize() {
    	
    	if (size > 0) {
    		Diamond[] newD = new Diamond[size];
        	
        	for (int i = 0; i < size; i++) {
        		newD[i] = (Diamond) diamonds[i].clone();
        	}
        	
        	diamonds = newD;
    	}
    	
    }
    
    public int getSize() {
        return size;
    }
    
    public Diamond getPosition(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        return diamonds[index];
    }
    
    public void ensureCapacity(int num) {
    	if (num < size) {
    		throw new IndexOutOfBoundsException("Invalid Capacity to Ensure");
    	}
    	
    	Diamond[] newD = new Diamond[num];
    	
    	for (int i = 0; i < size; i++) {
    		newD[i] = (Diamond) diamonds[i].clone();
    	}
    	
    	diamonds = newD;
    }
    
    public Boolean contains(Diamond d) {
    	Boolean contains = false;
    	
    	for (int i = 0; i < size; i++) {
    		if (diamonds[i].equals(d)) {
    			contains = true;
    		}
    	}
    	
    	return contains;
    }
    
    public int occurrences(Diamond d) {
    	int x = 0;
    	
    	for (int i = 0; i < size; i++) {
    		if (diamonds[i].equals(d)) {
    			x++;
    		}
    	}
    	
    	return x;
    	
    }
    

    
    public static DiamondArrayBag intersection(DiamondArrayBag d1, DiamondArrayBag d2) {
    	DiamondArrayBag intersectionD = new DiamondArrayBag();
    	
    	for (int i = 0; i < d1.getSize(); i++) {
    		
    		for (int j = 0; j < d2.getSize(); j++) {
    			
    			if (d1.getPosition(i).equals(d2.getPosition(j))) {
    				
    				intersectionD.add(d1.getPosition(i));
    			}
    		}
    	}
    	
    	return intersectionD;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {

        DiamondArrayBag clonedD = (DiamondArrayBag) super.clone();

        clonedD.diamonds = new Diamond[diamonds.length];
        clonedD.size = size;

        for (int i = 0; i < size; i++) {
            clonedD.diamonds[i] = (Diamond) diamonds[i].clone();
        }
        
        return clonedD;
    }
    
    
    public String toString() {
    	String allDiamonds = "";

        for (int i = 0; i < size; i++) {
        	allDiamonds += "Diamond " + i + ":\n";
            allDiamonds += diamonds[i].toString();

            if (i < size - 1) {
                allDiamonds += "\n";
            }
        }

        return allDiamonds;
    	
    }
    
    public static void addDiamondsToFile(DiamondArrayBag d, String name) {
    	
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(name))) {
        	
            out.writeObject(d);
            
        } 
        catch (IOException e) {
        	
            e.printStackTrace();
            
        }
    }
    
    
    public static DiamondArrayBag loadFromFile(String filename) {
    	
        DiamondArrayBag d = null;
        
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
        	
            d = (DiamondArrayBag) in.readObject();
            
        } 
        catch (IOException | ClassNotFoundException e) {
        	
            e.printStackTrace();
            
        }
        
        return d;
    }
    
    public static void creatDiamondSer() {
    	DiamondArrayBag DBag1 = new DiamondArrayBag(6);
        DBag1.add(new Diamond(1.0, 'D', "DMN0085", "VVS1", "Rose"));
        DBag1.add(new Diamond(2.0, 'X', "DSN1285", "VS1", "Baguette"));
        DBag1.add(new Diamond(3.0, 'H', "FRN9985", "IF", "Lozenge"));
        DBag1.add(new Diamond(1.5, 'I', "TTN9999", "VVS2", "Pearl"));
        DBag1.add(new Diamond(2.5, 'Z', "LNN1111", "SI1", "Brilliant"));
        DBag1.add(new Diamond(4.0, 'E', "FFF0000", "I1", "Radiant"));
        DBag1.add(new Diamond(4.0, 'E', "FFF0000", "I1", "Radiant"));
        DBag1.add(new Diamond(4.0, 'E', "FFF0000", "I1", "Radiant"));
        
    	addDiamondsToFile(DBag1, "Diamonds.ser");
    	
    }

}


