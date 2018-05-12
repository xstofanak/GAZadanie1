package fiit.stuba;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Vertex {
	private int ID;
	private Partition partition;
	private final Set<Vertex> neighbours = new HashSet<>();

	Vertex(int iD) {
		this.ID = iD;
	}

	void addNeighbour(Vertex neighbour) {
		neighbours.add(neighbour);
	}

	Partition getPartition() {
		return partition;
	}

	Set<Vertex> getNeighbours() {
		return neighbours;
	}

	void setPartition(Partition partition) {
		this.partition = partition;
	}

	public int getID() {
		return ID;
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
