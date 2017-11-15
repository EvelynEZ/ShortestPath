/**
 * CSE 373 HW5
 * Author: Jiaqi Zhang
 * Vertex.java:
 * Representation of a graph vertex
 */
public class Vertex implements Comparable<Vertex>{
	private final String label;   // label attached to this vertex
	private boolean known; // for dijkstra's method, known or not known.
	private int cost; // for dijkstra's method: distance to get to this vertex.
	private Vertex path; // for dijkstra's method: the previous vertex connected.
	
	
	/**
	 * Construct a new vertex
	 * @param label the label attached to this vertex
	 */
	public Vertex(String label) {
		if(label == null)
			throw new IllegalArgumentException("null");
		this.label = label;
		this.known = false; 
		this.cost = Integer.MAX_VALUE; 
		this.path = null;
	}

	/**
	 * Get a vertex label
	 * @return the label attached to this vertex
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * A string representation of this object
	 * @return the label attached to this vertex
	 */
	public String toString() {
		return label;
	}

	//auto-generated: hashes on label
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}

	//auto-generated: compares labels
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Vertex other = (Vertex) obj;
		if (label == null) {
                    return other.label == null;
		} else {
		    return label.equals(other.label);
		}
	}
   
   @Override
   public int compareTo(Vertex other) {
	   return this.cost - other.cost;
   }
   
   public void setCost(int cost) {
	   this.cost = cost;
   }
   
   public void setPath(Vertex path){
	   this.path = path;
   }
   
   public void setKnown (boolean known){
	   this.known = known;
   }
   
   public int getCost (){
	   return this.cost;
   }
   
   public Vertex getPath() {
	   return this.path;
   }
   
   public boolean getKnown(){
	   return this.known;
   }
   


}