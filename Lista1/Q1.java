import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Q1 {

  public static void main(String[] args) {

    Conta conta = new Conta();
    Thread[] t = new Thread[6];

    for(int i = 0; i < 6; i++) {
      if (i < 2) t[i] = new Thread(new Pessoa(conta, true));
      else t[i] = new Thread(new Pessoa(conta, false));
      t[i].start();
    }
  }

}

class Pessoa implements Runnable {

  private boolean consumidora;
  private Conta conta;

  public Pessoa(Conta conta, boolean consumidora) {
    this.conta = conta;
    this.consumidora = consumidora;
  }

  public void run() {
    Random rand = new Random();
    int value, counter = 50;
    for(; counter > 0; counter--) {
      value = rand.nextInt(1000);
      if (this.consumidora) this.conta.retira(value);
      else this.conta.deposita(value);
    }
  }
}

class Conta {

  private int saldo;
  private Lock trava = new ReentrantLock();

  public Conta() {
    this.saldo = 0;
  }

  public synchronized void deposita(int value) {
    this.trava.lock();
    try {
        this.saldo += value;
        System.out.println("Adicionou " + value + ". Saldo atual: " + this.saldo);
    } finally {
      this.trava.unlock();
    }
  }

  public synchronized void retira(int value) {
    this.trava.lock();
    try {
        if (this.saldo >= value) {
          this.saldo -= value;
          System.out.println("Retirou " + value + ". Saldo atual: " + this.saldo);
        } else {
          System.out.println("Tentou retirar " + value + ". Saldo atual: " + this.saldo);
        }
    } finally {
      this.trava.unlock();
    }
  }

}
