package fiit.stuba;

import java.util.Collection;
import java.util.List;

public interface GraphMediator {
    void addNeighbors(Vertex srcVertex, Collection<Vertex> dstVertexes);
    List<Vertex> getVertexesOfPartition(int partitionId);
    void addPartition(Partition partition);
    void addVertex(Partition partition, Vertex vertex);
    void addNeighbors(Vertex vertex1, Vertex vertex2);
    boolean testNeighboursConsistency(Partition srcPartition, Collection<Vertex> dstVertexes);
    boolean testNeighbours(Vertex srcVertex, Collection<Vertex> dstVertexes);
}
