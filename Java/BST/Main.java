import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    BST b = new BST(new Node(0));
    int N = in.nextInt();
    Thread[] t = new Thread[N];

    for(int i = 0; i < N; i++) {
      t[i] = new Thread(new MyRunnable(b, N), ""+i);
      t[i].start();
    }

    for(int i = 0; i < N; i++) {
      try {
        t[i].join();
      } catch (InterruptedException ie) {
        ie.printStackTrace();
      }
    }

    System.exit(0);
  }
}

class MyRunnable implements Runnable {

  private BST b;
  private int N;

  public MyRunnable(BST b, int val) {
    this.b = b;
    this.N = val;
  }

  public void run() {
    Random rand = new Random();
    int value;
    for(int i = 0; i < 20000 / N; i++) {
      value = rand.nextInt(100);
      this.b.insert(this.b.getRoot(), new Node(value));
    }
  }
}

class BST {

  private Node root;

  public BST(Node n) {
    this.root = n;
  }

  void insert(Node cur, Node n) {
    if (n.getValue() < cur.getValue()) {
      if (cur.getLeft() == null) {
        cur.setLeft(new Node(n.getValue()));
      } else {
        insert(cur.getLeft(), n);
      }
    } else {
      if (cur.getRight() == null) {
        cur.setRight(new Node(n.getValue()));
      } else {
        insert(cur.getRight(), n);
      }
    }
  }

  public Node getRoot() {
    return this.root;
  }

  public void print(Node n) {
      if (n != null) {
        System.out.println(n.getValue());
        print(n.getLeft());
        print(n.getRight());
      }
  }
}

class Node {

  private int value;
  private Node left;
  private Node right;

  public Node(int value) {
    this.value = value;
    this.left = null;
    this.right = null;
  }

  public void setValue(int val) {
    this.value = val;
  }

  public int getValue() {
    return this.value;
  }

  public void setLeft(Node n) {
    this.left = n;
  }

  public Node getLeft() {
    return this.left;
  }

  public void setRight(Node n) {
    this.right = n;
  }

  public Node getRight() {
    return this.right;
  }
}
