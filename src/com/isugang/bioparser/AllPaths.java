/**
 * http://introcs.cs.princeton.edu
 * 
 */
package com.isugang.bioparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class AllPaths<Vertex> {
	private static List<String> pathsCombined = new ArrayList<String>();
	private static String allPaths = "";
    private Stack<String> path  = new Stack<String>();   // the current path
    private SET<String> onPath  = new SET<String>();     // the set of vertices on the path
    private static int pathCounter = 0;

    public AllPaths(List<String> pathsCombined, String allPaths, int pathCounter) {
        AllPaths.pathsCombined = pathsCombined;
        AllPaths.allPaths = allPaths;
        AllPaths.pathCounter = pathCounter;

    }
    
    public List<String> getPathsCombined() {
        return pathsCombined;
    }
    public void setPathsCombined(List<String> pathsCombined) {
        AllPaths.pathsCombined = pathsCombined;
    }
    public String getAllPaths() {
        return allPaths;
    }
    public void setAllPaths(String allPaths) {
        AllPaths.allPaths = allPaths;
    }
    
    public int getPathCounter() {
        return pathCounter;
    }
    public void setPathCounter(int pathCounter) {
        AllPaths.pathCounter = pathCounter;
    }
    
    public AllPaths(Graph G, String s, String t, List<String> levelsReverse, List<String> ancestorsList) {
        enumerate(G, s, t, levelsReverse, ancestorsList);
    }
    
  

    // I created this method to eliminate bothway edges.
    //I still need method even though I  commented out line 93 in the Graphs class which fixes the non-directional graph (st.get(w).add(v);), as otherwise the number of paths goes exponential.

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
List<String> eachPath = new ArrayList<String>();
String immune = "";
        // add node v to current path from s
        path.push(v);
        onPath.add(v);

        // found path from s to t - currently prints in reverse order because of stack
        if (v.equals(t)){
      	
if(notDirectEdge(path.toString(), levelsReverse)){
   // StdOut.println(path);
List<String> pathList = Arrays.asList(path.toString().replace("[","").replace("]","").split("\\s*,\\s*"));


for(int w=0; w<pathList.size(); w++){
	String goID = ancestorsList.get(Integer.parseInt(pathList.get(w))); 
	eachPath.add(goID);
}

if(eachPath.toString().contains("GO:0002376"))
	immune = "I";
else
	immune = "N";
for(int w=0; w<eachPath.size(); w++){
	String goID = eachPath.get(w); 
	pathsCombined.add(goID+"."+w+"."+immune);

}


//StdOut.println(eachPath);
allPaths = allPaths + "Path " + (++pathCounter) + ":\t" + eachPath.toString() + "\n"; 
eachPath.clear();
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

    public static AllPaths<Object> printAllPaths(List<String> levels, 
    		List<String> levelsReverse, int rootIndex, List<String> ancestorsList) {
    	pathsCombined.clear();
    	allPaths = "";
    	pathCounter = 0;
        Graph G = new Graph();

for(int i = 0; i<levels.size(); i++){
	String entry = levels.get(i).toString();
	String s = entry.substring(0,entry.indexOf("."));
	String t = entry.substring(entry.indexOf(".")+1,entry.length());
	G.addEdge(s, t);//Direct graph from s to t
}

       
         new AllPaths<Object>(G, "0", rootIndex+"", levelsReverse, ancestorsList);

      Set<String> hs = new HashSet<>();
      hs.addAll(pathsCombined);
      pathsCombined.clear();
      pathsCombined.addAll(hs);
      
     // System.out.println(allPaths);
         return new AllPaths<Object>(pathsCombined, allPaths, pathCounter);
    }

}