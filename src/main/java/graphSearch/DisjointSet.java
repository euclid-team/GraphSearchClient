package graphSearch;

import java.util.ArrayList;

// Based on code from 
// https://www.techiedelight.com/disjoint-set-data-structure-union-find-algorithm/

import java.util.HashMap;
import java.util.Map;
//import java.util.stream.LongStream;

// A class to represent a disjoint set
public class DisjointSet
{
    private Map<Long, Long> parent = new HashMap<>();

    // Constructor from array
    public DisjointSet(Long[] universe)
    {
        // create `n` disjoint sets (one for each item)
        for (Long i: universe) {
            parent.put(i, i);
        }
    }

    // Constructor from ArrayList
    public DisjointSet(ArrayList<Long> universe)
    {
        // create `n` disjoint sets (one for each item)
        for (Long i: universe) {
            parent.put(i, i);
        }
    }
    
    // Constructor from ArrayList
//    public DisjointSet(LongStream longStream)
//    {
//        // create `n` disjoint sets (one for each item)   	
//        longStream.forEach((i) -> parent.put(i, i));
//    }
    
    // Find the root of the set in which element `k` belongs
    public Long find(Long k)
    {
        // if `k` is root
        if (parent.get(k) == k) {
            return k;
        }

        // recur for the parent until we find the root
        return find(parent.get(k));
    }

    // Perform Union of two subsets
    public void union(Long a, Long b)
    {
        // find the root of the sets in which elements `x` and `y` belongs
        Long x = find(a);
        Long y = find(b);

        parent.put(x, y);
    }

    public static void printSets(Long[] universe, DisjointSet ds)
    {
        for (Long i: universe) {
            System.out.print(ds.find(i) + " ");
        }

        System.out.println();
    }

    // Disjoint–Set data structure (Union–Find algorithm)
    public static void main(String[] args)
    {
        // universe of items
        Long[] universe = { 1L, 2L, 3L, 4L, 5L };

        // initialize `DisjointSet` class
        DisjointSet ds = new DisjointSet(universe);

        // create a singleton set for each element of the universe
//        ds.makeSet(universe);
        printSets(universe, ds);

        ds.union(4L, 3L);		// 4 and 3 are in the same set
        printSets(universe, ds);

        ds.union(2L, 1L);		// 1 and 2 are in the same set
        printSets(universe, ds);

        ds.union(1L, 3L);		// 1, 2, 3, 4 are in the same set
        printSets(universe, ds);
    }
}