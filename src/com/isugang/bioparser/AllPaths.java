package com.isugang.bioparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class AllPaths<Vertex> {

    private Stack<String> path  = new Stack<String>();   // the current path
    private SET<String> onPath  = new SET<String>();     // the set of vertices on the path

    public AllPaths(Graph G, String s, String t, List<String> levelsReverse, List<String> ancestorsList) {
        enumerate(G, s, t, levelsReverse, ancestorsList);
    }

    // use DFS
    private boolean notDirectEdge(String path, List<String> levelsReverse){
    	boolean notDirect = true;
    	
    	for(int x = 0; x < levelsReverse.size(); x++){
    		if(path.toString().contains(levelsReverse.get(x))){
    		    notDirect = false;
    		}
    		}
    	
    	return notDirect;
    }
    
    private void enumerate(Graph G, String v, String t, List<String> levelsReverse, List<String> ancestorsList) {

        // add node v to current path from s
        path.push(v);
        onPath.add(v);

        // found path from s to t - currently prints in reverse order because of stack
        if (v.equals(t)){
      	
if(notDirectEdge(path.toString(), levelsReverse)){
    StdOut.println(path);
List<String> pathList = Arrays.asList(path.toString().replace("[","").replace("]","").split("\\s*,\\s*"));
for(int w=0; w<pathList.size(); w++){
	System.out.print(ancestorsList.get(Integer.parseInt(pathList.get(w)))+", ");
}
System.out.println("");
    
}
      
    

      
        }
        // consider all neighbors that would continue path with repeating a node
        else {
            for (String w : G.adjacentTo(v)) {
                if (!onPath.contains(w)) enumerate(G, w, t, levelsReverse, ancestorsList);
            }
        }

        // done exploring from v, so remove from path
        path.pop();
        onPath.delete(v);
    }

    public static void printAllPaths(List<String> levels, List<String> levelsReverse, int rootIndex, List<String> ancestorsList) {
        Graph G = new Graph();

for(int i = 0; i<levels.size(); i++){
	String entry = levels.get(i).toString();
	String s = entry.substring(0,entry.indexOf("."));
	String t = entry.substring(entry.indexOf(".")+1,entry.length());
	G.addEdge(s, t);//Direct graph from s to t
}



        
         new AllPaths<Object>(G, "0", rootIndex+"", levelsReverse, ancestorsList);

    }

}