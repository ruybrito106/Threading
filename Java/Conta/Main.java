import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    Conta c1 = new Conta();
    Conta c2 = new Conta();

    Thread a = new Thread(new MThread(c1, c2, 1), "A");
    Thread b = new Thread(new MThread(c2, c1, 1), "B");
    Thread c = new Thread(new MThread(c1, c2, 0), "C");
    Thread d = new Thread(new MThread(c2, c1, 0), "D");
    Thread e = new Thread(new MThread(c1, c2, 2), "D");

    a.start();
    b.start();
    c.start();
    d.start();

    try {
      a.join();
      b.join();
      c.join();
      d.join();
    } catch (InterruptedException ie) {
      // print
    }

    c1.getSaldo();
    c2.getSaldo();

    e.start();

    try {
      e.join();
    } catch (InterruptedException ie) {
      // print
    }

    c1.getSaldo();
    c2.getSaldo();
  }

}

class MThread implements Runnable {

  private Conta conta;
  private Conta conta2;
  private int type;

  public MThread(Conta conta, Conta conta2, int type) {
    this.conta = conta;
    this.conta2 = conta2;
    this.type = type;
  }

  public void run() {
    Random rand = new Random();
    int value, counter = 100;
    while (counter-- > 0) {
      value = rand.nextInt(100);
      if (this.type == 1) this.conta.deposita(value);
      else if (this.type == 0) this.conta.consome(value);
    }

    if (this.type == 2) {
      counter = 1000;
      while (counter-- > 0) {
        value = rand.nextInt(100);
        if (counter % 2 == 0) conta.transfere(conta2, value);
        else if (counter % 2 == 1) conta2.transfere(conta, value);
      }
    }
  }

}

class Conta {

  private int saldo;
  private Lock lock = new ReentrantLock();

  public Conta() {
    this.saldo = 0;
  }

  public void deposita(int value) {
    this.lock.lock();
    try {
      this.saldo += value;
    } finally {
      this.lock.unlock();
    }
  }

  public void consome(int value) {
    this.lock.lock();
    try {
      this.saldo -= value;
    } finally {
      this.lock.unlock();
    }
  }

  public void transfere(Conta dest, int value) {
    boolean first = this.lock.tryLock();
    boolean second = dest.lock.tryLock();

    try {
      while (!(first && second)) {
        if (first) this.lock.unlock();
        if (second) dest.lock.unlock();
        first = this.lock.tryLock();
        second = dest.lock.tryLock();
      }
      dest.deposita(value);
      this.consome(value);
    } finally {
      if (first) this.lock.unlock();
      if (second) dest.lock.unlock();
    }
  }

  public void getSaldo() {
    System.out.println(this.saldo);
  }
}
