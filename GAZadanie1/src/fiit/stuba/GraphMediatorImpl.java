package fiit.stuba;

import javafx.util.Pair;
import java.util.*;
import java.util.stream.Collectors;

/**
 * One instance of graph with partitions and vertexes.
 */
public class GraphMediatorImpl implements GraphMediator {
    /**
     * Set of all partitions (partition contains information about vertexes).
     */
    private final Set<Partition> partitionSet = new HashSet<>();
    /**
     * Degree of all vertexes against one other partition.
     */
    private int degree;

    /**
     * Construction of new empty graph.
     * @param degree Degree of each vertex against one partition (total degree = 2 * degree).
     */
    public GraphMediatorImpl(int degree) {
        this.degree = degree;
    }

    /**
     * Listing of vertexes in one partition.
     * @param partitionId - ID of selected partition (usually 0 / 1 / 2).
     * @return - List of vertexes in partition.
     */
    @Override
    public List<Vertex> getVertexesOfPartition(int partitionId) {
        return partitionSet.stream()
                .filter(partition -> partition.getID() == partitionId)
                .flatMap(partition -> partition.getVertexes().stream())
                .collect(Collectors.toList());
    }

    /**
     * Adding of new partiotion into graph.
     * @param partition Partition to be added.
     */
    @Override
    public void addPartition(Partition partition) {
        partitionSet.add(partition);
    }

    /**
     * Adding of new vertex in partition.
     * @param partition - Partition of vertex.
     * @param vertex - Vertex definition.
     */
    @Override
    public void addVertex(Partition partition, Vertex vertex) {
        partition.addVertex(vertex);
        vertex.setPartition(partition);
    }

    /**
     * Adding of new neighbourhood between two vertexes.
     * @param vertex1 - First vertex.
     * @param vertex2 - Second vertex.
     */
    @Override
    public void addNeighbors(Vertex vertex1, Vertex vertex2) {
        vertex1.addNeighbour(vertex2);
        vertex2.addNeighbour(vertex1);
    }

    /**
     * Testing of neighbours between source and destination vertexes before they are inserted into graph.
     * @param srcVertex - Vertex from which we test neighbourhoods to other vertexes.
     * @param dstVertexes - Group of destination vertexes (size of degree determines dstVertexes).
     * @return - True if source vertex can be connected to destination group of vertexes and the graph is still
     * solvable against all constraints.
     */
    @Override
    public boolean testNeighbours(Vertex srcVertex, Set<Vertex> dstVertexes) {
        // testing of degree, neighbor's partition, and degrees of vertexes
        if (dstVertexes.size() == degree &&
                dstVertexes.stream().allMatch(neighbor -> !neighbor.getPartition().equals(srcVertex.getPartition()) &&
                        getDegreeToPartition(neighbor, srcVertex.getPartition()) < degree) &&
                getDegreeToPartition(srcVertex, dstVertexes.stream().findAny().get().getPartition()) == 0) {

            Set<Vertex> reflectedVertexes = getReflectedVertexes(srcVertex);
            reflectedVertexes.addAll(dstVertexes.stream()
                    .flatMap(vertex -> vertex.getNeighbours().stream()
                            .filter(vertex1 -> vertex1.getPartition().equals(srcVertex.getPartition())))
                    .collect(Collectors.toSet()));
            if (getCountOfAvailableNeighbours(srcVertex) * degree >=
                    getCountOfNonCoveredVertexes(srcVertex.getPartition(), reflectedVertexes)) {
                // testing of solvability - minimum degree vs count of free degrees in partition
                Partition dstPartition = dstVertexes.stream().findAny().get().getPartition();
                Map<Vertex, Integer> mappedDegrees = getPartitionCopyWithDegrees(srcVertex.getPartition(), dstPartition);
                dstVertexes.forEach(vertex -> {
                    int originalDegree = mappedDegrees.get(vertex);
                    int newDegree = originalDegree + 1;
                    mappedDegrees.replace(vertex, newDegree);
                });
                // testing of solvability - access from srcVertex to other vertexes in partition
                if ((degree - getMinDegree(mappedDegrees)) * degree <= getCountOfFreeDegrees(mappedDegrees)) {
                    // ---- to do: testing of solvability - access from root vertex to last partition ----
                    return true;
                }
            }
        }
        return false;
    }

    private int getDegreeToPartition(Vertex vertex, Partition partition) {
        return (int)vertex.getNeighbours().stream()
                .filter(vertex1 -> vertex1.getPartition().equals(partition))
                .count();
    }

    private Map<Vertex, Integer> getPartitionCopyWithDegrees(Partition srcPartition, Partition dstPartition) {
        return srcPartition.getVertexes().stream()
                .map(vertex -> {
                    int count = (int)vertex.getNeighbours().stream()
                            .filter(neighbor -> neighbor.getPartition().equals(dstPartition))
                            .count();
                    return new Pair<>(vertex, count);
                })
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    private int getMinDegree(Map<Vertex, Integer> mappedDegrees) {
        Optional<Integer> optMinDegree = mappedDegrees.values().stream()
                .min(Integer::compareTo);
        return optMinDegree.orElse(0);
    }

    private int getCountOfFreeDegrees(Map<Vertex, Integer> mappedDegrees) {
        return mappedDegrees.values().stream()
                .mapToInt(integer -> degree - integer)
                .sum();
    }

    private Set<Vertex> getReflectedVertexes(Vertex vertex) {
        return vertex.getNeighbours().stream()
                .flatMap(vertex1 -> vertex1.getNeighbours().stream()
                        .filter(vertex2 -> vertex2.getPartition().equals(vertex.getPartition())))
                .collect(Collectors.toSet());
    }

    private int getCountOfAvailableNeighbours(Vertex vertex) {
        return 2 * degree - vertex.getNeighbours().size();
    }

    private int getCountOfNonCoveredVertexes(Partition partition, Set<Vertex> vertexSet) {
        return partition.getVertexes().size() - vertexSet.size();
    }
}
