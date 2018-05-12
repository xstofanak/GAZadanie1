package fiit.stuba;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Building of empty graph with vertexes but without any edges.
 */
public class GraphBuilder {
    /**
     * Generation of graph with 3 partitions and maximum possible number of vertexes.
     * @param degree - Degree of one vertex to one other partition.
     * @return - Generated graph.
     */
    public Graph generateEmptyMaxGraph(int degree) {
        int numberOfVertexes = getMaxVertexes(degree);
        return generateEmptyGraph(degree, numberOfVertexes);
    }

    /**
     * Generation of graph with 3 partitions and selected number of vertexes in one partition.
     * @param degree - Degree of one vertex to one other partition.
     * @param numberOfVertexes - Number of vertexes per partition.
     * @return - Generated graph.
     */
    public Graph generateEmptyGraph(int degree, int numberOfVertexes) {
        Graph graphMediator = new GraphImpl(degree);
        AtomicInteger counter = new AtomicInteger(0);
        for(int i = 0; i < 3; i++) {
            Partition partition = new Partition(i);
            graphMediator.addPartition(partition);
            for(int j = 0; j < numberOfVertexes; j++) {
                graphMediator.addVertex(partition, new Vertex(counter.getAndIncrement()));
            }
        }
        return graphMediator;
    }

    private int getMaxVertexes(int degree) {
        return Math.min(degree * degree + degree, 1 + 2 * degree * degree);
    }
}
