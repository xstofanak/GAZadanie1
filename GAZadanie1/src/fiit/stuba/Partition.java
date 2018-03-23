package fiit.stuba;

import java.util.ArrayList;
import java.util.Objects;

public class Partition {
	private int ID;
	private final ArrayList<Vertex> vertexes =  new ArrayList<>();

	public Partition(int ID) {
		this.ID = ID;
	}

	public void addVertex(Vertex vertex) {
		vertexes.add(vertex);
	}

	public ArrayList<Vertex> getVertexes() {
		return vertexes;
	}

	public int getID() {
		return ID;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Partition partition = (Partition) o;
		return ID == partition.ID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID);
	}
}
