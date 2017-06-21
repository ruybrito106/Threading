import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.*;

public class Q2 {

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

  private AtomicInteger saldo;

  public Conta() {
    this.saldo = new AtomicInteger(0);
  }

  public synchronized void deposita(int value) {
    this.saldo.set(this.saldo.get() + value);
    System.out.println("Add " + value + ". Current value = " + this.saldo.get());
  }

  public synchronized void retira(int value) {
    if (this.saldo.get() >= value) {
      this.saldo.set(this.saldo.get() - value);
      System.out.println("Rem " + value + ". Current value = " + this.saldo.get());
    } else {
      System.out.println("Try Rem " + value + ". Current value = " + this.saldo.get());
    }
  }

}
