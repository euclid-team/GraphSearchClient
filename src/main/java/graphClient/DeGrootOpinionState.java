package graphClient;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;

public class DeGrootOpinionState implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8159056981935661032L;
	private HashMap<Long, Double> opinionVector;

	public DeGrootOpinionState(double[] opinions, HashMap<Integer, Long> uuids) {
		opinionVector = new HashMap<Long, Double>();

		// Ideally, opinions.length and uuids.size() should be equal but is not
		// because the politicians don't have a dedicated uuid
		for (int i = 0; i < uuids.size(); i++) {
			this.opinionVector.put(uuids.get(i), opinions[i]);
		}

		System.out.println();
	}

	public double averageOpinion() {
		double average = 0;
		double count = 0;
		for (Entry<Long, Double> entry : opinionVector.entrySet()) {
			Double value = entry.getValue();
			average += value.doubleValue();
			count++;
		}
		average = average / count;
		return average;
	}

	public void prettyPrint(int maxNodesToPrint) {
		double average = this.averageOpinion();
		int size = opinionVector.size();
		if (size <= maxNodesToPrint) {
			int count = 0;
			for (Entry<Long, Double> entry : opinionVector.entrySet()) {
				Long key = entry.getKey();
				Double value = entry.getValue();
				System.out.println(count + ", uuid: " + key.toString() + ", opinion: " + value.toString());
				count++;
			}
		} else {
			System.out.println("Skipping detailed printout (number of nodes:" + size + " is larger than the limit " + maxNodesToPrint + ")");
		}
		System.out.println("My Opinion: 0.0");
		System.out.println("Opponent Opinion: 1.0");
		System.out.println("Average opinion: " + average);
		System.out.println("Winner: "
				+ ((average < 0.5) ? ("me") : ("opponent")));
	}
}