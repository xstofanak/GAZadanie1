package fiit.stuba;

import java.util.ArrayList;

public class Partition {
	
	
	private int ID;
	private int d;

	public Partition(int iD, int d) {
		super();
		ID = iD;
		this.d = d;
	}
	
	private ArrayList<Vertex> verteces =  new ArrayList<>();
	//private Map<vertex, Boolean> availability =  new HashMap<>();
	
	public void addVertex(Vertex a) {
		verteces.add(a);
		a.setPartiion(this);
	}	
	
	public int getD() {
		return d;
	}
}
