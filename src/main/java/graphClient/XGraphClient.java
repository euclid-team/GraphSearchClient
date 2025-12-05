package graphClient;

import graphSearch.GraphSearch;
import graphSearch.Result;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;

public class XGraphClient {
	// Server address
	String serverHost;
	int serverPort;
	int executionMode;
	int nodes;
	long seed;

	ObjectInputStream in;
	ObjectOutputStream out;

	boolean verbose; // print information during the execution

	public XGraphClient(boolean parVerbose, String parServerHost, int parServerPort, int parExecutionMode, int parnodes,
						long parSeed) {
		serverHost = parServerHost;
		serverPort = parServerPort;
		executionMode = parExecutionMode;
		verbose = parVerbose;
		nodes = parnodes;
		seed = parSeed;
	}

	public void initiate(GraphSearch gsearch) {
		System.out.println("GraphSearchClient " + Finals.VERSION + " is starting ...");

		System.out.println("GraphSearchClient is trying to connect to " + serverHost + " at port " + serverPort);

		Socket socket = null;
		try {
			socket = new Socket(InetAddress.getByName(serverHost), serverPort);

			System.out.println("GraphSearchClient: connection established !");

			out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));

			out.writeUTF(Finals.VERSION);
			out.flush();

			out.writeInt(gsearch.afm);
			out.flush();

			out.writeUTF(gsearch.firstname);
			out.flush();

			out.writeUTF(gsearch.lastname);
			out.flush();

			out.writeInt(executionMode);
			out.flush();

			out.writeInt(nodes);
			out.flush();

			out.writeLong(seed);
			out.flush();

			in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

