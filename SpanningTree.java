import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpanningTree {
	public static void main(String[] args) {
		Graph g = new Graph();
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()) {
			String token = sc.nextLine();
			if(token.endsWith("]")){
				Matcher m = Pattern.compile("(.*?)--(.*?) \\[(.*?)\\]").matcher(token);
				while(m.find()) {
					g.addEdge(m.group(1), m.group(2), Integer.parseInt(m.group(3)));
				}	
			}
			else{
				g.addNode(token.trim());
			}
		}
		
		System.out.println(prim(g));
	}
	
	private static int prim(Graph g) {
		int totalWeight = 0;
		PriorityQueue<Edge> queue = new PriorityQueue<Edge>(10, new EdgeComparator());
		String startingCity = g.adjacencyList.keySet().iterator().next();
		visit(queue, g, startingCity);
	
		while(!queue.isEmpty()){
			Edge minimumEgde = queue.poll(); //log(N) - see javadoc
			String v = minimumEgde.fromVertex;
			String w = minimumEgde.toVertex;
			if(g.visited.get(v) && g.visited.get(w)) continue; //skip if both vertices has been visited
			totalWeight += minimumEgde.weight;
			if(!g.visited.get(v)) visit(queue , g, v);
			if(!g.visited.get(w)) visit(queue, g, w);
		}
		return totalWeight;	
	}
	
	private static void visit(PriorityQueue<Edge> pq,Graph g, String v){
		g.visited.put(v, true);
		for (Edge e : g.adjacencyList.get(v)){
			pq.add(e); //log(N) - see javadoc
		}
	}

	private static class Graph {
		HashMap<String,ArrayList<Edge>> adjacencyList = new HashMap<String,ArrayList<Edge>>();
		HashMap<String,Boolean> visited = new HashMap<String,Boolean>();
		
		public void addEdge(String v, String w, int weight){
			Edge e = new Edge(v, w, weight);
			adjacencyList.get(v).add(e);
			adjacencyList.get(w).add(e);
		}
		
		public void addNode(String node){
			adjacencyList.put(node, new ArrayList<Edge>());
			visited.put(node,false);
		}
	}
	
	public static class Edge{
		String fromVertex;
		String toVertex;
		int weight = 0;

		public Edge(String fromVertex, String toVertex, int weight){
			this.weight = weight;
			this.fromVertex = fromVertex;
			this.toVertex = toVertex;
		}
	}

	public static class EdgeComparator implements Comparator<Edge> {
		@Override
		public int compare(Edge e1, Edge e2) {
			if (e1.weight < e2.weight) return -1;
			if (e1.weight > e2.weight) return 1;
			return 0;
		}
	}
}
