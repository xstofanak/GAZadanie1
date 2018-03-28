package fiit.stuba;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class main {

    public static void main(String[] args) throws IOException {
        // reading of degree from CLI
        int numberOfD = 0;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        System.out.print("Enter number of D (degree) <3,5>: ");
        input = in.readLine();
        numberOfD = Integer.parseInt(input);

        // generation of empty graph
        GraphBuilder graphBuilder = new GraphBuilder();
        GraphMediator graph = graphBuilder.generateEmptyMaxGraph(numberOfD);
        List<Vertex> vertexList = graph.getVertexesOfPartition(0);

        // printing of combinations from one partition
        List<List<Vertex>> combinations = GraphUtils.createCombinations(vertexList, numberOfD);
        printCombination(combinations);
    
    }

    private static void printCombination(List<List<Vertex>> combinations) {
        combinations.forEach(combination -> {
            combination.forEach(vertex -> System.out.print(vertex.getID() + "\t"));
            System.out.println();
        });
    }
}
