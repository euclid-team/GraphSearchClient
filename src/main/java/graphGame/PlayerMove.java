package graphGame;

import graphClient.Finals;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

import com.google.gson.Gson;

public class PlayerMove implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6424069664913571699L;
	public LinkedList<NodeBribery> actions;
	private int numOfNodeBribes;
	private double totalBribeAmount;
	private int maxNumOfNodeBribes;
	private double maxTotalBribeAmount;

	public PlayerMove(int maxNumOfNodeBribes, double maxTotalBribeAmount) {
		actions = new LinkedList<NodeBribery>();
		numOfNodeBribes = 0;
		totalBribeAmount = 0;
		this.maxNumOfNodeBribes = maxNumOfNodeBribes;
		this.maxTotalBribeAmount = maxTotalBribeAmount;
	}

	public void clear() {
		actions.clear();
		numOfNodeBribes = 0;
		totalBribeAmount = 0;
	}

	public void addNodeBribery(long node, double bribe) {
		if (numOfNodeBribes >= maxNumOfNodeBribes) {
			System.err.println("Reached max number of NodeBribes ("
					+ maxNumOfNodeBribes + "). Action (" + node + ", " + bribe
					+ ") ignored!");
			return;
		}

		if (bribe < 0) {
			System.err.println("Invalid bribe amount: " + bribe + ". Action ("
					+ node + ", " + bribe + ") ignored!");
			return;
		}

		if (totalBribeAmount + bribe > maxTotalBribeAmount) {
			System.err
					.println("Suggested bribe would exceed the maxTotalBribe = "
							+ maxTotalBribeAmount
							+ ". Current total bribe amount = "
							+ totalBribeAmount
							+ ". Action ("
							+ node
							+ ", "
							+ bribe + ") ignored!");
			return;
		}

		NodeBribery nodeBribery = new NodeBribery(node, bribe);
		actions.add(nodeBribery);
		numOfNodeBribes++;
		totalBribeAmount += bribe;

	}

	public boolean checkData() {
		boolean validData = true;
		
		// Number of items
		if (actions.size() > graphClient.Finals.DEFAULT_MAX_NUM_OF_NODE_BRIBES) {
			validData = false;
		}

		// Total weight
		double totalWeight = 0; 
		for (NodeBribery bribe:actions) {
			totalWeight += bribe.getBribe();
		}
		
		if (totalWeight > graphClient.Finals.DEFAULT_MAX_TOTAL_BRIBE) {
			validData = false;
		}

		return validData;
	}
	
	public static void main(String[] args) {
		PlayerMove playerAllBriberies = new PlayerMove(
				Finals.DEFAULT_MAX_NUM_OF_NODE_BRIBES,
				Finals.DEFAULT_MAX_TOTAL_BRIBE);

		Gson gson = new Gson();

		String json = gson.toJson(playerAllBriberies);

		System.out.println(json);

		playerAllBriberies.addNodeBribery(5, 33.30);
		playerAllBriberies.addNodeBribery(2, 50);
		playerAllBriberies.addNodeBribery(1, 10);
		playerAllBriberies.addNodeBribery(12, 180);
		playerAllBriberies.addNodeBribery(7, 15.70);
		playerAllBriberies.addNodeBribery(9, 70);
		playerAllBriberies.addNodeBribery(17, 50);

		json = gson.toJson(playerAllBriberies);

		System.out.println(json);

	}
}
