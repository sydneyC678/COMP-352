/**
Question 1: 
My code's structuring pass reverses runs in ascending order that are longer than the corresponding runs of descending order. This has an impact on how many swaps and comparisons are made during the sorting process. The swap count is increased with each reverse operation. The structuring pass compares elements to define the lengths of ascending and descending runs, which also has an impact on the comparisons. With fewer inversions, the selected reversals hope to improve the sorting procedure. The structuring pass has no direct impact on other parts of the code, such as input reading and array manipulation.

Question 2:The duration of the particular runs that were recorded (in descending order of length 2) probably had little effect on performance. Runs of length 2 are rather brief and wouldn't have a big impact on the sorting algorithm's overall effectiveness. However, the existence of these runs may offer perceptions into the input data and aid in the discovery of patterns or traits that can be used for future optimisation.


Question 3: A Doubly Linked List implementation of the sorting algorithm would enable effective insertion and reversal operations because these operations can be carried out in constant time by altering the links between nodes. The Doubly Linked List would still be subject to the defined structuring, which involved reversals of ascending runs. However, the structuring process may necessitate additional considerations for the implementation in order to preserve and update the links between nodes. Overall, utilising a Doubly Linked List can have benefits in terms of simpler insertion and reversal operations, thereby increasing the algorithm's efficiency.
**/



package ca.concordia.algos;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
public class RunSort {

	private static int compares = 0;
	private static int swap = 0;
	
	public static void main(String[] args) {
		
		//main method will take one argument
		//the name of the file containing the input data
		//int[] array1 = {32, 62, 50, 42, 75, 91, 18, 57, 3, 47, 71, 7, 80, 33, 38};

		
		if(args.length!=1) {
			System.out.println("Error. Please only enter only 1 argument");
			return;
		}
		Scanner sc = null;
		String text = args[0]; //name of text file
		int[] input = new int[0];
		
		//reading through text file
		try {
			
			sc = new Scanner(new FileInputStream(text));
			
			String numbers = sc.nextLine();
			String[] data = numbers.split(" ");
			input = new int[data.length];
			
			//store the data in the input array
			
			for(int i = 0; i<input.length;i++) {
				try {
				input[i]= Integer.parseInt(data[i]);
				}catch(NumberFormatException e) {
					System.out.print(e);
				}
			}
			//check to see if the array of inputs is empty
			if(input.length==0) {
				System.out.println("input data is empty");
				return;
			}
			//close the scanner
			sc.close();
			
			
			
		}catch(IOException e) {
			System.out.println("Error. File not found");
		}
		
		//print array
		
		for(int i = 0; i<input.length;i++) {
			System.out.print(input[i] + " ");
		}
		
		structuredpass(input);
		insertion(input);
		System.out.println("We performed " + compares++ + " compares overall");
		System.out.println("We performed " + swap + " swap overall");
		
		//print array
		for(int i = 0; i<input.length;i++) {
			System.out.print(input[i] + " ");
		}
		

	}
public static void insertion(int[] arr) {
		
		compares = compares - 3;
		//In insertion sort first element is already sorted with itself
		//Therefore, start i at 1, instead of 0
		for(int index = 1; index < arr.length; index++) {
			compares++;
			int current = arr[index];
			
			//need current value to be compared to with the value to the left of it
			int j = index - 1;
			while(j>=0 && arr[j] > current) {
				compares++;
				swap++;
				arr[j + 1] = arr[j];
				j--;
			}
			arr[j + 1] = current;
		}
		}
	public static void structuredpass(int[]arr) {
		
		int ascendcount = 0;
		//keeps track of descending count of length 4
		int descendcount2 = 0;
		
	
		
		for(int i = 1; i <= arr.length; i++) {
			compares++;
			int a_length = 0;
			int d_length = 0;
		
				while(i < arr.length && arr[i] > arr[i - 1]) {
					compares++;
					a_length++;
					i++;
				}
			
			i -= a_length;
			
	
				while(i < arr.length && arr[i] < arr[i-1]) {
					compares++;
					d_length++;
					if(++d_length == 2) {
						descendcount2++;
					}
					i++;
				}
			
			
			if(a_length > d_length) {
				ascendcount++;
				i = i - d_length + a_length;
				reverse(arr, i, a_length);
			}
		}
		
		System.out.print("\nWe sorted in ASC order");
		System.out.print("\nWe counted " + descendcount2 + " DECR runs of length 2");
		System.out.print("\nWe performed " +  ascendcount + " reversals of runs in ASC order");
		System.out.print("\nWe performed " + --compares + " compares during structuring\n");
		

		//printing array
}
		
	
	//array to be reverse, index where it starts, length of portion to be reverse
	public static void reverse(int[] arr, int i, int length) {
		swap++;
		
		i -= 1;
		
		int newindex = 0;
		int index = 0;
		int[] temp = new int[length + 1];
		for(newindex = i - length; newindex < arr.length && index <= length; newindex++, index++) {
			temp[index] = arr[newindex];
		}
		
		
		newindex = newindex -1;
		for(int indx = 0; indx <=length; indx++) {
			arr[newindex--] = temp[indx];
		}
		
	}
	
	
}
