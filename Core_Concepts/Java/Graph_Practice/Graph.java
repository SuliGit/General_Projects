class Graph {
	private boolean[][] edges;
	private Object[] labels;

	public Graph(int n) {
		edges = new boolean[n][n];
		labels = new Object[n];
	}

	public void addEdge(int source, int target) {
		edges[source][target] = true;
	}

	public static void depthFirstPrint(Graph g, int start) {
		boolean[] marked = new boolean[g.size()];
		depthFirstRecurse(g, start, marked);
	}

	public static void depthFirstRecurse(Graph g, int v, boolean[] marked) {
		int[] connections = g.neighbors(v);
		int i, nextNeighbor;
		marked[v] = true;
		System.out.println(g.getLabel(v));
		for (i = 0; i < connections.length; i++) {
			nextNeighbor = connections[i];
			if (!marked[nextNeighbor])
				depthFirstRecurse(g, nextNeighbor, marked);
		}
	}

	public Object getLabel(int vertex) {
		return labels[vertex];
	}

	public boolean isEdge(int source, int target) {
		return edges[source][target];
	}

	public int[] neighbors(int vertex) {
		int i, count = 0;
		int[] answer;
		for (i = 0; i < labels.length; i++)
			if (edges[vertex][i])
				count++;
		answer = new int[count];
		count = 0;
		for (i = 0; i < labels.length; i++)
			if (edges[vertex][i])
				answer[count++] = i;
		return answer;
	}
	
	public int[] suggest(int vertex) {
		int i, count = 0;
		int[] answer;
		for (i = 0; i < labels.length; i++)
			if (!edges[vertex][i]) {
				count++;
			}
		answer = new int[count];
		count = 0;
		
		for (i = 0; i < labels.length; i++) {
			if (edges[vertex][i]) {
				answer[count] = i;
				count++;
			}
		}
		return answer;
	}

	public void removeEdge(int source, int target) {
		edges[source][target] = false;
	}

	public void setLabel(int vertex, Object newLabel) {
		labels[vertex] = newLabel;
	}

	public int size() {
		return labels.length;
	}
}
