package graphSearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

import graphClient.XGraphClient;

public class GraphSearch {

	public int afm = 141516; // AFM should be in form 5XXXX
	public String firstname = "Jane";
	public String lastname = "Doe";

	XGraphClient xgraph;

	public GraphSearch(XGraphClient xgraph) {
		this.xgraph = xgraph;
	}


	public Result findResults() {
		Result res = null;

		// ////////////////////
		// WRITE YOUR OWN CODE
		// ////////////////////

		// EXAMPLE CODE

		// Example of creating the Result object
		res = new Result();

		// Retrieve the number of clusters (parameter k of maximum spacing clustering)
		int numOfClusters = xgraph.getNumOfClusters();

		// Retrieve the first node of the unknown graph
		long firstNode = xgraph.firstNode();

		// Print the ID of the first node
		System.out.println("The id of the first node is: " + firstNode);

		// Inform that GraphSearch starts
		System.out.println("Graph search from node : " + firstNode);

		// Retrieve the neighbors of the first node
		long[] neighbors = xgraph.getNeighborsOf(firstNode);

		// Convert long[] to ArrayList<Long>
//		Long[] a = new Long[10];
//		Arrays.fill(a, 123L);
//		ArrayList<Long> n = new ArrayList<Long>(Arrays.asList(a));
//
//		long[] input = new long[]{1,2,3,4};
//		List<Long> output = new ArrayList<Long>();
//		for (long value : input) {
//		    output.add(value);
//		}
//
		// Print all the neighbors of the firstNode
		// Approach A
		int numOfNeighbors = neighbors.length;

		for (int i = 0; i < numOfNeighbors; i++) {
			System.out.println("Neighbor " + i + ", id: " + neighbors[i]);
		}

		// newline
		System.out.println();

		// Print all the neighbors of the firstNode
		// Approach B
		for (long id : neighbors) {
			System.out.println("Neighbor id: " + id);
		}

		// WRITE ALL RESULTS INTO THE RESULT OBJECT

		// COMPULSORY questions
		// res.n = (Number of nodes, type: int)
		// res.m =  (Number of edges, type: int)
		// res.heaviestEdge = (The nodes of the heaviest edge of the undirected graph, type: SEdge)
		// res.sGraph = (The xgraph as an undirected graph with the original node IDs and edge weights, type: SGraph)

		// BONUS
		// res.mst = (The edges of the MST, type: TreeSet<SEdge>)
		// res.maxSpacingClusteringLabels = (The node labels of a maximum spacing clustering
		// of the graph nodes, type: HashMap<Long, Long> )


		// Return the result Object with the results of the computation
		return res;
	}

}
