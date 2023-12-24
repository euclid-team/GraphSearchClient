package graphSearch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import graphClient.Finals;
import graphClient.Tools;
import graphGame.PlayerMove;

public class Result implements Serializable {

	private static final long serialVersionUID = -4053476480114048838L;

	public static final int ASSESSMENT_STRING_LENGTH = 17;
	public static final int BIT_DEGREE_ARRAY = 0b1;
	public static final int BIT_MAX_DISTANCE_NODE_ID = 0b1 << 1;
	public static final int BIT_MAX_DISTANCE = 0b1 << 2;
	public static final int BIT_MAX_DEGREE_NODE_ID = 0b1 << 3;
	public static final int BIT_MAX_DEGREE = 0b1 << 4;
	public static final int BIT_M = 0b1 << 5;
	public static final int BIT_N = 0b1 << 6;
	public static final int BIT_MAX_NUM_OF_COMMON_NEIGHBORS = 0b1 << 7;
	public static final int BIT_PAIR_WITH_MAX_NUM_OF_COMMON_NEIGHBORS = 0b1 << 8;
	public static final int BIT_NUM_NODES_WITH_ODD_DEGREE = 0b1 << 9;
	public static final int BIT_NUM_NODES_WITH_EVEN_DEGREE = 0b1 << 10;
	public static final int BIT_BFS_NODE_SEQUENCE = 0b1 << 11;
	public static final int BIT_DFS_NODE_SEQUENCE = 0b1 << 12;
	public static final int BIT_BFS_TREE = 0b1 << 13;
	public static final int BIT_DFS_TREE = 0b1 << 14;
	public static final int BIT_EULER_GRAPH = 0b1 << 15;
	public static final int BIT_EULER_CYCLE = 0b1 << 16;
	public static final int BIT_MST = 0b1 << 17;
	public static final int BIT_CLUSTERING = 0b1 << 18;
	public static final int BIT_BIPARTITE = 0b1 << 19;
	public static final int BIT_ODD_CYCLE_EDGE = 0b1 << 20;
	public static final int BIT_HEAVIEST_EDGE = 0b1 << 21;
	public static final int BIT_MAX_SPACING_CLUSTERING = 0b1 << 22;
	public static final int BIT_SGRAPH = 0b1 << 23; // The original graph and an SGraph object

	public static final int BIT_MASK_GRAPH_SEARCH_2018_COMPULSORY = BIT_N | BIT_M | BIT_NUM_NODES_WITH_ODD_DEGREE
			| BIT_NUM_NODES_WITH_EVEN_DEGREE;
	public static final int BIT_MASK_GRAPH_SEARCH_2018_BONUS = BIT_PAIR_WITH_MAX_NUM_OF_COMMON_NEIGHBORS
			| BIT_MAX_NUM_OF_COMMON_NEIGHBORS;

	public static final int BIT_MASK_GRAPH_SEARCH_2019_COMPULSORY = BIT_N | BIT_M | BIT_BFS_TREE
			| BIT_BFS_NODE_SEQUENCE;
	public static final int BIT_MASK_GRAPH_SEARCH_2019_BONUS = BIT_DFS_TREE | BIT_DFS_NODE_SEQUENCE;

	public static final int BIT_MASK_GRAPH_SEARCH_2020_COMPULSORY = BIT_N | BIT_M | BIT_DFS_NODE_SEQUENCE
			| BIT_NUM_NODES_WITH_ODD_DEGREE | BIT_EULER_GRAPH;
	public static final int BIT_MASK_GRAPH_SEARCH_2020_BONUS = BIT_EULER_CYCLE;

	public static final int BIT_MASK_GRAPH_SEARCH_2021_COMPULSORY = BIT_N | BIT_M;
	public static final int BIT_MASK_GRAPH_SEARCH_2021_BONUS = BIT_BIPARTITE | BIT_ODD_CYCLE_EDGE;

	public static final int BIT_MASK_GRAPH_SEARCH_2022_COMPULSORY = BIT_N | BIT_M | BIT_HEAVIEST_EDGE | BIT_SGRAPH;
	public static final int BIT_MASK_GRAPH_SEARCH_2022_BONUS = BIT_MST | BIT_MAX_SPACING_CLUSTERING;

	public static final int BIT_MASK_GRAPH_SEARCH_2023_COMPULSORY = BIT_N | BIT_M | BIT_DEGREE_ARRAY;
	public static final int BIT_MASK_GRAPH_SEARCH_2023_BONUS = BIT_BFS_NODE_SEQUENCE | BIT_BFS_TREE;

//	public static final int BIT_MASK_GRAPH_SEARCH_2022_COMPULSORY = BIT_N | BIT_M | BIT_BFS_NODE_SEQUENCE
//			| BIT_MST;
//	public static final int BIT_MASK_GRAPH_SEARCH_2022_BONUS = BIT_CLUSTERING;

