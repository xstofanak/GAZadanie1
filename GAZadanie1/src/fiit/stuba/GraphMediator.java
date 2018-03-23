package fiit.stuba;

import java.util.List;
import java.util.Set;

public interface GraphMediator {
    boolean testNeighbours(Vertex srcVertex, Set<Vertex> dstVertexes);

    List<Vertex> getVertexesOfPartition(int partitionId);

    void addPartition(Partition partition);
    void addVertex(Partition partition, Vertex vertex);
    void addNeighbors(Vertex vertex1, Vertex vertex2);
}
