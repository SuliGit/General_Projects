public class SocialNetwork {
	public static void main(String[] args) {
		String[] friends = { "Adam", "Bob", "Carol", "Dave", "Eve" };
		Graph myNet = new Graph(5);
		for (int i = 0; i < friends.length; i++) {
			myNet.setLabel(i, friends[i]);
		}
		myNet.addEdge(0, 1);
		myNet.addEdge(0, 2);
		myNet.addEdge(1, 0);
		myNet.addEdge(1, 2);
		myNet.addEdge(1, 3);
		myNet.addEdge(2, 0);
		myNet.addEdge(2, 1);
		myNet.addEdge(2, 4);
		myNet.addEdge(3, 1);
		myNet.addEdge(3, 4);
		myNet.addEdge(4, 2);
		myNet.addEdge(4, 3);
		
		//Printing carol's friends
		System.out.println("Carol's friends:\n");
		for (int i = 0; i < friends.length; i++) {
			if (myNet.isEdge(2, i)) {
				System.out.println(myNet.getLabel(i));
			}
		}
		System.out.println("\n");
		
		// Check if adam and dave are friends
		if (myNet.isEdge(0, 3) && myNet.isEdge(3, 0)) {
			System.out.println("Adam and Dave are both friends with each other");
		}
		else {
			System.out.println("Adam and Dave are not both friends with each other");
		}
		
		
		// Unfriending carol and eve 
		myNet.removeEdge(2, 4);
		myNet.removeEdge(4, 2);
		
		if (myNet.isEdge(2, 4) && myNet.isEdge(4, 2)) {
			System.out.println("Carol and Eve are both friends with each other");
		}
		else {
			System.out.println("Carol and Eve are not both friends with each other");
		}
		System.out.println("\n\nDepth First Print:\n");
		
		// The complexity of this is O(n)
		Graph.depthFirstPrint(myNet, 4);
		
		// Suggest any friend Dave doesn't have
		// Time analysis O(n)
		System.out.println("\n\nDave Suggested Friends:\n");
		for (int i = 0; i < friends.length; i++) {
			if (!myNet.isEdge(3, i) && i != 3) {
				System.out.println(myNet.getLabel(i));
			}
		}
		
		
	}
}
