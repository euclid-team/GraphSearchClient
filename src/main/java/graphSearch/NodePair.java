package graphSearch;

import java.io.Serializable;

public class NodePair implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4908263048514724838L;
	long first;
	long second;
	
	@Override
	public int hashCode() {
	    return super.hashCode() + (int) first * 24 + (int) second * 24;
	}

	@Override
	public boolean equals(Object other) {
	    if (!(other instanceof NodePair)) return false;
	    return ((NodePair) other).first == this.first && ((NodePair) other).second == this.second;
	}
	
	public NodePair(long _first, long _second) {
		// store the two nodes 
		// normalize: the first node is is less or equal to the second node
		
		if (_first <= _second) {
			first = _first;
			second = _second;
		} else {
			first = _second;
			second = _first;
		}		
	}
	
	public long getFirst() {
		return first;
	}
	
	public long getSecond() {
		return second;
	}

}