package fiit.stuba;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Vertex {
	public int getID() {
		return ID;
	}

	private int ID;
	private final Set<Vertex> neighbours = new HashSet<>();
	private Partition partition;

	public Vertex(int iD) {
		super();
		this.ID = iD;
	}

	public boolean addNeighbour(Vertex neighbour) {
		long count = neighbour.getNeighbours().stream()
				.filter(vertex -> vertex.getPartition().equals(partition))
				.count();
		if (count < partition.getD()) {
			if(neighbour.addNeighbour(this)) {
				neighbours.add(neighbour);
			}
			
			return true;
		} else {
			return false;
		}
	}

	public Partition getPartition() {
		return partition;
	}

	public Set<Vertex> getNeighbours() {
		return neighbours;
	}

	public void setPartition(Partition partition) {
		this.partition = partition;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Vertex vertex = (Vertex) o;
		return ID == vertex.ID &&
				Objects.equals(partition, vertex.partition);
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID, partition);
	}
}
