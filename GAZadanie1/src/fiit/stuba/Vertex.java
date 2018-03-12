package fiit.stuba;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Vertex {
	
	private int ID;
	private Partition partition;
	
	public Vertex(int iD) {
		super();
	}
	
	private Set<Vertex> neighbour =  new HashSet<>();
	private Map<Vertex, Boolean> avaiability =  new HashMap<>();
	
	public void addNeighbour(Vertex a) {
		if(avaiability.get(a)) {
			long count = a.getNeighbour().stream()
					.filter(vertex -> vertex.getPartition().equals(partition))
					.count();
				if(count == partition.getD()) {
					avaiability.replace(a, false);
				}
		} else {
			
		}
	}

	public Partition getPartition() {
		return partition;
	}

	public Set<Vertex> getNeighbour() {
		return neighbour;
	}

	public void setPartiion(Partition partition2) {
		this.partition = partition2;
		
	}
		
}
