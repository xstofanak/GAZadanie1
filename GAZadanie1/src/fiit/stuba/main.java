package fiit.stuba;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

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
		
		
	}
}
