import java.util.concurrent.TimeUnit;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int N = in.nextInt();

    Thread t[] = new Thread[N];

    Lista l = new Lista(0);

    for(int i = 0; i < N; i++) {
      t[i] = new Thread(new MyRunnable(l), i+"");
      t[i].start();
    }

    try {
        TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException ie) {
      ie.printStackTrace();
    }

    for(int i = 0; i < N; i++) {
      t[i].interrupt();
    }

    l.print();
  }

}

class MyRunnable implements Runnable {

  private Lista l;

  MyRunnable(Lista l) {
    this.l = l;
  }

  public void run() {
    while (true) {
      int val = Integer.parseInt(Thread.currentThread().getName());
      this.l.Increment(val);
    }
  }

}

class Lista {

  private int[] list = new int[100];
  private int cur_pos;

  public Lista(int pos) {
    this.cur_pos = pos;
  }

  public void Increment(int val) {
    synchronized(this) {
      this.list[this.cur_pos] = val;
      this.cur_pos = (this.cur_pos + 1) % 100;
    }
  }

  public void print() {
    for(int i = 0; i < 100; i++) {
      System.out.print(list[i]+ " ");
    }
    System.out.println("");
  }

}
