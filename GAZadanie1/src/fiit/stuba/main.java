package fiit.stuba;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class main {

	public static void main(String[] args) {

		int numberOfD = 0;
		double moor = 0; 
		int Counter = 0;
		
		Partition partition_1  = new Partition(1,3);
		Partition partition_2  = new Partition(2,3);
		Partition partition_3  = new Partition(3,3);
		
		ArrayList<Partition> partitions = new ArrayList<Partition>();
		
		partitions.addAll(Arrays.asList(partition_1,partition_2,partition_3));
		
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String input = "";
			
			if(input != null){
				
				System.out.print("Enter  number of D<3,5>: ");
				input = in.readLine();
				numberOfD = Integer.parseInt(input);
			
			}
 
		}catch(IOException e){
			
		}
		
		moor = numberOfD + Math.pow(numberOfD,2);
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < moor; j++) {
				partitions.get(i).addVertex(new Vertex(Counter));
				Counter++;
			}
		}
		
		
		int n = partition_3.getVerteces().size();
		printCombination(partition_3.getVerteces(), n, numberOfD);
		
	}
	
	static void combinationUtil(List<Vertex> verteces, List<Vertex> data, int start,
			
			int end, int index, int r){
				// Current combination is ready to be printed, print it
				if (index == r){
					
				for (int j=0; j<r; j++)
					System.out.print(data.get(j).getID()+" ");
					System.out.println("");
					return;
				}
				
				// replace index with all possible elements. The condition
				// "end-i+1 >= r-index" makes sure that including one element
				// at index will make a combination with remaining elements
				// at remaining positions
				for (int i=start; i<=end && end-i+1 >= r-index; i++){
					data.set(index, verteces.get(i));
					combinationUtil(verteces, data, i+1, end, index+1, r);
				}
			}

			// The main function that prints all combinations of size r
			// in arr[] of size n. This function mainly uses combinationUtil()
	static void printCombination(List<Vertex> verteces, int n, int r){
		// A temporary array to store all combination one by one
		List<Vertex> data = new ArrayList<>(Collections.nCopies(r, null));

		// Print all combination using temprary array 'data[]'
		combinationUtil(verteces, data, 0, n-1, 0, r);
	}
}
