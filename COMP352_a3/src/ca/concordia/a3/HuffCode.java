/*
 * Theory 1
What is the purpose of using a priority queue in Huffman coding, and how does it help to generate an optimal code?

In order to efficiently build the Huffman tree, Huffman coding employs a priority queue. 
It helps create an ideal code where frequently recurring characters have shorter codes by merging nodes with the lowest frequencies. 
The building of the Huffman tree is made possible by the priority queue, 
which guarantees effective selection of nodes with the lowest frequencies. 
In order to maximise compression effectiveness, popular characters are given shorter codes and less common characters 
larger codes in the resulting compressed representation of the data.

Q3) Theory 2
How does the length of a Huffman code relate to the frequency of the corresponding symbol, and why is this useful for data compression?

In Huffman coding, the length of a Huffman code and the frequency of the associated symbol are inversely correlated.
 Shorter codes are given to symbols with higher frequencies, whilst longer codes are given to symbols with lower frequencies. 
 This connection is useful for data compression since it enables the representation of more common symbols with fewer bits.
It decreases the total number of bits needed to represent the data by assigning shorter codes to frequently recurring symbols. This is therefore
useful for data compression. 


Q4) Theory 3
What is the time complexity of building a Huffman code, and how can you optimize it?

The time complexity is typically O(n log n) but the complexity of the priority queue itself can be reduced to O(n) or O(n + k log k) using specialised data structures such heaps,
tries, or hash tables, where k is the number of distinct symbols. The goal would be to reduce unecessary or excessive calculations or computations.
Efficiency can be further increased by using frequency counting methods for example
 nd storing computed Huffman trees, reusing the results, etc. 
 */


package ca.concordia.a3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class HuffCode {
    private Node root;
    private String[] encodingTable;

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Please enter two arguments");
            return;
        }

        String filename = args[0];
        String task = args[1].toLowerCase();

        //make sure user enters either encode or decode
        if (!task.equals("encode") && !task.equals("decode")) {
            System.out.println("Invalid mode. Use 'encode' or 'decode'.");
            return;
        }

        HuffCode huff = new HuffCode();
        huff.buildHuffmanTree(filename); //build the tree

        Scanner sc = new Scanner(System.in);

        if (task.equals("encode")) {
            System.out.print("What do you want to encode?: ");
            String word = sc.nextLine();
            String encodedWord = huff.encode(word); //encode what user whants
            System.out.println("Result: " + encodedWord);

        } else {
            System.out.print("Enter the code to decode: ");
            String code = sc.nextLine();
            String decodedWord = huff.decode(code);
            System.out.println("Result: " + decodedWord);
        }

        sc.close();
    }

class PriorityQueue {
    private Node[] heap;
    private int size;

    public PriorityQueue(int capacity) {
        heap = new Node[capacity];
        size = 0;
    }

    public int size() {
        return size;
    }

    public void insert(Node node) {
        if (size == heap.length) {
            resizeHeap();
        }

        heap[size] = node;
        Up(size);
        size++;
    }

    //method to remove node
    public Node remove() {
        if (isEmpty()) {
            throw new IllegalStateException("Priority queue is empty");
        }

        Node root = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        Down(0);
        return root;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void Up(int index) {
        int parent = (index - 1) / 2;

        while (index > 0 && heap[index].compareTo(heap[parent]) < 0) {
            swap(index, parent);
            index = parent;
            parent = (index - 1) / 2;
        }
    }

    private void Down(int index) {
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        int smallest = index;

        if (leftChild < size && heap[leftChild].compareTo(heap[smallest]) < 0) {
            smallest = leftChild;
        }

        if (rightChild < size && heap[rightChild].compareTo(heap[smallest]) < 0) {
            smallest = rightChild;
        }

        if (smallest != index) {
            swap(index, smallest);
            Down(smallest);
        }
    }

    private void swap(int i, int j) {
        Node temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private void resizeHeap() {
        Node[] newHeap = new Node[heap.length * 2];
        System.arraycopy(heap, 0, newHeap, 0, heap.length);
        heap = newHeap;
    }
}
//node class
class Node implements Comparable<Node> {
    char data;
    int frequency;
    Node left, right;

    public Node(char data, int frequency) {
        this.data = data;
        this.frequency = frequency;
        left = null;
        right = null;
    }
    public int compareTo(Node other) {
        if (this.frequency < other.frequency) {
            return -1;
        } else if (this.frequency > other.frequency) {
            return 1;
        } else {
            return Character.compare(other.data, this.data);
        }
    }
    
}

//method that returns frequencies after counting count character by character
    private int[] readFrequencies(String filename) throws IOException {
        int[] frequencies = new int[128]; // Use 128 as the size
    
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            int ch;
            while ((ch = reader.read()) != -1) {
                if (ch < 128) { // Check if the character is within the ASCII range
                    frequencies[ch]++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
    
        return frequencies;
    }
    
    //nethod to buil tree using encodeTable method 
    private void buildHuffmanTree(String filename) throws IOException {
        int[] frequencies = readFrequencies(filename);

        PriorityQueue priorityQueue = new PriorityQueue(128); // Use 128 as the capacity

        for (int i = 0; i < 128; i++) { // Iterate up to 128
            if (frequencies[i] > 0) {
                Node node = new Node((char) i, frequencies[i]);
                priorityQueue.insert(node);
            }
        }
        
        //remove two rarest characters, combine into single node as its two children
        while (priorityQueue.size() > 1) {
            Node left = priorityQueue.remove();
            Node right = priorityQueue.remove();
            Node parent = new Node('\0', left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;
            priorityQueue.insert(parent);
        }

        root = priorityQueue.remove();

        encodingTable = new String[128]; // Use 128 as the size
        buildEncodingTable(root, "");

        

        //testing to see frquencies and codes
      /*   System.out.println("Character\tFrequency\tCode");
        for (int i = 0; i < encodingTable.length; i++) {
            if (encodingTable[i] != null) {
                char ch = (char) i;
                System.out.println(ch + "\t\t" + frequencies[i] + "\t\t" + encodingTable[i]);
            }
        } */
    }

    private void buildEncodingTable(Node node, String code) {
        if (node == null) {
            return;
        }

        if (node.left == null && node.right == null) {
            encodingTable[node.data] = code;
            
        }

        buildEncodingTable(node.left,  code + "1");// Assign "1" to the left child
        buildEncodingTable(node.right, code + "0");  // Assign "0" to the right child
       

       
    }

    //method that puts it all together and returns the encoded code
    private String encode(String word) {
        StringBuilder encoded = new StringBuilder();
    
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            String code = encodingTable[c]; // Use the character directly as an index
            encoded.append(code);
        }
    
        return encoded.toString();
    }
    

    //method that puts its all together and returns decoded word
    private String decode(String code) {
        StringBuilder decoded = new StringBuilder();
        Node current = root;

        for (int i = 0; i < code.length(); i++) {
            char bit = code.charAt(i);

            if (bit == '1') {
                current = current.left;
            } else if (bit == '0') {
                current = current.right;
            }

            if (current.left == null && current.right == null) {
                decoded.append(current.data);
                current = root; // Reset to the root for the next character
            }
        }

        return decoded.toString();
    }
}




 