	public static boolean assessmentCompulsoryQuestions(long res) {
		// A bit value of 1 indicates failure
		boolean assessmentCompulsory = false;

		long compRes = res & Result.BIT_MASK_GRAPH_SEARCH_2023_COMPULSORY;
		assessmentCompulsory = (compRes == 0);

		return assessmentCompulsory;
	}

	public static boolean assessmentBonusQuestions(long res) {
		// A bit value of 1 indicates failure
		boolean assessmentBonus = false;

		long bonusRes = res & Result.BIT_MASK_GRAPH_SEARCH_2023_BONUS;
		assessmentBonus = (bonusRes == 0);

		return assessmentBonus;
	}

	long maxDegreeNodeID; // The id of a max degree node
	int maxDegree; // The max degree (number of edges attached to the node)
	int n; // The total number of nodes in the graph
	int m; // The total number of edges in the graph
	int numOfNodesWithOddDegree; // Number of nodes with odd degree
	int numOfNodesWithEvenDegree; // Number of nodes with even degree

	long maxDistanceNodeID; // The id of a max distance node
	int maxDistance; // The max distance (number of edges from the start node)

	int[] degreeArray;
	ArrayList<Integer> degreeArrayList; // The degrees of the nodes in decreasing order

	int maxNumOfCommonNeighbors; // The max number of common neighbors between two nodes
	NodePair pairWithMaxNumOfCommonNeighbors;

	ArrayList<Long> bfsNodeSequence;
	SGraph bfsTree;

	ArrayList<Long> dfsNodeSequence;
	SGraph dfsTree;

	boolean graphIsBipartite; // Has to be true if the graph is bipartite, otherwise false
	SEdge oddCycleEdge; // An edge belonging to an odd cycle

	int graphType; // 0: No Euler cycle or path, 1: Euler cycle, 2: Euler path
	ArrayList<Long> eulerData; // The node sequence of the Euler path or cycle, or a list of nodes with odd
	// degree

	SEdge heaviestEdge; // The nodes of the heaviest edge of the undirected graph
	SGraph sGraph; // The xgraph as an undirected graph with the original node IDs and edge weights
	TreeSet<SEdge> mst; // The edges of the MST
	HashMap<Long, Long> maxSpacingClusteringLabels; // The node labels of a maximum spacing clustering of the graph nodes

	public SEdge getHeaviestEdge() {
		return heaviestEdge;
	}

	public TreeSet<SEdge> getMst() {
		return mst;
	}

	public HashMap<Long, Long> getMaxSpacingClustering() {
		return maxSpacingClusteringLabels;
	}

	// These two fields are ignored in the equals() method
	PlayerMove myMove; // My Moves: which nodes do I bribe.
	PlayerMove opponentMove; // Optional field, used only for testing. Which nodes does the opponent player
	// bribe.

	public Result() {
		bfsTree = new SGraph();
		dfsTree = new SGraph();
		bfsNodeSequence = new ArrayList<Long>();
		dfsNodeSequence = new ArrayList<Long>();
		eulerData = new ArrayList<Long>();
		sGraph = new SGraph();
		mst = new TreeSet<SEdge>();
		maxSpacingClusteringLabels = new HashMap<Long, Long>();
		degreeArray = null;
		degreeArrayList = new ArrayList<Integer>();

		// Used for the DeGroot game
//		myMove = new PlayerMove(Finals.DEFAULT_MAX_NUM_OF_NODE_BRIBES, Finals.DEFAULT_MAX_TOTAL_BRIBE);
		// Optional. Used only for testing in the DeGroot game.
//		opponentMove = new PlayerMove(Finals.DEFAULT_MAX_NUM_OF_NODE_BRIBES, Finals.DEFAULT_MAX_TOTAL_BRIBE);
	}

	public Result(long nodeID, int degree, int n, int m) {
		super();
		this.maxDegreeNodeID = nodeID;
		this.maxDegree = degree;
		this.n = n;
		this.m = m;
	}

	public Result(int n, int m) {
		super();
		this.n = n;
		this.m = m;
	}

	public boolean equals(Object obj) {
		boolean ret = false;
		Result tmp = (Result) obj;
		if (tmp.maxDegreeNodeID == this.maxDegreeNodeID && tmp.maxDegree == this.maxDegree && tmp.n == this.n
				&& tmp.m == this.m) {
			ret = true;
		}
		return ret;
	}

	public PlayerMove getMyMove() {
		return myMove;
	}

	public PlayerMove getOpponentMove() {
		return opponentMove;
	}

	public long getMaxDegreeNodeID() {
		return maxDegreeNodeID;
	};

	public int getMaxDegree() {
		return maxDegree;
	};

	public int getN() {
		return n;
	};

