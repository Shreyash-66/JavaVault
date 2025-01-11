import java.util.LinkedList;
import java.util.Queue;

class SharedQueue {
    private Queue<Integer> q;
    private int MAX_SIZE;
    private int counter;

    SharedQueue (int sz) {
        q = new LinkedList<>();
        MAX_SIZE = sz;
        counter = 1;
    }

    synchronized void producerQueue() {
        while (q.size() >= MAX_SIZE) {
            try {
                System.out.println("Producer waiting for space in queue " + Thread.currentThread().getName());
                wait();
            } catch (InterruptedException e) {
                
            }
        }
        System.out.println("Adding element in queue : counter " + counter + " : " + Thread.currentThread().getName());
        q.add(counter++);
        notify();
    }

    synchronized void consumerQueue() {
        while(q.size() == 0) {
            try {
                System.out.println("Consumer waiting as no item available in queue " + Thread.currentThread().getName());
                wait();
            } catch (InterruptedException e) {
                
            }
        }
        System.out.println("Removing item from queue " + Thread.currentThread().getName());
        q.poll();
        counter--;
        notify();
    }
}

public class ProducerConsumerQueue {
    public static void main(String[] args) {
        SharedQueue sharedQueue = new SharedQueue(3);
        Thread producerThread = new Thread(() -> {
            for (int i = 0; i < 6; i++) {
                sharedQueue.producerQueue();
            }
        });
        Thread consumerThread = new Thread(() -> {
            for (int i = 0; i < 6; i++) {
                sharedQueue.consumerQueue();
            }
        });
        producerThread.start();
        consumerThread.start();
    }
    
}
