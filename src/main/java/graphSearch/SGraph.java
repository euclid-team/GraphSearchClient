package graphSearch;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import graphClient.ByteUtils;
import graphClient.Tools;

/**
 * Auxialiary Class implementing a simple (no parallel edges) undirected Graph
 */
public class SGraph implements Serializable {
    /**
     * Simple Undirected Graph
     *
     */
    private static final long serialVersionUID = -1681097005516868677L;
    Set<Long> V;
    Set<SEdge> E;
    HashMap<AbstractMap.SimpleEntry<Long, Long>, SEdge> D;

    public SGraph() {
        // Looses item order in Serialization (Java 1.8) - Derialization (Java 1.7)
//		V = new HashSet<Long>();
//		E = new HashSet<DEdge>();

        V = new TreeSet<Long>();
        E = new TreeSet<SEdge>();
        D = new HashMap<AbstractMap.SimpleEntry<Long, Long>, SEdge>();

        // LinkedHashSet keeps the insertion order
//		V = new LinkedHashSet<Long>();
//		E = new LinkedHashSet<DEdge>();
    }

    public ArrayList<Long> getNodes() {
        ArrayList<Long> nodeList = new ArrayList<Long>();

        nodeList.addAll(V);

        return nodeList;
    }

    public ArrayList<SEdge> getEdges() {
        ArrayList<SEdge> edgeList = new ArrayList<SEdge>();

//		for (SEdge e:E) {
//			System.out.println(e);
//		}
        edgeList.addAll(E);

        return edgeList;
    }

    public void addNode(Long v) {
        V.add(v);
    }

    /**
     * Add a new edge
     * @param e
     */
    public void addEdge(SEdge e) {
        if (!V.contains(e.nodeOne)) {
            V.add(e.nodeOne);
        }
        if (!V.contains(e.nodeTwo)) {
            V.add(e.nodeTwo);
        }
        E.add(e);
        // TODO: Warning about casting
        D.put((AbstractMap.SimpleEntry<Long, Long>) new AbstractMap.SimpleEntry(e.nodeOne, e.nodeTwo), e);
    }

    /**
     * Add an edge connecting nodes h and t
     * @param h
     * @param t
     */
    public void addEdge(Long h, Long t) {
        addEdge(new SEdge(h, t));
    }


    /**
     * Get an edge
     * If the edge does not exist, return null
     * @param h
     * @param t
     * @return
     */
    public SEdge getEdge(Long h, Long t) {
        if (!V.contains(h) || !V.contains(t)) {
            return null;
        }
        SEdge sEdge = null;

        if (h < t) {
            sEdge = D.get(new AbstractMap.SimpleEntry(h, t));
        } else {
            sEdge = D.get(new AbstractMap.SimpleEntry(t, h));
        }

        return sEdge;
    }

    /**
     * Get the neighbors of a node
     * @param node
     * @return
     */
    public Long[] getNeighborsOf(Long node) {
        Long[] neighbors = null;

        Set<Long> neighborSet = new HashSet<Long>();
        for (SEdge e : E) {
            if (e.contains(node)) {
                neighborSet.add(e.otherNode(node));
            }
        }
        neighbors = new Long[neighborSet.size()];
        int i = 0;
        for (Long v : neighborSet) {
            neighbors[i] = v;
            i++;
        }
        return neighbors;
    }

    @Override
    public String toString() {
        String str = "DGraph.";

        str += " Nodes: ";
        for (Long v : V) {
            str += (v + ", ");
        }

        str += " DEdges: ";
        for (SEdge e : E) {
            str += "(" + e.toString() + "), ";
        }

        return str;
    }

    // Overriding equals() to compare two DGraph objects
    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /*
         * Check if o is an instance of DEdge or not "null instanceof [type]" also
         * returns false
         */
        if (!(o instanceof SGraph)) {
            return false;
        }

        // typecast o to DEdge so that we can compare data members
        SGraph g = (SGraph) o;