	public int getM() {
		return m;
	};

	public int getNumOfNodesWithOddDegree() {
		return numOfNodesWithOddDegree;
	}

	public int getNumOfNodesWithEvenDegree() {
		return numOfNodesWithEvenDegree;
	}

	public long getMaxDistanceNodeID() {
		return maxDistanceNodeID;
	};

	public int getMaxDistance() {
		return maxDistance;
	};

	public int[] getDegreeArray() {
		return degreeArray;
	};

	public ArrayList<Integer> getDegreeArrayList() {
		return degreeArrayList;
	};

	public int getMaxNumOfCommonNeighbors() {
		return maxNumOfCommonNeighbors;
	};

	public NodePair getPairWithMaxNumOfCommonNeighbors() {
		return pairWithMaxNumOfCommonNeighbors;
	};

	public ArrayList<Long> getBfsNodeSequence() {
		return bfsNodeSequence;
	}

	public ArrayList<Long> getDfsNodeSequence() {
		return dfsNodeSequence;
	}

	public SGraph getBfsTree() {
		return bfsTree;
	}

	public SGraph getDfsTree() {
		return dfsTree;
	}

	public SGraph getSGraph() {
		return sGraph;
	}

	public int getGraphType() {
		return graphType;
	};

	public ArrayList<Long> getEulerData() {
		return eulerData;
	};

	public boolean getGraphIsBipartite() {
		return graphIsBipartite;
	};

	public SEdge getOddCycleEdge() {
		return oddCycleEdge;
	};

	public String toString() {
//		String str = "ResultMsg: nodeID:=" + maxDegreeNodeID + ", degree:=" + maxDegree + ", n:=" + n + ", m:=" + m;
		String str = "ResultMsg: n:=" + n + ", m:=" + m
//				+ ", dfsNodeSequence: " + (dfsNodeSequence != null ? Tools.getHexDigestOfObject(dfsNodeSequence) : null)
//				+ ", dfsTree: " + (dfsTree!=null?dfsTree.getHexDigest():null);
//				+ ", bipartite: " + graphIsBipartite
//				+ ", odd cycle edge: " + oddCycleEdge;
//				+ ", Number of odd degree nodes: " + numOfNodesWithOddDegree
//				+ ", Graph Type (0:No euler path or cycle, 1: Euler path, 2: Euler cycle): " + graphType
//				+ ", EulerData: " + (eulerData != null ? Tools.getHexDigestOfObject(eulerData) : null);
//				+ ", heaviestEdge: " + (heaviestEdge != null ? heaviestEdge.toString() : null)
//				+ ", SGraph: " + (sGraph != null ? Tools.getHexDigestOfObject(sGraph) : null)
//				+ ", mst: " + (mst != null ? Tools.getHexDigestOfObject(mst) : null)
//				+ ", maxSpacingClustering: " + (maxSpacingClusteringLabels != null ? Tools.getHexDigestOfObject(maxSpacingClusteringLabels) : null);
				+ ", degreeArray: " + (degreeArray != null ? Tools.getHexDigestOfObject(degreeArray) : null)
				+ ", bfsNodeSequence: " + (bfsNodeSequence != null? Tools.getHexDigestOfObject(bfsNodeSequence):null)
				+ ", bfsTree: " + (bfsTree!=null?bfsTree.getHexDigest():null);
		return str;
	}

	public String toStringLong() {
//		String str = "ResultMsg: nodeID:=" + maxDegreeNodeID + ", degree:=" + maxDegree + ", n:=" + n + ", m:=" + m;
		String str = "ResultMsg: n:=" + n + ", m:=" + m
//				+ ", dfsNodeSequence: " + (dfsNodeSequence != null ? Tools.arrayListToString(dfsNodeSequence) : null)
//				+ ", Number of odd degree nodes: " + numOfNodesWithOddDegree
//				+ ", Graph Type (0:No euler path or cycle, 1: Euler path, 2: Euler cycle): " + graphType
//				+ ", EulerData: " + (eulerData != null ? Tools.arrayListToString(eulerData) : null)
//				+ ", bipartite: " + graphIsBipartite
//				+ ", odd cycle edge: " + oddCycleEdge
//				+ ", heaviestEdge: " + (heaviestEdge != null ? heaviestEdge.toString() : null)
//				+ ", SGraph: " + (sGraph != null ? Tools.getHexDigestOfObject(sGraph) : null)
//				+ ", mst: " + (mst != null ? Tools.getHexDigestOfObject(mst) : null)
//				+ ", maxSpacingClustering: " + (maxSpacingClusteringLabels != null ? Tools.getHexDigestOfObject(maxSpacingClusteringLabels) : null);
				+ ", degreeArray: " + (degreeArray != null ? Tools.arrayListToString(degreeArrayList) : null)
				+ ", bfsNodeSequence: " + (bfsNodeSequence != null ? Tools.arrayListToString(bfsNodeSequence) : null)
				+ ", bfsTree: " + (bfsTree!=null?bfsTree.getHexDigest():null);
		return str;
	}

