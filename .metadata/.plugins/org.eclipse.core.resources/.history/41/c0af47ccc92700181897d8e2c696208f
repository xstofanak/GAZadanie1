package fiit.stuba;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class main {

	public static void main(String[] args) {
		
		Partition partition_1  = new Partition(1,3);
		Partition partition_2  = new Partition(2,3);
		Partition partition_3  = new Partition(3,3);
		
		ArrayList<Partition> partitions = new ArrayList<Partition>();
		partitions.addAll(Arrays.asList(partition_1,partition_2,partition_3));
		int Counter = 0;
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				partitions.get(i).addVertex(new Vertex(Counter));
				Counter++;
			}
		}
		
		
		
	}

}
