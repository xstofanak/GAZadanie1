package fiit.stuba;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
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
        List<CombinationsEntry> combinations0 = GraphUtils.createCombinations(vertexList0, numberOfD);
        List<CombinationsEntry> combinations1 = GraphUtils.createCombinations(vertexList1, numberOfD);
        List<CombinationsEntry> combinations2 = GraphUtils.createCombinations(vertexList2, numberOfD);
        
        //select first vertex
        for(int vertexIndex = 0; vertexIndex < vertexList0.size(); vertexIndex++) {
        	//go from partition0 to partition 1 
        	for(int partition1 = 0; partition1 < combinations1.size(); partition1++) {
        		//check neighbor and flag for used combination 
        		if(combinations1.get(partition1).getFlag() == false && 
        				graph.testNeighbours(vertexList0.get(vertexIndex), new HashSet<>(combinations1.get(partition1).getCombinationList()))) {
        			for(int j = 0; j < numberOfD; j++) {
        				graph.addNeighbors(vertexList0.get(vertexIndex), combinations1.get(partition1).getCombinationList().get(j));
        			}
        			
        			combinations1.get(partition1).setFlag(true);	
        			
        			//go from partition1 to partition 2
        			for(int partition2 = 0; partition2 < combinations2.size(); partition2++) {
        				//check neighbor and flag for used combination
        				if(combinations2.get(partition2).getFlag() == false && 
                				graph.testNeighbours(vertexList1.get(vertexIndex), new HashSet<>(combinations2.get(partition2).getCombinationList()))) {
        					
        					for(int j = 0; j < numberOfD; j++) {
                				graph.addNeighbors(vertexList1.get(vertexIndex), combinations2.get(partition2).getCombinationList().get(j));
                			}
        					
        					combinations2.get(partition2).setFlag(true);
        					
        					//go from partition2 to partition 0
        					for(int partition0 = 0; partition0 < combinations0.size(); partition0++) {
        						//check neighbor and flag for used combination
        						if(combinations0.get(partition0).getFlag() == false && 
                        				graph.testNeighbours(vertexList2.get(vertexIndex), new HashSet<>(combinations0.get(partition0).getCombinationList()))) {
                				
        							for(int j = 0; j < numberOfD; j++) {
        		        				graph.addNeighbors(vertexList2.get(vertexIndex), combinations2.get(partition2).getCombinationList().get(j));
        		        			}
        							
        							combinations0.get(partition0).setFlag(true);
        						}
                			}
        				}
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
