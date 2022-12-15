package graphGame;

import java.io.Serializable;

public class NodeBribery implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2140285828088798857L;
	/**
	 * 
	 */
	private long node;
	private double bribe;
	
	public NodeBribery(long node, double bribe) {
		this.setNode(node);
		this.setBribe(bribe);
	}

	public long getNode() {
		return node;
	}

	private void setNode(long node) {
		this.node = node;
	}

	public double getBribe() {
		return bribe;
	}

	private void setBribe(double bribe) {
		this.bribe = bribe;
	}

}
