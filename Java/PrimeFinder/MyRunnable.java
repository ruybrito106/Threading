package PrimeFinder;
import PrimeFinder.Counter;

public class MyRunnable implements Runnable {
	
	private Counter cnt;
	private int lim;
	
	public MyRunnable(Counter counter, int lim) {
		this.cnt = counter;
		this.lim = lim;
	}
	
	public void run() {
		int prime = 0;
		while (prime < lim) {
			prime = this.cnt.move(); 
			if (prime != -1 && prime < lim) System.out.println(prime + " " + Thread.currentThread().getName());
		}
		
	}
}

