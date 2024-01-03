/*
 * Theory Question: Sydney Campbell (40243309)
 * Question 1:Describe how hash functions distribute data into buckets. How did the hash function you used achieve this distribution?
 * 
 * Hash functions divide data into groups by giving each data item a unique number called a hash code. The hash code is calculated 
 * based on the properties of the data. In this program, the hash function uses XOR and multiplying with a prime number to spread 
 * out the hash codes across the buckets in the hash table.
 * 
 * Question 2: How does the size of the hash table affect its performance in terms of both time and space complexity? 
 * How did you choose the size of your hash table?
 * he size of the hash table affects how well it performs in terms of time and space. A bigger hash table decreases the chances
 *  of data items clashing, which makes it faster to find an item. However, a larger table also takes up more memory. 
 * The size of the hash table is chosen based on factors like how many items are expected and how much memory is available.
 * so it's a compromise between performance and space usage.
 * 
 * 
 * Question 3:Discuss the time complexity of your hash table operations: insertion, deletion, and lookup. 
 * How do these complexities compare to those of the TRHASH version?
 * 
 * The effectiveness of the hash function and the way collisions are handled determine the temporal complexity of hash table operations.
 *  Inserting, deleting, and looking up entries in the hash table often take this program's short and constant amount of time (O(1)). 
 * The time complexity can reach O(n), where n is the number of elements in the table, in the worst case scenario where there are numerous
 *  collisions. Although the TRHASH version's implementation might differ, it might have similar complexity. To reduce the amount 
 * of time required for hash table operations, a good hash function and collision resolution method must be used.
 */
package comp352.dsandalgos.myhash;


import main.stuff.*;

public class OptimizedHashing extends MyHash {

    public OptimizedHashing(String file) throws IllegalArgumentException{
        super(file);
    }

    public void insert(String string) throws TooFullException{
        int i = finding(string);
        if(i != -1){
            put(i, string);
            incSize();
        }
    }
    private int finding(String string)throws TooFullException{
        int i = hash(string);
        int tries = 0;
        int step = 1;
        while(++tries <= getTableSize()){
            if(get(i)==null){
                return i;
            }
            if(get(i).equals(string)){
                return -1;
            }
            i = (i + (step * step))% (getTableSize());
            step ++;
        }
        throw new TooFullException(string);

    }
    protected String find(String string)throws NotFoundException, TooFullException{
        int i = hash(string);
        int tries = 0;
        int step = 1;
        while(++tries <= getTableSize()){
            if(get(i)==null){
                throw new NotFoundException(i);
            }
            if(get(i).equals(string)){
                return string;
            }
            i = (i + (step * step))% (getTableSize());
            step ++;
        }
        throw new TooFullException(string);
    }
    protected int hash(String string){
        final int OFFSET = 0x811c9dc5;
        final int PRIME = 0x01000193;
        
        int hash = OFFSET;
        for(int i = 0; i<string.length();i++){
            hash ^= string.charAt(i);
            hash *= PRIME;
        }
        return Math.abs(hash)%getTableSize();
    }
    protected int determineTableSize(int i){
        return (int)(i);
    }
    
}
