package fiit.stuba;

import java.util.*;

public class GraphUtils {
    /**
     * Generation of all combinations from list of vertexes.
     * @param vertexes - List of vertexes.
     * @param degree - Length of one combinations in number of elements.
     * @return Generated set of combinations.
     */
    public static List<List<Vertex>> createCombinations(List<Vertex> vertexes, int degree) {
        int start = 0;
        int end = vertexes.size() - 1;
        int index = 0;
        List<List<Vertex>> output = new ArrayList<>();
        List<Vertex> startData = new ArrayList<>(Collections.nCopies(degree, null));
        createCombinations(vertexes, startData, start, end, index, degree, output);
        return output;
    }

    /**
     * @param vertexes - Set of vertexes from which combinations are derived.
     * @param data - Generated combination
     * @param start - start index (0).
     * @param end - end index (size - 1).
     * @param index - current index (starts with 0).
     * @param r - Number of vertexes in one combination.
     */
    private static void createCombinations(List<Vertex> vertexes, List<Vertex> data, int start, int end, int index,
                                           int r, List<List<Vertex>> output) {
    	
    	if(index == r) {
            List<Vertex> copyOfData = new ArrayList<>(data);
            output.add(copyOfData);
            return;
        }
        for (int i = start; i <= end && end - i + 1 >= r - index; i++) {
            data.set(index, vertexes.get(i));
            createCombinations(vertexes, data, i + 1, end, index + 1, r, output);
        }
    }
    
    //deleting overlapping combinations
    private static void createNonOverlappingCombinations(int d) {
    	
    	for(int i = 0; i < d - 1; i++) {
    		for(int j = 0; j <  - 1; j++) {
    			
    		}
    	}
    }
}