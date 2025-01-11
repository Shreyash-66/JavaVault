// class A implements Runnable {

//     @Override
//     public void run() {
//         System.out.println("Code executed in class A " + Thread.currentThread().getName());
//     }

// }

// class MonitorLockExample {

//     public synchronized void task1() {
//         try {
//             System.out.println("Inside task 1");
//             Thread.sleep(5000);
//             System.out.println("Task 1 ended");
//         } catch (Exception ex) {

//         }
//     }

//     public void task2() {
//         System.out.println("Inside task 2 before syncronized");
//         synchronized (this) {
//             System.out.println("Inside task 2 after syncronized");
//         }
//     }

//     public void task3() {
//         System.out.println("Inside task 3");
//     }
// }

class SharedResource {
    
    private boolean itemAvailable = false;

    public synchronized void produceItem() {
        System.out.println("Producer method invoked " + Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
            itemAvailable = true;
            System.out.println("Item added by producer");
            notifyAll();
        } catch (InterruptedException e) {
            System.out.println("Error occurred");
        }

    }

    public synchronized void consumeItem() {
        System.out.println("Consumer method invoked " + Thread.currentThread().getName());
        while(!itemAvailable) {
            try {
                System.out.println("Waiting for item to be available...");
                wait();
            } catch (InterruptedException e) {
                System.out.println("Error !!!");
            }
        }
        itemAvailable = false;
        System.out.println("Item consumed by consumer");
    }

}

public class MultithreadingDemo {
    public static void main(String[] args) {
        System.out.println("Inside main method " + Thread.currentThread().getName());
        // A a = new A();
        // Thread thread = new Thread(a);
        // thread.start();
        // Thread thread1 = new Thread(a);
        // thread1.start();
        // Thread thread2 = new Thread(a);
        // thread2.start();
        // System.out.println("Finished main method " + Thread.currentThread().getName());

        // MonitorLockExample obj = new MonitorLockExample();
        // Runnable obj1 = () -> obj.task1();
        // Thread t1 = new Thread(obj1);
        
        
        // Thread t2 = new Thread(() -> obj.task2());
        // Thread t3 = new Thread(() -> obj.task3());
        // t1.start();
        // t2.start();
        // t3.start();

        SharedResource sharedResource = new SharedResource();

        Thread producerThread1 = new Thread(() -> sharedResource.produceItem()); 
        Thread consumerThread1 = new Thread(() -> sharedResource.consumeItem());
        
        consumerThread1.start();
        producerThread1.start();
        
    }
}