			if (in.readBoolean()) {

				System.out.println("OK, the XGraph is created by XGraphServer!");

				Result result = gsearch.findResults();

				if (result == null) {
					System.err.println("Error: Result Object is null!");
					throw new Exception("Error: Result Object is null!");
				}

				System.out.println("Sending results to the server (large items are replaced with digests): " + result);
				out.writeObject(result);
				out.flush();

				boolean succeeded = in.readBoolean();
				long assessment = in.readLong();
				ArrayList<String> optionalMsg = (ArrayList<String>) in.readObject();
				int nodes = in.readInt();
				long seed = in.readLong();

				System.out.println("\nThe XGraph was created for nodes = '" + nodes + "' and seed = '" + seed + "'.");

				System.out.println("\nVerification of the submitted answer");
				System.out.println("------------------------------------");
				System.out.println(result.assessmentInfo(assessment));

				if (succeeded) {
					boolean bonusQuestions = Result.assessmentBonusQuestions(assessment);
					System.out.println("\nXGraphServer response: CORRECT answer to compulsory questions! Bonus: "
							+ (bonusQuestions ? "YES" : "NO"));

					String proof = in.readUTF();
					if (!proof.equals("NULL"))
						System.out.println("\nPROOF OF PARTICIPATION: " + proof + "\n");

					// TODO: BRIBERY GAME: disabled
//					System.out.println("\nDeGroot game");
//					DeGrootOpinionState deGrootOpinionState = (DeGrootOpinionState) in
//							.readObject();
//					deGrootOpinionState.prettyPrint(Finals.MAX_NODES_TO_PRINT);
				} else {
					// Failure
					String errmsg = in.readUTF();

					System.out.println("\nXGraphSearchServer response: Execution failed!");

					System.out.println("\nFailure description: " + errmsg);
				}

				System.out.println("Additional Information from GraphSearchServer:");
				System.out.println(optionalMsg);

				in.close();
				out.close();
			} else {
				System.err.println("\nThe server rejected the execution!!!\n");
				String errmsg = in.readUTF();
				System.err.println(errmsg);

				in.close();
				out.close();
				System.exit(0);
			}
			socket.close();

		} catch (SocketException e) {
			System.err.println("Could not connect to host:" + serverHost + ", port:" + serverPort);
			System.err.println("Are you sure that the server is up and running at this address and port?");

			System.err.println(e);
			System.exit(-1);
			// e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e);
			System.exit(-1);
			// e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					System.err.println("Failed to close the socket");
				}
			}
		}

	}

	public long firstNode() {
		long ret = -1;
		try {
			if (verbose) {
				System.out.println("Requesting the first node of the graph ...");
			}
			out.writeObject("firstNode()");
			out.flush();

			ret = in.readLong();
			if (verbose) {
				System.out.println("Received the first node: " + ret);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public long[] getNeighborsOf(long currentNode) {
		long[] ret = null;

		try {
			if (verbose) {
				System.out.println("Requesting the Neighbors of node " + currentNode + " ...");
			}
			out.writeObject("getNeighborsOf(*)");
			out.flush();

			out.writeLong(currentNode);
			out.flush();

			ret = (long[]) in.readObject();
			if (verbose) {
				System.out.println("Received the Neighbors: " + Arrays.toString(ret));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public Long getEdgeWeight(long firstNode, long secondNode) {
		Long weight = null;

		try {
			if (verbose) {
				System.out.println("Requesting info about (potential) edge between node " + firstNode + " and node " + secondNode);
			}
			out.writeObject("getEdgeInfo(*)");
			out.flush();

			out.writeLong(firstNode);
			out.flush();

			out.writeLong(secondNode);
			out.flush();

			weight = (Long) in.readObject();
			if (verbose) {
				System.out.println("Received the edge weight: " + weight == null? weight: weight.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return weight;
	}

	public Integer getNumOfClusters() {
		Integer numOfClusters = null;

		try {
			if (verbose) {
				System.out.println("Requesting the number of clusters (parameter k).");
			}
			out.writeObject("getNumOfClusters(*)");
			out.flush();

			numOfClusters = (Integer) in.readObject();
			if (verbose) {
				System.out.println("Received the number of clusters (parameter k): " + numOfClusters == null? numOfClusters: numOfClusters.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return numOfClusters;
	}

	public static void main(String[] args) {
		String serverHost;
		int serverPort;
		int executionMode;
		boolean verbose;

		System.out.println("Usage" + "java -cp <jarfile.jar> graphSearch.XGraphClient "
				+ "[<verbose>] [<server-host>] [<server-port>] [<execution mode>] [<nodes>] [<seed>]");
		// System.out.println("\n** The values for parameters <nodes>(in [5,100]) and
		// <seed> are optional! They should only be used for debugging reasons.");

		// Verbose mode
		if (args.length >= 1) {
			verbose = Boolean.parseBoolean(args[0]);
		} else {
			// default parameter values
			verbose = Finals.DEFAULT_VERBOSE_MODE;
		}

		// Server address
		if (args.length >= 2) {
			serverHost = args[1];
		} else {
			// default parameter values
			serverHost = Finals.DEFAULT_HOST;
		}

		// Server port
		if (args.length >= 3) {
			serverPort = Integer.parseInt(args[2]);
		} else {
			// default parameter values
			serverPort = Finals.DEFAULT_PORT;
		}

		// Execution mode
		if (args.length >= 4) {
			executionMode = Integer.parseInt(args[3]);
		} else {
			// default parameter values
			executionMode = Finals.DEFAULT_EXECUTION_MODE;
		}

		// Problem parameters
		int n;
		long seed;

		if (args.length >= 5) {
			n = Integer.parseInt(args[4]);
		} else {
			// default parameter values
			n = Finals.DEFAULT_N;
		}

		if (args.length >= 6) {
			seed = Long.parseLong(args[5]);
		} else {
			// default parameter values
			seed = Finals.DEFAULT_SEED;
		}

		XGraphClient client = new XGraphClient(verbose, serverHost, serverPort, executionMode, n, seed);

		GraphSearch gsearch = new GraphSearch(client);

		client.initiate(gsearch);

	}
}
