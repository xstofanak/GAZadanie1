package fiit.stuba;

import java.util.ArrayList;
import java.util.Objects;

public class Partition {
	private int ID;
	private int d;

	public Partition(int iD, int d) {
		super();
		ID = iD;
		this.d = d;
	}
	
	private ArrayList<Vertex> verteces =  new ArrayList<>();
	
	public void addVertex(Vertex a) {
		verteces.add(a);
		a.setPartition(this);
	}	
	
	public int getD() {
		return d;
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
