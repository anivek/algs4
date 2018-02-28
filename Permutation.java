import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;


public class Permutation {
    public static void main(String[] args) {
        if (args.length != 1) {
            StdOut.println("args length is " + args.length);
            return;
        }

        int num = Integer.valueOf(args[0]);

        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            randomizedQueue.enqueue(StdIn.readString());
            //StdOut.println("queue size=" + randomizedQueue.size());
        }

        for (int i = 0; i < num; i++) {
            String str = randomizedQueue.dequeue();
            StdOut.println(str);
        }

        // Deque<String> deque = new Deque<String>();
        // deque.addFirst("Hello");
    }
}