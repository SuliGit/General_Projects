import java.util.Random;   // Needed for the Random class

/**
   This class simulates rolling a die 10,000 times
   and counts the number of times each face is rolled
*/

public class TwoDice
{
   public static void main(String[] args)
   {
      final int NUMBER = 10000;  // Number of dice rolls

      // A random number generator used in
      // simulating the rolling of dice
      Random generator = new Random();

      int count = 0;       // Total number of dice rolls
      int ones = 0;        // Number of double one rolls
      int twos = 0;        // Number of double two rolls
      int threes = 0;      // Number of double three rolls
      int fours = 0;       // Number of double four rolls
      int fives = 0;       // Number of double five rolls
      int sixes = 0;       // Number of double six rolls
      
      int dieValue1;   // Value of the die

      
      for (count = 0; count < NUMBER; count++) {
    	  dieValue1 = generator.nextInt(6);

    	  dieValue1++;
    	  
    	  
    	 // if statement is required to check if
    	 
      switch (dieValue1) {
      		case 1:
      			ones++;
       	  		break;
       	  	case 2:
       	  		twos++;
       	  		break;
       	  	case 3:
       	  		threes++;
       	  		break;
       	  	case 4:
       	  		fours++;
       	  		break;
       	  	case 5:
       		  	fives++;
       		  	break;
       	  	case 6:
       	  		sixes++;
       	  		break;
       	  	}
    	 }




      // Display the results
      System.out.println ("You rolled SnakeEyes " +
                          ones + " out of " +
                          count + " rolls.");
      System.out.println ("You rolled a pair of twos " +
                          twos + " out of " + count +
                          " rolls.");
      System.out.println ("You rolled a pair of threes " +
                          threes + " out of " + count +
                          " rolls.");
      System.out.println ("You rolled a pair of fours " +
                          fours + " out of " + count +
                          " rolls.");
      System.out.println ("You rolled a pair of fives " +
                          fives + " out of " + count +
                          " rolls.");
      System.out.println ("You rolled a pair of sixes " +
                          sixes + " out of " + count +
                          " rolls.");
   }
}
