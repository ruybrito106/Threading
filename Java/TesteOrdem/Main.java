public class Main {

    public static void main(String[] args) {

        Thread[] t = new Thread[5];

        for(int i = 0; i < 5; i++) t[i] = new Thread(new MeuRunnable(), ""+i);
        for(int i = 0; i < 5; i++) t[i].start();
        for(int i = 0; i < 5; i++) {
            try {
                t[i].join();
            } catch (InterruptedException ie) {
                // babaca
            }
        }
        for(int i = 0; i < 5; i++) t[i].interrupt();
    }

}

class MeuRunnable implements Runnable {

    public void run() {
        System.out.println("Essa é a Thread " + Thread.currentThread().getName());
        for(int i = 0; i < 10000; i++) {
            if (i == 9999) {
                System.out.println("Acabei! <- " + Thread.currentThread().getName());
                notifyAll();
            }
        }
    }

}