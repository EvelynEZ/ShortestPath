import java.util.*;

/**
 * Author: Jiaqi Zhang
 * CSE 373 Winter 2017 HW5
 * A representation of a graph.
 * No duplicated edges or vertices allowed.
 */
public class MyGraph implements Graph {

    private Map<Vertex, Set<Edge>> graph;
    private Set<Edge> myEdge;

    /**
     * Creates a MyGraph object with the given collection of vertices
     * and the given collection of edges.
     * @param v a collection of the vertices in this graph
     * @param e a collection of the edges in this graph
     * @throws IllegalArgumentException if 
     * 1)weight of any edge is negative
     * 2)any source or destination of the edge does not exist in the graph.
     * 3)there are two edges with same source and destination but different weight.
  
     */
    public MyGraph(Collection<Vertex> v, Collection<Edge> e) {
    	if (v == null || e == null || v.size() == 0 || e.size() == 0){
    		throw new IllegalArgumentException();
    	}
    	
    	myEdge = new HashSet<>();
    	graph = new HashMap<Vertex, Set<Edge>>();
    	
    	//add vertices
    	for(Vertex vet: v) {
			if (!graph.containsKey(vet)){
				graph.put(vet, new HashSet<Edge>());
         }
		}
    	//add edges
    	for (Edge edge:e){
    		if(edge.getWeight() < 0){
    			throw new IllegalArgumentException();
    		} 
    		Vertex source = edge.getSource();
    		Vertex destination = edge.getDestination();
    		if (!v.contains(source) || !v.contains(destination)) {
    			throw new IllegalArgumentException();
    		}
         
         boolean found = false;
    		for (Edge eachEdge:graph.get(source)){
    			if(eachEdge.getDestination().equals(edge.getDestination())){
               found = true;
    				if(edge.getWeight() != eachEdge.getWeight()){
    					throw new IllegalArgumentException();
    				} 			
    			}
    		}
         if(!found){
        	 myEdge.add(edge);
        	 graph.get(source).add(edge);
         }
    	}

    }

    /** 
     * Return the collection of vertices of this graph
     * @return the vertices as a collection (which is anything iterable)
     */
    public Collection<Vertex> vertices() {
    	//Deep copy out.
    	Collection<Vertex> copyVertex = new HashSet<Vertex>();
    	copyVertex.addAll(graph.keySet());
    	return  copyVertex;
    }

    /** 
     * Return the collection of edges of this graph
     * @return the edges as a collection (which is anything iterable)
     */
    public Collection<Edge> edges() {
    	//Deep copy out.
    	Collection<Edge> copyEdge = new HashSet<Edge>();
    	copyEdge.addAll(myEdge);
    	return copyEdge;
    }

    /**
     * Return a collection of vertices adjacent to a given vertex v.
     *   i.e., the set of all vertices w where edges v -> w exist in the graph.
     * Return an empty collection if there are no adjacent vertices.
     * @param v one of the vertices in the graph
     * @return an iterable collection of vertices adjacent to v in the graph
     * @throws IllegalArgumentException if v does not exist.
     */
    public Collection<Vertex> adjacentVertices(Vertex v) {
    	checkExistence(v);
    	Set<Vertex> ws = new HashSet<Vertex>();
    	for (Edge eachEdge: graph.get(v)){
    		ws.add(eachEdge.getDestination());
    	}
    	Set<Vertex> copyW = new HashSet<Vertex>();
    	copyW.addAll(ws);
    	return copyW;
    }

    /**
     * Test whether vertex b is adjacent to vertex a (i.e. a -> b) in a directed graph.
     * Assumes that we do not have negative cost edges in the graph.
     * @param a one vertex
     * @param b another vertex
     * @return cost of edge if there is a directed edge from a to b in the graph, 
     * return -1 otherwise.
     * @throws IllegalArgumentException if a or b do not exist.
     */
    public int edgeCost(Vertex a, Vertex b) {
    	checkExistence(a);
    	checkExistence(b);
    	//Deep copy out.
    	int cost = -1;
    	for (Edge eachEdge: graph.get(a)){
    		if(eachEdge.getDestination().equals(b)){
    			cost = eachEdge.getWeight();
    			break;
    		}
    	}
    	return cost;

    }
    
    /**
     * Returns the shortest path from a to b in the graph, or null if there is
     * no such path.  Assumes all edge weights are nonnegative.
     * Uses Dijkstra's algorithm.
     * @param a the starting vertex
     * @param b the destination vertex
     * @return a Path where the vertices indicate the path from a to b in order
     *   and contains a (first) and b (last) and the cost is the cost of 
     *   the path. Returns null if b is not reachable from a.
     * @throws IllegalArgumentException if a or b does not exist.
     */
    public Path shortestPath(Vertex a, Vertex b) {
    	checkExistence(a);
    	checkExistence(b);
		List<Vertex> vertices = new ArrayList<Vertex>();
    	//If start and destination are equal
    	if(a.equals(b)){
    		vertices.add(a);
    		return new Path(vertices, 0);
    	}
    	PriorityQueue<Vertex> q = new PriorityQueue<Vertex>();
    	//Dijkstra begins.
        for (Vertex vet: graph.keySet()){
            vet.setCost(Integer.MAX_VALUE);
            vet.setKnown(false);
        }
        a.setCost(0);
    	q.add(a);
    	
    	while (!q.isEmpty()){
    		Vertex current = q.poll();
    		current.setKnown(true);
    		
    		for (Edge eachEdge: graph.get(current)){
            Vertex next = null;

            for (Vertex vet: graph.keySet()){
               if(vet.equals(eachEdge.getDestination())){
                  next = vet;
               }
            }
         
    			if(!next.getKnown()){
    				int thisDis = current.getCost() + eachEdge.getWeight();
    				int originalDis = next.getCost();
    				
    				if (thisDis < originalDis) {
    					next.setCost(thisDis);
    					next.setPath(current);
    					q.add(next);
    					
    					if(next.equals(b)){
    						b.setCost(next.getCost());
    						b.setPath(next.getPath());
    					}		
    				}
    			}
    		}
    	}
    	//If no path found
    	if (b.getPath() == null){
    		return null;
    	}
    	for (Vertex v = b; v != null; v = v.getPath()){
    		vertices.add(v);
    	}
    	Collections.reverse(vertices);
    	return new Path(vertices, b.getCost());

    }
    
    //Checks if the given node exists in this graph.
    private void checkExistence(Vertex v){
    	if (!graph.containsKey(v)){
    		throw new IllegalArgumentException();
    	}
    }

}