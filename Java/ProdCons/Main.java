

public class Main {

  public static void main(String[] args) {

    Produto p = new Produto(-1);
    Thread produtor = new Thread(new Produtor(p));
    Thread consumidor = new Thread(new Consumidor(p));

    produtor.start();
    consumidor.start();
  }

}

class Produtor implements Runnable {

  private Produto p;

  public Produtor(Produto p) {
    this.p = p;
  }

  public void run() {
      for(int i = 0; i <= 100; i++) p.produz(i);
  }

}

class Consumidor implements Runnable {

  private Produto p;

  public Consumidor(Produto p) {
    this.p = p;
  }

  public void run() {
    for(int i = 1; i <= 100; i++) p.consome(i);
  }

}

class Produto {

  private int value;
  private boolean hasProduct;

  public Produto(int value) {
    this.value = value;
    this.hasProduct = false;
  }

  public synchronized void produz(int value) {
    while (this.hasProduct == true) {
      try {
        wait();
      } catch (InterruptedException ie) {
        // print
      }
    }
    this.value = value;
    this.hasProduct = true;
    System.out.println("Produziu " + value);
    notifyAll();
  }

  public synchronized void consome(int value) {
    while (this.hasProduct == false) {
      try {
        wait();
      } catch (InterruptedException ie) {
        // print
      }
    }
    this.hasProduct = false;
    System.out.println("Consumiu " + value);
    notifyAll();
  }
}
