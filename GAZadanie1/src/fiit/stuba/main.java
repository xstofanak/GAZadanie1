package fiit.stuba;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

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
        List<Vertex> vertexList0 = graph.getVertexesOfPartition(0);
        List<Vertex> vertexList1 = graph.getVertexesOfPartition(1);
        List<Vertex> vertexList2 = graph.getVertexesOfPartition(2);

        
        // printing of combinations from one partition
        Map<List<Vertex>, Boolean> combinations0 = GraphUtils.createCombinations(vertexList0, numberOfD);
        Map<List<Vertex>, Boolean> combinations1 = GraphUtils.createCombinations(vertexList1, numberOfD);
        Map<List<Vertex>, Boolean> combinations2 = GraphUtils.createCombinations(vertexList2, numberOfD);
        
        //printCombination(combinations);
        
        for(int vertexIndex = 0; vertexIndex < vertexList0.size(); vertexIndex++) {
        	for(int partition1 = 0; partition1 < combinations1.size(); partition1++) {
        	    
        		for(int partition2 = 0; partition2 < combinations2.size(); partition2++) {
        			for(int partition0 = 0; partition0 < combinations0.size(); partition0++) {
        				
        				Vertex vertex = vertexList0.get(vertexIndex);
//        				List<Vertex> vertexesPartition1 = combinations1.get(partition1);
        				
        				
        			
        			}
        		}
        	}
        }
        
        //printCombination(combinations0);
        //printCombination(combinations1);
        //printCombination(combinations2);
        
    }

    private static void printCombination(List<List<Vertex>> combinations) {
        combinations.forEach(combination -> {
            combination.forEach(vertex -> System.out.print(vertex.getID() + "\t"));
            System.out.println();
        });
    }
}
