package graphClient;

public class Finals {
	public static final String VERSION = "2025";

	public static final boolean DEFAULT_VERBOSE_MODE = false;

	public static final String DEFAULT_HOST = "euclid.ee.duth.gr";
	public static final int DEFAULT_PORT = 4475;

	// Used by DeGrootOpinionState.prettyPrint()
	public static final int MAX_NODES_TO_PRINT = 100;

	// Execution modes
	// 0: Normal mode
	// 1: Graph game 1
	// 2: Graph game 2
	public static final int DEFAULT_EXECUTION_MODE = 0;

	public static final int DEFAULT_N = -1;   // The server decides
	public static final long DEFAULT_SEED = -1; // The server decides

	public static final int DEFAULT_MAX_NUM_OF_NODE_BRIBES = 5; // The maximum number of nodes that are bribed
	public static final double DEFAULT_MAX_TOTAL_BRIBE = 200; // The maximum total amount used in the DeGroot game

	// Edge weights
	public static final int DEFAULT_MIN_EDGE_WEIGHT = 100;
	public static final int DEFAULT_MAX_EDGE_WEIGHT = 100000;
}

