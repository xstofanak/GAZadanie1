package fiit.stuba;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Vertex {
	private int ID;
	private Partition partition;
	private Vertex grandPa;
	
	public Vertex getGrandPa() {
		return grandPa;
	}

	public void setGrandPa(Vertex grandPa) {
		this.grandPa = grandPa;
	}

	private final Set<Vertex> neighbours = new HashSet<>();

	public Vertex(int iD) {
		this.ID = iD;
	}

	public void addNeighbour(Vertex neighbour) {
		neighbours.add(neighbour);
	}

	public Partition getPartition() {
		return partition;
	}

	public int getID() {
		return ID;
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
