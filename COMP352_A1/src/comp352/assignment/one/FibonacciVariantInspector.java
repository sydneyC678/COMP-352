package comp352.assignment.one;
/**
 * 
 * @author sydneycampbell
 * Fibonacci Assignment 1 COMP 352
 *
 */
/**
 * THEORY QUESTIONS
 * 1. Explain how recursion might be used in the sequence generation in this assignment. Limit your answer to 100 words.
 * 
 * The method fibonacci takes in an index as we loop through the for loop in the main and the result of it is assigned to the index position in the sequence array. 
 * The base case: if (i<4) returns 1, making sure that the first 4 elements in the array are number 1. If the base case is not matched, it moves on to the else statement, which
 * adds 3*fibonacci(i-3) and fibonacci(1-2) which intern result in the sum of all N values where N is the specified intial number of 1s. Recursion is therefore used because the method calls upon itself,
 * then upon itself, then upon itself, as much as it needs when adding the sum.
 * 
 * Input: 9
 * 
 * After calling method 4 times (index 0 to 3), sequence is currently 1 1 1 1 
 * fibonacci(4) return fibonacci(0) + 3*fibonacci(1) => fibonacci(0) = 1 + 3* fibonacci(1) = 1 + 3(1) = 4
 * sequence = 1 1 1 1 4 
 * fibonacci(5) return fibonacci(1) + 3*fibonacci(2) => 1 + 3 * 1 = 4
 * sequence = 1 1 1 1 4 4 
 * fibonacci(6) return fibonacci(4) + 3*fibonacci(3) => fib(2) + 3*fib(1) + 3 * fib(3) => 1 + 3 + 3 = 7
 * sequence = 1 1 1 1 4 4 7 
 * fibonacci(7) return fibonacci(5) + 3*fibonacci(4) => fib(1) + 3 * fib(2) + 3 * fib(4) => 1 + 3 + 3 * fib(4) => 4 + 3*fib(4) => fib(2) + 3*fib(1) => 1 + 3 => 4
 * => 4 + 3 *(4) = 16
 * fibonacci(8) return fibonacci(6) + 3*fibonacci(5) => fib(6) = 7 + 3 *[fib(5) = 4] = 7 + 3x4 = 19
 * 
 *
 * 2. As the size of the input sequence grows, how do you anticipate the runtime of your program to scale? Explain why. Limit your answer to 100 words.
 * 
 * As the size of the input sequence grows, the runtime would be expected to grow (increase) as well. As the number of times a recursion call is called,
 * its time complexity will increase in correspondance. For example, in this case, the number of times the recursive method is called is 31, therefore the time complexity is O(31^n).
 * The more calls to the function = the more calculations and work required.
 * 
 *
 */
public class FibonacciVariantInspector {
	private static int numCalls = 0;
	public static void main(String[] args) {
		

		if(args.length>1||args.length==0) {
			System.out.println("Error. Too many arguments or not enough arguments entered");
			return;
		}
		
		
		
		//length of sequence
		int length = Integer.parseInt(args[0]);
		
		if (length <= 0) {
            System.out.println("Error. Length must be greater than 0");
            return;
        }
		
		int[] sequence = new int[length];
		
		//print
		
		for(int i = 0; i<sequence.length;i++) {
			sequence[i] = fibonacci(i);
			System.out.print(sequence[i]+" ");
		}
		System.out.println();
		System.out.println("Calls: " + numCalls);
		
		

	}
	public static int fibonacci(int i) {
		numCalls++;
		if (i < 4) {
            return 1;
        } else {
        	return (3*fibonacci(i-3)) + fibonacci(i-2);
        }
		
		
	}
	

}