        return V.equals(g.V) && E.equals(g.E);

    }

    public String findDifferences(SGraph g) {
        String strdiff = "";

        HashSet<Long> nodes = new HashSet<Long>(V);
        nodes.removeAll(g.V);
        int size = nodes.size();
        if (size > 0) {
            strdiff += ("#Nodes missing: " + size + ". ");
        }

        nodes = new HashSet<Long>(g.V);
        nodes.removeAll(V);
        size = nodes.size();
        if (size > 0) {
            strdiff += ("#Nodes wrong: " + size + ". ");
        }

        HashSet<SEdge> edges = new HashSet<SEdge>(E);
        edges.removeAll(g.E);
        size = edges.size();
        if (size > 0) {
            strdiff += ("#Edges missing: " + size + ". ");
        }

        edges = new HashSet<SEdge>(g.E);
        edges.removeAll(E);
        size = edges.size();
        if (size > 0) {
            strdiff += ("#Edges wrong: " + size + ". ");
//    		strdiff += ("Correct number: " + E.size() + ". ");
//    		strdiff += ("Answer number: " + g.E.size() + ". ");
//    		strdiff += "\n" + E.toString() + "\n" + g.E.toString() + "\n";
        }

        return strdiff;
    }

    public static boolean deepEqualMST(TreeSet<SEdge> mst1, TreeSet<SEdge> mst2) {
        // If the MSTs are the same object then return true
        if (mst1 == mst2) {
            return true;
        }

        int size1 = mst1.size();
        int size2 = mst2.size();
        if (size1 != size2) {
            return false;
        }

        Iterator<SEdge> iter1 = mst1.iterator();
        Iterator<SEdge> iter2 = mst2.iterator();

        while (iter1.hasNext()) {
            SEdge edge1 = iter1.next();
            SEdge edge2 = iter2.next();
            if (! edge1.equals(edge2)) {
                return false;
            }
        }

        return true;
    }

    public byte[] getDigestFromRawComputation() {
        byte[] digest = null;

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            System.exit(-1);
        }
        // MessageDigest md = MessageDigest.getInstance("MD5");
        md.reset();

        for (Long v : V) {
            md.update(ByteUtils.longToBytes(v));
        }

        for (SEdge e : E) {
            md.update(ByteUtils.longToBytes(e.nodeOne));
            md.update(ByteUtils.longToBytes(e.nodeTwo));
        }

        digest = md.digest();

        return digest;
    }

    public String getHexDigest() {
        // https://stackoverflow.com/questions/2836646/java-serializable-object-to-byte-array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        String strHexDigest = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(V);
            out.writeObject(E);
            out.flush();
            byte[] yourBytes = bos.toByteArray();
            // strHexDigest = DatatypeConverter.printHexBinary(yourBytes);
            strHexDigest = Tools.SHAsum(yourBytes);
        } catch (Exception ex) {
            System.err.println(ex); // ignore close exception
            System.exit(-1);
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }

        return strHexDigest;
    }

    public byte[] getDigest() {
        // https://stackoverflow.com/questions/2836646/java-serializable-object-to-byte-array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] digest = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(V);
            out.writeObject(E);
            out.flush();
            byte[] byteArray = bos.toByteArray();
            digest = Tools.SHAbsum(byteArray);

            // strHexDigest = Tools.SHAsum(yourBytes);
        } catch (Exception ex) {
            System.err.println(ex); // ignore close exception
            System.exit(-1);
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }

        return digest;
    }

    public static void test(String[] args) {
        SEdge e1 = new SEdge(100L, 200L);
        SEdge e2 = new SEdge(100L, 200L);

        System.out.println("DEdges");
        System.out.println(e1 == e2);
        System.out.println(e1.equals(e2));
        System.out.println(e1.compareTo(e2));

        HashSet<SEdge> E1 = new HashSet<SEdge>();
        HashSet<SEdge> E2 = new HashSet<SEdge>();
        E1.add(e1);
        E2.add(e2);
        System.out.println(E1.equals(E2));

        System.out.println("DGraphs");
        SGraph g1 = new SGraph();
        SGraph g2 = new SGraph();
        System.out.println(g1.equals(g2));

        g1.addEdge(e1);
        System.out.println(g1.equals(g2));

        g2.addEdge(e2);
        System.out.println(g1.equals(g2));

        g1.addEdge(50L, 300L);
        g2.addEdge(50L, 300L);
        System.out.println(g1.equals(g2));

        g1.addEdge(50L, 300L);

        System.out.println(g1.getHexDigest());
        System.out.println(g2.getHexDigest());
        System.out.println(g1);
        System.out.println(g2);

        System.out.println("Long hashCode()");
        Long d1 = 10L;
        System.out.println(d1.hashCode());
        Long d2 = 9L;
        System.out.println(d2.hashCode());
        d2++;
        System.out.println(d2.hashCode());

    }
}
