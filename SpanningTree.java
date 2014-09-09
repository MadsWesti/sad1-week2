import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpanningTree {
	public static void main(String[] args) {
		Graph g = new Graph();
		Scanner sc;
		try {
			sc = new Scanner(new File("USA-highway-miles.in"));
			while(sc.hasNext()) {
				String token = sc.nextLine();
				if(token.endsWith("]")){
					Matcher m = Pattern.compile("(.*?)--(.*?) \\[(.*?)\\]").matcher(token);
					while(m.find()) {
						g.addEdge(m.group(1), m.group(2), Double.parseDouble(m.group(3)));
					}	
				}
				else{
					g.addNode(token.trim());
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static class Graph {
		HashMap<String,HashSet<Edge>> adjacencyList = new HashMap<String,HashSet<Edge>>();
		
		public void addEdge(String v, String w, double weight){
			Edge e = new Edge(v, w, weight);
			adjacencyList.get(v).add(e);
			adjacencyList.get(w).add(e);
		}
		
		public void addNode(String node){
			adjacencyList.put(node, new HashSet<Edge>());
		}
	}
	
	private static class Edge{
		String fromVertex;
		String toVertex;
		double weight = 0.0;

		public Edge(String fromVertex, String toVertex, double weight){
			this.weight = weight;
			this.fromVertex = fromVertex;
			this.toVertex = toVertex;
		}
	}
}
