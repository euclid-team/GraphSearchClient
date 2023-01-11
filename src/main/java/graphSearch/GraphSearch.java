package graphSearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

import graphClient.XGraphClient;

public class GraphSearch {

	public int afm = 58633; // AFM should be in form 5XXXX
	public String firstname = "Anastasios";
	public String lastname = "Fragkopoulos";

	XGraphClient xgraph;

	public GraphSearch(XGraphClient xgraph) {
		this.xgraph = xgraph;
	}

	public void DFS(long startNode, SGraph graph) {
		Stack<Long> stack = new Stack<>();
		ArrayList<Long> visited = new ArrayList<>();

		stack.push(startNode);

		while (!stack.isEmpty()) {
			startNode = stack.pop();
			if (!visited.contains(startNode)) {
				visited.add(startNode);
				long[] neighbors = xgraph.getNeighborsOf(startNode);
				for (int i = 0; i < neighbors.length; i++) {
					stack.push(neighbors[i]);
					if (!visited.contains(neighbors[i])) {
						SEdge newEdge = new SEdge(startNode, neighbors[i],
								xgraph.getEdgeWeight(startNode, neighbors[i]));
						graph.addEdge(newEdge);
					}
				}
			}
		}
	}

	public ArrayList<SEdge> Kruskal(SGraph graph) {
		ArrayList<SEdge> edgeList = graph.getEdges();
		ArrayList<Long> nodeList = graph.getNodes();
		DisjointSet nodes = new DisjointSet(nodeList);

		ArrayList<SEdge> result = new ArrayList<>();

		SEdge currentEdge;
		for (int i = 0; i < edgeList.size(); i++) {
			currentEdge = edgeList.get(i);
			if (nodes.find(currentEdge.nodeOne) != nodes.find(currentEdge.nodeTwo)) {
				nodes.union(currentEdge.nodeOne, currentEdge.nodeTwo);
				result.add(currentEdge);
			}
		}
		return result;
	}

	public HashMap<Long, Long> maximuSpacingCluster(SGraph graph, int k) {
		ArrayList<SEdge> edgeList = graph.getEdges();
		ArrayList<Long> nodeList = graph.getNodes();
		DisjointSet nodes = new DisjointSet(nodeList);

		ArrayList<SEdge> mst = new ArrayList<>();

		SEdge currentEdge;
		for (int i = 0; i < edgeList.size(); i++) {
			currentEdge = edgeList.get(i);
			if (nodes.find(currentEdge.nodeOne) != nodes.find(currentEdge.nodeTwo)) {
				nodes.union(currentEdge.nodeOne, currentEdge.nodeTwo);
				mst.add(currentEdge);
			}

			if (mst.size() == nodeList.size() - k) {
				break;
			}
		}

		HashMap<Long, Long> results = new HashMap<>();
		for (int i = 0; i < nodeList.size(); i++) {
			Long clusterName = nodes.find(nodeList.get(i));
			results.put(nodeList.get(i), clusterName);
		}

		return results;
	}

	public SEdge findHeviestEdge(SGraph graph) {
		ArrayList<SEdge> edges = graph.getEdges();

		SEdge heviestEdge = edges.get(0);
		SEdge currentEdge;
		for (int i = 0; i < edges.size(); i++) {
			currentEdge = edges.get(i);
			if (currentEdge.weight >= heviestEdge.weight) {
				heviestEdge = currentEdge;
			}
		}

		return heviestEdge;
	}

	public Result findResults() {
		Result res = null;

		// ////////////////////
		// WRITE YOUR OWN CODE
		// ////////////////////

		// Getting first node of the xgraph
		long firstNode = xgraph.firstNode();

		// Making a graph
		SGraph graph = new SGraph();

		// DFS algorithm
		DFS(firstNode, graph);

		// Creating Result object
		res = new Result();

		// Saving Results
		res.n = graph.getNodes().size();
		res.m = graph.getEdges().size();
		res.heaviestEdge = findHeviestEdge(graph);
		res.sGraph = graph;

		// Bonus
		res.mst.addAll(Kruskal(graph));
		res.maxSpacingClusteringLabels = maximuSpacingCluster(graph, xgraph.getNumOfClusters());

		return res;
	}

}
