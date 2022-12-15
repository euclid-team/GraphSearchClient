package graphSearch;

import java.io.Serializable;

public class SEdge implements Serializable, Comparable<Object> {
	/**
	 * Undirected Edge, Default weight = 1
	 * 
	 */
	private static final long serialVersionUID = -5102809899392580542L;
	Long nodeOne;
	Long nodeTwo;
	Long weight;

	/**
	 * Constructor
	 * 
	 * @param h
	 * @param t
	 * @param w
	 */
	public SEdge(Long h, Long t, Long w) {
		if (h <= t) {
			nodeOne = h;
			nodeTwo = t;
		} else {
			nodeOne = t;
			nodeTwo = h;
		}
		weight = w;
	}

	/**
	 * Constructor with default weight 1
	 * 
	 * @param h
	 * @param t
	 */
	public SEdge(Long h, Long t) {
		this(h, t, 1L);
	}

	public Long getNodeOne() {
		return nodeOne;
	}

	public Long getNodeTwo() {
		return nodeTwo;
	}

	public Long getWeight() {
		return weight;
	}

	@Override
	public int hashCode() {
		// int result = (int) (d ^ (id >>> 32));
		int result = nodeOne.hashCode();
		result = 31 * result + nodeTwo.hashCode();
		result = 31 * result + weight.hashCode();
		return result;
	}

	// Overriding equals() to compare two DEdge objects
	@Override
	public boolean equals(Object o) {

		// If the object is compared with itself then return true
		if (o == this) {
			return true;
		}
//  
		/*
		 * Check if o is an instance of DEdge or not "null instanceof [type]" also
		 * returns false
		 */
		if (!(o instanceof SEdge)) {
			return false;
		}
//          
		// typecast o to DEdge so that we can compare data members
		SEdge e = (SEdge) o;
//          
		// Compare the data members and return accordingly
		return Long.compare(nodeOne, e.nodeOne) == 0 && Long.compare(nodeTwo, e.nodeTwo) == 0
				&& Long.compare(weight, e.weight) == 0;

	}

	@Override
	public String toString() {
		String str = "DEdge between " + nodeOne + " and " + nodeTwo + " with weight " + weight;
		return str;
	}

	public String toStringShort() {
		String str = nodeOne + " " + nodeTwo + " " + weight;
		return str;
	}

	public boolean contains(Long node) {
		if ((Long.compare(node, this.nodeOne) == 0) || (Long.compare(node, this.nodeTwo) == 0)) {
			return true;
		} else {
			return false;
		}
	}

	/***
	 * Compare the edge weights
	 */
	@Override
	public int compareTo(Object o) {

		SEdge e = (SEdge) o;

		int cmp = weight.compareTo(e.weight);

		return cmp;
	}
}