	public String assessmentInfo(long assessment) {
		String info = "Number of nodes (" + n + ") -> " + (((assessment & BIT_N) == 0) ? "OK" : "Wrong answer");
		info += "\nNumber of edges (" + m + ") -> " + (((assessment & BIT_M) == 0) ? "OK" : "Wrong answer");
//		info += "\nNumber of nodes with odd degree (" + numOfNodesWithOddDegree + ") -> "
//				+ (((assessment & BIT_NUM_NODES_WITH_ODD_DEGREE) == 0) ? "OK" : "Wrong answer");
//		info += "\nNumber of nodes with even degree (" + numOfNodesWithEvenDegree + ") -> "+ (((assessment & BIT_NUM_NODES_WITH_EVEN_DEGREE) == 0)? "OK": "Wrong answer");
		info += "\nSorted degree array -> " + (((assessment & BIT_DEGREE_ARRAY) == 0)? "OK": "Wrong answer");
		info += "\n(optional) BFS Node Sequence -> "+ (((assessment & BIT_BFS_NODE_SEQUENCE) == 0)? "OK": "Wrong answer");
		info += "\n(optional) BFS Tree -> "+ (((assessment & BIT_BFS_TREE) == 0)? "OK": "Wrong answer");
//		info += "\nDFS Node Sequence -> " + (((assessment & BIT_DFS_NODE_SEQUENCE) == 0) ? "OK" : "Wrong answer");
//		info += "\nDFS Graph Type (Euler etc.) -> " + (((assessment & BIT_EULER_GRAPH) == 0) ? "OK" : "Wrong answer");
//		info += "\nHeaviest edge -> "+ (((assessment & BIT_HEAVIEST_EDGE) == 0)? "OK": "Wrong answer");
//		info += "\nSGraph -> "+ (((assessment & BIT_SGRAPH) == 0)? "OK": "Wrong answer");
//		info += "\n(optional) MST -> "+ (((assessment & BIT_MST) == 0)? "OK": "Wrong answer");
//		info += "\n(bonus) DFS Tree -> "+ (((assessment & BIT_DFS_TREE) == 0)? "OK": "Wrong answer");
//		info += "\n(bonus) Pair with Max Num of Common Pairs -> "+ (((assessment & BIT_PAIR_WITH_MAX_NUM_OF_COMMON_NEIGHBORS) == 0)? "OK": "Wrong answer");
//		info += "\n(bonus) Max Num Of Common Neighbors (" + maxNumOfCommonNeighbors + ") -> "+ (((assessment & BIT_MAX_NUM_OF_COMMON_NEIGHBORS) == 0)? "OK": "Wrong answer");
//		info += "\n(optional) Max degree (" + maxDegree + ") -> "+ (((assessment & BIT_MAX_DEGREE) == 0)? "OK": "Wrong answer");
//		info += "\n(optional) Max degree node ("+ maxDegreeNodeID + ") -> " + (((assessment & BIT_MAX_DEGREE_NODE_ID) == 0)? "OK": "Wrong answer");
//		info += "\n(optional) Max distance (" + maxDistance + ") -> " + (((assessment & BIT_MAX_DISTANCE) == 0)? "OK": "Wrong answer");
//		info += "\n(optional) Max distance node (" + maxDistanceNodeID + ") -> " + (((assessment & BIT_MAX_DISTANCE_NODE_ID) == 0)? "OK": "Wrong answer");
//		info += "\n(optional) Euler Cycle/Path or Odd Degree Nodes -> "
//				+ (((assessment & BIT_EULER_CYCLE) == 0) ? "OK" : "Wrong answer");
//		info += "\n(optional) Clustering -> "
//				+ (((assessment & BIT_CLUSTERING) == 0) ? "OK" : "Wrong answer");
//		info += "\n(optional) Bipartite (" + graphIsBipartite + ") -> "
//				+ (((assessment & BIT_BIPARTITE) == 0) ? "OK" : "Wrong answer");
//		info += "\n(optional) Odd Cycle Edge (" + oddCycleEdge + ") -> "
//				+ (((assessment & BIT_ODD_CYCLE_EDGE) == 0) ? "OK" : "Wrong answer");
//		info += "\n(optional) maxSpacingClustering -> "
//		+ (((assessment & BIT_MAX_SPACING_CLUSTERING) == 0) ? "OK" : "Wrong answer");
//		info += "\n(optional) Sorted degree array -> " + (((assessment & BIT_DEGREE_ARRAY) == 0)? "OK": "Wrong answer");
		return info;
	}

}
