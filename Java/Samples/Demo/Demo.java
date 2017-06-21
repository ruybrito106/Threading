class MinhaThread implements Runnable {

  String s1;
  Parenteses p1;
  public MinhaThread (Parenteses p2, String s2) {
    p1 = p2;
    s1 = s2;
  }
  public void run() {
    p1.exibir(s1);
  }
}

class Parenteses {

  public void exibir(String s) {
    System.out.print("(" + s);
    try {
      Thread.sleep(1000);
    }
    catch(InterruptedException e) {
      System.out.println("Interrupted");
    }
    System.out.println(")");
  }
}

public class Demo {
  public static void main(String args[]) {
    Parenteses p3 = new Parenteses();
    Thread n1 = new Thread(new MinhaThread(p3, "Bob"));
    Thread n2 = new Thread(new MinhaThread(p3, "Maria"));
    n1.start(); n2.start();
    try {
      n1.join();
      n2.join();
    } catch(InterruptedException ie) {
      System.out.println("Interrupted");
    } 
  }
}
