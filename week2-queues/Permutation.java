import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Permutation client. Write a client program Permutation.java that takes
 * a command-line integer k; reads in a sequence of strings from
 * standard input using StdIn.readString(); and prints exactly k of them,
 * uniformly at random. Print each item from the sequence at most once.
 */
public class Permutation {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            rq.enqueue(s);
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }
    }

}
