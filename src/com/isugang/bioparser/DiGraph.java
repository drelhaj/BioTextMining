package com.isugang.bioparser;


/******************************************************************************
 *  Compilation:  javac Digraph.java
 *  Execution:    java Digraph
 *  Dependencies: ST.java SET.java StdOut.java
 *  
 *  Directed graph data type implemented using a symbol table
 *  whose keys are strings and whose values are sets of strings.
 *
 ******************************************************************************/

public class DiGraph {

    // symbol table of linked lists
    private ST<String, SET<String>> st;

    // create an empty digraph
    public DiGraph() {
        st = new ST<String, SET<String>>();
    }

    // add v to w's list of neighbors; self-loops allowed
    public void addEdge(String v, String w) {
        if (!st.contains(v)) addVertex(v);
        if (!st.contains(w)) addVertex(w);
        st.get(v).add(w);
    }

    // add a new vertex v with no neighbors if vertex does not yet exist
    public void addVertex(String v) {
        if (!st.contains(v)) st.put(v, new SET<String>());
    }

    // return the degree of vertex v
    public int degree(String v) {
        if (!st.contains(v)) return 0;
        else                 return st.get(v).size();
    }

    // return the array of vertices incident to v
    public Iterable<String> adjacentTo(String v) {
        if (!st.contains(v)) return new SET<String>();
        else                 return st.get(v);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (String v : st) {
            s.append(v + ": ");
            for (String w : st.get(v)) {
                s.append(w + " ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        DiGraph G = new DiGraph();
        G.addEdge("0", "1"); G.addEdge("0", "2"); G.addEdge("0", "3");
        G.addEdge("2", "4"); G.addEdge("1", "4"); G.addEdge("1", "5");
        G.addEdge("3", "5"); G.addEdge("4", "6"); G.addEdge("5", "6");    // Tetrahedron with tail
        
        G.addEdge("6", "7"); G.addEdge("12", "7"); G.addEdge("7", "8");
        G.addEdge("14", "9"); G.addEdge("7", "9"); G.addEdge("9", "10");
        G.addEdge("8", "10"); G.addEdge("10", "11"); G.addEdge("5", "12");
        G.addEdge("15", "13"); G.addEdge("12", "13"); G.addEdge("13", "14");
        G.addEdge("3", "15");
        StdOut.println(G);
        
             
        
    }

}

