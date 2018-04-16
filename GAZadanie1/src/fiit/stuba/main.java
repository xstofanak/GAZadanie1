package fiit.stuba;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    private static GraphBuilder graphBuilder = new GraphBuilder();

    public static void main(String[] args) throws IOException {
        // reading of degree from CLI
        int numberOfD;
        String input;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter number of D (degree) <3,5>: ");
        input = in.readLine();
        numberOfD = Integer.parseInt(input);

        // searching for first graph
        GraphMediator graph = findGraph(numberOfD);
        System.out.println(graph);
    }

    private static GraphMediator findGraph(int numberOfD) {
        GraphMediator graph = graphBuilder.generateEmptyMaxGraph(numberOfD);
        List<Vertex> vertexList0 = graph.getVertexesOfPartition(0);
        List<Vertex> vertexList1 = graph.getVertexesOfPartition(1);
        List<Vertex> vertexList2 = graph.getVertexesOfPartition(2);
        List<CombinationsEntry> combinations0 = GraphUtils.createCombinations(vertexList0, numberOfD);
        List<CombinationsEntry> combinations1 = GraphUtils.createCombinations(vertexList1, numberOfD);
        List<CombinationsEntry> combinations2 = GraphUtils.createCombinations(vertexList2, numberOfD);
        if(processCombinations(graph, vertexList0, combinations1)) {
            if(processCombinations(graph, vertexList1, combinations2)) {
                if(processCombinations(graph, vertexList2, combinations0)) {
                    return graph;
                }
            }
        }
        return null;
    }

    private static boolean processCombinations(GraphMediator graph, List<Vertex> vertexList0,
                                            List<CombinationsEntry> combinations1) {
        boolean completedAllVertexes1 = true;
        for (Vertex aVertexList0 : vertexList0) {
            boolean setCombination = false;
            for (CombinationsEntry aCombinations1 : combinations1) {
                if (!aCombinations1.getFlag()) {
                    final Set<Vertex> set = new HashSet<>(aCombinations1.getCombinationList());
                    if (graph.testNeighboursConsistency(aVertexList0.getPartition(), set)) {
                        if (graph.testNeighbours(aVertexList0, set)) {
                            graph.addNeighbors(aVertexList0, set);
                            setCombination = true;
                            break;
                        }
                    } else {
                        aCombinations1.setFlag(false);
                    }
                }
            }
            if (!setCombination) {
                completedAllVertexes1 = false;
                break;
            }
        }
        return completedAllVertexes1;
    }
}
